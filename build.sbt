import smithy4s.codegen.Smithy4sCodegenPlugin

val scala3Version = "2.13.11"

lazy val root = project
  .in(file("."))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    name := "bookApi",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.4.4",
      "org.mongodb.scala" %% "mongo-scala-driver" % "4.10.0",
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "com.disneystreaming.smithy4s" %% "smithy4s-core" % smithy4sVersion.value,
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s" % smithy4sVersion.value,
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s-swagger" % smithy4sVersion.value,
      "org.http4s" %% "http4s-ember-server" % "0.23.23",  
      "org.http4s" %% "http4s-ember-client" % "0.23.23",
    )
  )

  