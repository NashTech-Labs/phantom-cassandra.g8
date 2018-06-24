// give the user a nice default project!
lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.11.12"
    )),
    name := "phantom-cassandra",
    libraryDependencies ++= Seq(
      "com.outworkers" %% "phantom-dsl" % "2.7.6",
      "com.outworkers" %% "phantom-connectors" % "2.7.6",
      "org.scalatest" %% "scalatest" % "3.0.5" % "test",
      "org.cassandraunit" % "cassandra-unit" % "3.1.3.2"
    )
  )
