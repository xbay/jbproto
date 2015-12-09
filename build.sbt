lazy val commonSettings = Seq(
  organization := "xbay.github.io",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.11.7"
)

fork := true

resolvers ++= Seq(
  "Twitter" at "http://maven.twttr.com",
  "Typesafe Releases" at "http://dl.bintray.com/typesafe/maven-releases/")

lazy val versions = new {
  val javaparser = "2.1.0"
  val scalaz = "7.1.3"
}

assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if ps.last endsWith "BUILD" => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

mainClass in assembly := Some("io.github.xbay.jbproto.Main")

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "jbproto",
    libraryDependencies ++= Seq(
      "org.scalaz" %% "scalaz-core" % versions.scalaz,
      "com.github.javaparser" %% "javaparser-core" % versions.javaparser
    )
  )
