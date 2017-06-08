package org.valet.generates.default.routes

import java.io.File

import org.valet.common._

import scala.io.Source

object DefRoutes extends ValetUtility {

  def strShowIndex(tableName: String): String  = s"""GET         /${toFirstCharLower(tableName)}                      controllers.${tableName}Controller.showIndex"""
  def strShowDetail(tableName: String): String = s"""GET         /${toFirstCharLower(tableName)}/detail/:id           controllers.${tableName}Controller.showDetail(id:Int)"""
  def strShowCreate(tableName: String): String = s"""GET         /${toFirstCharLower(tableName)}/store                controllers.${tableName}Controller.showCreate"""
  def strStore(tableName: String): String      = s"""POST        /${toFirstCharLower(tableName)}/store                controllers.${tableName}Controller.store"""
  def strShowEdit(tableName: String): String   = s"""GET         /${toFirstCharLower(tableName)}/edit/:id             controllers.${tableName}Controller.showEdit(id:Int)"""
  def strUpdate(tableName: String): String     = s"""POST        /${toFirstCharLower(tableName)}/edit/:id             controllers.${tableName}Controller.update(id:Int)"""
  def strDestroy(tableName: String): String    = s"""GET         /${toFirstCharLower(tableName)}/destroy/:id          controllers.${tableName}Controller.destroy(id:Int)"""

  def methodList(tableName: String): Seq[String] = Seq(
    strShowIndex(tableName),
    strShowDetail(tableName),
    strShowCreate(tableName),
    strStore(tableName),
    strShowEdit(tableName),
    strUpdate(tableName),
    strDestroy(tableName)
  )

  def getAll(nowTable: GeneratedTable, dtos: ScaffoldDtos): Unit = {

    makeFileIfNotExist(new File("conf/routes"))

    val source: Seq[String] = Source.fromFile("conf/routes", "UTF-8").getLines().toSeq
    val formatSource = source.map(str => toSingleSpace(str))
    val list :Seq[String] = methodList(getTableName(nowTable)).map { methodRoute =>
      formatSource.contains(toSingleSpace(methodRoute)) match {
        case true  => None
        case false => Some(methodRoute+"\n")
      }
    }.filter(_.isDefined).map(_.get)

    if(list.nonEmpty) {
      writeAppending(new File("conf/routes"), "\n")
      list.foreach { addrow =>
        writeAppending(new File("conf/routes"), addrow)
      }
    }
  }

}
