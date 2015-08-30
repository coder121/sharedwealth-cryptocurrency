name := "shared-wealth"

organization := "com.siddiq"

version := "0.1.1-SNAPSHOT"

lazy val commonSettings = Seq(
  organization := "com.insnapinc",
  version := "0.1.0",
  scalaVersion := "2.11.4"
)


// project description
description := "Masters Project"

// Enables publishing to maven repo
publishMavenStyle := true

// Do not append Scala versions to the generated artifacts
crossPaths := false

// This forbids including Scala related libraries into the dependency
autoScalaLibrary := false

// library dependencies. (orginization name) % (project name) % (version)
libraryDependencies ++= Seq(
   "org.apache.commons" % "commons-math3" % "3.1.1",
   "org.fluentd" % "fluent-logger" % "0.2.10",
   "org.mockito" % "mockito-core" % "1.9.5" % "test",  // Test-only dependency
   "com.novocode" % "junit-interface" % "0.10" % "test"
)





