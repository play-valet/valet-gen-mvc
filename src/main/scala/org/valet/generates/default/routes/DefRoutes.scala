package org.valet.generates.default.routes

import java.io.File

import org.valet.common._

import scala.io.Source

object DefRoutes extends ValetUtility {

  def strShowIndex (nowTable: GeneratedTable): String    = s"""GET         /${getTableName(nowTable).toLowerCase}                   $pkg_controller_ag.${getAgController(nowTable)}.showIndex"""
  def strShowDetail(nowTable: GeneratedTable): String    = s"""GET         /${getTableName(nowTable).toLowerCase}/detail/:id        $pkg_controller_ag.${getAgController(nowTable)}.showDetail(id:Int)"""
  def strShowCreate(nowTable: GeneratedTable): String    = s"""GET         /${getTableName(nowTable).toLowerCase}/store             $pkg_controller_ag.${getAgController(nowTable)}.showCreate"""
  def strStore     (nowTable: GeneratedTable): String    = s"""POST        /${getTableName(nowTable).toLowerCase}/store             $pkg_controller_ag.${getAgController(nowTable)}.store"""
  def strShowEdit  (nowTable: GeneratedTable): String    = s"""GET         /${getTableName(nowTable).toLowerCase}/edit/:id          $pkg_controller_ag.${getAgController(nowTable)}.showEdit(id:Int)"""
  def strUpdate    (nowTable: GeneratedTable): String    = s"""POST        /${getTableName(nowTable).toLowerCase}/edit/:id          $pkg_controller_ag.${getAgController(nowTable)}.update(id:Int)"""
  def strDestroy   (nowTable: GeneratedTable): String    = s"""GET         /${getTableName(nowTable).toLowerCase}/destroy/:id       $pkg_controller_ag.${getAgController(nowTable)}.destroy(id:Int)"""

  def methodList(nowTable: GeneratedTable): Seq[String] = Seq(
    strShowIndex(nowTable),
    strShowDetail(nowTable),
    strShowCreate(nowTable),
    strStore(nowTable),
    strShowEdit(nowTable),
    strUpdate(nowTable),
    strDestroy(nowTable)
  )

  def getAll(nowTable: GeneratedTable, dtos: ScaffoldDtos): Unit = {

    val filepath = "./conf/routes"

    makeFileIfNotExist(new File(filepath))
    val source: Seq[String] = Source.fromFile(filepath, "UTF-8").getLines().toSeq
    val formatSource = source.map(str => toSingleSpace(str))
    val list :Seq[String] = methodList(nowTable).map { methodRoute =>
      formatSource.contains(toSingleSpace(methodRoute)) match {
        case true  => None
        case false => Some(methodRoute+"\n")
      }
    }.filter(_.isDefined).map(_.get)

    if(list.nonEmpty) {
      writeAppending(new File(filepath), "\n")
      list.foreach { addrow =>
        writeAppending(new File(filepath), addrow)
      }
    }
  }

}
