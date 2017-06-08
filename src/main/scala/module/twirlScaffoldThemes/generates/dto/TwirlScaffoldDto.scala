package module.twirlScaffoldThemes.generates.dto

import java.io.File

import org.valet.common._
import module.twirlScaffoldThemes.generates._common.ValetProcessCycle

object TwirlScaffoldDto extends ValetProcessCycle with ValetUtility {

  override def initAction(dtos: ScaffoldDtos): Unit = {
    forceMkdir(new File(s"$path_model_dto_im"))
  }

  override def mainAction(dtos: ScaffoldDtos): Unit = {
    val isUsevResultDto = dtos.confDto.modulesTwirlScaffoldThemesModulesResultDtoIsUse
    if (isUsevResultDto == "YES") {
      forceWrite(s"$path_model_dto_ag/${default_ag_resultViewDtos(dtos)}.scala", DefViewDto.getAll(dtos))
    }


  }

  override def endAction(dtos: ScaffoldDtos): Unit = {}


}
