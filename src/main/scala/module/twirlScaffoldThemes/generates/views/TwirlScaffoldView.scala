package module.twirlScaffoldThemes.generates.views

import java.io._

import module.twirlScaffoldThemes.generates._common.ValetProcessCycle
import module.twirlScaffoldThemes.generates.views.TwirlArrange._
import module.twirlScaffoldThemes.utils._
import org.valet.common._

object TwirlScaffoldView extends ValetProcessCycle with TwirlConst {

  override def initAction(dtos: ScaffoldDtos): Unit = {

  }

  override def mainAction(dtos: ScaffoldDtos): Unit = {
    val pathDto: TwirlPathDto = getTwirlPathDto(dtos)
    arrange(pathDto)

    val fileList: Seq[File] = getDirFileList(pathDto.app_views_autogen_project, Seq()).filter(_.isFile)
    adjustTemplateViewToNowProject(fileList, dtos, pathDto)
  }

  override def endAction(dtos: ScaffoldDtos): Unit = {
    val pathDto: TwirlPathDto = getTwirlPathDto(dtos)
    if (isActiveInternet) {
      deleteCrudTemplate(pathDto)
    }
  }

  private def arrange(pathDto: TwirlPathDto) = {
    if (isActiveInternet) {
      initDir(pathDto)
      gitClone(pathDto)
      bowerInstall(pathDto)
      movetoDownload(pathDto)
      copyToAppAssets(pathDto)
      copyToPublicAssets(pathDto)
      copyToView(pathDto)
    } else {
      val ANSI_RED = "\u001B[31m"
      val ANSI_RESET = "\u001B[0m"
      System.out.println(ANSI_RED + "[INIT SKIP] Internet is not active. init processing is skipped." + ANSI_RESET);
    }
  }

  private def adjustTemplateViewToNowProject(fileList: Seq[File], dtos: ScaffoldDtos, pathDto: TwirlPathDto) = {
    fileList.foreach { file => FuncCrud.run(file, dtos, pathDto) }
  }


}

