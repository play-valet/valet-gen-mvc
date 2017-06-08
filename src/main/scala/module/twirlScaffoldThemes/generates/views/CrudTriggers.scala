package module.twirlScaffoldThemes.generates.views

import module.twirlScaffoldThemes.utils.{CustomJsoupElement, TwirlConst, Valiables}
import org.valet.common.GeneratedColumn

object CrudTriggers extends CrudCommons {

  def actTriggerCreateField(dto: CustomJsoupElement, ves: Valiables): CustomJsoupElement = {
    for {
      (column, i) <- TwirlLogic.getUseColumn(ves).zipWithIndex
    } yield {
      if (i == 0) {

      } else {
        dto
          .scopeClosestElements(CLASS_SCAFFOLD_CREATE_ITERATOR) { element =>
            CustomJsoupElement(element)
              // 自身を複製。複製したものは取っておいて、今のものに対して処理を行うことでリスト処理を実現する。わかりづらいので注意。
              .copySelfAfter
              .removeAttrValue("class", SCAFFOLD_CREATE_ITERATOR)
              // 処理開始
              .replaceTextContent(CLASS_SCAFFOLD_CREATE_FIELD, column.columnName)
              .replaceElementAndOverwriteAttr(CLASS_SCAFFOLD_CREATE_VALUE, getTagDefault("input-text", None))
              .replaceAttr(CLASS_SCAFFOLD_CREATE_VALUE, "value", getCreateFieldValue(column, ves))
              .replaceAttr(CLASS_SCAFFOLD_CREATE_VALUE, "name", toSnakeCase(column.columnName))
              .removeAttrValue("class", SCAFFOLD_CREATE_VALUE, CLASS_SCAFFOLD_CREATE_VALUE)
              .removeAttrValue("class", SCAFFOLD_CREATE_FIELD, CLASS_SCAFFOLD_CREATE_FIELD)
          }
      }
    }
    dto.removeElement(CLASS_SCAFFOLD_CREATE_ITERATOR)
  }

  def actTriggerCreateLogic(dto: CustomJsoupElement, ves: Valiables): String = {
    val tableName = getTableName(ves.nowTable.get)
    // TODO: PARENTが付いている場合には、その親要素の部分にTwirlコードを付与するといった使い方を実現したい。
    // TODO: CHILDが付いている場合には、その子要素の部分にTwirlコードを付与するといった使い方を実現したい。
    // custom tag 処理
    val CUSTOM_TAG = "CUSTOM_TAG"
    dto
      .scopeClosestElement(CLASS_SCAFFOLD_CREATE_LOGIC_FORM)
      .renameTag(CUSTOM_TAG)
      .replaceAttr(CUSTOM_TAG, "name", getCreateSubmitFormName(ves.nowTable.get))
      .removeAttrValue("class", SCAFFOLD_CREATE_LOGIC_FORM)

    // custom tag replace 処理
    dto.getElement.root().ownerDocument().body().children().toString.split("\n").map {
      case ln if ln.trim.startsWith("<" + CUSTOM_TAG)  =>
        val formAttr: String =
          dto
            .scopeRoot
            .scopeClosestElement(CUSTOM_TAG).getAttr.map(x =>s""" '${x._1} -> "${x._2}"""").mkString(",")

        s"""|@helper.form(${getRouteController(ves.nowTable.get)}.$METHOD_CONTRL_STORE(), $formAttr) {""".stripMargin
      case ln if ln.trim.startsWith("</" + CUSTOM_TAG) => s"}".stripMargin
      case ln                                          => ln
    }.mkString("\n")
  }

  def actTriggerDetailField(dto: CustomJsoupElement, ves: Valiables): CustomJsoupElement = {
    for {
      (column, i) <- TwirlLogic.getUseColumn(ves).zipWithIndex
    } yield {
      if (i == 0) {
        dto
          .scopeClosestElements(CLASS_SCAFFOLD_DETAIL_ITERATOR) { element =>
            CustomJsoupElement(element)
              // 自身を複製。複製したものは取っておいて、今のものに対して処理を行うことでリスト処理を実現する。わかりづらいので注意。
              .copySelfAfter
              .removeAttrValue("class", SCAFFOLD_DETAIL_ITERATOR)

              // フィールド処理開始
              .replaceTextContent(CLASS_SCAFFOLD_DETAIL_FIELD, column.columnName)
              // 表示用 primary id
              .replaceElementAndOverwriteAttr(CLASS_SCAFFOLD_DETAIL_VALUE, getTagDefault("span", None))
              .replaceTextContent(CLASS_SCAFFOLD_DETAIL_VALUE, getEditFieldValue(column, ves))
              .removeAttrSearched("type", CLASS_SCAFFOLD_DETAIL_VALUE)
              .removeAttrSearched("value", CLASS_SCAFFOLD_DETAIL_VALUE)
              .removeAttrSearched("name", CLASS_SCAFFOLD_DETAIL_VALUE)

              // 送信用 primary id
              .addAfterSearched(CLASS_SCAFFOLD_DETAIL_VALUE,
              getTagDefault("input-hidden",
                Some(Map(
                  "value" -> getEditFieldValue(column, ves),
                  "name" -> toSnakeCase(column.columnName)
                ))))

              .removeAttrValue("class", SCAFFOLD_DETAIL_VALUE, CLASS_SCAFFOLD_DETAIL_VALUE)
              .removeAttrValue("class", SCAFFOLD_DETAIL_FIELD, CLASS_SCAFFOLD_DETAIL_FIELD)
          }
      } else {
        dto
          .scopeClosestElements(CLASS_SCAFFOLD_DETAIL_ITERATOR) { element =>
            CustomJsoupElement(element)
              // 自身を複製する
              .copySelfAfter
              .removeAttrValue("class", SCAFFOLD_DETAIL_ITERATOR)
              // 処理開始
              .replaceTextContent(CLASS_SCAFFOLD_DETAIL_FIELD, column.columnName)
              .replaceElementAndOverwriteAttr(CLASS_SCAFFOLD_DETAIL_VALUE, getTagDefault("span", None))
              .replaceTextContent(CLASS_SCAFFOLD_DETAIL_VALUE, getEditFieldValue(column, ves))
              .removeAttrSearched("type", CLASS_SCAFFOLD_DETAIL_VALUE)
              .removeAttrSearched("value", CLASS_SCAFFOLD_DETAIL_VALUE)
              .removeAttrSearched("name", CLASS_SCAFFOLD_DETAIL_VALUE)

              .removeAttrValue("class", SCAFFOLD_DETAIL_VALUE, CLASS_SCAFFOLD_DETAIL_VALUE)
              .removeAttrValue("class", SCAFFOLD_DETAIL_FIELD, CLASS_SCAFFOLD_DETAIL_FIELD)
          }
      }
    }
    dto.removeElement(CLASS_SCAFFOLD_DETAIL_ITERATOR)
  }

  def actTriggerDetailLogic(dto: CustomJsoupElement, ves: Valiables): String = {
    dto.getElement.root().ownerDocument().body().children().toString
  }


  def actTriggerEditField(dto: CustomJsoupElement, ves: Valiables): CustomJsoupElement = {
    for {
      (column, i) <- TwirlLogic.getUseColumn(ves).zipWithIndex
    } yield {
      if (i == 0) {
        dto
          .scopeClosestElements(CLASS_SCAFFOLD_EDIT_ITERATOR) { element =>
            CustomJsoupElement(element)
              // 自身を複製。複製したものは取っておいて、今のものに対して処理を行うことでリスト処理を実現する。わかりづらいので注意。
              .copySelfAfter
              .removeAttrValue("class", SCAFFOLD_EDIT_ITERATOR)

              // フィールド処理開始
              .replaceTextContent(CLASS_SCAFFOLD_EDIT_FIELD, column.columnName)
              // 表示用 primary id
              .replaceElementAndOverwriteAttr(CLASS_SCAFFOLD_EDIT_VALUE, getTagDefault("span", None))
              .replaceTextContent(CLASS_SCAFFOLD_EDIT_VALUE, getEditFieldValue(column, ves))
              .removeAttr("type")
              .removeAttr("value")
              .removeAttr("name")

              // 送信用 primary id
              .addAfterSearched(CLASS_SCAFFOLD_EDIT_VALUE,
              getTagDefault("input-hidden",
                Some(Map(
                  "value" -> getEditFieldValue(column, ves),
                  "name" -> toSnakeCase(column.columnName)
                ))))

              .removeAttrValue("class", SCAFFOLD_EDIT_VALUE, CLASS_SCAFFOLD_EDIT_VALUE)
              .removeAttrValue("class", SCAFFOLD_EDIT_FIELD, CLASS_SCAFFOLD_EDIT_FIELD)
          }
      } else {
        dto
          .scopeClosestElements(CLASS_SCAFFOLD_EDIT_ITERATOR) { element =>
            CustomJsoupElement(element)
              // 自身を複製する
              .copySelfAfter
              .removeAttrValue("class", SCAFFOLD_EDIT_ITERATOR)
              // 処理開始
              .replaceTextContent(CLASS_SCAFFOLD_EDIT_FIELD, column.columnName)
              .replaceElementAndOverwriteAttr(CLASS_SCAFFOLD_EDIT_VALUE, getTagDefault("input-text", None))
              .replaceAttr(CLASS_SCAFFOLD_EDIT_VALUE, "value", getEditFieldValue(column, ves))
              .replaceAttr(CLASS_SCAFFOLD_EDIT_VALUE, "name", toSnakeCase(column.columnName))
              .removeAttrValue("class", SCAFFOLD_EDIT_VALUE, CLASS_SCAFFOLD_EDIT_VALUE)
              .removeAttrValue("class", SCAFFOLD_EDIT_FIELD, CLASS_SCAFFOLD_EDIT_FIELD)
          }
      }
    }
    dto.removeElement(CLASS_SCAFFOLD_EDIT_ITERATOR)
  }

  def actTriggerEditLogic(dto: CustomJsoupElement, ves: Valiables): String = {
    val tableName = getTableName(ves.nowTable.get)
    // TODO: PARENTが付いている場合には、その親要素の部分にTwirlコードを付与するといった使い方を実現したい。
    // TODO: CHILDが付いている場合には、その子要素の部分にTwirlコードを付与するといった使い方を実現したい。
    // custom tag 処理
    val CUSTOM_TAG = "CUSTOM_TAG"
    dto
      .scopeClosestElement(CLASS_SCAFFOLD_EDIT_LOGIC_FORM)
      .renameTag(CUSTOM_TAG)
      .replaceAttr(CUSTOM_TAG, "name", getEditSubmitFormName(ves.nowTable.get))
      .removeAttrValue("class", SCAFFOLD_EDIT_LOGIC_FORM)

    // custom tag replace 処理
    dto.getElement.root().ownerDocument().body().children().toString.split("\n").map {
      case ln if ln.trim.startsWith("<" + CUSTOM_TAG)  =>
        val formAttr: String =
          dto
            .scopeRoot
            .scopeClosestElement(CUSTOM_TAG).getAttr.map(x =>s""" '${x._1} -> "${x._2}"""").mkString(",")
        s"""|@helper.form(${getRouteController(ves.nowTable.get)}.$METHOD_CONTRL_UPDATE(${getPrimaryKeySyntaxTypeInt(ves)}), $formAttr) {""".stripMargin
      case ln if ln.trim.startsWith("</" + CUSTOM_TAG) => s"}".stripMargin
      case ln                                          => ln
    }.mkString("\n")
  }

  def actTriggerListField(dto: CustomJsoupElement, ves: Valiables): CustomJsoupElement = {
    for {
      (column, _) <- TwirlLogic.getUseColumn(ves).zipWithIndex
    } yield {
      dto
        .scopeClosestElements(CLASS_SCAFFOLD_LIST_ITERATOR) { element =>
          CustomJsoupElement(element)
            // 自身を複製。複製したものは取っておいて、今のものに対して処理を行うことでリスト処理を実現する。わかりづらいので注意。
            .copySelfAfter
            .removeAttrValue("class", SCAFFOLD_LIST_ITERATOR)
            // 処理開始
            .replaceTextContent(CLASS_SCAFFOLD_LIST_FIELD, column.columnName)
            .replaceTextContent(CLASS_SCAFFOLD_LIST_VALUE, getFieldValueRow(column, ves))
            .removeAttrValue("class", SCAFFOLD_LIST_VALUE, CLASS_SCAFFOLD_LIST_VALUE)
            .removeAttrValue("class", SCAFFOLD_LIST_FIELD, CLASS_SCAFFOLD_LIST_FIELD)
        }
    }
    dto.removeElement(CLASS_SCAFFOLD_LIST_ITERATOR)
  }

  def actTriggerListLogic(dto: CustomJsoupElement, ves: Valiables): String = {
    // TODO: PARENTが付いている場合には、その親要素の部分にTwirlコードを付与するといった使い方を実現したい。
    // TODO: CHILDが付いている場合には、その子要素の部分にTwirlコードを付与するといった使い方を実現したい。
    val rowIterator = dto.scopeRoot.scopeClosestElement(".SCAFFOLD_LIST_LOGIC_ROWS--PARENT")
    val trueTagName = rowIterator.getTagName

    val CUSTOM_TAG = "CUSTOM_TAG"

    // custom tag 処理
    rowIterator.renameTag(CUSTOM_TAG)
    // custom tag replace 処理
    dto.getElement.root().ownerDocument().body().children().toString.split("\n").map {
      case ln if ln.trim.startsWith("<" + CUSTOM_TAG)  =>
        s"""|@$DTO_PARAM.${getTableFieldName(ves.nowTable.get)}DtoList.map { $ROW_PARAM =>
            |  <$trueTagName>""".stripMargin
      case ln if ln.trim.startsWith("</" + CUSTOM_TAG) => s"  </$trueTagName>\n}".stripMargin
      case ln                                          => ln
    }.mkString("\n")
  }

  def actTriggerButton(dto: CustomJsoupElement, ves: Valiables): CustomJsoupElement = {
    val btnList = Seq(
      SCAFFOLD_LIST_BTN_SHOW_CREATE,
      SCAFFOLD_LIST_BTN_SHOW_DETAIL,
      SCAFFOLD_LIST_BTN_SHOW_EDIT,
      SCAFFOLD_LIST_BTN_SUBMIT_DELETE,
      SCAFFOLD_DETAIL_BTN_SHOW_EDIT,
      SCAFFOLD_CREATE_BTN_SUBMIT_CREATE,
      SCAFFOLD_EDIT_BTN_SUBMIT_EDIT,
      SCAFFOLD_DETAIL_BTN_SUBMIT_DELETE,
      SCAFFOLD_CREATE_BTN_CANCEL,
      SCAFFOLD_EDIT_BTN_CANCEL,
      SCAFFOLD_DETAIL_BTN_CANCEL
    )
    btnList.foreach { btn =>
      dto
        .replaceElementAndOverwriteAttr("." + btn, getTagDefault("a", None))
        .replaceAttr("." + btn, getButtonAttrMap(btn, ves))
        .replaceTextContent("." + btn, getButtonI18nTextContent(btn))
    }
    dto
      .removeClassAttrValueForDescendant(btnList)
  }

}

trait CrudCommons extends TwirlConst {

  def getPrimaryKeySyntaxTypeInt(ves: Valiables): String = {
    val primaryKeyFieldName = ves.nowTable.flatMap(_.columnList.headOption).map(_.columnName).getOrElse("")
    val isUsevResultDto = ves.dtos.confDto.modulesTwirlScaffoldThemesModulesResultDtoIsUse
    if (isUsevResultDto == "YES") {
      s"""$DTO_PARAM.${getAgEditFieldForm(ves.nowTable.get)}("${toSnakeCase(primaryKeyFieldName)}").value.getOrElse("-1").toInt"""
    } else {
      ""
    }
  }

  def getButtonAttrMap(btn: String, ves: Valiables): Map[String, String] = {
    val primaryKeyFieldName = ves.nowTable.flatMap(_.columnList.headOption).map(_.columnName).getOrElse("")
    val isUsevResultDto = ves.dtos.confDto.modulesTwirlScaffoldThemesModulesResultDtoIsUse
    if (isUsevResultDto == "YES") {
      btn match {
        case SCAFFOLD_LIST_BTN_SHOW_CREATE     => Map("href" -> s"""@${getRouteController(ves.nowTable.get)}.$METHOD_CONTRL_SHOWCREATE()""")
        case SCAFFOLD_LIST_BTN_SHOW_DETAIL     => Map("href" -> s"""@${getRouteController(ves.nowTable.get)}.$METHOD_CONTRL_SHOWDETAIL($ROW_PARAM.$primaryKeyFieldName)""")
        case SCAFFOLD_LIST_BTN_SHOW_EDIT       => Map("href" -> s"""@${getRouteController(ves.nowTable.get)}.$METHOD_CONTRL_SHOWEDIT($ROW_PARAM.$primaryKeyFieldName)""")
        case SCAFFOLD_LIST_BTN_SUBMIT_DELETE   => Map("href" -> s"""@${getRouteController(ves.nowTable.get)}.$METHOD_CONTRL_DESTROY($ROW_PARAM.$primaryKeyFieldName)""")
        case SCAFFOLD_EDIT_BTN_SUBMIT_EDIT     => Map("href" -> "javascript:void(0)", "onclick" -> s"document.${getEditSubmitFormName(ves.nowTable.get)}.submit();return false;")
        case SCAFFOLD_EDIT_BTN_CANCEL          => Map("href" -> s"""@${getRouteController(ves.nowTable.get)}.$METHOD_CONTRL_SHOWINDEX()""")
        case SCAFFOLD_DETAIL_BTN_SHOW_EDIT     => Map("href" -> s"""@${getRouteController(ves.nowTable.get)}.$METHOD_CONTRL_SHOWEDIT(${getPrimaryKeySyntaxTypeInt(ves)})""")
        case SCAFFOLD_DETAIL_BTN_SUBMIT_DELETE => Map("href" -> s"""@${getRouteController(ves.nowTable.get)}.$METHOD_CONTRL_DESTROY(${getPrimaryKeySyntaxTypeInt(ves)})""")
        case SCAFFOLD_DETAIL_BTN_CANCEL        => Map("href" -> s"""@${getRouteController(ves.nowTable.get)}.$METHOD_CONTRL_SHOWINDEX()""")
        case SCAFFOLD_CREATE_BTN_SUBMIT_CREATE => Map("href" -> "javascript:void(0)", "onclick" -> s"document.${getCreateSubmitFormName(ves.nowTable.get)}.submit();return false;")
        case SCAFFOLD_CREATE_BTN_CANCEL        => Map("href" -> s"""@${getRouteController(ves.nowTable.get)}.$METHOD_CONTRL_SHOWINDEX()""")
        case _                                 => Map()
      }
    } else {
      Map()
    }
  }


  def getCreateFieldValue(column: GeneratedColumn, ves: Valiables): String = {
    val isUsevResultDto = ves.dtos.confDto.modulesTwirlScaffoldThemesModulesResultDtoIsUse
    if (isUsevResultDto == "YES") {
      "@{" + DTO_PARAM + "." + getAgCreateFieldForm(ves.nowTable.get) + "(\"" + toSnakeCase(column.columnName) + "\").value}"
    } else {
      ""
    }
  }

  def getEditFieldValue(column: GeneratedColumn, ves: Valiables): String = {
    val isUsevResultDto = ves.dtos.confDto.modulesTwirlScaffoldThemesModulesResultDtoIsUse
    if (isUsevResultDto == "YES") {
      "@{" + DTO_PARAM + "." + getAgEditFieldForm(ves.nowTable.get) + "(\"" + toSnakeCase(column.columnName) + "\").value}"
    } else {
      ""
    }
  }

  def getFieldValueRow(column: GeneratedColumn, ves: Valiables): String = {
    val tableName = getTableName(ves.nowTable.get)
    val isUsevResultDto = ves.dtos.confDto.modulesTwirlScaffoldThemesModulesResultDtoIsUse
    if (isUsevResultDto == "YES") {
      column.columnType match {
        case "Int"                        => s"""@$ROW_PARAM.${column.columnName}"""
        case "Option[Int]"                => s"""@$ROW_PARAM.${column.columnName}.getOrElse("")"""
        case "String"                     => s"""@$ROW_PARAM.${column.columnName}"""
        case "Option[String]"             => s"""@$ROW_PARAM.${column.columnName}.getOrElse("")"""
        case "java.sql.Timestamp"         => s"""@$ROW_PARAM.${column.columnName}"""
        case "Option[java.sql.Timestamp]" => s"""@$ROW_PARAM.${column.columnName}.getOrElse("")"""
        case _                            => s"""@$ROW_PARAM.${column.columnName}"""
      }
    } else {
      ""
    }
  }

}