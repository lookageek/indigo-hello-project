import indigoplugin._

lazy val mygame =
  (project in file("."))
    .enablePlugins(ScalaJSPlugin, SbtIndigo) // Enable the Scala.js and Indigo plugins
    .settings( // Standard SBT settings
      name := "indigo-101",
      version := "0.0.1",
      scalaVersion := "3.3.1",
      organization := "org.mygame"
    )
    .settings( // Indigo specific settings
      indigoOptions :=
        IndigoOptions.defaults
          .withTitle("My Game")
          .withWindowSize(720, 480),
      libraryDependencies ++= Seq(
        "io.indigoengine" %%% "indigo" % "0.15.2",
        "io.indigoengine" %%% "indigo-extras" % "0.15.2",
        "io.indigoengine" %%% "indigo-json-circe" % "0.15.2",
      )
    )


addCommandAlias("buildGame", ";compile;fastOptJS;indigoBuild")
addCommandAlias("runGame", ";compile;fastOptJS;indigoRun")