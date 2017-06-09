package module.twirlScaffoldThemes.utils

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.valet.common.{GeneratedTable, ScaffoldDtos, ValetUtility}

trait FormsRegex {
  val REGEX_NUM = """\d*"""
  val REGEX_TIMESTAMP = """(((19|[2-9][0-9])[0-9]{2}/(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])).([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]))"""

  val TITLE_TIMESTAMP = """2017/04/01 13:32:55"""
}

trait TwirlConst extends FormsRegex with ValetUtility with AutogenTwirl {

  val DIV_TAG = "<div></div>"
  val TR_TAG = "<tr></tr>"
  val CRUD_TEMPLATE_DIR = "crud_template"
  val ROW_PARAM = "row"
  val DTO_PARAM = "vdto"
  val IMPORT_MESSAGES_VARIABLE = "mI18n"

  val SUBMIT_FORM = "SubmitForm"

  def getRouteController(nowTable: GeneratedTable): String = pkg_controller_ag + s".routes.${getAgTableName(nowTable)}Controller"

  def getEditSubmitFormName(nowTable: GeneratedTable): String = "edit" + SUBMIT_FORM + getTableName(nowTable)

  def getCreateSubmitFormName(nowTable: GeneratedTable): String = "create" + SUBMIT_FORM + getTableName(nowTable)

  def getButtonI18nCode(str: String): String = {
    str match {
      case SCAFFOLD_LIST_BTN_SHOW_CREATE     => "button.create.form"
      case SCAFFOLD_LIST_BTN_SHOW_DETAIL     => "button.detail.form"
      case SCAFFOLD_LIST_BTN_SHOW_EDIT       => "button.edit.form"
      case SCAFFOLD_DETAIL_BTN_SHOW_EDIT     => "button.edit.form"
      case SCAFFOLD_CREATE_BTN_SUBMIT_CREATE => "button.create"
      case SCAFFOLD_EDIT_BTN_SUBMIT_EDIT     => "button.edit"
      case SCAFFOLD_LIST_BTN_SUBMIT_DELETE   => "button.delete"
      case SCAFFOLD_DETAIL_BTN_SUBMIT_DELETE => "button.delete"
      case SCAFFOLD_CREATE_BTN_CANCEL        => "button.cancel"
      case SCAFFOLD_EDIT_BTN_CANCEL          => "button.cancel"
      case SCAFFOLD_DETAIL_BTN_CANCEL        => "button.cancel"
      case _                                 => "button"
    }
  }

  def getButtonI18nTextContent(str: String): String = {
    s"""@$IMPORT_MESSAGES_VARIABLE("${getButtonI18nCode(str)}")"""
  }


  def getTwirlPathDto(dtos: ScaffoldDtos): TwirlPathDto = {
    val gitPathBackendAdminPath = dtos.confDto.modulesTwirlScaffoldThemesSourceBackendAdmin
    val gitProjectName: String = gitPathBackendAdminPath.split("/").last.dropRight(4)
    val snakeCase = toSnakeCase(gitProjectName.replaceAll("-", "_"))

    val optFile = getDirFileList(s"./app/views/autogen/$snakeCase", Seq()).find(file => file.isFile &&
      file.getParentFile.getName == CRUD_TEMPLATE_DIR)

    val str = if (optFile.isDefined) {
      optFile.map(file => file.getParentFile.getPath.replace("./app/views/", "")
        .replace("/", ".").split('.').init.mkString(".")).getOrElse("")
    } else {
      ""
    }

    TwirlPathDto(
      projectName = gitProjectName,
      snakeCase = snakeCase,
      gitpath = gitPathBackendAdminPath,
      gitclone = s"./$gitProjectName",
      valet_download = s"./valet/downloads",
      valet_download_project = s"./valet/downloads/$gitProjectName",
      valet_download_project_public = s"./valet/downloads/$gitProjectName/public",
      valet_download_project_app_view = s"./valet/downloads/$gitProjectName/app/views",
      valet_download_project_app_asses = s"./valet/downloads/$gitProjectName/app/assets",
      public = s"./public",
      public_project = s"./public/$snakeCase",
      app_assets = s"./app/assets",
      app_assets_project = s"./app/assets/$snakeCase",
      app_views = s"./app/views",
      app_views_autogen = s"./app/views/autogen",
      app_views_autogen_project = s"./app/views/autogen/$snakeCase",
      pkg_views_autogen_crud_dir = str
    )
  }

  def getTagDefault(tagName: String, defaultAttr: Option[Map[String, String]]): Element = {
    def setAttr(elm: Element, attr: Option[Map[String, String]]): CustomJsoupElement =
      if (attr.isDefined) CustomJsoupElement(elm).setAttr(attr.get) else CustomJsoupElement(elm)

    tagName match {
      case "input-text"   => setAttr(Jsoup.parse("""<input type="text">""").body().child(0), defaultAttr).getElement
      case "input-hidden" => setAttr(Jsoup.parse("""<input type="hidden">""").body().child(0), defaultAttr).getElement
      case "textarea"     => setAttr(Jsoup.parse("""<textarea rows="4"></textarea>""").body().child(0), defaultAttr).getElement
      case "a"            => setAttr(Jsoup.parse("""<a></a>""").body().child(0), defaultAttr).getElement
      case "span"         => setAttr(Jsoup.parse("""<span></span>""").body().child(0), defaultAttr).getElement
      case _              => setAttr(Jsoup.parse("""<input type="text">""").body().child(0), defaultAttr).getElement
    }
  }


  def getColumnTypeAttr(columnType: String): Map[String, String] = {
    columnType match {
      case "Int"                        => Map("required" -> "", "pattern" -> REGEX_NUM)
      case "Option[Int]"                => Map("pattern" -> REGEX_NUM)
      case "String"                     => Map("required" -> "")
      case "Option[String]"             => Map()
      case "java.sql.Timestamp"         => Map("required" -> "", "pattern" -> REGEX_TIMESTAMP, "title" -> TITLE_TIMESTAMP)
      case "Option[java.sql.Timestamp]" => Map("pattern" -> REGEX_TIMESTAMP, "title" -> TITLE_TIMESTAMP)
      case _                            => Map()
    }
  }

}

trait AutogenTwirl {
  val SCAFFOLD_FLASH_MSG_ITERATOR: String = "SCAFFOLD_FLASH_MSG_ITERATOR"
  val SCAFFOLD_FLASH_MSG_CLASS_VALUE: String = "SCAFFOLD_FLASH_MSG_CLASS_VALUE"
  val SCAFFOLD_FLASH_MSG_FILED: String = "SCAFFOLD_FLASH_MSG_FILED"
  val SCAFFOLD_FLASH_MSG_VALUE: String = "SCAFFOLD_FLASH_MSG_VALUE"
  val SCAFFOLD_NAV_ITERATOR: String = "SCAFFOLD_NAV_ITERATOR"
  val SCAFFOLD_NAV_CRUD_TABLE_LINK: String = "SCAFFOLD_NAV_CRUD_TABLE_LINK"
  val SCAFFOLD_NAV_CRUD_TABLE_NAME: String = "SCAFFOLD_NAV_CRUD_TABLE_NAME"
  val SCAFFOLD_DETAIL_ITERATOR: String = "SCAFFOLD_DETAIL_ITERATOR"
  val SCAFFOLD_DETAIL_FIELD: String = "SCAFFOLD_DETAIL_FIELD"
  val SCAFFOLD_DETAIL_VALUE: String = "SCAFFOLD_DETAIL_VALUE"
  val SCAFFOLD_EDIT_LOGIC_FORM: String = "SCAFFOLD_EDIT_LOGIC_FORM"
  val SCAFFOLD_EDIT_ITERATOR: String = "SCAFFOLD_EDIT_ITERATOR"
  val SCAFFOLD_EDIT_FIELD: String = "SCAFFOLD_EDIT_FIELD"
  val SCAFFOLD_EDIT_VALUE: String = "SCAFFOLD_EDIT_VALUE"
  val SCAFFOLD_LIST_LOGIC_ROWS: String = "SCAFFOLD_LIST_LOGIC_ROWS"
  val SCAFFOLD_LIST_ITERATOR: String = "SCAFFOLD_LIST_ITERATOR"
  val SCAFFOLD_LIST_FIELD: String = "SCAFFOLD_LIST_FIELD"
  val SCAFFOLD_LIST_VALUE: String = "SCAFFOLD_LIST_VALUE"
  val SCAFFOLD_CREATE_LOGIC_FORM: String = "SCAFFOLD_CREATE_LOGIC_FORM"
  val SCAFFOLD_CREATE_ITERATOR: String = "SCAFFOLD_CREATE_ITERATOR"
  val SCAFFOLD_CREATE_FIELD: String = "SCAFFOLD_CREATE_FIELD"
  val SCAFFOLD_CREATE_VALUE: String = "SCAFFOLD_CREATE_VALUE"
  val SCAFFOLD_LIST_BTN_SHOW_EDIT: String = "SCAFFOLD_LIST_BTN_SHOW_EDIT"
  val SCAFFOLD_LIST_BTN_SHOW_DETAIL: String = "SCAFFOLD_LIST_BTN_SHOW_DETAIL"
  val SCAFFOLD_LIST_BTN_SUBMIT_DELETE: String = "SCAFFOLD_LIST_BTN_SUBMIT_DELETE"
  val SCAFFOLD_LIST_BTN_SHOW_CREATE: String = "SCAFFOLD_LIST_BTN_SHOW_CREATE"
  val SCAFFOLD_EDIT_BTN_SUBMIT_EDIT: String = "SCAFFOLD_EDIT_BTN_SUBMIT_EDIT"
  val SCAFFOLD_EDIT_BTN_CANCEL: String = "SCAFFOLD_EDIT_BTN_CANCEL"
  val SCAFFOLD_DETAIL_BTN_SHOW_EDIT: String = "SCAFFOLD_DETAIL_BTN_SHOW_EDIT"
  val SCAFFOLD_DETAIL_BTN_CANCEL: String = "SCAFFOLD_DETAIL_BTN_CANCEL"
  val SCAFFOLD_DETAIL_BTN_SUBMIT_DELETE: String = "SCAFFOLD_DETAIL_BTN_SUBMIT_DELETE"
  val SCAFFOLD_CREATE_BTN_SUBMIT_CREATE: String = "SCAFFOLD_CREATE_BTN_SUBMIT_CREATE"
  val SCAFFOLD_CREATE_BTN_CANCEL: String = "SCAFFOLD_CREATE_BTN_CANCEL"
  val CLASS_SCAFFOLD_FLASH_MSG_ITERATOR: String = "." + SCAFFOLD_FLASH_MSG_ITERATOR
  val CLASS_SCAFFOLD_FLASH_MSG_CLASS_VALUE: String = "." + SCAFFOLD_FLASH_MSG_CLASS_VALUE
  val CLASS_SCAFFOLD_FLASH_MSG_FILED: String = "." + SCAFFOLD_FLASH_MSG_FILED
  val CLASS_SCAFFOLD_FLASH_MSG_VALUE: String = "." + SCAFFOLD_FLASH_MSG_VALUE
  val CLASS_SCAFFOLD_NAV_ITERATOR: String = "." + SCAFFOLD_NAV_ITERATOR
  val CLASS_SCAFFOLD_NAV_CRUD_TABLE_LINK: String = "." + SCAFFOLD_NAV_CRUD_TABLE_LINK
  val CLASS_SCAFFOLD_NAV_CRUD_TABLE_NAME: String = "." + SCAFFOLD_NAV_CRUD_TABLE_NAME
  val CLASS_SCAFFOLD_DETAIL_ITERATOR: String = "." + SCAFFOLD_DETAIL_ITERATOR
  val CLASS_SCAFFOLD_DETAIL_FIELD: String = "." + SCAFFOLD_DETAIL_FIELD
  val CLASS_SCAFFOLD_DETAIL_VALUE: String = "." + SCAFFOLD_DETAIL_VALUE
  val CLASS_SCAFFOLD_EDIT_LOGIC_FORM: String = "." + SCAFFOLD_EDIT_LOGIC_FORM
  val CLASS_SCAFFOLD_EDIT_ITERATOR: String = "." + SCAFFOLD_EDIT_ITERATOR
  val CLASS_SCAFFOLD_EDIT_FIELD: String = "." + SCAFFOLD_EDIT_FIELD
  val CLASS_SCAFFOLD_EDIT_VALUE: String = "." + SCAFFOLD_EDIT_VALUE
  val CLASS_SCAFFOLD_LIST_LOGIC_ROWS: String = "." + SCAFFOLD_LIST_LOGIC_ROWS
  val CLASS_SCAFFOLD_LIST_ITERATOR: String = "." + SCAFFOLD_LIST_ITERATOR
  val CLASS_SCAFFOLD_LIST_FIELD: String = "." + SCAFFOLD_LIST_FIELD
  val CLASS_SCAFFOLD_LIST_VALUE: String = "." + SCAFFOLD_LIST_VALUE
  val CLASS_SCAFFOLD_CREATE_LOGIC_FORM: String = "." + SCAFFOLD_CREATE_LOGIC_FORM
  val CLASS_SCAFFOLD_CREATE_ITERATOR: String = "." + SCAFFOLD_CREATE_ITERATOR
  val CLASS_SCAFFOLD_CREATE_FIELD: String = "." + SCAFFOLD_CREATE_FIELD
  val CLASS_SCAFFOLD_CREATE_VALUE: String = "." + SCAFFOLD_CREATE_VALUE
  val CLASS_SCAFFOLD_LIST_BTN_SHOW_EDIT: String = "." + SCAFFOLD_LIST_BTN_SHOW_EDIT
  val CLASS_SCAFFOLD_LIST_BTN_SHOW_DETAIL: String = "." + SCAFFOLD_LIST_BTN_SHOW_DETAIL
  val CLASS_SCAFFOLD_LIST_BTN_SUBMIT_DELETE: String = "." + SCAFFOLD_LIST_BTN_SUBMIT_DELETE
  val CLASS_SCAFFOLD_LIST_BTN_SHOW_CREATE: String = "." + SCAFFOLD_LIST_BTN_SHOW_CREATE
  val CLASS_SCAFFOLD_EDIT_BTN_SUBMIT_EDIT: String = "." + SCAFFOLD_EDIT_BTN_SUBMIT_EDIT
  val CLASS_SCAFFOLD_EDIT_BTN_CANCEL: String = "." + SCAFFOLD_EDIT_BTN_CANCEL
  val CLASS_SCAFFOLD_DETAIL_BTN_SHOW_EDIT: String = "." + SCAFFOLD_DETAIL_BTN_SHOW_EDIT
  val CLASS_SCAFFOLD_DETAIL_BTN_CANCEL: String = "." + SCAFFOLD_DETAIL_BTN_CANCEL
  val CLASS_SCAFFOLD_DETAIL_BTN_SUBMIT_DELETE: String = "." + SCAFFOLD_DETAIL_BTN_SUBMIT_DELETE
  val CLASS_SCAFFOLD_CREATE_BTN_SUBMIT_CREATE: String = "." + SCAFFOLD_CREATE_BTN_SUBMIT_CREATE
  val CLASS_SCAFFOLD_CREATE_BTN_CANCEL: String = "." + SCAFFOLD_CREATE_BTN_CANCEL
}