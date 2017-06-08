package org.valet.generates.default.dao

import java.io.File

import org.valet.common._
import org.valet.generates.default._common.ValetProcessCycle


object ScaffoldDao extends ValetProcessCycle with ValetUtility {

  override def initAction(dtos: ScaffoldDtos): Unit = {
    forceMkdir(new File(s"$path_model_dao_im"))
  }

  override def mainAction(dtos: ScaffoldDtos): Unit = {
    dtos.generatedTables.foreach { nowTable =>
      val isScaffoldList: Seq[String] = getIsScaffoldList(dtos, nowTable)
      if (isScaffoldList.contains(valetconf_isscaffoldlist_dao)) {
        forceWrite(s"$path_model_dao_ag/${getAgDao(nowTable)}.scala", DefDao.getAll(nowTable, dtos))
      }
    }
  }

  override def endAction(dtos: ScaffoldDtos): Unit = {

  }


}
