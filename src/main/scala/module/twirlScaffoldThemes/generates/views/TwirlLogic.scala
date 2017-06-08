package module.twirlScaffoldThemes.generates.views

import module.twirlScaffoldThemes.utils._
import module.twirlScaffoldThemes.utils.play.twirl.parser.TreeNodes._
import module.twirlScaffoldThemes.utils.play.twirl.parser.TwirlParser
import org.valet.common.{ConfTableColumn, GeneratedColumn}

import scala.io.Codec

object TwirlLogic extends TwirlConst {

  def getTwirlParsed(source: java.io.File): Option[Template] = {
    val content: Array[Byte] = CustomTwirlIO.readFile(source)
    val inclusiveDot: Boolean = false
    val codec = Codec(scala.util.Properties.sourceEncoding)
    val templateParser = new TwirlParser(inclusiveDot)

    templateParser.parse(new String(content, codec.charSet)) match {
      case templateParser.Success(parsed: Template, rest) if rest.atEnd => Some(parsed)
      case templateParser.Success(_, rest)                              => None
      case templateParser.Error(_, rest, errors)                        => None
    }
  }

  def desTemplateTree(templ: Seq[TemplateTree], add: String, ves: Valiables)
                     (processCode: (String, Option[String], Valiables) => String)
                     (processPlain: (String, Valiables) => String): String = {
    val h = templ.headOption
    if (h.isDefined) {
      val str = h.get match {
        case Plain(text)     => processPlain(text, ves)
        case Display(exp)    => desScalaExpPart(exp.parts, "", ves)(processCode)(processPlain)
        case Comment(msg)    => s"@*$msg*@"
        case ScalaExp(parts) => desScalaExpPart(parts, "", ves)(processCode)(processPlain)
        case _               => print("\n[ERROR]\n")
      }
      add + str + desTemplateTree(templ.tail, add, ves)(processCode)(processPlain)
    } else {
      add
    }
  }

  private def desScalaExpPart(list: Seq[ScalaExpPart], add: String, ves: Valiables)
                             (processCode: (String, Option[String], Valiables) => String)
                             (processPlain: (String, Valiables) => String): String = {
    val expHead = list.headOption
    if (expHead.isDefined) {
      val expHeadImp = expHead.get
      val headText: String = expHeadImp match {
        case Simple(code) if code.startsWith("@")  => s"$code"
        case Simple(code) if !code.startsWith("@") => s"@$code"
        case _                                     => ""
      }
      if (list.tail.isEmpty) {
        add + processCode(headText, None, ves)
      } else {
        add + list.tail.map {
          case Simple(code)                     => processCode(headText, Some(code), ves)
          case Block(whitespace, args, content) => s"$whitespace{${args.getOrElse("")}" + desTemplateTree(content, add, ves)(processCode)(processPlain) + "}"
          case _                                => print("\n[ERROR]\n")
        }.mkString("")
      }
    } else {
      add
    }
  }

  /**
    * TODO:仕様で制約する依存コードになっているので修正すること
    */
  def processParameters(params: PosString, ves: Valiables): String = {
    val isUsevResultDto = ves.dtos.confDto.modulesTwirlScaffoldThemesModulesResultDtoIsUse
    val resultDtoName = ves.dtos.confDto.modulesTwirlScaffoldThemesModulesResultDtoName

    if (isUsevResultDto == "YES") {
      if (ves.file.get.getName != "main.scala.html") {
        val IMP_1 = s"""@($DTO_PARAM: ${pkg_model_dto_ag + ".Ag" + resultDtoName})"""
        val IMP_2 = params.toString.replace("()", "")
        val IMP_3 = s"""(implicit mI18n: Messages, flash: Flash, req: RequestHeader, lang: Lang)"""
        IMP_1 + IMP_2 + IMP_3
      } else {
        "@" + params.toString
      }
    } else {
      // TODO:FORMベースのシンプルなものを追加
      "@" + params.toString
    }
  }

  def processImports(imports: Seq[Simple], ves: Valiables): String = {
    if (imports.isEmpty) {
      ""
    } else {
      "\n" + imports.map(x => s"@$x.code").mkString("\n")
    }
  }


  def alterPathByRegex(value: String, ves: Valiables): String = {
    val reg1 =
      """views\.([a-zA-Z][a-zA-Z0-9_]*\.)*[a-zA-Z][a-zA-Z0-9_]*\(.*""".r // views
    val reg2 =
      """\"(\\\"|[^\"])*\"""".r // Assets
    val reg3 =
      """routes\.(?!Assets)([a-zA-Z][a-zA-Z0-9_]*\.)*[a-zA-Z][a-zA-Z0-9_]*\(""".r // routes
    val reg4 =
      """(.*)(?:\.([^.]+$))""".r // 拡張子

    val list1 =
      (for (v1 <- reg1.findAllMatchIn(value)) yield {
        val v = v1.toString()
        if (!v.isEmpty && (v.split('.').length > 1)) {
          alterMaybeViewsFile(v, ves)
        } else {
          None
        }
      }).toSeq.filter(_.isDefined).map(_.get)

    val list2 =
      (for (v1 <- reg2.findAllMatchIn(value)) yield {
        val v = v1.toString().replace("\"", "")
        if (!v.isEmpty && (value.split("/").length > 1) && reg4.findFirstMatchIn(value.split("/").last).isDefined) {
          alterMaybeAssetsFile(v, ves)
        } else {
          None
        }
      }).toSeq.filter(_.isDefined).map(_.get)

    val list3 =
      (for (v1 <- reg3.findAllMatchIn(value)) yield {
        val v = v1.toString()
        if (!v.isEmpty && (v.split('.').length > 1)) {
          alterMaybeRoutes(v.dropRight(1), ves)
        } else {
          None
        }
      }).toSeq.filter(_.isDefined).map(_.get)

    val replaced1: String = list1.foldLeft(value) { (A, B) => A.replace(B.target, B.replacement) }
    val replaced2: String = list2.foldLeft(replaced1) { (A, B) => A.replace(B.target, B.replacement) }
    val replaced3: String = list3.foldLeft(replaced2) { (A, B) => A.replace(B.target, B.replacement) }

    replaced3
  }


  private def alterMaybeAssetsFile(maybeAssetsFile: String, ves: Valiables): Option[ReplaceDto] = {
    if (!better.files.File(ves.pathDto.valet_download_project + s"/public/$maybeAssetsFile").isEmpty) {
      // public
      Some(ReplaceDto(maybeAssetsFile, ves.pathDto.snakeCase + s"/$maybeAssetsFile"))
    } else if (!better.files.File(ves.pathDto.valet_download_project + s"/app/assets/$maybeAssetsFile").isEmpty) {
      // app assets
      Some(ReplaceDto(maybeAssetsFile, ves.pathDto.app_assets_project + s"/$maybeAssetsFile"))
    } else {
      None
    }
  }

  private def alterMaybeRoutes(maybeRoutes: String, ves: Valiables): Option[ReplaceDto] = {
    // controller
    None
  }

  private def alterMaybeViewsFile(maybeViewsFile: String, ves: Valiables): Option[ReplaceDto] = {
    // views
    val list = Seq(
      maybeViewsFile.split('.').head,
      maybeViewsFile.split('.').tail.head,
      autogenName,
      ves.pathDto.snakeCase
    ).++({
      val twirlPath: Seq[String] = ves.nowTable match {
        case Some(tbl) => maybeViewsFile.split('.').tail.tail.map(s => if (s == CRUD_TEMPLATE_DIR) getTableFieldName(tbl) else s)
        case None      => maybeViewsFile.split('.').tail.tail
      }
      twirlPath.init.++(Seq(alterTwirlPathArgument(twirlPath.last, ves)))
    })
    Some(ReplaceDto(maybeViewsFile, list.mkString(".")))
  }

  /**
    * TODO:仕様で制約する依存コードになっているので修正すること
    */
  private def alterTwirlPathArgument(last: String, ves: Valiables): String = {
    val isUsevResultDto = ves.dtos.confDto.modulesTwirlScaffoldThemesModulesResultDtoIsUse
    val resultDtoName = ves.dtos.confDto.modulesTwirlScaffoldThemesModulesResultDtoName
    if (isUsevResultDto == "YES") {
      if (ves.file.get.getName != "main.scala.html") {
        toSingleSpace(last).replace("()", s"($DTO_PARAM)")
      } else {
        last
      }
    } else {
      last
    }
  }

  def getUseColumn(ves: Valiables): Seq[GeneratedColumn] = {
    ves.nowTable.get.columnList.filter { column =>
      val tableInfoByvaletConf = getScaffoldTableColumn(column, ves)
      tableInfoByvaletConf.isDefined && !tableInfoByvaletConf.get.cAttr.contains("NO_VIEW_MODEL")
    }
  }

  private def getScaffoldTableColumn(column: GeneratedColumn, ves: Valiables): Option[ConfTableColumn] = {
    tryOpt {
      val tmpDtoColumnInfo = ves.dtos.confDto.tableTableList.filter(_.tableName == getTableName(ves.nowTable.get))
      tmpDtoColumnInfo.map(x => x.columnList.filter(cf => toCamelCase(cf.cName) == column.columnName).head).head
    }
  }

}

