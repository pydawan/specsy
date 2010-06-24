package net.orfjackal.specsy.internal

import Context._

object Context {
  abstract sealed class Status
  case object NotStarted extends Status
  case object Running extends Status
  case object Finished extends Status
}

class Context(targetPath: Path) {
  private var status: Status = NotStarted
  private var current: SpecRun = null
  private var executed: SpecRun = null // TODO: make 'executed' a list?
  private var postponed = List[Path]()
  private var _failures = List[(SpecRun, Throwable)]()

  def this() = this (Path.Root)

  def bootstrap(className: String, rootSpec: => Unit) {
    changeStatus(NotStarted, Running)

    enterRootSpec(className)
    processSpec(rootSpec)
    exitSpec()

    changeStatus(Running, Finished)
  }

  private def enterRootSpec(name: String) {
    current = new SpecRun(name, null, Path.Root, targetPath)
  }

  def specify(name: String, body: => Unit) {
    assertStatusIs(Running)

    enterSpec(name)
    processSpec(body)
    exitSpec()
  }

  private def enterSpec(name: String) {
    current = current.addChild(name)
  }

  private def processSpec(body: => Unit) {
    if (current.shouldExecute) {
      // TODO: there is no test that this assignment must be first (otherwise the path will be root's path, and IDEA's test runner will get confused)
      executed = current
      executeSafely(body)
    }
    if (current.shouldPostpone) {
      postponed = current.path :: postponed
    }
  }

  private def executeSafely(body: => Unit) {
    try {
      body
    } catch {
      case e => _failures ::= (current, e)
    }
  }

  private def exitSpec() {
    current = current.parent
  }

  def executedSpec: SpecRun = {
    assertStatusIs(Finished)
    executed
  }

  def executedPath: Path = {
    assertStatusIs(Finished)
    executed.path
  }

  def postponedPaths: List[Path] = {
    assertStatusIs(Finished)
    postponed
  }

  def failures: List[(SpecRun, Throwable)] = {
    assertStatusIs(Finished)
    _failures
  }

  private def changeStatus(from: Status, to: Status) {
    assertStatusIs(from)
    status = to
  }

  private def assertStatusIs(expected: Status) {
    assert(status == expected, "expected status " + expected + ", but was " + status)
  }
}
