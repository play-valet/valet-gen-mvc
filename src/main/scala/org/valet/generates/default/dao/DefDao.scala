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
        |  def all: Future[Seq[${nowTable.tableName}]]
        |  def findById(id: Int): Future[Option[${nowTable.tableName}]]
        |  def store(content: ${nowTable.tableName}): Future[Int]
        |  def edit(content: ${nowTable.tableName}): Future[Int]
        |  def delete(id: Int): Future[Int]
        |
        |  def allT: EitherT[Future, Throwable, Seq[${nowTable.tableName}]]
        |  def findByIdT(id: Int): EitherT[Future, Throwable, Option[${nowTable.tableName}]]
        |  def storeT(content: ${nowTable.tableName}): EitherT[Future, Throwable, Int]
        |  def editT(content: ${nowTable.tableName}): EitherT[Future, Throwable, Int]
        |  def deleteT(id: Int): EitherT[Future, Throwable, Int]
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
        |  def all: Future[Seq[${nowTable.tableName}]] = {
        |    db.run {
        |      Tables.${getTableName(nowTable)}.result
        |    }
        |  }
        |
        |  def findById(id: Int): Future[Option[${nowTable.tableName}]] = {
        |    db.run {
        |      Tables.${getTableName(nowTable)}.filter(_.${nowTable.columnList.head.columnName} === id.bind).result.headOption
        |    }
        |  }
        |
        |  def store(content: ${nowTable.tableName}): Future[Int] = {
        |    db.run {
        |      Tables.${getTableName(nowTable)}.map { x =>
        |        (${nowTable.columnList.tail.map(c => "x." + c.columnName).mkString(", ")})
        |      } += (${nowTable.columnList.tail.map(c => "content." + c.columnName).mkString(", ")})
        |    }
        |  }
        |
        |  def edit(content: ${nowTable.tableName}): Future[Int] = {
        |    db.run {
        |      Tables.${getTableName(nowTable)}.filter(_.${nowTable.columnList.head.columnName} === content.${nowTable.columnList.head.columnName}.bind).map { x =>
        |        (${nowTable.columnList.tail.map(c => "x." + c.columnName).mkString(", ")})
        |      }.update(${nowTable.columnList.tail.map(c => "content." + c.columnName).mkString(", ")})
        |    }
        |  }
        |
        |  def delete(id: Int): Future[Int] = {
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
