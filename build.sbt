import Dependencies._
import DependencyOverrides._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "uk.gov.ons",
      scalaVersion := "2.12.7",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "br-api-test-common",
    scalacOptions ++= Seq(
      "-target:jvm-1.8",
      "-encoding", "utf8",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xcheckinit",
      "-Xlint:_",
      "-Xfatal-warnings",
      "-Yno-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-value-discard",
      "-Ywarn-unused"
    ),
    scapegoatVersion in ThisBuild := "1.3.8",
    conflictManager := ConflictManager.strict,
    libraryDependencies ++= Seq(
      compilerPlugin(silencerPlugin),
      play, 
      playTest,
      playWs,
      scalaTest, 
      scalaTestPlus,
      silencerLib % Provided,
      slf4jApi,
      wireMock),
    dependencyOverrides ++= Seq(
      commonsLang,
      findBugs,
      guava,
      httpClient,
      jacksonDatabind,
      jettyHttp,
      jettyIo,
      jettyUtil,
      jodaTime,
      playJson,
      playTest,
      playWs,
      reactiveStreams,
      scalaParserCombinators,
      scalaXml,
      slf4jApi,
      sslConfigCore
    )
  )
