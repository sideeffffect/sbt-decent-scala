val Dependencies = new {
  val Versions = new {
    val sbtCiRelease = "1.5.3"
    val sbtMima = "0.8.0"
    val sbtMissinglink = "0.3.1"
    val sbtRewarn = "0.1.1"
    val sbtScalafmt = "2.4.2"
    val sbtScalafix = "0.9.20"
    val sbtTpolecat = "0.1.13"
  }
  val sbtCiRelease = "com.geirsson" % "sbt-ci-release" % Versions.sbtCiRelease
  val sbtMima = "com.typesafe" % "sbt-mima-plugin" % Versions.sbtMima
  val sbtMissinglink = "ch.epfl.scala" % "sbt-missinglink" % Versions.sbtMissinglink
  val sbtRewarn = "com.timushev.sbt" % "sbt-rewarn" % Versions.sbtRewarn
  val sbtScalafmt = "org.scalameta" % "sbt-scalafmt" % Versions.sbtScalafmt
  val sbtScalafix = "ch.epfl.scala" % "sbt-scalafix" % Versions.sbtScalafix
  val sbtTpolecat = "io.github.davidgregory084" % "sbt-tpolecat" % Versions.sbtTpolecat
}

addSbtPlugin(Dependencies.sbtCiRelease)
addSbtPlugin(Dependencies.sbtMima)
addSbtPlugin(Dependencies.sbtMissinglink)
addSbtPlugin(Dependencies.sbtRewarn)
addSbtPlugin(Dependencies.sbtScalafmt)
addSbtPlugin(Dependencies.sbtScalafix)
addSbtPlugin(Dependencies.sbtTpolecat)
