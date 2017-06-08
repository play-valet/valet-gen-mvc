package org.valet.generates.default.common

import java.io.File

import org.valet.common._
import org.valet.generates.default._common.ValetProcessCycle
import org.valet.generates.default.common.pkg_utils.{DefConsts, DefUtils}

object ScaffoldCommon extends ValetProcessCycle with ValetUtility {

  override def initAction(dtos: ScaffoldDtos): Unit = {
    forceMkdir(new File(s"$path_common_util_im"))
  }

  override def mainAction(dtos: ScaffoldDtos): Unit = {
    forceWrite(s"$path_common_util_ag/$default_ag_utility.scala", DefUtils.getAll(dtos))
    forceWrite(s"$path_common_util_ag/$default_ag_const.scala", DefConsts.getAll(dtos))
    forceWrite(s"$path_common_util_im/$default_im_utility.scala", DefUtils.getAllImplement(dtos))
    forceWrite(s"$path_common_util_im/$default_im_const.scala", DefConsts.getAllImplement(dtos))
  }

  override def endAction(dtos: ScaffoldDtos): Unit = {

  }



}
