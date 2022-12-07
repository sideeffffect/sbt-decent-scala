val Dependencies = new {
  val Versions = new {
    val missinglink = "0.2.6"
    val sbtBuildinfo = "0.11.0"
    val sbtCiRelease = "1.5.11"
    val sbtMissinglink = "0.3.3"
    val sbtRewarn = "0.1.3"
    val sbtScalafmt = "2.5.0"
    val sbtScalafix = "0.10.4"
    val sbtTpolecat = "0.4.1"
    val sbtVersionPolicy = "2.1.0"
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
