import sbt.Defaults.sbtPluginExtra

Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / turbo := true
ThisBuild / scalaVersion := "2.12.15" // scala-steward:off

lazy val sbtDecentScala = project
  .in(file("."))
  .settings(commonSettings)
  .settings(
    name := "sbt-decent-scala",
    sbtPlugin := true,
    addSbtPlugin(Dependencies.sbtBuildinfo),
    addSbtPlugin(Dependencies.sbtDynver),
    addSbtPlugin(Dependencies.sbtMima),
    addSbtPlugin(Dependencies.sbtMissinglink),
    addSbtPlugin(Dependencies.sbtRewarn),
    addSbtPlugin(Dependencies.sbtScalafmt),
    addSbtPlugin(Dependencies.sbtScalafix),
    addSbtPlugin(Dependencies.sbtTpolecat),
  )
  .enablePlugins(BuildInfoPlugin)

lazy val commonSettings: List[Def.Setting[_]] = List(
  libraryDependencies ++= List(
    compilerPlugin(Dependencies.betterMonadicFor),
    compilerPlugin(Dependencies.kindProjector),
    compilerPlugin(Dependencies.silencer),
    Dependencies.silencerLib,
  ),
  semanticdbEnabled := true, // enable SemanticDB
  semanticdbOptions += "-P:semanticdb:synthetics:on",
  semanticdbVersion := scalafixSemanticdb.revision, // use Scalafix compatible version
  ThisBuild / scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value),
  ThisBuild / scalafixDependencies ++= List(
    Dependencies.organizeImports,
    Dependencies.scaluzzi,
  ),
  scalacOptions ++= Seq(
    "-P:silencer:checkUnused",
    "-Ywarn-macros:after",
    "-Xsource:3",
  ),
  ThisBuild / dynverSonatypeSnapshots := {
    !git.gitCurrentBranch.value.contains("master")
  },
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
  buildInfoKeys := List[BuildInfoKey](organization, moduleName, version),
  buildInfoPackage := s"${organization.value}.${moduleName.value}".replace("-", "."),
  Compile / packageBin / packageOptions += Package.ManifestAttributes(
    "Automatic-Module-Name" -> s"${organization.value}.${moduleName.value}".replace("-", "."),
  ),
  missinglinkExcludedDependencies ++= List(
    moduleFilter( // fails on Java 8: `Problem: Method not found: java.nio.LongBuffer.position(int)`
      organization = "com.googlecode.javaewah",
      name = "JavaEWAH",
    ),
    moduleFilter(organization = "com.squareup.okhttp3", name = "okhttp"),
    moduleFilter(organization = "com.timushev.sbt", name = "sbt-rewarn"),
    moduleFilter(organization = "org.apache.logging.log4j", name = "log4j-core"),
    moduleFilter(organization = "org.apache.logging.log4j", name = "log4j-slf4j-impl"),
  ),
  mimaPreviousArtifacts := previousStableVersion.value.map { version =>
    sbtPluginExtra(
      organization.value % moduleName.value % version,
      (pluginCrossBuild / sbtBinaryVersion).value,
      (update / scalaBinaryVersion).value,
    )
  }.toSet,
  mimaBinaryIssueFilters ++= List(
  ),
  ciReleaseCont,
)

lazy val ciReleaseCont = {
  commands += Command.command("ci-release-cont") { currentState =>
    if (!CiReleasePlugin.isSecure) {
      println("No access to secret variables, doing nothing")
      currentState
    } else {
      println(
        s"Running ci-release-cont.\n" +
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
  }
}

addCommandAlias(
  "ci",
  "; check; +publishLocal",
)

addCommandAlias(
  "check",
  "; lint; +missinglinkCheck; +mimaReportBinaryIssues; +test",
)

addCommandAlias(
  "lint",
  "; scalafmtSbtCheck; scalafmtCheckAll; Compile/scalafix --check; Test/scalafix --check",
)
addCommandAlias(
  "fix",
  "; Compile/scalafix; Test/scalafix; scalafmtSbt; scalafmtAll",
)
