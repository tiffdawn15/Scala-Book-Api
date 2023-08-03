import smithy4s.codegen.Smithy4sCodegenPlugin

val scala3Version = "3.3.0"

lazy val root = project
  .in(file("."))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    name := "bookApi",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    // libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s" % smithy4sVersion.value,
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s-swagger" % smithy4sVersion.value,
      "org.http4s" %% "http4s-ember-server" % "0.23.18"
    )
  )

  


  

// val example = project
//   .in(file("modules/example"))
//   .enablePlugins(Smithy4sCodegenPlugin)
//   .settings(
//     libraryDependencies ++= Seq(
//       "com.disneystreaming.smithy4s" %% "smithy4s-http4s" % smithy4sVersion.value,
//       "com.disneystreaming.smithy4s" %% "smithy4s-http4s-swagger" % smithy4sVersion.value,
//       "org.http4s" %% "http4s-ember-server" % "0.23.18"
//     )
//   )