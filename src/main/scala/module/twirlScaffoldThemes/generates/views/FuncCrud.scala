package module.twirlScaffoldThemes.generates.views

import java.io.File

import module.twirlScaffoldThemes.utils.play.twirl.parser.TreeNodes.Template
import module.twirlScaffoldThemes.utils.{CustomJsoupElement, TwirlConst, TwirlPathDto, Valiables}
import org.jsoup.Jsoup
import org.valet.common.ScaffoldDtos

object FuncCrud extends TwirlConst {

  def run(file: File, dtos: ScaffoldDtos, pathDto: TwirlPathDto): Unit = {
    val dirName = file.getParentFile.getName
    if (dirName == CRUD_TEMPLATE_DIR) {
      dtos.generatedTables.foreach { nowTable =>
        if (isScaffoldOk(VALETCONF_ISSCAFFOLDLIST_CONTROLLER, dtos, nowTable)) {
          val parsed: Option[Template] = TwirlLogic.getTwirlParsed(file)
          if (parsed.isDefined) {
            val ves = Valiables(dtos, pathDto, Some(file), Some(nowTable))
            val body = getBody(parsed.get, ves)
            //          val fullpath = "/Users/keigo/development/scalaide/scalapress/output/" + getTableFieldName(nowTable) + "/" + file.getName
            val fullpath = file.getParentFile.getParentFile.getPath + "/" + getTableFieldName(nowTable) + "/" + file.getName
            forceWrite(new File(fullpath), body)
          }
        }
      }
    } else {
      val parsed: Option[Template] = TwirlLogic.getTwirlParsed(file)
      if (parsed.isDefined) {
        val ves = Valiables(dtos, pathDto, Some(file), None)
        val body = getBody(parsed.get, ves)
        //          val fullpath = "/Users/keigo/development/scalaide/scalapress/output/" + getTableFieldName(nowTable) + "/" + file.getName
        val fullpath = file.getParentFile.getPath + "/" + file.getName
        forceWrite(new File(fullpath), body)
      }
    }

  }

  def getBody(parsed: Template, ves: Valiables): String = {
    val adds = ""

    val paramters = TwirlLogic.processParameters(parsed.params, ves)
    val imports = TwirlLogic.processImports(parsed.imports, ves)
    val adjustedBody = TwirlLogic.desTemplateTree(parsed.content, adds, ves)(processCodeImpl)(processPlainImpl)
    paramters + "\n" + imports + "\n" + adjustedBody
  }

  def processCodeImpl(headText: String, code: Option[String], ves: Valiables): String = {
    if (code.isDefined) {
      TwirlLogic.alterPathByRegex(s"$headText${code.get}", ves)
    } else {
      TwirlLogic.alterPathByRegex(headText, ves)
    }
  }

  def processPlainImpl(text: String, ves: Valiables): String = {
    val dirName = ves.file.get.getParentFile.getName
    if (!text.trim.replaceAll("\n", "").isEmpty && dirName == CRUD_TEMPLATE_DIR) {
      val dto = CustomJsoupElement(Jsoup.parse(text))

      // (customタグ) 処理
      // TODO: custum tagを使って実現する方法では、新しくJsoupElementを作った場合に消えてしまうため、
      // もう一度twirlでparseする、tagをうまいこと見極めてうまいこと対処するなど、何かしらの対策が必要になることに注意。
      // 今回はfile名で切り分けるという逃げで対処。
      ves.file.get.getName.split('.').headOption.getOrElse("") match {
        case "create" =>
          CrudTriggers.actTriggerCreateField(dto, ves)
          CrudTriggers.actTriggerButton(dto, ves)
          CrudTriggers.actTriggerCreateLogic(dto, ves).replace("&quot;", "\"") + "\n"
        case "edit"   =>
          CrudTriggers.actTriggerEditField(dto, ves)
          CrudTriggers.actTriggerButton(dto, ves)
          CrudTriggers.actTriggerEditLogic(dto, ves).replace("&quot;", "\"") + "\n"
        case "detail" =>
          CrudTriggers.actTriggerEditField(dto, ves)
          CrudTriggers.actTriggerButton(dto, ves)
          CrudTriggers.actTriggerEditLogic(dto, ves).replace("&quot;", "\"") + "\n"
        case "list"   =>
          CrudTriggers.actTriggerListField(dto, ves)
          CrudTriggers.actTriggerButton(dto, ves)
          CrudTriggers.actTriggerListLogic(dto, ves).replace("&quot;", "\"") + "\n"
        case _        => text
      }
    } else {
      text
    }
  }


}
