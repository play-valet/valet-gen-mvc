package module.twirlScaffoldThemes.generates.controller

import org.valet.common._
import module.twirlScaffoldThemes.generates._common.ValetProcessCycle

object TwirlScaffoldController extends ValetProcessCycle with ValetUtility {


  override def initAction(dtos: ScaffoldDtos): Unit = {


  }

  override def mainAction(dtos: ScaffoldDtos): Unit = {
    dtos.generatedTables.foreach { nowTable =>
      val isScaffoldList: Seq[String] = getIsScaffoldList(dtos, nowTable)
      if (isScaffoldList.contains(VALETCONF_ISSCAFFOLDLIST_CONTROLLER)) {
        val isUsevResultDto = dtos.confDto.modulesTwirlScaffoldThemesModulesResultDtoIsUse
        if (isUsevResultDto == "YES") {
          val controllerOutputOption = getOutputOptionList(dtos).filter(p => p._1 == "CONTROLLER").map(_._2).headOption
          controllerOutputOption match {
            case Some(a) if a == "EITHERT" => forceWrite(s"$path_controller_ag/${getAgController(nowTable)}.scala", DefTwirl_EitherT_Controller.getAll(nowTable, dtos))
            case Some(a) if a == "ASYNC"   => forceWrite(s"$path_controller_ag/${getAgController(nowTable)}.scala", DefTwirl_ASync_Controller.getAll(nowTable, dtos))
            case Some(a) if a == "SYNC"    => forceWrite(s"$path_controller_ag/${getAgController(nowTable)}.scala", DefTwirl_Sync_Controller.getAll(nowTable, dtos))
            case None                      => ""
          }
        } else {

        }
      }
    }
  }

  override def endAction(dtos: ScaffoldDtos): Unit = {

  }

}
