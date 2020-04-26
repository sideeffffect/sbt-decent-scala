package com.github.sideeffffect.sbtdecentscala

import org.scalafmt.sbt.ScalafmtPlugin
import sbt.Keys._
import sbt._
import scalafix.sbt.ScalafixPlugin
import scalafix.sbt.ScalafixPlugin.autoImport._

import _root_.io.github.davidgregory084.TpolecatPlugin

object DecentScalaPlugin extends AutoPlugin {

  override def requires: Plugins = ScalafixPlugin && ScalafmtPlugin && TpolecatPlugin

  override def trigger: PluginTrigger = allRequirements

  override def projectSettings: List[Def.Setting[_]] =
    List(
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
      scalacOptions ++= List(
        "-P:silencer:checkUnused",
        "-Ywarn-macros:after",
      ),
      scalacOptions --= {
        if (!sys.env.contains("CI"))
          List("-Xfatal-warnings") // to enable Scalafix
        else
          List()
      },
    ) ++
      addCommandAlias(
        "check",
        "; scalafmtSbtCheck; scalafmtCheckAll; compile:scalafix --check; test:scalafix --check",
      ) ++
      addCommandAlias(
        "fix",
        "; compile:scalafix; test:scalafix; scalafmtSbt; scalafmtAll",
      )
}
