import sbt._

object Dependencies {

  object Versions {

    val betterMonadicFor = "0.3.1"
    val kindProjector = "0.13.3"
    val missinglink = "0.2.11"
    val sbtBuildinfo = "0.12.0"
    val sbtDynver = "5.0.1"
    val sbtMissinglink = "0.3.6"
    val sbtRewarn = "0.1.3"
    val sbtScalafmt = "2.5.2"
    val sbtScalafix = "0.12.0"
    val sbtTpolecat = "0.5.2"
    val sbtVersionPolicy = "3.2.1"
    val scaluzzi = "0.1.23"
    val zerowaste = "0.2.24"

  }

  val betterMonadicFor = "com.olegpy" %% "better-monadic-for" % Versions.betterMonadicFor
  val kindProjector = "org.typelevel" %% "kind-projector" % Versions.kindProjector cross CrossVersion.full
  val missinglink = "com.spotify" % "missinglink-core" % Versions.missinglink
  val sbtBuildinfo = "com.eed3si9n" % "sbt-buildinfo" % Versions.sbtBuildinfo
  val sbtDynver = "com.github.sbt" % "sbt-dynver" % Versions.sbtDynver
  val sbtMissinglink = "ch.epfl.scala" % "sbt-missinglink" % Versions.sbtMissinglink
  val sbtRewarn = "com.timushev.sbt" % "sbt-rewarn" % Versions.sbtRewarn
  val sbtScalafmt = "org.scalameta" % "sbt-scalafmt" % Versions.sbtScalafmt
  val sbtScalafix = "ch.epfl.scala" % "sbt-scalafix" % Versions.sbtScalafix
  val sbtTpolecat = "org.typelevel" % "sbt-tpolecat" % Versions.sbtTpolecat
  val sbtVersionPolicy = "ch.epfl.scala" % "sbt-version-policy" % Versions.sbtVersionPolicy
  val scaluzzi = "com.github.vovapolu" %% "scaluzzi" % Versions.scaluzzi
  val zerowaste = "com.github.ghik" % "zerowaste" % Versions.zerowaste cross CrossVersion.full

}
