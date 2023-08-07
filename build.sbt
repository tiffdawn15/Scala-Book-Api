import smithy4s.codegen.Smithy4sCodegenPlugin

val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    name := "bookApi",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    // TODO: Create def for the versions
    // def http4sVersion = "0.10.0"
    // def circeVersion = "0.14.15"

    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-server" % "0.23.23",  
      "org.http4s" %% "http4s-ember-client" % "0.23.23",
      "com.disneystreaming.smithy4s" %% "smithy4s-core" % smithy4sVersion.value,
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s-swagger" % smithy4sVersion.value,

      "org.http4s" %% "http4s-circe" % "0.23.18", 
      "io.circe" %% "circe-core" %  "0.14.1",
      "io.circe" %% "circe-generic" %  "0.14.1",
      "io.circe" %% "circe-jawn" %  "0.14.1",

      "io.github.kirill5k::mongo4cats-core:0.6.8",
      "io.github.kirill5k::mongo4cats-circe:0.6.8",

      "com.tersesystems.blindsight" %% "blindsight-logstash" % "1.5.2",

      "org.typelevel" %% "cats-effect" % "3.4.4",
      // "org.mongodb.scala" %% "mongo-scala-driver" % "4.10.0",
    )
  )
