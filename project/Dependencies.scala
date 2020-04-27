import sbt._

object Dependencies {

  object Versions {

    val betterMonadicFor = "0.3.1"
    val kindProjector = "0.10.3"
    val organizeImports = "0.2.1"
    val sbtMissinglink = "0.3.1"
    val sbtScalafmt = "2.3.4"
    val sbtScalafix = "0.9.15"
    val sbtTpolecat = "0.1.11"
    val scaluzzi = "0.1.6"
    val silencer = "1.6.0"

  }

  val betterMonadicFor = "com.olegpy" %% "better-monadic-for" % Versions.betterMonadicFor
  val kindProjector = "org.typelevel" %% "kind-projector" % Versions.kindProjector
  val organizeImports = "com.github.liancheng" %% "organize-imports" % Versions.organizeImports
  val sbtMissinglink = "ch.epfl.scala" % "sbt-missinglink" % Versions.sbtMissinglink
  val sbtScalafmt = "org.scalameta" % "sbt-scalafmt" % Versions.sbtScalafmt
  val sbtScalafix = "ch.epfl.scala" % "sbt-scalafix" % Versions.sbtScalafix
  val sbtTpolecat = "io.github.davidgregory084" % "sbt-tpolecat" % Versions.sbtTpolecat
  val scaluzzi = "com.github.vovapolu" %% "scaluzzi" % Versions.scaluzzi
  val silencer = "com.github.ghik" % "silencer-plugin" % Versions.silencer cross CrossVersion.full
  val silencerLib = "com.github.ghik" % "silencer-lib" % Versions.silencer % Provided cross CrossVersion.full

}
