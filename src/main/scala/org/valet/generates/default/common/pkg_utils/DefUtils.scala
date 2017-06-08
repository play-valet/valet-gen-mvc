package org.valet.generates.default.common.pkg_utils

import org.valet.common._

object DefUtils extends ValetUtility {

  def getAll(dtos: ScaffoldDtos): String = {
    s"""package ${pkg_common_util_ag}
       |
       |object ${default_ag_utility} extends {
       |
       |} with ${default_ag_utility}
       |
       |trait ${default_ag_utility} {
       |
       |  def wrapOpt[T](list: Seq[T]): Option[Seq[T]] = {
       |    !list.isEmpty match {
       |      case true  => Some(list)
       |      case false => None
       |    }
       |  }
       |
       |  /***********************
       |   * Date Time Utils
       |   *******************/
       |
       |  def fromSqlTimestampToLocalDateTime(ts: java.sql.Timestamp): java.time.LocalDateTime = {
       |    ts.toLocalDateTime
       |  }
       |
       |  def fromLocalDateTimeToSqlTimestamp(ldt: java.time.LocalDateTime): java.sql.Timestamp = {
       |    java.sql.Timestamp.valueOf(ldt)
       |  }
       |
       |  def nowTimestamp: java.sql.Timestamp = {
       |    val now: java.time.LocalDateTime = java.time.LocalDateTime.now()
       |    fromLocalDateTimeToSqlTimestamp(now)
       |  }
       |
       |}
       |
    """.stripMargin
  }

  def getAllImplement(dtos: ScaffoldDtos): String = {
    s"""package $pkg_common_util_im
       |
       |import $pkg_common_util_ag.$default_ag_utility
       |
       |object $default_im_utility extends {
       |
       |} with $default_im_utility
       |
       |trait $default_im_utility extends $default_ag_utility {
       |
       |
       |}
       |
    """.stripMargin
  }

}
