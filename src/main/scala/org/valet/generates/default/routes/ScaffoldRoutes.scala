package org.valet.generates.default.routes

import org.valet.common._
import org.valet.generates.default._common.ValetProcessCycle

object ScaffoldRoutes extends ValetProcessCycle with ValetUtility {


  override def initAction(dtos: ScaffoldDtos): Unit = {

  }

  override def mainAction(dtos: ScaffoldDtos): Unit = {
    dtos.generatedTables.foreach { nowTable =>
      val isScaffoldList: Seq[String] = getIsScaffoldList(dtos, nowTable)
      if (isScaffoldList.contains(valetconf_isscaffoldlist_controller)) {
        DefRoutes.getAll(nowTable, dtos)
      }
    }
  }

  override def endAction(dtos: ScaffoldDtos): Unit = {

  }


}

