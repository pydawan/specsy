
Release Notes
=============

**Upcoming**

- Experimental JUnit 5 support
- Dropped support for Scala 2.7-2.9

**2.1.1 (2014-07-09)**

- Upgraded to Jumi 0.5.437 (not that it matters to users, because the older versions are compatible)

**2.1.0 (2012-12-25)**

- Upgraded to Jumi 0.2.235
- The `@RunVia` annotation is now inherited from the base class, so each test class doesn't need to repeat it. Due to this the `ScalaSpecsy` base class is now an abstract class instead of a trait

**2.0.0 (2012-09-30)**

- Runs using the [Jumi](http://jumi.fi/) test runner, fixing all previously known issues (e.g. it now runs tests in parallel and reports test execution in real time)
- Rewrote the core in Java to support multiple programming languages through thin language-specific frontends
- Supports Scala, version 2.7.7 upwards
- Supports Groovy, all versions
- Supports Java, version 7 upwards (lambdas are recommended for more tolerable syntax noise)

**1.2.0 (2011-05-17)**

- Fixed the order of tests in JUnit results
- Added `shareSideEffects()` for a non-isolated execution model
- Added Scaladocs for the methods in `Spec`
- Compiled with Scala 2.9.0 and 2.8.1

**1.1.0 (2011-05-13)**

- Made the `Spec.defer` method public, to allow it to be used from helper classes
- Renamed the implicit `Spec.specify` method for declaring nested specs, to avoid potential name clashes
- Execute child specs in the same order as they are declared (when single-threaded)
- Made output capturing disabled by default. Use the JVM option `-Dspecsy.captureOutput=true` to enable it
- Upgraded to Scala 2.9.0

**1.0.1 (2010-08-29)**

- Fixed Scala's `println()` not being captured, due to `scala.Console` being unaffected by `java.lang.System.setOut()`

**1.0.0 (2010-08-16)**

- Isolated execution model
- Unlimited nested specs
- Defer blocks
- JUnit test runner