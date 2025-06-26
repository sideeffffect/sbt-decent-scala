package com.github.sideeffffect.sbtdecentscala

import ch.epfl.scala.sbtmissinglink.MissingLinkPlugin
import com.timushev.sbt.rewarn.RewarnPlugin
import org.scalafmt.sbt.ScalafmtPlugin
import org.typelevel.sbt.tpolecat.TpolecatPlugin
import sbt.Keys._
import sbt._
import sbtbuildinfo.BuildInfoKey
import sbtbuildinfo.BuildInfoKeys._
import sbtdynver.DynVerPlugin
import sbtversionpolicy.SbtVersionPolicyPlugin
import sbtversionpolicy.SbtVersionPolicyPlugin.autoImport._
import scalafix.sbt.ScalafixPlugin
import scalafix.sbt.ScalafixPlugin.autoImport._

object DecentScalaPlugin extends AutoPlugin {

  override def requires: Plugins =
    DynVerPlugin && MissingLinkPlugin && RewarnPlugin && SbtVersionPolicyPlugin && ScalafixPlugin && ScalafmtPlugin && TpolecatPlugin

  override def trigger: PluginTrigger = allRequirements

  object autoImport {

    object DecentScala extends DecentScala

  }

  trait DecentScala {
    def decentScalaVersion3 = "3.3.6"
    def decentScalaVersion213 = "2.13.16"
    def decentScalaVersion212 = "2.12.20" // scala-steward:off
    def decentScalaVersion211 = "2.11.12"
    def decentScalaSettings: List[Def.Setting[_]] =
      List(
        scalaVersion := decentScalaVersion213,
        crossScalaVersions := List(
          decentScalaVersion3,
          decentScalaVersion213,
          decentScalaVersion212,
        ),
        libraryDependencies ++= {
          CrossVersion.partialVersion(scalaVersion.value) match {
            case Some((2, 11)) =>
              List(
                compilerPlugin(Dependencies.betterMonadicFor),
                compilerPlugin(Dependencies.kindProjector),
              )
            case Some((2, _)) =>
              List(
                compilerPlugin(Dependencies.betterMonadicFor),
                compilerPlugin(Dependencies.kindProjector),
                compilerPlugin(Dependencies.zerowaste),
              )
            case _ =>
              List()
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
        ThisBuild / scalafixDependencies ++= List(
          Dependencies.scaluzzi,
        ),
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
        scalacOptions ++= {
          CrossVersion.partialVersion(scalaVersion.value) match {
            case Some((3, _)) =>
              List(
                "-Xmax-inlines:1024",
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
        version ~= (_.replace('+', '-')),
        ThisBuild / versionPolicyIntention := Compatibility.BinaryCompatible,
        ThisBuild / versionPolicyIgnoredInternalDependencyVersions := Some("^\\d+\\.\\d+\\.\\d+\\+\\d+".r),
      ) ++
        addCommandAlias("check", "; lint; +test; +missinglinkCheck; +versionPolicyCheck") ++
        addCommandAlias(
          "lint",
          "; scalafmtSbtCheck; scalafmtCheckAll; scalafixAll --check",
        ) ++
        addCommandAlias("fix", "; scalafixAll; scalafmtSbt; scalafmtAll")
  }
}
