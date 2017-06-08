package module.twirlScaffoldThemes.generates.dto

import org.valet.common._

object DefViewDto extends ValetUtility {

  def getAll(dtos: ScaffoldDtos): String = {
    s"""package $pkg_model_dto_ag
       |
       |import $pkg_form_ag._
       |import $pkg_model_tables_ag.$default_TABLES_NAME._
       |import play.api.data.Form
       |
       |case class ${default_ag_resultViewDtos(dtos)}(
       |                    account: Option[String]
       |${paramList(dtos)}
       |                  ) {
       |  def copy(
       |            account: Option[String] = this.account
       |${copyParamList(dtos)}
       |          ) = {
       |    new ${default_ag_resultViewDtos(dtos)}(
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
      if (isScaffoldList.contains(VALETCONF_ISSCAFFOLDLIST_CONTROLLER)) {
        s"""|                    ,${getAgCreateFieldForm(nowTable)}: Form[${getAgCreateForm(nowTable)}] = $pkg_form_ag.${getObj(getAgCreateForm(nowTable))}.${getAgCreateFieldForm(nowTable)}
            |                    ,${getAgEditFieldForm(nowTable)}: Form[${getAgEditForm(nowTable)}] = $pkg_form_ag.${getObj(getAgEditForm(nowTable))}.${getAgEditFieldForm(nowTable)}
            |                    ,${getTableFieldName(nowTable)}DtoList: Seq[${nowTable.tableName}] = Seq()""".stripMargin
      } else {
        ""
      }
    }.filter(!_.isEmpty).mkString("\n")
  }

  def copyParamList(dtos: ScaffoldDtos): String = {
    dtos.generatedTables.map { nowTable =>
      val isScaffoldList: Seq[String] = getIsScaffoldList(dtos, nowTable)
      if (isScaffoldList.contains(VALETCONF_ISSCAFFOLDLIST_CONTROLLER)) {
        s"""|                    ,${getAgCreateFieldForm(nowTable)}: Form[${getAgCreateForm(nowTable)}] = this.${getAgCreateFieldForm(nowTable)}
            |                    ,${getAgEditFieldForm(nowTable)}: Form[${getAgEditForm(nowTable)}] = this.${getAgEditFieldForm(nowTable)}
            |                    ,${getTableFieldName(nowTable)}DtoList: Seq[${nowTable.tableName}] = this.${getTableFieldName(nowTable)}DtoList""".stripMargin
      } else {
        ""
      }
    }.filter(!_.isEmpty).mkString("\n")
  }

  def newParamList(dtos: ScaffoldDtos): String = {
    dtos.generatedTables.map { nowTable =>
      val isScaffoldList: Seq[String] = getIsScaffoldList(dtos, nowTable)
      if (isScaffoldList.contains(VALETCONF_ISSCAFFOLDLIST_CONTROLLER)) {
        s"""|                    ,${getAgCreateFieldForm(nowTable)}
            |                    ,${getAgEditFieldForm(nowTable)}
            |                    ,${getTableFieldName(nowTable)}DtoList""".stripMargin
      } else {
        ""
      }
    }.filter(!_.isEmpty).mkString("\n")
  }


}
