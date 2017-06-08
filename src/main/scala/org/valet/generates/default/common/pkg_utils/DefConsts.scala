package org.valet.generates.default.common.pkg_utils

import org.valet.common.{ScaffoldDtos, ValetUtility}

object DefConsts  extends ValetUtility{

  def getAll(dtos: ScaffoldDtos): String = {
    s"""package $pkg_common_util_ag
       |
       |object ${default_ag_const} extends {
       |
       |} with ${default_ag_const}
       |
       |trait ${default_ag_const} {
       |
       |  val AG_SUCCESS = "success"
       |  val AG_ERROR = "error"
       |  val AG_INFO = "info"
       |  val AG_WARNING = "warning"
       |  val SUCCESS_CREATE = "success.create"
       |  val SUCCESS_EDIT = "success.edit"
       |  val SUCCESS_UPDATE = "success.update"
       |  val SUCCESS_DELETE = "success.delete"
       |  val ERROR_FORM = "error.form"
       |  val ERROR_CREATE = "error.create"
       |  val ERROR_CREATE_BY_DB = "error.create.by.db"
       |  val ERROR_EDIT = "error.edit"
       |  val ERROR_EDIT_BY_DB = "error.edit.by.db"
       |  val ERROR_DELETE = "error.delete"
       |  val ERROR_DELETE_BY_DB = "error.delete.by.db"
       |
       |}
       |
    """.stripMargin
  }

  def getAllImplement(dtos: ScaffoldDtos): String = {
    s"""package $pkg_common_util_im
       |
       |import $pkg_common_util_ag.$default_ag_const
       |
       |object $default_im_const extends {
       |
       |} with $default_im_const
       |
       |trait $default_im_const extends $default_ag_const {
       |
       |
       |}
       |
    """.stripMargin
  }
}
