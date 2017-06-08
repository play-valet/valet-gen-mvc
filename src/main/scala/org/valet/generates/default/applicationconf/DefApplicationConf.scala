package org.valet.generates.default.applicationconf

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}
import org.valet.common.{Loaders, ScaffoldDtos, ValetUtility}


object DefApplicationConf extends ValetUtility {

//  def addCon(dtos: ScaffoldDtos): Unit = {
//
//    val filepath = "./conf/application.conf"
//
//    makeFileIfNotExist(new File(filepath))
//    val conf: Config = ConfigFactory.parseFile(new File(filepath))
//
//    val source: Seq[String] = Source.fromFile(filepath, "UTF-8").getLines().toSeq
//    val applicationConf = Loaders.getKeyListFromHocon(conf)
//    val targetKey = Seq(
//      ("slick.dbs.default.db.driver","""slick.dbs.default.driver = "slick.driver.MySQLDriver$" """)
//      , ("slick.dbs.default.db.password","""slick.dbs.default.db.driver = "com.mysql.jdbc.Driver" """)
//      , ("slick.dbs.default.db.url","""slick.dbs.default.db.url = "jdbc:mysql://127.0.0.1/test?characterEncoding=UTF8&autoReconnect=true&useSSL=false" """)
//      , ("slick.dbs.default.db.user","""slick.dbs.default.db.user = "test" """)
//      , ("slick.dbs.default.driver","""slick.dbs.default.db.password = "test" """)
//    )
//    val content = source.++(targetKey.filter(x => !applicationConf.contains(x._1)).map(_._2)).mkString("\n")
//    writeAppending(new File(filepath), content)
//  }

  def addConf(dtos: ScaffoldDtos) = {
    val applicationConfFilePath = "./conf/application.conf"
    val targetKeyList = Seq(
      ("slick.dbs.default.db.driver","""slick.dbs.default.driver = "slick.driver.MySQLDriver$" """)
      , ("slick.dbs.default.db.password","""slick.dbs.default.db.driver = "com.mysql.jdbc.Driver" """)
      , ("slick.dbs.default.db.url","""slick.dbs.default.db.url = "jdbc:mysql://127.0.0.1/test?characterEncoding=UTF8&autoReconnect=true&useSSL=false" """)
      , ("slick.dbs.default.db.user","""slick.dbs.default.db.user = "test" """)
      , ("slick.dbs.default.driver","""slick.dbs.default.db.password = "test" """)
    )

    makeFileIfNotExist(new File(applicationConfFilePath))
    val conf: Config = ConfigFactory.parseFile(new File(applicationConfFilePath))
    val keyList = Loaders.getKeyListFromHocon(conf)
    val addList = targetKeyList.filter(x => !keyList.contains(x._1))

    if (addList.nonEmpty) {
      writeAppending(new File(applicationConfFilePath), "\n")
      addList.foreach { addrow =>
        writeAppending(new File(applicationConfFilePath), addrow._2)
      }
    }
  }

}
