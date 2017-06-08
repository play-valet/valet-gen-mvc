package org.valet.generates.default.applicationconf

import org.valet.common.{ScaffoldDtos, ValetUtility}
import org.valet.generates.default._common.ValetProcessCycle

object ScaffoldApplicationConf extends ValetProcessCycle with ValetUtility {

  override def initAction(dtos: ScaffoldDtos) = {

  }

  override def mainAction(dtos: ScaffoldDtos): Unit = {
    DefApplicationConf.addConf(dtos)
  }

  override def endAction(dtos: ScaffoldDtos) = {

  }

}