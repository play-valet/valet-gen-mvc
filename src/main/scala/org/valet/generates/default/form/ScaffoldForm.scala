package org.valet.generates.default.form

import java.io.File

import org.valet.common._
import org.valet.generates.default._common.ValetProcessCycle

object ScaffoldForm extends ValetProcessCycle with ValetUtility {

  override def initAction(dtos: ScaffoldDtos): Unit = {
    forceMkdir(new File(s"$path_form_im"))
  }

  override def mainAction(dtos: ScaffoldDtos): Unit = {
    forceWrite(s"$path_form_ag/$default_ag_formconstraint.scala", DefFormConstraints.getAll(dtos))

    // by table
    dtos.generatedTables.foreach { nowTable =>
      val isScaffoldList: Seq[String] = getIsScaffoldList(dtos, nowTable)
      if (isScaffoldList.contains(VALETCONF_ISSCAFFOLDLIST_CONTROLLER)) {
        forceWrite(s"$path_form_ag/${getAgForm(nowTable)}.scala", DefForm.getAll(nowTable, dtos))
      }
    }
  }

  override def endAction(dtos: ScaffoldDtos): Unit = {

  }





}
