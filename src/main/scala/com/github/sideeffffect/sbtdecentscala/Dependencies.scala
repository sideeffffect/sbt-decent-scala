package com.github.sideeffffect.sbtdecentscala

import sbt._

object Dependencies {

  object Versions {

    val betterMonadicFor: String = "0.3.1"
    val kindProjector: String = "0.10.3"
    val organizeImports: String = "0.4.3"
    val scaluzzi: String = "0.1.15"
    val silencer: String = "1.7.1"

  }

  val betterMonadicFor: ModuleID = "com.olegpy" %% "better-monadic-for" % Versions.betterMonadicFor
  val kindProjector: ModuleID = "org.typelevel" %% "kind-projector" % Versions.kindProjector
  val organizeImports: ModuleID = "com.github.liancheng" %% "organize-imports" % Versions.organizeImports
  val scaluzzi: ModuleID = "com.github.vovapolu" %% "scaluzzi" % Versions.scaluzzi
  val silencer: ModuleID = "com.github.ghik" % "silencer-plugin" % Versions.silencer cross CrossVersion.full
  val silencerLib: ModuleID =
    "com.github.ghik" % "silencer-lib" % Versions.silencer % Provided cross CrossVersion.full

}
