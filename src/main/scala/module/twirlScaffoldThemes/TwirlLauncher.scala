package module.twirlScaffoldThemes

import module.twirlScaffoldThemes.generates._common.ValetProcessCycle
import module.twirlScaffoldThemes.generates.controller.TwirlScaffoldController
import module.twirlScaffoldThemes.generates.dto.TwirlScaffoldDto
import module.twirlScaffoldThemes.generates.views.TwirlScaffoldView
import org.valet.common._

object TwirlLauncher extends Utility {

  def run(dtos: ScaffoldDtos): Unit = {
    val customizeTaskList: Seq[ValetProcessCycle] = Seq(
      TwirlScaffoldController,
      TwirlScaffoldDto,
      TwirlScaffoldView
    )
    customizeTaskList.foreach { obj => obj.initAction(dtos) }
    customizeTaskList.foreach { obj => obj.mainAction(dtos) }
    customizeTaskList.foreach { obj => obj.endAction(dtos) }
  }


}
