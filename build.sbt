Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalaVersion := "2.12.11"
ThisBuild / organization := "com.github.sideeffffect"

lazy val root = project
  .in(file("."))
  .settings(
    name := "root",
    publish / skip := true,
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
)

addCommandAlias(
  "ci",
  "; check; ci-release",
)

addCommandAlias(
  "check",
  "; scalafmtSbtCheck; scalafmtCheckAll; compile:scalafix --check; test:scalafix --check",
)
addCommandAlias(
  "fix",
  "; compile:scalafix; test:scalafix; scalafmtSbt; scalafmtAll",
)
