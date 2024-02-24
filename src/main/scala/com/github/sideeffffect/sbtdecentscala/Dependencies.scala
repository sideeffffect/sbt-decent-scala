package com.github.sideeffffect.sbtdecentscala

import sbt.*

object Dependencies {

  object Versions {

    val betterMonadicFor: String = "0.3.1"
    val kindProjector: String = "0.13.3"
    val scaluzzi: String = "0.1.23"
    val zerowaste = "0.2.16"

  }

  val betterMonadicFor: ModuleID = "com.olegpy" %% "better-monadic-for" % Versions.betterMonadicFor
  val kindProjector: ModuleID =
    "org.typelevel" %% "kind-projector" % Versions.kindProjector cross CrossVersion.full
  val scaluzzi: ModuleID = "com.github.vovapolu" %% "scaluzzi" % Versions.scaluzzi
  val zerowaste: ModuleID = "com.github.ghik" % "zerowaste" % Versions.zerowaste cross CrossVersion.full

}
