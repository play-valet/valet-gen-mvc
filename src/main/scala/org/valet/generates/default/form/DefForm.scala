package org.valet.generates.default.form

import org.valet.common._

object DefForm extends ValetUtility with DefFormUtils {

  def getAll(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {
    s"""|package $pkg_form_ag
        |
        |import play.api.data.Form
        |import play.api.data.Forms._
        |import $pkg_model_tables_ag.$default_TABLES_NAME._
        |
        |${getCreateFormDto(nowTable, dtos)}
        |${getCreateFormMapping(nowTable, dtos)}
        |${getEditFormDto(nowTable, dtos)}
        |${getEditFormMapping(nowTable, dtos)}
        |
        |object ${getAgMappingForm(nowTable)} {
        |
        |${getCreate_toEntityMethod(nowTable, dtos)}
        |${getEdit_toEntityMethod(nowTable, dtos)}
        |${getEdit_toFormMethod(nowTable, dtos)}
        |
        |}
    """.stripMargin
  }

  private def getEdit_toFormMethod(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {
    val className = getAgEditForm(nowTable)
    s"""|  // db:${getTableName(nowTable)}
        |  def toEditForm(id: Int, form: ${nowTable.tableName}): Form[$className] = {
        |    ${getObj(className)}.${toFirstCharLower(className)}.fill($className(id, ${getTailParam_noview(nowTable, dtos)}))
        |  }""".stripMargin
  }

  private def getCreate_toEntityMethod(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {
    val className = getAgCreateForm(nowTable)
    s"""|  // db:${getTableName(nowTable)}
        |  def toEntity(form: $className): ${nowTable.tableName} = {
        |    ${nowTable.tableName}(-1, ${getTailParam(nowTable, dtos)})
        |  }""".stripMargin
  }

  private def getEdit_toEntityMethod(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {
    val className = getAgEditForm(nowTable)
    s"""|  // db:${getTableName(nowTable)}
        |  def toEntity(id: Int, form: $className): ${nowTable.tableName} = {
        |    ${nowTable.tableName}(id, ${getTailParam(nowTable, dtos)})
        |  }""".stripMargin
  }

  private def getTailParam(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {
    nowTable.columnList.tail.map { column =>
      if (IsColumn_Having_NO_VIEW_MODEL(column, nowTable, dtos)) {
        defaultMappingValues(column.columnType)
      } else {
        "form." + column.columnName
      }
    }.filter(!_.isEmpty).mkString(", ")
  }

  private def getTailParam_noview(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {
    nowTable.columnList.tail.map { column =>
      if (IsColumn_Having_NO_VIEW_MODEL(column, nowTable, dtos)) {
        ""
      } else {
        "form." + column.columnName
      }
    }.filter(!_.isEmpty).mkString(", ")
  }

  private def getCreateFormMapping(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {
    val iterateColumnList = nowTable.columnList.tail
    val caseClass = getAgCreateForm(nowTable)
    val caseClassField = getAgCreateFieldForm(nowTable)
    s"""
       |object ${getObj(caseClass)} {
       |  val $caseClassField = Form(
       |    mapping(
       | """.stripMargin +
      iterateColumnList.map { column =>
        if (IsColumn_Having_NO_VIEW_MODEL(column, nowTable, dtos)) {
          ""
        } else {
          s"""|     "${toSnakeCase(column.columnName)}" -> ${alterFormParam(column.columnType)}""".stripMargin
        }
      }.filter(!_.isEmpty).mkString(",\n") +
      s"""|
          |    )($caseClass.apply)($caseClass.unapply)
          |  )
          |}
          |""".stripMargin
  }

  private def getEditFormMapping(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {
    val iterateColumnList = nowTable.columnList
    val caseClass = getAgEditForm(nowTable)
    val caseClassField = getAgEditFieldForm(nowTable)
    s"""
       |object ${getObj(caseClass)} {
       |  val $caseClassField = Form(
       |    mapping(
       | """.stripMargin +
      iterateColumnList.map { column =>
        if (IsColumn_Having_NO_VIEW_MODEL(column, nowTable, dtos)) {
          ""
        } else {
          s"""|     "${toSnakeCase(column.columnName)}" -> ${alterFormParam(column.columnType)}""".stripMargin
        }
      }.filter(!_.isEmpty).mkString(",\n") +
      s"""|
          |    )($caseClass.apply)($caseClass.unapply)
          |  )
          |}
          |""".stripMargin
  }

  private def getCreateFormDto(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {
    val iterateColumnList = nowTable.columnList.tail
    val caseClass = getAgCreateForm(nowTable)
    s"""
       |case class $caseClass(""".stripMargin +
      iterateColumnList.map { column =>
        if (IsColumn_Having_NO_VIEW_MODEL(column, nowTable, dtos)) {
          ""
        } else {
          s""" ${column.columnName}: ${column.columnType}""".stripMargin
        }
      }.filter(!_.isEmpty).mkString(",") +
      """|)
      """.stripMargin
  }

  private def getEditFormDto(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {
    val iterateColumnList = nowTable.columnList
    val caseClass = getAgEditForm(nowTable)
    s"""
       |case class $caseClass(""".stripMargin +
      iterateColumnList.map { column =>
        if (IsColumn_Having_NO_VIEW_MODEL(column, nowTable, dtos)) {
          ""
        } else {
          s""" ${column.columnName}: ${column.columnType}""".stripMargin
        }
      }.filter(!_.isEmpty).mkString(",") +
      """|)
      """.stripMargin
  }

}

trait DefFormUtils extends ValetUtility {

  def IsColumn_Having_NO_VIEW_MODEL(column: GeneratedColumn, nowTable: GeneratedTable, dtos: ScaffoldDtos): Boolean = {
    try {
      val confNowTable: ConfTable = dtos.confDto.tableTableList.filter(p => p.tableName == getTableName(nowTable)).head
      val confNowTableColumn = confNowTable.columnList.filter(f => toCamelCase(f.cName) == column.columnName).head
      confNowTableColumn.cAttr.contains(VALETCONF_NO_VIEW_MODEL)
    } catch {
      case _: Throwable => println("[error] REASON: .conf-table is not match slick-table"); false
    }
  }

  def defaultMappingValues(str: String): String = {
    str match {
      case "String"                     => """ "" """
      case "Option[String]"             => "None"
      case "Int"                        => "-1"
      case "Option[Int]"                => "None"
      case "Boolean"                    => "true"
      case "Option[Boolean]"            => "None"
      case "java.sql.Timestamp"         => "java.sql.Timestamp.valueOf(java.time.LocalDateTime.now())"
      case "Option[java.sql.Timestamp]" => "None"
      case _                            => ""

    }
  }

  def alterFormParam(str: String): String = {
    str match {
      case "String"          => "text"
      case "Option[String]"  => "optional(text)"
      case "Int"             => "number"
      case "Option[Int]"     => "optional(number)"
      case "Boolean"         => "boolean"
      case "Option[Boolean]" => "optional(boolean)"
      case _                 => ""

    }
  }

}