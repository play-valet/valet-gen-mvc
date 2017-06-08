package org.valet.generates.default.service

import java.io.File

import org.valet.common._
import org.valet.generates.default._common.ValetProcessCycle

object ScaffoldService extends ValetProcessCycle with ValetUtility {


  override def initAction(dtos: ScaffoldDtos): Unit = {
    forceMkdir(new File(s"$path_model_service_im"))
  }

  override def mainAction(dtos: ScaffoldDtos): Unit = {

    // default
    forceWrite(s"$path_model_service_ag/$default_ag_utilService.scala", DefUtilsService.getAll(dtos))

    // by table
    dtos.generatedTables.foreach { slickTable =>
      val isScaffoldList: Seq[String] = getIsScaffoldList(dtos, slickTable)
      if (isScaffoldList.contains(valetconf_isscaffoldlist_service)) {

      }
    }

    //    // TODO:use meta.isolate default and custom.solve dependency.
//    val accountTableName: String = dtos.confDto.modulesAuthTableAccountTableName
//    val errorLogTableName: String = dtos.confDto.modulesErrorLogTableTableName
//    val isUserAuth = dtos.confDto.modulesAuthIsUse
//    val isUserErrorLog = dtos.confDto.modulesErrorLogIsUse
//    val isSYNC_STYLE = dtos.confDto.outputOptions.map(x => (x.split(':').head, x.split(':').last)).exists(f => f._1 == "CONTROLLER" && f._2 == "SYNC_STYLE")
//    dtos.generatedTables.foreach { nowTable =>
//      val tableName = getAgTableName(nowTable)
//      if (isUserAuth == "YES" && tableName == accountTableName) {
//        //        genAuthService(slickTable, dtos)
//      } else if (isUserErrorLog == "YES" && tableName == errorLogTableName) {
//        //        genErrorLogService(dtos)
//      } else if (isSYNC_STYLE) {
//        genSyncService(nowTable, dtos)
//      }
//    }
  }

  override def endAction(dtos: ScaffoldDtos): Unit = {}




  def genSyncService(nowTable: GeneratedTable, dtos: ScaffoldDtos) = {
    //
    //      val fullpath: String = s"${path_model_service_ag}/${getAgTableName(nowTable)}SyncService.scala"
    //      val body = DefSyncService.getAll(table, dtos)
    //      ScUtils.forceWrite(new File(fullpath), body)
  }


}
