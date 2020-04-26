package com.github.sideeffffect.sbtdecentscala

import sbt._

object Dependencies {

  object Versions {

    val betterMonadicFor: String = "0.3.1"
    val kindProjector: String = "0.10.3"
    val organizeImports: String = "0.2.1"
    val sbtScalafmt: String = "2.3.4"
    val sbtScalafix: String = "0.9.15"
    val sbtTpolecat: String = "0.1.11"
    val scaluzzi: String = "0.1.6"
    val silencer: String = "1.6.0"

  }

  val betterMonadicFor: ModuleID = "com.olegpy" %% "better-monadic-for" % Versions.betterMonadicFor
  val kindProjector: ModuleID = "org.typelevel" %% "kind-projector" % Versions.kindProjector
  val organizeImports: ModuleID = "com.github.liancheng" %% "organize-imports" % Versions.organizeImports
  val sbtScalafmt: ModuleID = "org.scalameta" % "sbt-scalafmt" % Versions.sbtScalafmt
  val sbtScalafix: ModuleID = "ch.epfl.scala" % "sbt-scalafix" % Versions.sbtScalafix
  val sbtTpolecat: ModuleID = "io.github.davidgregory084" % "sbt-tpolecat" % Versions.sbtTpolecat
  val scaluzzi: ModuleID = "com.github.vovapolu" %% "scaluzzi" % Versions.scaluzzi
  val silencer: ModuleID = "com.github.ghik" % "silencer-plugin" % Versions.silencer cross CrossVersion.full
  val silencerLib: ModuleID =
    "com.github.ghik" % "silencer-lib" % Versions.silencer % Provided cross CrossVersion.full

}
