ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.10"

lazy val AkkaVersion = "2.7.0"
lazy val AkkaHttpVersion = "10.5.0"
lazy val AlpakkaSlickVersion = "5.0.0"
lazy val LogbackVersion = "1.4.5"
lazy val PostgresSQLVersion = "42.5.4"
lazy val SlickVersion = "3.4.1"
lazy val AkkaHttpCorsVersion = "1.1.3"
lazy val ScalaTestVersion = "3.2.15"
lazy val ScalaMockVersion = "5.2.0"
lazy val TestContainersVersion = "0.40.12"
lazy val PureConfigVersion = "0.17.2"
lazy val FlywayVersion = "9.15.0"

lazy val root = (project in file("."))
  .configs(IntegrationTest)
  .settings(
    name := "akka-http-slick",
    idePackagePrefix := Some("com.mucciolo"),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
      "com.lightbend.akka" %% "akka-stream-alpakka-slick" % AlpakkaSlickVersion,
      "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
      "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
      "ch.megard" %% "akka-http-cors" % AkkaHttpCorsVersion,

      "ch.qos.logback" % "logback-classic" % LogbackVersion,

      "org.postgresql" % "postgresql" % PostgresSQLVersion,

      "org.flywaydb" % "flyway-core" % FlywayVersion,

      "com.github.pureconfig" %% "pureconfig" % PureConfigVersion,

      "org.scalatest" %% "scalatest" % ScalaTestVersion % "test, it",
      "org.scalamock" %% "scalamock" % ScalaMockVersion % "test",
      "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion % "test",
      "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion % "test",
      "com.dimafeng" %% "testcontainers-scala-scalatest" % TestContainersVersion % "it"
    ),
    Defaults.itSettings,
    Test / testForkedParallel := true,
    Test / fork := true,
    IntegrationTest / testForkedParallel := true,
    IntegrationTest / fork := true,
  )
