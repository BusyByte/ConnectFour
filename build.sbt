lazy val root = (project in file("."))
  .settings(
    name         := "connect-four",
    organization := "net.nomadicalien",
    scalaVersion := "2.12.4",
    version      := "0.1.0-SNAPSHOT"
  )
   .settings(
     libraryDependencies ++= Seq(
       "org.specs2" %% "specs2-core" % "4.0.0" % Test
     ),
     scalacOptions in Test ++= Seq("-Yrangepos")
   )
