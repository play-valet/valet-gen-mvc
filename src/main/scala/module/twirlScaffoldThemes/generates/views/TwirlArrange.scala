package module.twirlScaffoldThemes.generates.views

import java.io.{File => JFile}

import better.files.File
import module.twirlScaffoldThemes.utils.TwirlPathDto
import org.valet.common.ValetUtility

import scala.sys.process.Process

object TwirlArrange extends ValetUtility {

  def initDir(tdto: TwirlPathDto): File = {
    File(tdto.valet_download).createIfNotExists(asDirectory = true)
    File(tdto.app_assets).createIfNotExists(asDirectory = true)
  }

  def gitClone(tdto: TwirlPathDto): Int = {
    cli(s"rm -rf ${tdto.projectName}")
    cli(s"git clone ${tdto.gitpath}")
    cli(s"rm -rf ${tdto.projectName}/.git")
    cli(s"rm -rf ${tdto.projectName}/build.sbt")
    cli(s"rm -rf ${tdto.projectName}/project")
  }

  def bowerInstall(tdto: TwirlPathDto): AnyVal = {
    import java.io.File
    if (new File(s"./${tdto.projectName}/bower.json").exists()) {
      Process(Seq("bower", "install"), new File(s"./${tdto.projectName}")).!
    }
  }

  def movetoDownload(tdto: TwirlPathDto): File = {
    cli(s"rm -rf " + tdto.valet_download_project)
    File(tdto.gitclone).moveTo(File(tdto.valet_download_project))
  }

  def copyToAppAssets(tdto: TwirlPathDto): File = {
    cli(s"rm -rf " + tdto.app_assets_project)
    File(tdto.app_assets_project).createIfNotExists(asDirectory = true)
    File(tdto.valet_download_project_app_asses).copyTo(File(tdto.app_assets_project))
  }

  def copyToPublicAssets(tdto: TwirlPathDto): File = {
    cli(s"rm -rf " + tdto.public_project)
    File(tdto.public_project).createIfNotExists(asDirectory = true)
    File(tdto.valet_download_project_public).copyTo(File(tdto.public_project))
  }

  def copyToView(tdto: TwirlPathDto): File = {
    cli(s"rm -rf " + tdto.app_views_autogen_project)
    File(tdto.app_views_autogen).createIfNotExists(asDirectory = true)
    File(tdto.app_views_autogen_project).createIfNotExists(asDirectory = true)
    File(tdto.valet_download_project_app_view).copyTo(File(tdto.app_views_autogen_project))
  }

  def deleteCrudTemplate(tdto: TwirlPathDto): Unit = {
    for (
      file <- getDirFileList(tdto.app_views_autogen_project, Seq())
    ) {
      if (file.getName == "crud_template" && file.isDirectory) {
        cli(s"rm -rf " + file.getPath)
      } else {

      }
    }

  }

}
