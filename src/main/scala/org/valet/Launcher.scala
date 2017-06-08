package org.valet

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}
import module.twirlScaffoldThemes.TwirlLauncher
import org.valet.common._
import org.valet.generates.default._common.ValetProcessCycle
import org.valet.generates.default.applicationconf.ScaffoldApplicationConf
import org.valet.generates.default.buildsbt.ScaffoldBuildsbt
import org.valet.generates.default.common.ScaffoldCommon
import org.valet.generates.default.controller.ScaffoldController
import org.valet.generates.default.dao.ScaffoldDao
import org.valet.generates.default.dto.ScaffoldDto
import org.valet.generates.default.form.ScaffoldForm
import org.valet.generates.default.routes.ScaffoldRoutes
import org.valet.generates.default.service.ScaffoldService
import org.valet.generates.default.view.ScaffoldView


object Launcher extends Utility {

  def main(args: Array[String]) {
    run(new File(".").getCanonicalPath + "/valet.conf")
    //    args.toList match {
    //      case name :: params if (name == "mvc") => run(params.headOption.getOrElse((new File(".").getCanonicalPath() + "/valet.conf")))
    //      case _                                 => showUsage
    //    }
  }

  def run(confPath: String): Unit = {
    val conf: Config = ConfigFactory.parseFile(new File(confPath))
    //    Developments.getConfKeyValues(conf)
    //    Developments.echoClassNames
    //    Developments.echoValAndEcho

    val confDto = Loaders.getConfDto(conf)
    val generatedTables: Seq[GeneratedTable] = Loaders.getSlickTableList(confDto).filter(_.tableName != "SchemaVersionRow")
    val dtos = ScaffoldDtos(confDto, generatedTables)
    runDefaultScaffold(dtos)
    runCustomScaffold(dtos)
  }

  def runDefaultScaffold(dtos: ScaffoldDtos): Unit = {
    val defaultTaskList: Seq[ValetProcessCycle] = Seq(
      ScaffoldService,
      ScaffoldForm,
      ScaffoldCommon,
      ScaffoldDto,
      ScaffoldDao,
      ScaffoldRoutes,
      ScaffoldView,
      ScaffoldController,
      ScaffoldApplicationConf,
      ScaffoldBuildsbt
    )
    defaultTaskList.foreach { obj => obj.initAction(dtos) }
    defaultTaskList.foreach { obj => obj.mainAction(dtos) }
    defaultTaskList.foreach { obj => obj.endAction(dtos) }
  }

  def runCustomScaffold(dtos: ScaffoldDtos): Unit = {
    TwirlLauncher.run(dtos)
  }

  def showUsage(): Unit = {
    println(
      s"""
         | Usage:
         |        sbt "run mvc $$(pwd)/valet.conf"
       """.stripMargin
    )
  }


}
