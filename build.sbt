Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / turbo := true

inThisBuild(
  List(
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
  ),
)

ThisBuild / scalaVersion := "2.12.11"
ThisBuild / organization := "com.github.sideeffffect"

lazy val root = project
  .in(file("."))
  .settings(
    name := "root",
    publish / skip := true,
    commands += Command.command("ci-release-cont") { currentState =>
      if (!CiReleasePlugin.isSecure) {
        println("No access to secret variables, doing nothing")
        currentState
      } else {
        println(
          s"Running ci-release.\n" +
            s"  branch=${CiReleasePlugin.currentBranch}",
        )
        CiReleasePlugin.setupGpg()
        // https://github.com/olafurpg/sbt-ci-release/issues/64
        val reloadKeyFiles =
          "; set pgpSecretRing := pgpSecretRing.value; set pgpPublicRing := pgpPublicRing.value"
        if (!CiReleasePlugin.isTag && !git.gitCurrentBranch.value.contains("master")) {
          if (CiReleasePlugin.isSnapshotVersion(currentState)) {
            println(s"No tag push, publishing SNAPSHOT")
            reloadKeyFiles ::
              sys.env.getOrElse("CI_SNAPSHOT_RELEASE", "+publish") ::
              currentState
          } else {
            // Happens when a tag is pushed right after merge causing the master branch
            // job to pick up a non-SNAPSHOT version even if TRAVIS_TAG=false.
            println(
              "Snapshot releases must have -SNAPSHOT version number, doing nothing",
            )
            currentState
          }
        } else {
          println("Tag push detected, publishing a stable release")
          reloadKeyFiles ::
            sys.env.getOrElse("CI_CLEAN", "; clean ; sonatypeBundleClean") ::
            sys.env.getOrElse("CI_RELEASE", "+publishSigned") ::
            sys.env.getOrElse("CI_SONATYPE_RELEASE", "sonatypeBundleRelease") ::
            currentState
        }
      }
    },
  )
  .aggregate(sbtDecentScala)

lazy val sbtDecentScala = project
  .in(file("sbt-decent-scala"))
  .settings(commonSettings)
  .settings(
    name := "sbt-decent-scala",
    sbtPlugin := true,
    addSbtPlugin(Dependencies.sbtScalafmt),
    addSbtPlugin(Dependencies.sbtScalafix),
    addSbtPlugin(Dependencies.sbtTpolecat),
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
  ThisBuild / dynverSonatypeSnapshots := {
    !git.gitCurrentBranch.value.contains("master")
  },
)

addCommandAlias(
  "ci",
  "; check; ci-release-cont",
)

addCommandAlias(
  "check",
  "; scalafmtSbtCheck; scalafmtCheckAll; compile:scalafix --check; test:scalafix --check",
)
addCommandAlias(
  "fix",
  "; compile:scalafix; test:scalafix; scalafmtSbt; scalafmtAll",
)
