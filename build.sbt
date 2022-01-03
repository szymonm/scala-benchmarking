val scala3Version = "3.1.0"

enablePlugins(JmhPlugin)

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala-bencharking",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "com.novocode" % "junit-interface" % "0.11" % "test",
      "org.openjdk.jmh" %  "jmh-core" % "1.32",
      "it.unimi.dsi" % "fastutil" % "8.1.0",
      "org.roaringbitmap" % "RoaringBitmap" % "0.7.16",
      "org.apache.commons" % "commons-lang3" % "3.8"
    )
  )
