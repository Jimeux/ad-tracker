enablePlugins(JavaAppPackaging)

lazy val akkaHttpVersion    = "10.1.1"
lazy val akkaVersion        = "2.5.12"
lazy val json4sVersion      = "3.5.4"
lazy val scalikejdbcVersion = "3.2.3"

inThisBuild(List(
  scalaVersion    := "2.12.6",
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    "-feature",
    "-language:implicitConversions",
    "-language:higherKinds",
    "-language:postfixOps",
    "-Xfatal-warnings"
  ),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-stream"          % akkaVersion,

    "org.json4s"        %% "json4s-native"        % json4sVersion,
    "org.json4s"        %% "json4s-jackson"       % json4sVersion,
    "de.heikoseeberger" %% "akka-http-json4s"     % "1.20.1",

    "org.postgresql"    % "postgresql"            % "42.2.2",
    "ch.qos.logback"    % "logback-classic"       % "1.2.3",

    "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
    "com.typesafe.akka" %% "akka-testkit"         % akkaVersion     % Test,
    "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion     % Test,
    "org.scalatest"     %% "scalatest"            % "3.0.5"         % Test
  )
))

lazy val ads = (project in file("."))
  .enablePlugins(RevolverPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "org.scalikejdbc" %% "scalikejdbc"        % scalikejdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-test"   % scalikejdbcVersion,

      "net.debasishg"   %% "redisclient"        % "3.7",

      "com.zaxxer"      %  "HikariCP"           % "3.1.0",
    )
  )

sources in(Compile, doc) := Seq.empty
publishArtifact in(Compile, packageDoc) := false

Revolver.settings
