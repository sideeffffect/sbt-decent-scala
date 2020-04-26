val Dependencies = new {
  val Versions = new {
    val sbtCiRelease = "1.5.3"
    val sbtScalafmt = "2.3.4"
    val sbtScalafix = "0.9.15"
    val sbtTpolecat = "0.1.11"
  }
  val sbtCiRelease = "com.geirsson" % "sbt-ci-release" % Versions.sbtCiRelease
  val sbtScalafmt = "org.scalameta" % "sbt-scalafmt" % Versions.sbtScalafmt
  val sbtScalafix = "ch.epfl.scala" % "sbt-scalafix" % Versions.sbtScalafix
  val sbtTpolecat = "io.github.davidgregory084" % "sbt-tpolecat" % Versions.sbtTpolecat
}

addSbtPlugin(Dependencies.sbtCiRelease)
addSbtPlugin(Dependencies.sbtScalafmt)
addSbtPlugin(Dependencies.sbtScalafix)
addSbtPlugin(Dependencies.sbtTpolecat)
