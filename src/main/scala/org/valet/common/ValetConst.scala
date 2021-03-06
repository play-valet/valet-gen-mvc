package org.valet.common

trait ValetConst {

  val VALETCONF_NO_VIEW_MODEL = "NO_VIEW_MODEL"
  val VALETCONF_ISSCAFFOLDLIST_DAO = "dao"
  val VALETCONF_ISSCAFFOLDLIST_SERVICE = "service"
  val VALETCONF_ISSCAFFOLDLIST_CONTROLLER = "controller"

  val METHOD_CONTRL_SHOWINDEX = "showIndex"
  val METHOD_CONTRL_SHOWDETAIL = "showDetail"
  val METHOD_CONTRL_SHOWCREATE = "showCreate"
  val METHOD_CONTRL_SHOWEDIT = "showEdit"
  val METHOD_CONTRL_STORE = "store"
  val METHOD_CONTRL_UPDATE = "update"
  val METHOD_CONTRL_DESTROY = "destroy"

  val METHOD_DAO_ALL = "all"
  val METHOD_DAO_FIND = "findById"
  val METHOD_DAO_STORE = "store"
  val METHOD_DAO_EDIT = "edit"
  val METHOD_DAO_DELETE = "delete"

  val CRUD_FILENAME_CREATE = "create"
  val CRUD_FILENAME_EDIT = "edit"
  val CRUD_FILENAME_LIST = "list"
  val CRUD_FILENAME_DETAIL = "detail"

  val autogenName = "autogen"
  val implementNm = "implement"
  val path_route = "./conf/routes"
  val srccurrent: String = "./app"
  val agPrefix = "Ag"
  val mappingFormPrefix = "Mapping"
  val suffixLike = "Like"
  val suffixObject = "Obj"
  val suffixDao = "Dao"
  val suffixForm = "Form"
  val suffixDto = "Dto"
  val suffixDtoList = "DtoList"
  val suffixService = "Service"
  val suffixController = "Controller"

  val createFormStr = "Create"
  val editFormStr = "Edit"

  val assets_ag             : String = Seq("assets" , autogenName , "").filter(!_.isEmpty).mkString("/")
  val assets_im             : String = Seq("assets" , implementNm , "").filter(!_.isEmpty).mkString("/")
  val common_ag             : String = Seq("common" , autogenName , "").filter(!_.isEmpty).mkString("/")
  val common_im             : String = Seq("common" , implementNm , "").filter(!_.isEmpty).mkString("/")
  val common_util_ag        : String = Seq("common" , autogenName , "utils").filter(!_.isEmpty).mkString("/")
  val common_util_im        : String = Seq("common" , implementNm , "utils").filter(!_.isEmpty).mkString("/")
  val common_auth_ag        : String = Seq("common" , autogenName , "auth").filter(!_.isEmpty).mkString("/")
  val common_auth_im        : String = Seq("common" , implementNm , "auth").filter(!_.isEmpty).mkString("/")
  val common_filter_ag      : String = Seq("common" , autogenName , "filter").filter(!_.isEmpty).mkString("/")
  val common_filter_im      : String = Seq("common" , implementNm , "filter").filter(!_.isEmpty).mkString("/")
  val controller_ag         : String = Seq("controllers" , autogenName , "").filter(!_.isEmpty).mkString("/")
  val controller_im         : String = Seq("controllers" , implementNm , "").filter(!_.isEmpty).mkString("/")
  val form_ag               : String = Seq("forms" , autogenName , "").filter(!_.isEmpty).mkString("/")
  val form_im               : String = Seq("forms" , implementNm , "").filter(!_.isEmpty).mkString("/")
  val model_ag              : String = Seq("models" , autogenName , "").filter(!_.isEmpty).mkString("/")
  val model_im              : String = Seq("models" , implementNm , "").filter(!_.isEmpty).mkString("/")
  val model_dao_ag          : String = Seq("models" , autogenName , "daos").filter(!_.isEmpty).mkString("/")
  val model_dao_im          : String = Seq("models" , implementNm , "daos").filter(!_.isEmpty).mkString("/")
  val model_dto_ag          : String = Seq("models" , autogenName , "dtos").filter(!_.isEmpty).mkString("/")
  val model_dto_im          : String = Seq("models" , implementNm , "dtos").filter(!_.isEmpty).mkString("/")
  val model_service_ag      : String = Seq("models" , autogenName , "services").filter(!_.isEmpty).mkString("/")
  val model_service_im      : String = Seq("models" , implementNm , "services").filter(!_.isEmpty).mkString("/")
  val model_tables_ag       : String = Seq("models" , autogenName , "tables").filter(!_.isEmpty).mkString("/")
  val model_tables_im       : String = Seq("models" , implementNm , "tables").filter(!_.isEmpty).mkString("/")
  val model_repositories_ag : String = Seq("models" , autogenName , "repositories").filter(!_.isEmpty).mkString("/")
  val model_repositories_im : String = Seq("models" , implementNm , "repositories").filter(!_.isEmpty).mkString("/")
  val model_logics_ag       : String = Seq("models" , autogenName , "logics").filter(!_.isEmpty).mkString("/")
  val model_logics_im       : String = Seq("models" , implementNm , "logics").filter(!_.isEmpty).mkString("/")
  val view_ag               : String = Seq("views" , autogenName , "").filter(!_.isEmpty).mkString("/")
  val view_im               : String = Seq("views" , implementNm , "").mkString("/")

  val pkg_assets_ag             : String = assets_ag.replace("/", ".")
  val pkg_assets_im             : String = assets_im.replace("/", ".")
  val pkg_common_ag             : String = common_ag.replace("/", ".")
  val pkg_common_im             : String = common_im.replace("/", ".")
  val pkg_common_util_ag        : String = common_util_ag.replace("/", ".")
  val pkg_common_util_im        : String = common_util_im.replace("/", ".")
  val pkg_common_auth_ag        : String = common_auth_ag.replace("/", ".")
  val pkg_common_auth_im        : String = common_auth_im.replace("/", ".")
  val pkg_common_filter_ag      : String = common_filter_ag.replace("/", ".")
  val pkg_common_filter_im      : String = common_filter_im.replace("/", ".")
  val pkg_controller_ag         : String = controller_ag.replace("/", ".")
  val pkg_controller_im         : String = controller_im.replace("/", ".")
  val pkg_form_ag               : String = form_ag.replace("/", ".")
  val pkg_form_im               : String = form_im.replace("/", ".")
  val pkg_model_ag              : String = model_ag.replace("/", ".")
  val pkg_model_im              : String = model_im.replace("/", ".")
  val pkg_model_dto_ag          : String = model_dto_ag.replace("/", ".")
  val pkg_model_dto_im          : String = model_dto_im.replace("/", ".")
  val pkg_model_dao_ag          : String = model_dao_ag.replace("/", ".")
  val pkg_model_dao_im          : String = model_dao_im.replace("/", ".")
  val pkg_model_service_ag      : String = model_service_ag.replace("/", ".")
  val pkg_model_service_im      : String = model_service_im.replace("/", ".")
  val pkg_model_tables_ag       : String = model_tables_ag.replace("/", ".")
  val pkg_model_tables_im       : String = model_tables_im.replace("/", ".")
  val pkg_model_repositories_ag : String = model_repositories_ag.replace("/", ".")
  val pkg_model_repositories_im : String = model_repositories_im.replace("/", ".")
  val pkg_model_logics_ag       : String = model_logics_ag.replace("/", ".")
  val pkg_model_logics_im       : String = model_logics_im.replace("/", ".")
  val pkg_view_ag               : String = view_ag.replace("/", ".")
  val pkg_view_im               : String = view_im.replace("/", ".")

  val path_assets_ag             : String = srccurrent + "/" + assets_ag
  val path_assets_im             : String = srccurrent + "/" + assets_im
  val path_common_ag             : String = srccurrent + "/" + common_ag
  val path_common_im             : String = srccurrent + "/" + common_im
  val path_common_util_ag        : String = srccurrent + "/" + common_util_ag
  val path_common_util_im        : String = srccurrent + "/" + common_util_im
  val path_common_auth_ag        : String = srccurrent + "/" + common_auth_ag
  val path_common_auth_im        : String = srccurrent + "/" + common_auth_im
  val path_common_filter_ag      : String = srccurrent + "/" + common_filter_ag
  val path_common_filter_im      : String = srccurrent + "/" + common_filter_im
  val path_controller_ag         : String = srccurrent + "/" + controller_ag
  val path_controller_im         : String = srccurrent + "/" + controller_im
  val path_form_ag               : String = srccurrent + "/" + form_ag
  val path_form_im               : String = srccurrent + "/" + form_im
  val path_model_ag              : String = srccurrent + "/" + model_ag
  val path_model_im              : String = srccurrent + "/" + model_im
  val path_model_dto_ag          : String = srccurrent + "/" + model_dto_ag
  val path_model_dto_im          : String = srccurrent + "/" + model_dto_im
  val path_model_dao_ag          : String = srccurrent + "/" + model_dao_ag
  val path_model_dao_im          : String = srccurrent + "/" + model_dao_im
  val path_model_service_ag      : String = srccurrent + "/" + model_service_ag
  val path_model_service_im      : String = srccurrent + "/" + model_service_im
  val path_model_tables_ag       : String = srccurrent + "/" + model_tables_ag
  val path_model_tables_im       : String = srccurrent + "/" + model_tables_im
  val path_model_repositories_ag : String = srccurrent + "/" + model_repositories_ag
  val path_model_repositories_im : String = srccurrent + "/" + model_repositories_im
  val path_model_logics_ag       : String = srccurrent + "/" + model_logics_ag
  val path_model_logics_im       : String = srccurrent + "/" + model_logics_im
  val path_view_ag               : String = srccurrent + "/" + view_ag
  val path_view_im               : String = srccurrent + "/" + view_im

  // default class
  val default_TABLES_NAME : String = "Tables"

  def default_ag_resultViewDtos(dtos: ScaffoldDtos) : String = agPrefix + dtos.confDto.modulesTwirlScaffoldThemesModulesResultDtoName
  def default_im_resultViewDtos(dtos: ScaffoldDtos) : String = dtos.confDto.modulesTwirlScaffoldThemesModulesResultDtoName
  val default_ag_exceptionDtos  : String = agPrefix + "Exceptions"
  val default_im_exceptionDtos  : String = "Exceptions"
  val default_ag_const          : String = agPrefix + "Const"
  val default_im_const          : String = "Const"
  val default_ag_utility        : String = agPrefix + "Utility"
  val default_im_utility        : String = "Utility"
  val default_ag_authConf       : String = agPrefix + "AuthConfig"
  val default_im_authConf       : String = "AuthConfig"
  val default_ag_formconstraint : String = agPrefix + "FormConstraints"
  val default_im_formconstraint : String = "FormConstraints"
  val default_ag_rollConf       : String = agPrefix + "RollConfig"
  val default_im_rollConf       : String = "RollConfig"
  val default_ag_utilService    : String = agPrefix + "UtilService"
  val default_im_utilService    : String = "UtilService"
  val default_ag_errorService   : String = agPrefix + "ErrorService"
  val default_im_errorService   : String = "ErrorService"
  val default_ag_auditService   : String = agPrefix + "AuditService"
  val default_im_auditService   : String = "AuditService"

  def ag_filed_resultViewDtos(dtos: ScaffoldDtos) : String = default_ag_resultViewDtos(dtos).head.toLower + default_ag_resultViewDtos(dtos).tail
  def im_filed_resultViewDtos(dtos: ScaffoldDtos) : String = default_im_resultViewDtos(dtos).head.toLower + default_im_resultViewDtos(dtos).tail
  val ag_filed_exceptionDtos  : String = default_ag_exceptionDtos .head.toLower + default_ag_exceptionDtos .tail
  val im_filed_exceptionDtos  : String = default_im_exceptionDtos .head.toLower + default_im_exceptionDtos .tail
  val ag_filed_const          : String = default_ag_const         .head.toLower + default_ag_const         .tail
  val im_filed_const          : String = default_im_const         .head.toLower + default_im_const         .tail
  val ag_filed_utility        : String = default_ag_utility       .head.toLower + default_ag_utility       .tail
  val im_filed_utility        : String = default_im_utility       .head.toLower + default_im_utility       .tail
  val ag_filed_authConf       : String = default_ag_authConf      .head.toLower + default_ag_authConf      .tail
  val im_filed_authConf       : String = default_im_authConf      .head.toLower + default_im_authConf      .tail
  val ag_filed_rollConf       : String = default_ag_rollConf      .head.toLower + default_ag_rollConf      .tail
  val im_filed_rollConf       : String = default_im_rollConf      .head.toLower + default_im_rollConf      .tail
  val ag_filed_utilService    : String = default_ag_utilService   .head.toLower + default_ag_utilService   .tail
  val im_filed_utilService    : String = default_im_utilService   .head.toLower + default_im_utilService   .tail
  val ag_filed_errorService   : String = default_ag_errorService  .head.toLower + default_ag_errorService  .tail
  val im_filed_errorService   : String = default_im_errorService  .head.toLower + default_im_errorService  .tail
  val ag_filed_auditService   : String = default_ag_auditService  .head.toLower + default_ag_auditService  .tail
  val im_filed_auditService   : String = default_im_auditService  .head.toLower + default_im_auditService  .tail

  def pkg_ag_ResultViewDtos(dtos: ScaffoldDtos): String = pkg_model_dto_ag    + "." + default_ag_resultViewDtos(dtos)
  def pkg_im_ResultViewDtos(dtos: ScaffoldDtos): String = pkg_model_dto_im    + "." + default_im_resultViewDtos(dtos)
  val pkg_ag_ExceptionDtos : String = pkg_model_dto_ag    + "." + default_ag_exceptionDtos
  val pkg_im_ExceptionDtos : String = pkg_model_dto_im    + "." + default_im_exceptionDtos
  val pkg_ag_Const         : String = pkg_common_util_ag   + "." + default_ag_const
  val pkg_im_Const         : String = pkg_common_util_im   + "." + default_im_const
  val pkg_ag_Utility       : String = pkg_common_util_ag   + "." + default_ag_utility
  val pkg_im_Utility       : String = pkg_common_util_im   + "." + default_im_utility
  val pkg_ag_AuthConf      : String = pkg_common_auth_ag   + "." + default_ag_authConf
  val pkg_im_AuthConf      : String = pkg_common_auth_im   + "." + default_im_authConf
  val pkg_ag_RollConf      : String = pkg_common_auth_ag   + "." + default_ag_rollConf
  val pkg_im_RollConf      : String = pkg_common_auth_im   + "." + default_im_rollConf
  val pkg_ag_UtilService   : String = pkg_model_service_ag + "." + default_ag_utilService
  val pkg_im_UtilService   : String = pkg_model_service_im + "." + default_im_utilService
  val pkg_ag_ErrorService  : String = pkg_model_service_ag + "." + default_ag_errorService
  val pkg_im_ErrorService  : String = pkg_model_service_im + "." + default_im_errorService
  val pkg_ag_AuditService  : String = pkg_model_service_ag + "." + default_ag_auditService
  val pkg_im_AuditService  : String = pkg_model_service_im + "." + default_im_auditService

  def path_ag_ResultViewDtos(dtos: ScaffoldDtos): String = path_model_dto_ag    + "/" +  default_ag_resultViewDtos(dtos)
  def path_im_ResultViewDtos(dtos: ScaffoldDtos): String = path_model_dto_im    + "/" +  default_im_resultViewDtos(dtos)
  val path_ag_ExceptionDtos : String = path_model_dto_ag    + "/" +  default_ag_exceptionDtos
  val path_im_ExceptionDtos : String = path_model_dto_im    + "/" +  default_im_exceptionDtos
  val path_ag_Const         : String = path_common_util_ag   + "/" +  default_ag_const
  val path_im_Const         : String = path_common_util_im   + "/" +  default_im_const
  val path_ag_Utility       : String = path_common_util_ag   + "/" +  default_ag_utility
  val path_im_Utility       : String = path_common_util_im   + "/" +  default_im_utility
  val path_ag_AuthConf      : String = path_common_auth_ag   + "/" +  default_ag_authConf
  val path_im_AuthConf      : String = path_common_auth_im   + "/" +  default_im_authConf
  val path_ag_RollConf      : String = path_common_auth_ag   + "/" +  default_ag_rollConf
  val path_im_RollConf      : String = path_common_auth_im   + "/" +  default_im_rollConf
  val path_ag_UtilService   : String = path_model_service_ag + "/" +  default_ag_utilService
  val path_im_UtilService   : String = path_model_service_im + "/" +  default_im_utilService
  val path_ag_ErrorService  : String = path_model_service_ag + "/" +  default_ag_errorService
  val path_im_ErrorService  : String = path_model_service_im + "/" +  default_im_errorService
  val path_ag_AuditService  : String = path_model_service_ag + "/" +  default_ag_auditService
  val path_im_AuditService  : String = path_model_service_im + "/" +  default_im_auditService

}
