import sbt._

object Dependencies {
  private lazy val silencerVersion = "1.3.1"
  
  lazy val play = "com.typesafe.play" %% "play" % "2.6.20"
  lazy val playTest = "com.typesafe.play" %% "play-test" % "2.6.20"
  lazy val playWs = "com.typesafe.play" %% "play-ws" % "2.6.20"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4"
  lazy val scalaTestPlus = "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2"
  lazy val silencerLib = "com.github.ghik" %% "silencer-lib" % silencerVersion
  lazy val silencerPlugin = "com.github.ghik" %% "silencer-plugin" % silencerVersion
  lazy val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.25"
  lazy val wireMock = "com.github.tomakehurst" % "wiremock" % "2.19.0"
}

/*
 * For explicit resolution of transitive dependency conflicts.
 * Favour versions required by Play where possible
 */
object DependencyOverrides {
  lazy val commonsLang = "org.apache.commons" % "commons-lang3" % "3.6"
  lazy val findBugs = "com.google.code.findbugs" % "jsr305" % "3.0.2"
  lazy val guava = "com.google.guava" % "guava" % "22.0"
  lazy val jacksonDatabind = "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.11.2"
  lazy val jodaTime = "joda-time" % "joda-time" % "2.9.9"
  lazy val playJson = "com.typesafe.play" %% "play-json" % "2.6.10"
  lazy val reactiveStreams = "org.reactivestreams" % "reactive-streams" % "1.0.2"
  lazy val scalaParserCombinators = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.6"
  lazy val scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
  lazy val sslConfigCore = "com.typesafe" %% "ssl-config-core" % "0.2.4"

  // we are not using selenium - favour the httpclient & jetty components needed by wiremock
  lazy val httpClient = "org.apache.httpcomponents" % "httpclient" % "4.5.5"
  lazy val jettyHttp = "org.eclipse.jetty" % "jetty-http" % "9.2.24.v20180105"
  lazy val jettyIo = "org.eclipse.jetty" % "jetty-io" % "9.2.24.v20180105"
  lazy val jettyUtil = "org.eclipse.jetty" % "jetty-util" % "9.2.24.v20180105"
}