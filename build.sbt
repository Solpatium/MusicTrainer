enablePlugins(ScalaJSPlugin)
libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.1"
libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.0" % "test"
libraryDependencies += "org.scalamock" %%% "scalamock-scalatest-support" % "3.5.0" % Test


name := "Music Trainer"
scalaVersion := "2.12.2" // or any other Scala version >= 2.10.2

// This is an application with a main method
scalaJSUseMainModuleInitializer := true
