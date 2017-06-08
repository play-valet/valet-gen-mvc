package module.twirlScaffoldThemes.generates.dto

import org.valet.common._

object DefViewDto extends ValetUtility {

  def getAll(dtos: ScaffoldDtos): String = {
    //    val tableNameRowAccount = dtos.confDto.modulesAuthTableAccountTableName
    s"""package $pkg_model_dto_ag
       |
       |import $pkg_model_tables_ag.$default_TABLES_NAME._
       |
       |case class $default_ag_resultViewDtos(
       |                    account: Option[String]
       |${paramList(dtos)}
       |                  ) {
       |  def copy(
       |            account: Option[String] = this.account
       |${copyParamList(dtos)}
       |          ) = {
       |    new $default_ag_resultViewDtos(
       |      account
       |${newParamList(dtos)}
       |    )
       |  }
       |}
       |
     """.stripMargin

  }

  def paramList(dtos: ScaffoldDtos): String = {
    dtos.generatedTables.map { nowTable =>
      val isScaffoldList: Seq[String] = getIsScaffoldList(dtos, nowTable)
      if (isScaffoldList.contains(valetconf_isscaffoldlist_controller)) {
        val tableName = getTableName(nowTable)
        getTableFieldName(nowTable)
        s"""|                    ,${toFirstCharLower(tableName)}Dto: Option[${nowTable.tableName}] = None""".stripMargin + "\n" +
          s"""|                    ,${toFirstCharLower(tableName)}DtoList: Seq[${nowTable.tableName}] = Seq()""".stripMargin
      } else {
        ""
      }
    }.filter(!_.isEmpty).mkString("\n")
  }

  def copyParamList(dtos: ScaffoldDtos): String = {
    dtos.generatedTables.map { nowTable =>
      val isScaffoldList: Seq[String] = getIsScaffoldList(dtos, nowTable)
      if (isScaffoldList.contains(valetconf_isscaffoldlist_controller)) {
        val tableName = getTableName(nowTable)
        s"""|            ,${toFirstCharLower(tableName)}Dto: Option[${nowTable.tableName}] = this.${toFirstCharLower(tableName)}Dto""".stripMargin + "\n" +
          s"""|            ,${toFirstCharLower(tableName)}DtoList: Seq[${nowTable.tableName}] = this.${toFirstCharLower(tableName)}DtoList""".stripMargin
      } else {
        ""
      }
    }.filter(!_.isEmpty).mkString("\n")
  }

  def newParamList(dtos: ScaffoldDtos): String = {
    dtos.generatedTables.map { nowTable =>
      val isScaffoldList: Seq[String] = getIsScaffoldList(dtos, nowTable)
      if (isScaffoldList.contains(valetconf_isscaffoldlist_controller)) {
        val tableName = getTableName(nowTable)
        s"""|      ,${toFirstCharLower(tableName)}Dto""".stripMargin + "\n" +
          s"""|      ,${toFirstCharLower(tableName)}DtoList""".stripMargin
      } else {
        ""
      }
    }.filter(!_.isEmpty).mkString("\n")
  }


}
