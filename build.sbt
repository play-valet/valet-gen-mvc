// -----------------------------
lazy val root = (project in file("."))
  .settings(
    name := "valet-gen-mvc",
    version := "0.0.1",
    scalaVersion := "2.11.8",
    libraryDependencies ++= Seq(
      "com.typesafe" % "config" % "1.3.1",
      "commons-io" % "commons-io" % "2.5",
      "org.scalameta" %% "scalameta" % "1.6.0",
      "com.github.tototoshi" %% "slick-joda-mapper" % "2.1.0",
      "org.jsoup" % "jsoup" % "1.10.2",
      //      "net.ruippeixotog" %% "scala-scraper" % "1.2.0",
      "com.typesafe.play" %% "play-slick" % "2.0.2",
      "org.skinny-framework" %% "skinny-framework" % "2.3.5",
      "org.skinny-framework" %% "skinny-task" % "2.3.5",
      "joda-time" % "joda-time" % "2.9.7",
      "org.joda" % "joda-convert" % "1.8.1",
      "org.typelevel" %% "cats" % "0.9.0",
      "org.scalaz" %% "scalaz-core" % "7.2.11",
      "com.github.pathikrit" %% "better-files" % "2.17.1",
      "org.scalikejdbc" % "scalikejdbc-core_2.11" % "2.5.1"
    )
  )

