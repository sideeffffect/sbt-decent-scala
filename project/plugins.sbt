val Dependencies = new {
  val Versions = new {
    val sbtBuildinfo = "0.10.0"
    val sbtCiRelease = "1.5.9"
    val sbtMima = "1.0.1"
    val sbtMissinglink = "0.3.2"
    val sbtRewarn = "0.1.3"
    val sbtScalafmt = "2.4.3"
    val sbtScalafix = "0.9.31"
    val sbtTpolecat = "0.1.20"
  }
  val sbtBuildinfo = "com.eed3si9n" % "sbt-buildinfo" % Versions.sbtBuildinfo
  val sbtCiRelease = "com.github.sbt" % "sbt-ci-release" % Versions.sbtCiRelease
  val sbtMima = "com.typesafe" % "sbt-mima-plugin" % Versions.sbtMima
  val sbtMissinglink = "ch.epfl.scala" % "sbt-missinglink" % Versions.sbtMissinglink
  val sbtRewarn = "com.timushev.sbt" % "sbt-rewarn" % Versions.sbtRewarn
  val sbtScalafmt = "org.scalameta" % "sbt-scalafmt" % Versions.sbtScalafmt
  val sbtScalafix = "ch.epfl.scala" % "sbt-scalafix" % Versions.sbtScalafix
  val sbtTpolecat = "io.github.davidgregory084" % "sbt-tpolecat" % Versions.sbtTpolecat
}

addSbtPlugin(Dependencies.sbtBuildinfo)
addSbtPlugin(Dependencies.sbtCiRelease)
addSbtPlugin(Dependencies.sbtMima)
addSbtPlugin(Dependencies.sbtMissinglink)
addSbtPlugin(Dependencies.sbtRewarn)
addSbtPlugin(Dependencies.sbtScalafmt)
addSbtPlugin(Dependencies.sbtScalafix)
addSbtPlugin(Dependencies.sbtTpolecat)
