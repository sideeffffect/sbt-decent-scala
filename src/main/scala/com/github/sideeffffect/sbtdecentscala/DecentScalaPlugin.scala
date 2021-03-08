package com.github.sideeffffect.sbtdecentscala

import ch.epfl.scala.sbtmissinglink.MissingLinkPlugin
import com.timushev.sbt.rewarn.RewarnPlugin
import com.typesafe.tools.mima.plugin.MimaPlugin
import com.typesafe.tools.mima.plugin.MimaPlugin.autoImport._
import io.github.davidgregory084.TpolecatPlugin
import org.scalafmt.sbt.ScalafmtPlugin
import sbt.Keys._
import sbt._
import sbtdynver.DynVerPlugin
import sbtdynver.DynVerPlugin.autoImport._
import scalafix.sbt.ScalafixPlugin
import scalafix.sbt.ScalafixPlugin.autoImport._

object DecentScalaPlugin extends AutoPlugin {

  override def requires: Plugins =
    DynVerPlugin && MimaPlugin && MissingLinkPlugin && RewarnPlugin && ScalafixPlugin && ScalafmtPlugin && TpolecatPlugin

  override def trigger: PluginTrigger = allRequirements

  object autoImport {

    object DecentScala extends DecentScala

  }

  trait DecentScala {
    lazy val decentScalaSettings: List[Def.Setting[_]] =
      List(
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
        mimaPreviousArtifacts := previousStableVersion.value
          .map(organization.value %% moduleName.value % _)
          .toList
          .toSet,
      ) ++
        addCommandAlias("check", "; lint; +missinglinkCheck; +mimaReportBinaryIssues; +test") ++
        addCommandAlias(
          "lint",
          "; scalafmtSbtCheck; scalafmtCheckAll; compile:scalafix --check; test:scalafix --check",
        ) ++
        addCommandAlias("fix", "; compile:scalafix; test:scalafix; scalafmtSbt; scalafmtAll")
  }
}
