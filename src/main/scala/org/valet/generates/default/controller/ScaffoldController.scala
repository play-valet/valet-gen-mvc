package org.valet.generates.default.controller

import java.io.File

import org.valet.common._
import org.valet.generates.default._common.ValetProcessCycle

object ScaffoldController extends ValetProcessCycle with ValetUtility {

  override def initAction(dtos: ScaffoldDtos): Unit = {
    forceMkdir(new File(s"$path_controller_ag"))
    forceMkdir(new File(s"$path_controller_im"))
  }
  override def mainAction(dtos: ScaffoldDtos): Unit = {

  }
  override def endAction(dtos: ScaffoldDtos): Unit = {
  }
}
