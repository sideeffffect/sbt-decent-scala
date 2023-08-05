import sbt._

object Dependencies {

  object Versions {

    val betterMonadicFor = "0.3.1"
    val kindProjector = "0.13.2"
    val missinglink = "0.2.9"
    val organizeImports = "0.6.0"
    val sbtBuildinfo = "0.11.0"
    val sbtDynver = "4.1.1"
    val sbtMissinglink = "0.3.5"
    val sbtRewarn = "0.1.3"
    val sbtScalafmt = "2.5.0"
    val sbtScalafix = "0.10.4"
    val sbtTpolecat = "0.5.0"
    val sbtVersionPolicy = "2.1.3"
    val scaluzzi = "0.1.23"
    val silencer = "1.17.13"
    val zerowaste = "0.2.11"

  }

  val betterMonadicFor = "com.olegpy" %% "better-monadic-for" % Versions.betterMonadicFor
  val kindProjector = "org.typelevel" %% "kind-projector" % Versions.kindProjector cross CrossVersion.full
  val missinglink = "com.spotify" % "missinglink-core" % Versions.missinglink
  val organizeImports = "com.github.liancheng" %% "organize-imports" % Versions.organizeImports
  val sbtBuildinfo = "com.eed3si9n" % "sbt-buildinfo" % Versions.sbtBuildinfo
  val sbtDynver = "com.dwijnand" % "sbt-dynver" % Versions.sbtDynver
  val sbtMissinglink = "ch.epfl.scala" % "sbt-missinglink" % Versions.sbtMissinglink
  val sbtRewarn = "com.timushev.sbt" % "sbt-rewarn" % Versions.sbtRewarn
  val sbtScalafmt = "org.scalameta" % "sbt-scalafmt" % Versions.sbtScalafmt
  val sbtScalafix = "ch.epfl.scala" % "sbt-scalafix" % Versions.sbtScalafix
  val sbtTpolecat = "org.typelevel" % "sbt-tpolecat" % Versions.sbtTpolecat
  val sbtVersionPolicy = "ch.epfl.scala" % "sbt-version-policy" % Versions.sbtVersionPolicy
  val scaluzzi = "com.github.vovapolu" %% "scaluzzi" % Versions.scaluzzi
  val silencer = "com.github.ghik" % "silencer-plugin" % Versions.silencer cross CrossVersion.full
  val silencerLib = "com.github.ghik" % "silencer-lib" % Versions.silencer % Provided cross CrossVersion.full
  val zerowaste = "com.github.ghik" % "zerowaste" % Versions.zerowaste cross CrossVersion.full

}
