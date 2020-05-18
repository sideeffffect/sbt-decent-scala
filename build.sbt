Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / turbo := true
ThisBuild / scalaVersion := "2.12.11"

lazy val sbtDecentScala = project
  .in(file("."))
  .settings(commonSettings)
  .settings(
    name := "sbt-decent-scala",
    sbtPlugin := true,
    addSbtPlugin(Dependencies.sbtMissinglink),
    addSbtPlugin(Dependencies.sbtScalafmt),
    addSbtPlugin(Dependencies.sbtScalafix),
    addSbtPlugin(Dependencies.sbtTpolecat),
    dynverSonatypeSnapshots := false,
  )

lazy val commonSettings: List[Def.Setting[_]] = List(
  libraryDependencies ++= List(
    compilerPlugin(Dependencies.betterMonadicFor),
    compilerPlugin(Dependencies.kindProjector),
    compilerPlugin(Dependencies.silencer),
    Dependencies.silencerLib,
  ),
  semanticdbEnabled := true, // enable SemanticDB
  semanticdbVersion := scalafixSemanticdb.revision, // use Scalafix compatible version
  ThisBuild / scalafixDependencies ++= List(
    Dependencies.organizeImports,
    Dependencies.scaluzzi,
  ),
  scalacOptions ++= Seq(
    "-P:silencer:checkUnused",
  ),
  organization := "com.github.sideeffffect",
  homepage := Some(url("https://github.com/sideeffffect/sbt-decent-scala")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "sideeffffect",
      "Ondřej Pelech",
      "ondra.pelech@gmail.com",
      url("https://github.com/sideeffffect"),
    ),
  ),
  missinglinkExcludedDependencies ++= List(
    moduleFilter(organization = "org.apache.logging.log4j", name = "log4j-core"),
    moduleFilter(organization = "org.apache.logging.log4j", name = "log4j-slf4j-impl"),
    moduleFilter(organization = "com.squareup.okhttp3", name = "okhttp"),
  ),
  commands += Command.command("ci-release-cont") { currentState =>
    println(
      s"Running ci-release-cont.\n" +
        s"  branch=${CiReleasePlugin.currentBranch}",
    )
    CiReleasePlugin.setupGpg()
    // https://github.com/olafurpg/sbt-ci-release/issues/64
    val reloadKeyFiles =
      "; set pgpSecretRing := pgpSecretRing.value; set pgpPublicRing := pgpPublicRing.value"
    reloadKeyFiles ::
      sys.env.getOrElse("CI_SNAPSHOT_RELEASE", "+publish") ::
      currentState
  },
  version := "0.3.1+12-00000000",
)

addCommandAlias(
  "ci",
  "; check; publishLocal",
)

addCommandAlias(
  "check",
  "; lint; missinglinkCheck; test",
)

addCommandAlias(
  "lint",
  "; scalafmtSbtCheck; scalafmtCheckAll; compile:scalafix --check; test:scalafix --check",
)
addCommandAlias(
  "fix",
  "; compile:scalafix; test:scalafix; scalafmtSbt; scalafmtAll",
)
