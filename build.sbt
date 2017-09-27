enablePlugins(ScalaJSPlugin)
enablePlugins(Angulate2Plugin)

libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.1"
libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.0" % "test"
libraryDependencies += "org.scalamock" %%% "scalamock-scalatest-support" % "3.5.0" % Test

resolvers += Resolver.sonatypeRepo("snapshots")

name := "music-trainer"
scalaVersion := "2.11.11" // or any other Scala version >= 2.10.2

ngBootstrap := Some("music_trainer.webapp.AppModule")
