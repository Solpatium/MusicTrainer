enablePlugins(ScalaJSPlugin)
enablePlugins(Angulate2Plugin)

libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.1"

resolvers += Resolver.sonatypeRepo("snapshots")

name := "music-trainer"
scalaVersion := "2.11.8" // or any other Scala version >= 2.10.2

ngBootstrap := Some("music_trainer.webapp.AppModule")
