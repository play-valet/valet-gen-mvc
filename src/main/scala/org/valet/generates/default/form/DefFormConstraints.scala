package org.valet.generates.default.form

import org.valet.common._

object DefFormConstraints extends ValetUtility {

  def getAll(dtos: ScaffoldDtos): String = {
    val email : String = "\"\"\"^[a-zA-Z0-9\\.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$\"\"\""
    val pass : String  = "\"\"\"^(?=.*?[a-z])(?=.*?\\d)[A-Za-z\\d@_]{8,20}$\"\"\""

    s"""|
        |package ${pkg_form_ag}
        |
        |import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}
        |
        |object ${default_ag_formconstraint} extends {
        |
        |} with ${default_ag_formconstraint}
        |
        |trait ${default_ag_formconstraint} {
        |
        |  private val emailRegex =  ${email}.r
        |
        |  def emailAddress: Constraint[String] = Constraint[String]("constraint.email") { e =>
        |    if ((e == null) || (e.trim.isEmpty)) Valid // use nonEmpty or custom nonEmpty constraints
        |    else emailRegex.findFirstMatchIn(e)
        |      .map(_ => Valid)
        |      .getOrElse(Invalid(ValidationError("error.email")))
        |  }
        |
        |  private val passwordLightRegex =  ${pass}.r
        |
        |  def passWordLight: Constraint[String] = Constraint[String]("constraint.password") { e =>
        |    if ((e == null) || (e.trim.isEmpty)) Valid // use nonEmpty or custom nonEmpty constraints
        |    else passwordLightRegex.findFirstMatchIn(e)
        |      .map(_ => Valid)
        |      .getOrElse(Invalid(ValidationError("error.password")))
        |  }
        |}
        |
        |
     """.stripMargin
  }




}
