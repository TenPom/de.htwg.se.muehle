// Project name (artifact name in Maven)
name := "muehle"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.11"

// project description
description := "Project for the Lectures SE and SA"

resolvers += "Restlet" at "http://maven.restlet.org/"

//mainClass in (Compile, run) := Some("game.MuehleGame")

lazy val muehle = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)
  .disablePlugins(PlayFilters)

libraryDependencies ++= Seq(
  guice,
  evolutions,
  "org.javassist" % "javassist" % "3.21.0-GA", // dependency for play-enhancer

  //JSON Dependencies
  "com.fasterxml.jackson.core" % "jackson-core" % "2.8.9",
  "com.fasterxml.jackson.core" % "jackson-annotations" % "2.8.9",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.9",

  //Logger dependencies
  "ch.qos.logback" % "logback-core" % "1.2.3",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.slf4j" % "slf4j-api" % "1.7.25",

  //Akka Modules
  "com.typesafe.akka" %% "akka-actor" % "2.5.2",
  //Included because of some random !@*?& see https://github.com/akka/akka-http/issues/821
  "com.typesafe.akka" %% "akka-stream" % "2.5.2",
  "com.typesafe.akka" %% "akka-http" % "10.0.7",

  //Persistence Support
  "org.ektorp" % "org.ektorp" % "1.4.4", //couchDB
  "com.db4o" % "com.db4o" % "7.7.67", //db4o
  "org.hibernate" % "hibernate-core" % "5.2.10.Final", //hibernate

  //Test dependencies
  "com.typesafe.akka" %% "akka-http-testkit" % "10.0.7" % Test,
  "junit" % "junit" % "4.12" % Test

)
playEbeanModels in Compile := Seq("persistence.dto.ebean.*")
