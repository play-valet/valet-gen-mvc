package module.twirlScaffoldThemes.generates.controller

import org.valet.common._

object DefTwirl_Sync_Controller extends ValetUtility {

//  def getViewPackage(table: SlickTable): String = {
//    val path = ScUtils.getDirFileList("./app/views/autogen", Seq()).filter(file => file.getParentFile.getName == "crud_template").headOption
//    if (path.isDefined) {
//      val tmp1 = path.get.getParentFile.getParentFile.getPath
//      val tmp2 = tmp1.replace("./app/views/autogen/", "").replace("/", ".") + "." + ScUtils.pTableName(table)
//      tmp2
//    } else {
//      ""
//    }
//  }

  def getAll(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {
    ""
  }

}
