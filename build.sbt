import Dependencies._
import DependencyOverrides._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "uk.gov.ons",
      scalaVersion := "2.12.7",
      licenses := Seq("MIT License" -> url("http://opensource.org/licenses/MIT")),
      homepage := Some(url("https://github.com/ONSdigital/br-api-test-common")),
      scmInfo := Some(ScmInfo(url("https://github.com/ONSdigital/br-api-test-common"), "scm:git:git@github.com:ONSDigital/br-api-test-common.git")),
      developers := List(Developer("awharris", "Adrian Harris", "adrian.harris@ons.gov.uk", url("https://github.com/awharris")), Developer("nigelhp", "Nigel Perkins", "nigel.perkins@ext.ons.gov.uk", url("https://github.com/nigelhp"))),
      // These are the sbt-release-early settings to configure
      pgpPublicRing := file("/keys/.gnupg/local.pubring.asc"),
      pgpSecretRing := file("/keys/.gnupg/local.secring.asc"),
      releaseEarlyWith := BintrayPublisher,
      releaseEarlyEnableSyncToMaven := false
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
