import sbt.Defaults.sbtPluginExtra

Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / turbo := true
ThisBuild / scalaVersion := "2.12.20" // scala-steward:off

lazy val sbtDecentScala = project
  .in(file("."))
  .settings(commonSettings)
  .settings(
    name := "sbt-decent-scala",
    sbtPlugin := true,
    addSbtPlugin(Dependencies.sbtBuildinfo),
    addSbtPlugin(Dependencies.sbtDynver),
    addSbtPlugin(Dependencies.sbtMissinglink),
    addSbtPlugin(Dependencies.sbtRewarn),
    addSbtPlugin(Dependencies.sbtScalafmt),
    addSbtPlugin(Dependencies.sbtScalafix),
    addSbtPlugin(Dependencies.sbtTpolecat),
    addSbtPlugin(Dependencies.sbtVersionPolicy),
  )
  .enablePlugins(BuildInfoPlugin)

lazy val commonSettings: List[Def.Setting[_]] = List(
  libraryDependencies ++= List(
    compilerPlugin(Dependencies.betterMonadicFor),
    compilerPlugin(Dependencies.kindProjector),
    compilerPlugin(Dependencies.zerowaste),
    Dependencies.missinglink,
  ),
  semanticdbEnabled := true, // enable SemanticDB
  semanticdbOptions += "-P:semanticdb:synthetics:on",
  semanticdbVersion := scalafixSemanticdb.revision, // use Scalafix compatible version
  ThisBuild / scalafixDependencies ++= List(
    Dependencies.scaluzzi,
  ),
  scalacOptions ++= List(
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
  version ~= (_.replace('+', '-')),
  missinglinkExcludedDependencies ++= List(
    moduleFilter(organization = "com.eed3si9n", name = "shaded-apache-httpclient5"),
    moduleFilter( // fails on Java 8: `Problem: Method not found: java.nio.LongBuffer.position(int)`
      organization = "com.googlecode.javaewah",
      name = "JavaEWAH",
    ),
    moduleFilter(organization = "com.squareup.okhttp3", name = "okhttp"),
    moduleFilter(organization = "com.timushev.sbt", name = "sbt-rewarn"),
    moduleFilter(organization = "net.openhft", name = "zero-allocation-hashing"),
    moduleFilter(organization = "org.apache.logging.log4j", name = "log4j-core"),
    moduleFilter(organization = "org.apache.logging.log4j", name = "log4j-slf4j-impl"),
  ),
  mimaBinaryIssueFilters ++= List(
  ),
  ThisBuild / versionPolicyIntention := Compatibility.None,
)

addCommandAlias(
  "ci",
  "; check; +publishLocal",
)

addCommandAlias(
  "check",
  "; lint; +missinglinkCheck; +versionPolicyCheck; +test",
)

addCommandAlias(
  "lint",
  "; scalafmtSbtCheck; scalafmtCheckAll; scalafixAll --check",
)
addCommandAlias(
  "fix",
  "; scalafixAll; scalafmtSbt; scalafmtAll",
)
