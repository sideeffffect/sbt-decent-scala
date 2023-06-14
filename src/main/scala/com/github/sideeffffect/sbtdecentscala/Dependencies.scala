package com.github.sideeffffect.sbtdecentscala

import sbt.*

object Dependencies {

  object Versions {

    val betterMonadicFor: String = "0.3.1"
    val kindProjector: String = "0.13.2"
    val organizeImports: String = "0.6.0"
    val scaluzzi: String = "0.1.23"
    val silencer: String = "1.17.13"
    val zerowaste = "0.2.8"

  }

  val betterMonadicFor: ModuleID = "com.olegpy" %% "better-monadic-for" % Versions.betterMonadicFor
  val kindProjector: ModuleID =
    "org.typelevel" %% "kind-projector" % Versions.kindProjector cross CrossVersion.full
  val organizeImports: ModuleID = "com.github.liancheng" %% "organize-imports" % Versions.organizeImports
  val scaluzzi: ModuleID = "com.github.vovapolu" %% "scaluzzi" % Versions.scaluzzi
  val silencer: ModuleID = "com.github.ghik" % "silencer-plugin" % Versions.silencer cross CrossVersion.full
  val silencerLib: ModuleID =
    "com.github.ghik" % "silencer-lib" % Versions.silencer % Provided cross CrossVersion.full
  val zerowaste: ModuleID = "com.github.ghik" % "zerowaste" % Versions.zerowaste cross CrossVersion.full

}
