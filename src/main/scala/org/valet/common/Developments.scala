package org.valet.common

import com.typesafe.config.Config
import org.valet.common.Loaders.getKeyListFromHocon

object Developments extends Utility {

  def getConfKeyValues(conf: Config): Unit = {

    val keyList = getKeyListFromHocon(conf)
//    val configJSON: String = conf.root().render(ConfigRenderOptions.concise())

    val f1 = keyList.map { key =>
      val fieldName = toFirstCharLower(key.split('.').tail.map(str => toFirstCharUpper(toCamelCase(str))).mkString(""))
      conf.getAnyRef(key).getClass.toString match {
        case "class java.lang.String"    => s"""  $fieldName = conf.getString("$key")"""
        case "class java.util.ArrayList" =>
          fieldName match {
            case "tableTableList" => s"""  $fieldName = getConfTable(conf).get"""
            case _                => s"""  $fieldName = conf.getStringList("$key")"""
          }
        case _                           => "----- error -----"
      }
    }.mkString(",\n")
    System.out.println("def getConfDto(conf: Config) = { ConfDto(\n" + f1 + ") }")

    /**
      * ScaffoldingSetting　Case Class用
      */
    val f2: String = keyList.map { key =>
      val fieldName = toFirstCharLower(key.split('.').tail.map(str => toFirstCharUpper(toCamelCase(str))).mkString(""))
      conf.getAnyRef(key).getClass.toString match {
        case "class java.lang.String"    => s"""  $fieldName : String"""
        case "class java.util.ArrayList" =>
          fieldName match {
            case "tableTableList" => s"""  $fieldName : Seq[ConfTable]"""
            case _                => s"""  $fieldName : Seq[String]"""
          }
        case _                           => "----- error -----"
      }
    }.mkString(",\n")
    System.out.println("case class ConfDto(\n" + f2 + ")")
  }

  def echoValAndEcho = {

    Seq(
      "SCAFFOLD_FLASH_MSG_ITERATOR",
    "SCAFFOLD_FLASH_MSG_CLASS_VALUE",
    "SCAFFOLD_FLASH_MSG_FILED",
    "SCAFFOLD_FLASH_MSG_VALUE",
    "SCAFFOLD_NAV_ITERATOR",
    "SCAFFOLD_NAV_CRUD_TABLE_LINK",
    "SCAFFOLD_NAV_CRUD_TABLE_NAME",
    "SCAFFOLD_DETAIL_ITERATOR",
    "SCAFFOLD_DETAIL_FIELD",
    "SCAFFOLD_DETAIL_VALUE",
    "SCAFFOLD_EDIT_LOGIC_FORM",
    "SCAFFOLD_EDIT_ITERATOR",
    "SCAFFOLD_EDIT_FIELD",
    "SCAFFOLD_EDIT_VALUE",
    "SCAFFOLD_LIST_LOGIC_ROWS",
    "SCAFFOLD_LIST_ITERATOR",
    "SCAFFOLD_LIST_FIELD",
    "SCAFFOLD_LIST_VALUE",
    "SCAFFOLD_CREATE_LOGIC_FORM",
    "SCAFFOLD_CREATE_ITERATOR",
    "SCAFFOLD_CREATE_FIELD",
    "SCAFFOLD_CREATE_VALUE",
      "SCAFFOLD_LIST_BTN_SHOW_EDIT"
      ,"SCAFFOLD_LIST_BTN_SHOW_DETAIL"
      ,"SCAFFOLD_LIST_BTN_SUBMIT_DELETE"
      ,"SCAFFOLD_LIST_BTN_SHOW_CREATE"
      ,"SCAFFOLD_EDIT_BTN_SUBMIT_EDIT"
      ,"SCAFFOLD_EDIT_BTN_CANCEL"
      ,"SCAFFOLD_DETAIL_BTN_SHOW_EDIT"
      ,"SCAFFOLD_DETAIL_BTN_CANCEL"
      ,"SCAFFOLD_DETAIL_BTN_SUBMIT_DELETE"
      ,"SCAFFOLD_CREATE_BTN_SUBMIT_CREATE"
      ,"SCAFFOLD_CREATE_BTN_CANCEL"

    ).map { x =>
      println(s"""val ${x} :String     = "${x}"""")
      x
    }.foreach { x =>
      println(s"""val CLASS_${x} :String     = "." + ${x}""")
    }
  }

  def echoClassNames = {
    print(
      Seq(
        "Dao",
        "Service",
        "Form",
        "Controller"
      ).map { f =>
        s"""
           |  // ${f}
           |  def get${f}(generatedTable: GeneratedTable): String = getTableName(generatedTable) + suffix${f}
           |  def getFieldAg${f}(generatedTable: GeneratedTable): String = toFirstCharLower(getAgTableName(generatedTable) + suffix${f})
           |  def getAg${f}(generatedTable: GeneratedTable): String = getAgTableName(generatedTable) + suffix${f}
           |  def get${f}Like(generatedTable: GeneratedTable): String = getLike(getTableName(generatedTable) + suffix${f})
           |  def getAg${f}Like(generatedTable: GeneratedTable): String = getLike(getAgTableName(generatedTable) + suffix${f})
      """.stripMargin
      }.mkString("")
    )
  }
}
