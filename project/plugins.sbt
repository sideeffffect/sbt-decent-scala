val Dependencies = new {
  val Versions = new {
    val missinglink = "0.2.9"
    val sbtBuildinfo = "0.11.0"
    val sbtCiRelease = "1.5.12"
    val sbtMissinglink = "0.3.6"
    val sbtRewarn = "0.1.3"
    val sbtScalafmt = "2.5.2"
    val sbtScalafix = "0.11.1"
    val sbtTpolecat = "0.4.4"
    val sbtVersionPolicy = "2.1.3"
  }
  val missinglink = "com.spotify" % "missinglink-core" % Versions.missinglink
  val sbtBuildinfo = "com.eed3si9n" % "sbt-buildinfo" % Versions.sbtBuildinfo
  val sbtCiRelease = "com.github.sbt" % "sbt-ci-release" % Versions.sbtCiRelease
  val sbtMissinglink = "ch.epfl.scala" % "sbt-missinglink" % Versions.sbtMissinglink
  val sbtRewarn = "com.timushev.sbt" % "sbt-rewarn" % Versions.sbtRewarn
  val sbtScalafmt = "org.scalameta" % "sbt-scalafmt" % Versions.sbtScalafmt
  val sbtScalafix = "ch.epfl.scala" % "sbt-scalafix" % Versions.sbtScalafix
  val sbtTpolecat = "io.github.davidgregory084" % "sbt-tpolecat" % Versions.sbtTpolecat
  val sbtVersionPolicy = "ch.epfl.scala" % "sbt-version-policy" % Versions.sbtVersionPolicy
}

addSbtPlugin(Dependencies.sbtBuildinfo)
addSbtPlugin(Dependencies.sbtCiRelease)
addSbtPlugin(Dependencies.sbtMissinglink)
addSbtPlugin(Dependencies.sbtRewarn)
addSbtPlugin(Dependencies.sbtScalafmt)
addSbtPlugin(Dependencies.sbtScalafix)
addSbtPlugin(Dependencies.sbtTpolecat)
addSbtPlugin(Dependencies.sbtVersionPolicy)

libraryDependencies ++= List(
  Dependencies.missinglink,
)
