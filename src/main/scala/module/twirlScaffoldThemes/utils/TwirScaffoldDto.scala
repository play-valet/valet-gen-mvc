package module.twirlScaffoldThemes.utils

import java.io.File

import org.valet.common.{GeneratedTable, ScaffoldDtos}

case class TwirlPathDto(
                         projectName: String,
                         snakeCase: String,
                         gitpath: String,
                         gitclone: String,
                         valet_download: String,
                         valet_download_project: String,
                         valet_download_project_public    :String,
                         valet_download_project_app_view  :String,
                         valet_download_project_app_asses :String,
                         public: String,
                         public_project: String,
                         app_assets: String,
                         app_assets_project: String,
                         app_views: String,
                         app_views_autogen: String,
                         app_views_autogen_project: String,
                         pkg_views_autogen_crud_dir:String
                       )

case class ReplaceDto(target: String, replacement: String)

case class ButtonParamDto(scopeClass: String, href: Option[String], onclick: Option[String])

case class TwirlTagAttrDto(name: Option[String], id: Option[String], cls: Option[String], content: Option[String])


case class Valiables(dtos: ScaffoldDtos, pathDto: TwirlPathDto, file: Option[File], nowTable: Option[GeneratedTable])
