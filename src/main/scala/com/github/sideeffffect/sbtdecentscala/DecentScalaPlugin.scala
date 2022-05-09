package com.github.sideeffffect.sbtdecentscala

import ch.epfl.scala.sbtmissinglink.MissingLinkPlugin
import com.timushev.sbt.rewarn.RewarnPlugin
import com.typesafe.tools.mima.plugin.MimaPlugin
import com.typesafe.tools.mima.plugin.MimaPlugin.autoImport._
import io.github.davidgregory084.TpolecatPlugin
import org.scalafmt.sbt.ScalafmtPlugin
import sbt.Keys._
import sbt._
import sbtbuildinfo.BuildInfoKey
import sbtbuildinfo.BuildInfoKeys._
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
    def decentScalaVersion3 = "3.1.2"
    def decentScalaVersion213 = "2.13.8"
    def decentScalaVersion212 = "2.12.15"
    def decentScalaVersion211 = "2.11.12"
    def decentScalaSettings: List[Def.Setting[_]] =
      List(
        scalaVersion := decentScalaVersion213,
        crossScalaVersions := List(
          decentScalaVersion3,
          decentScalaVersion213,
          decentScalaVersion212,
          decentScalaVersion211,
        ),
        libraryDependencies ++= {
          CrossVersion.partialVersion(scalaVersion.value) match {
            case Some((2, _)) =>
              List(
                compilerPlugin(Dependencies.betterMonadicFor),
                compilerPlugin(Dependencies.kindProjector),
                compilerPlugin(Dependencies.silencer),
                Dependencies.silencerLib,
              )
            case _ =>
              List(
                "com.github.ghik" % s"silencer-lib_$decentScalaVersion213" % Dependencies.Versions.silencer % Provided,
              )
          }
        },
        semanticdbEnabled := {
          CrossVersion.partialVersion(scalaVersion.value) match {
            case Some((2, _)) => true
            case _            => false
          }
        },
        semanticdbOptions += "-P:semanticdb:synthetics:on",
        semanticdbVersion := scalafixSemanticdb.revision, // use Scalafix compatible version
        ThisBuild / scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value),
        ThisBuild / scalafixDependencies ++= List(
          Dependencies.organizeImports,
          Dependencies.scaluzzi,
        ),
        scalacOptions ++= {
          CrossVersion.partialVersion(scalaVersion.value) match {
            case Some((2, _)) =>
              List(
                "-P:silencer:checkUnused",
              )
            case _ =>
              List()
          }
        },
        scalacOptions ++= {
          CrossVersion.partialVersion(scalaVersion.value) match {
            case Some((2, 12)) | Some((2, 13)) =>
              List(
                "-Ywarn-macros:after",
                "-Xsource:3",
              )
            case _ =>
              List()
          }
        },
        scalacOptions --= {
          if (!sys.env.contains("CI"))
            List("-Xfatal-warnings") // to enable Scalafix
          else
            List()
        },
        buildInfoKeys := List[BuildInfoKey](organization, moduleName, version),
        buildInfoPackage := s"${organization.value}.${moduleName.value}".replace("-", "."),
        Compile / packageBin / packageOptions += Package.ManifestAttributes(
          "Automatic-Module-Name" -> s"${organization.value}.${moduleName.value}".replace("-", "."),
        ),
        mimaPreviousArtifacts := previousStableVersion.value
          .map(organization.value %% moduleName.value % _)
          .toList
          .toSet,
      ) ++
        addCommandAlias("check", "; lint; +missinglinkCheck; +mimaReportBinaryIssues; +test") ++
        addCommandAlias(
          "lint",
          "; scalafmtSbtCheck; scalafmtCheckAll; Compile/scalafix --check; Test/scalafix --check",
        ) ++
        addCommandAlias("fix", "; Compile/scalafix; Test/scalafix; scalafmtSbt; scalafmtAll")
  }
}
