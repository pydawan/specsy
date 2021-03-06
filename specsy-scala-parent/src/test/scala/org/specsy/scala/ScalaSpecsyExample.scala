// Copyright © 2010-2012, Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0

package org.specsy.scala

import org.specsy.GlobalSpy

class ScalaSpecsyExample extends ScalaSpecsy {
  var counter = 0

  // Conservative syntax
  spec("name of a spec") {
    GlobalSpy.add("spec executed")
  }

  // Shorthand syntax, using some Scala magic
  "defer blocks" >> {
    defer {
      GlobalSpy.add("defer 1") // happens last
    }
    defer {
      GlobalSpy.add("defer 2") // happens first
    }
  }

  "isolated" >> {
    "mutation 1" >> {
      counter += 1
    }
    "mutation 2" >> {
      counter += 1
    }
    GlobalSpy.add("isolated: " + counter) // expecting 1
  }

  "non-isolated" >> {
    shareSideEffects()
    "mutation 1" >> {
      counter += 1
    }
    "mutation 2" >> {
      counter += 1
    }
    GlobalSpy.add("non-isolated: " + counter) // expecting 2
  }
}
