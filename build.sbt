ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "checkout-system",
    idePackagePrefix := Some("uk.softar"),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest-flatspec" % "3.2.14" % "test",
      "org.scalatestplus" %% "scalacheck-1-17" % "3.2.14.0" % "test"
    )
  )
