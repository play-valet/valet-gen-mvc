package org.valet.generates.default.form

import org.valet.common._

object DefForm extends ValetUtility with DefFormUtils {

  def getAll(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {
    val createFormClassName = getAgCreateForm(nowTable)
    val editFormClassName = getAgEditForm(nowTable)
    s"""|package $pkg_form_ag
        |
        |import play.api.data.Form
        |import play.api.data.Forms._
        |import $pkg_model_tables_ag.$default_TABLES_NAME._
        |
        |${getFormDto(nowTable, dtos, createFormClassName, isIncludeId = true)}
        |${getFormMapping(nowTable, dtos, createFormClassName, isIncludeId = true)}
        |${getFormDto(nowTable, dtos, editFormClassName, isIncludeId = false)}
        |${getFormMapping(nowTable, dtos, editFormClassName, isIncludeId = false)}
        |
        |object ${getAgMappingForm(nowTable)} {
        |
        |${getCreate_toEntityMethod(nowTable, dtos, createFormClassName)}
        |${getEdit_toEntityMethod(nowTable, dtos, editFormClassName)}
        |${getEdit_toFormMethod(nowTable, dtos, editFormClassName)}
        |
        |}
    """.stripMargin
  }

  private def getEdit_toFormMethod(nowTable: GeneratedTable, dtos: ScaffoldDtos, className: String): String = {
    s"""|  // db:${getTableName(nowTable)}
        |  def toEditForm(id: Int, form: ${nowTable.tableName}): Form[$className] = {
        |    ${getObj(className)}.${toFirstCharLower(className)}.fill($className(id, ${getTailParam_noview(nowTable, dtos)}))
        |  }""".stripMargin
  }

  private def getCreate_toEntityMethod(nowTable: GeneratedTable, dtos: ScaffoldDtos, className: String): String = {
    s"""|  // db:${getTableName(nowTable)}
        |  def toEntity(form: $className): ${nowTable.tableName} = {
        |    ${nowTable.tableName}(-1, ${getTailParam(nowTable, dtos)})
        |  }""".stripMargin
  }

  private def getEdit_toEntityMethod(nowTable: GeneratedTable, dtos: ScaffoldDtos, className: String): String = {
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


  private def getFormMapping(nowTable: GeneratedTable, dtos: ScaffoldDtos, className: String, isIncludeId: Boolean): String = {
    val iterateColumnList = if (isIncludeId) nowTable.columnList.tail else nowTable.columnList
    s"""
       |object ${getObj(className)} {
       |  val ${toFirstCharLower(className)} = Form(
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
          |    )($className.apply)($className.unapply)
          |  )
          |}
          |""".stripMargin
  }

  private def getFormDto(nowTable: GeneratedTable, dtos: ScaffoldDtos, className: String, isIncludeId: Boolean): String = {
    val iterateColumnList = if (isIncludeId) nowTable.columnList.tail else nowTable.columnList
    s"""
       |case class $className(""".stripMargin +
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