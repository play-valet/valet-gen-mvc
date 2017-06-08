package org.valet.generates.default.buildsbt

import java.io.File

import org.valet.common.{ScaffoldDtos, ValetUtility}

import scala.io.Source


object DefBuildsbt extends ValetUtility {

  /**
    * TODO: trickery
    */
  def addLibraryDependencies(dtos: ScaffoldDtos): Unit = {

    val filepath = "./build.sbt"
    makeFileIfNotExist(new File(filepath))
    val source: Seq[String] = Source.fromFile(filepath, "UTF-8").getLines().toSeq
    val formatSource = source.map(str => toSingleSpace(str))

    val libraryDependencies1 = """libraryDependencies +="""
    val libraryDependencies2 = """libraryDependencies ++="""

    val target1 =
      Seq(
        """|      libraryDependencies += filters""".stripMargin,
        """|      libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.40" """.stripMargin,
        """|      libraryDependencies += "com.typesafe.play" %% "play-slick" % "2.0.2"""".stripMargin,
        """|      libraryDependencies += "jp.t2v" %% "play2-auth" % "0.14.2"""".stripMargin,
        """|      libraryDependencies += "jp.t2v" %% "play2-auth-test" % "0.14.2" % "test"""".stripMargin,
        """|      libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.10"""".stripMargin,
        """|      libraryDependencies += "org.springframework.security" % "spring-security-web" % "4.2.2.RELEASE"""".stripMargin
      )

    val target2 =
      Seq(
        """|      filters,""".stripMargin,
        """|      "mysql" % "mysql-connector-java" % "5.1.40",""".stripMargin,
        """|      "com.typesafe.play" %% "play-slick" % "2.0.2",""".stripMargin,
        """|      "jp.t2v" %% "play2-auth" % "0.14.2",""".stripMargin,
        """|      "jp.t2v" %% "play2-auth-test" % "0.14.2" % "test",""".stripMargin,
        """|      "org.scalaz" %% "scalaz-core" % "7.2.10",""".stripMargin,
        """|      "org.springframework.security" % "spring-security-web" % "4.2.2.RELEASE",""".stripMargin
      )

    var tmp = 0
    val content = (for {
      (line, i) <- source.zipWithIndex
    } yield {
      if (line.contains(libraryDependencies1) && tmp == 0 && !isAllContains(target1, source)) {
        tmp = tmp + 1
        target1.mkString("\n") + "\n" + line
      } else if (line.contains(libraryDependencies2) && tmp == 0 && !isAllContains(target2, source)) {
        tmp = tmp + 1
        line + "\n" + target2.mkString("\n")
      } else {
        line
      }
    }).mkString("\n")

    forceWrite(new File(filepath), content)
  }

  def isAllContains(isContains: Seq[String], bigSrc: Seq[String]): Boolean = {
    isContains.forall(line => bigSrc.contains(line))
  }

}
