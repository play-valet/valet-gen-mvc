package org.valet.generates.default.dto

import java.io.File

import org.valet.common._
import org.valet.generates.default._common.ValetProcessCycle

object ScaffoldDto extends ValetProcessCycle with ValetUtility {

  override def initAction(dtos: ScaffoldDtos): Unit = {
    forceMkdir(new File(s"$path_model_dto_im"))
  }

  override def mainAction(dtos: ScaffoldDtos): Unit = {
    forceWrite(s"$path_model_dto_ag/$default_ag_exceptionDtos.scala", DefExceptionDto.getAll(dtos))
  }

  override def endAction(dtos: ScaffoldDtos): Unit = {}


}
