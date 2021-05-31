import sbt._

object Dependencies {

  object Versions {

    val betterMonadicFor = "0.3.1"
    val kindProjector = "0.13.0"
    val organizeImports = "0.5.0"
    val sbtBuildinfo = "0.10.0"
    val sbtDynver = "4.1.1"
    val sbtMima = "0.9.2"
    val sbtMissinglink = "0.3.2"
    val sbtRewarn = "0.1.3"
    val sbtScalafmt = "2.4.2"
    val sbtScalafix = "0.9.29"
    val sbtTpolecat = "0.1.19"
    val scaluzzi = "0.1.20"
    val silencer = "1.7.5"

  }

  val betterMonadicFor = "com.olegpy" %% "better-monadic-for" % Versions.betterMonadicFor
  val kindProjector = "org.typelevel" %% "kind-projector" % Versions.kindProjector cross CrossVersion.full
  val organizeImports = "com.github.liancheng" %% "organize-imports" % Versions.organizeImports
  val sbtBuildinfo = "com.eed3si9n" % "sbt-buildinfo" % Versions.sbtBuildinfo
  val sbtDynver = "com.dwijnand" % "sbt-dynver" % Versions.sbtDynver
  val sbtMima = "com.typesafe" % "sbt-mima-plugin" % Versions.sbtMima
  val sbtMissinglink = "ch.epfl.scala" % "sbt-missinglink" % Versions.sbtMissinglink
  val sbtRewarn = "com.timushev.sbt" % "sbt-rewarn" % Versions.sbtRewarn
  val sbtScalafmt = "org.scalameta" % "sbt-scalafmt" % Versions.sbtScalafmt
  val sbtScalafix = "ch.epfl.scala" % "sbt-scalafix" % Versions.sbtScalafix
  val sbtTpolecat = "io.github.davidgregory084" % "sbt-tpolecat" % Versions.sbtTpolecat
  val scaluzzi = "com.github.vovapolu" %% "scaluzzi" % Versions.scaluzzi
  val silencer = "com.github.ghik" % "silencer-plugin" % Versions.silencer cross CrossVersion.full
  val silencerLib = "com.github.ghik" % "silencer-lib" % Versions.silencer % Provided cross CrossVersion.full

}
