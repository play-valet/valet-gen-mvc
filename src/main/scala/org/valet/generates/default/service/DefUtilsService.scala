package org.valet.generates.default.service

import org.valet.common.{ ScaffoldDtos, ValetUtility}

object DefUtilsService extends ValetUtility {

  def getAll(dtos: ScaffoldDtos): String = {
    s"""package $pkg_model_service_ag
      |
      |import javax.inject.Inject
      |
      |import scalaz.EitherT
      |import scalaz.Scalaz._
      |import com.google.inject.ImplementedBy
      |import $pkg_model_dao_ag._
      |import $pkg_model_tables_ag.Tables._
      |
      |import scala.concurrent.{ExecutionContext, Future}
      |
      |@ImplementedBy(classOf[$default_ag_utilService])
      |trait ${getLike(default_ag_utilService)} {
      |
      |  def tryEither[T](f: => T)(implicit onError: Throwable => Either[Throwable, T] = { t: Throwable => Left(t) }): Either[Throwable, T]
      |
      |  def tryEitherT[T](f: => T)(implicit onError: Throwable => Either[Throwable, T] = { t: Throwable => Left(t) }): EitherT[Future, Throwable, T]
      |
      |  def tryEitherTF[T](f: => Future[T])(implicit onError: Throwable => Either[Throwable, T] = { t: Throwable => Left(t) }): EitherT[Future, Throwable, T]
      |
      |  def tryEitherTO[T](notfunctionThisisObject: Option[T])(implicit onError: Throwable => Either[Throwable, T] = { t: Throwable => Left(t) }): EitherT[Future, Throwable, T]
      |
      |}
      |
      |class $default_ag_utilService @Inject()()
      |                              (implicit ec: ExecutionContext) extends ${getLike(default_ag_utilService)} {
      |
      |  def tryEither[T](f: => T)
      |                  (implicit onError: Throwable => Either[Throwable, T] = { t: Throwable =>
      |                    t match {
      |                      case e: com.typesafe.config.ConfigException => Left(e)
      |                      case e: java.lang.ClassCastException        => Left(e)
      |                      case e: java.lang.IllegalArgumentException  => Left(e)
      |                      case e: java.sql.SQLException               => Left(e)
      |                      case e: Exception                           => Left(e)
      |                    }
      |                  }): Either[Throwable, T] = {
      |    try {
      |      Right(f)
      |    } catch {
      |      case c: Throwable => onError(c)
      |    }
      |  }
      |
      |
      |  def tryEitherTF[T](f: => Future[T])
      |                    (implicit onError: Throwable => Either[Throwable, T] = { t: Throwable =>
      |                      t match {
      |                        case e: com.typesafe.config.ConfigException => Left(e)
      |                        case e: java.lang.ClassCastException        => Left(e)
      |                        case e: java.lang.IllegalArgumentException  => Left(e)
      |                        case e: java.sql.SQLException               => Left(e)
      |                        case e: Exception                           => Left(e)
      |                      }
      |                    }): EitherT[Future, Throwable, T] = {
      |    val fxor: Future[Either[Throwable, T]] = f.map { data =>
      |      try {
      |        Right(data)
      |      } catch {
      |        case c: Throwable => onError(c)
      |      }
      |    }
      |    EitherT.fromEither(fxor)
      |  }
      |
      |  def tryEitherT[T](f: => T)
      |                   (implicit onError: Throwable => Either[Throwable, T] = { t: Throwable =>
      |                     t match {
      |                       case e: com.typesafe.config.ConfigException => Left(e)
      |                       case e: java.lang.ClassCastException        => Left(e)
      |                       case e: java.lang.IllegalArgumentException  => Left(e)
      |                       case e: java.sql.SQLException               => Left(e)
      |                       case e: Exception                           => Left(e)
      |                     }
      |                   }): EitherT[Future, Throwable, T] = {
      |    val tmp: Future[T] = Future.successful(f)
      |    val fxor: Future[Either[Throwable, T]] = tmp.map { data =>
      |      try {
      |        Right(data)
      |      } catch {
      |        case c: Throwable => onError(c)
      |      }
      |    }
      |    EitherT.fromEither(fxor)
      |  }
      |
      |  def tryEitherTO[T](notfunctionThisisObject: Option[T])
      |                    (implicit onError: Throwable => Either[Throwable, T] = { t: Throwable =>
      |                      t match {
      |                        case e: com.typesafe.config.ConfigException => Left(e)
      |                        case e: java.lang.ClassCastException        => Left(e)
      |                        case e: java.lang.IllegalArgumentException  => Left(e)
      |                        case e: java.sql.SQLException               => Left(e)
      |                        case e: Exception                           => Left(e)
      |                      }
      |                    }): EitherT[Future, Throwable, T] = {
      |    val tmp: Future[Either[Throwable, T]] =
      |      Future.successful(try {
      |        Right(notfunctionThisisObject.get)
      |      } catch {
      |        case e: Throwable => onError(e)
      |      })
      |    EitherT.fromEither(tmp)
      |  }
      |
      |
      |}
      |
    """.stripMargin
  }

}
