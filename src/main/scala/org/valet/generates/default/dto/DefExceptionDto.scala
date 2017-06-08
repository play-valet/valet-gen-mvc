package org.valet.generates.default.dto

import org.valet.common.{ScaffoldDtos, ValetUtility}

/**
  * Created by keigo on 2017/04/04.
  */
object DefExceptionDto extends ValetUtility {

  def getAll(dtos: ScaffoldDtos): String = {
    s"""package $pkg_model_dto_ag
       |
       |case class ${agPrefix}CustomException(s: String = "error") extends Exception
       |
    """.stripMargin
  }


}
