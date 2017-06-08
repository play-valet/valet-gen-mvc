package org.valet.generates.default.dao

import org.valet.common._


object DefDao extends ValetUtility {

  def getAll(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {

    s"""|package $pkg_model_dao_ag
        |
        |import javax.inject.Inject
        |
        |import scalaz.EitherT
        |import com.google.inject.ImplementedBy
        |import $pkg_model_service_ag._
        |import $pkg_model_tables_ag.$default_TABLES_NAME
        |import $pkg_model_tables_ag.$default_TABLES_NAME._
        |import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
        |import slick.driver.JdbcProfile
        |
        |import scala.concurrent.Future
        |
        |
        |@ImplementedBy(classOf[${getAgDao(nowTable)}])
        |trait ${getAgDaoLike(nowTable)} extends HasDatabaseConfigProvider[JdbcProfile] {
        |
        |  def $METHOD_DAO_ALL: Future[Seq[${nowTable.tableName}]]
        |  def $METHOD_DAO_FIND(id: Int): Future[Option[${nowTable.tableName}]]
        |  def $METHOD_DAO_STORE(content: ${nowTable.tableName}): Future[Int]
        |  def $METHOD_DAO_EDIT(content: ${nowTable.tableName}): Future[Int]
        |  def $METHOD_DAO_DELETE(id: Int): Future[Int]
        |
        |  def ${METHOD_DAO_ALL}T: EitherT[Future, Throwable, Seq[${nowTable.tableName}]]
        |  def ${METHOD_DAO_FIND}T(id: Int): EitherT[Future, Throwable, Option[${nowTable.tableName}]]
        |  def ${METHOD_DAO_STORE}T(content: ${nowTable.tableName}): EitherT[Future, Throwable, Int]
        |  def ${METHOD_DAO_EDIT}T(content: ${nowTable.tableName}): EitherT[Future, Throwable, Int]
        |  def ${METHOD_DAO_DELETE}T(id: Int): EitherT[Future, Throwable, Int]
        |
        |""".stripMargin +
      nowTable.columnList.tail.map { column =>
        s"""|  def findBy${toFirstCharUpper(column.columnName)}(${toFirstCharLower(column.columnName)} : ${column.columnType}): Future[Option[${nowTable.tableName}]]
            |""".stripMargin
      }.mkString("") +
      """
        |}
      """.stripMargin +
      s"""
        |
        |class ${getAgDao(nowTable)} @Inject()(protected val dbConfigProvider: DatabaseConfigProvider,
        |                                   val ${getTableFieldName(default_ag_utilService)}: ${getLike(default_ag_utilService)}) extends ${getAgDaoLike(nowTable)} {
        |
        |  import driver.api._
        |
        |  def $METHOD_DAO_ALL: Future[Seq[${nowTable.tableName}]] = {
        |    db.run {
        |      Tables.${getTableName(nowTable)}.result
        |    }
        |  }
        |
        |  def $METHOD_DAO_FIND(id: Int): Future[Option[${nowTable.tableName}]] = {
        |    db.run {
        |      Tables.${getTableName(nowTable)}.filter(_.${nowTable.columnList.head.columnName} === id.bind).result.headOption
        |    }
        |  }
        |
        |  def $METHOD_DAO_STORE(content: ${nowTable.tableName}): Future[Int] = {
        |    db.run {
        |      Tables.${getTableName(nowTable)}.map { x =>
        |        (${nowTable.columnList.tail.map(c => "x." + c.columnName).mkString(", ")})
        |      } += (${nowTable.columnList.tail.map(c => "content." + c.columnName).mkString(", ")})
        |    }
        |  }
        |
        |  def $METHOD_DAO_EDIT(content: ${nowTable.tableName}): Future[Int] = {
        |    db.run {
        |      Tables.${getTableName(nowTable)}.filter(_.${nowTable.columnList.head.columnName} === content.${nowTable.columnList.head.columnName}.bind).map { x =>
        |        (${nowTable.columnList.tail.map(c => "x." + c.columnName).mkString(", ")})
        |      }.update(${nowTable.columnList.tail.map(c => "content." + c.columnName).mkString(", ")})
        |    }
        |  }
        |
        |  def $METHOD_DAO_DELETE(id: Int): Future[Int] = {
        |    db.run {
        |      Tables.${getTableName(nowTable)}.filter(_.${nowTable.columnList.head.columnName} === id.bind).delete
        |    }
        |  }
        |
        |  def allT: EitherT[Future, Throwable, Seq[${nowTable.tableName}]] = {
        |    ${getTableFieldName(default_ag_utilService)}.tryEitherTF(all)
        |  }
        |
        |  def findByIdT(id: Int): EitherT[Future, Throwable, Option[${nowTable.tableName}]] = {
        |    ${getTableFieldName(default_ag_utilService)}.tryEitherTF(findById(id))
        |  }
        |
        |  def storeT(content: ${nowTable.tableName}): EitherT[Future, Throwable, Int] = {
        |    ${getTableFieldName(default_ag_utilService)}.tryEitherTF(store(content))
        |  }
        |
        |  def editT(content: ${nowTable.tableName}): EitherT[Future, Throwable, Int] = {
        |    ${getTableFieldName(default_ag_utilService)}.tryEitherTF(edit(content))
        |  }
        |
        |  def deleteT(id: Int): EitherT[Future, Throwable, Int] = {
        |    ${getTableFieldName(default_ag_utilService)}.tryEitherTF(delete(id))
        |  }
    """.stripMargin +
      nowTable.columnList.tail.map { column =>
        s"""
           |  def findBy${toFirstCharUpper(column.columnName)}(${toFirstCharLower(column.columnName)} : ${column.columnType}):Future[Option[${nowTable.tableName}]] = {
           |    db.run {
           |      Tables.${getTableName(nowTable)}.filter(_.${column.columnName} === ${toFirstCharLower(column.columnName)}.bind).result.headOption
           |    }
           |  }
      """.stripMargin
      }.mkString("") +
      """
        |}
      """.stripMargin
  }
}
