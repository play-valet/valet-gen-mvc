package module.twirlScaffoldThemes.generates.controller

import java.io.File

import module.twirlScaffoldThemes.utils.{TwirlConst, TwirlPathDto}
import org.valet.common._

object DefTwirl_ASync_Controller extends TwirlConst  {

  def getAllByResultDto(nowTable: GeneratedTable, dtos: ScaffoldDtos, pathDto: TwirlPathDto): String = {

    val gitPathBackendAdminPath = dtos.confDto.modulesTwirlScaffoldThemesSourceBackendAdmin
    val gitProjectName: String = gitPathBackendAdminPath.split("/").last.dropRight(4)
    val snakeCase = toSnakeCase(gitProjectName.replaceAll("-", "_"))
    val optFile: Option[File] = getDirFileList(s"./app/views/autogen/$snakeCase", Seq()).filter(file => file.isFile && file.getParentFile.getName == CRUD_TEMPLATE_DIR).headOption
    val path :String = optFile.map(_.getParentFile.getPath).getOrElse("")
    val pkg_views_autogen_crud_dir = path.replace("./app/views/", "").replace("/", ".").split('.').init.mkString(".")

    s"""|package $pkg_controller_ag
        |
        |import javax.inject._
        |
        |import $pkg_common_util_im.$default_im_const
        |import $pkg_form_ag.${getAgMappingForm(nowTable)}
        |import $pkg_form_ag.${getObj(getAgCreateForm(nowTable))}.${getAgCreateFieldForm(nowTable)}
        |import $pkg_form_ag.${getObj(getAgEditForm(nowTable))}.${getAgEditFieldForm(nowTable)}
        |import $pkg_model_dao_ag.${getAgDaoLike(nowTable)}
        |import $pkg_model_dto_ag.${default_ag_resultViewDtos(dtos)}
        |import play.api.i18n.{I18nSupport, MessagesApi}
        |import play.api.mvc._
        |import views.html.${pkg_views_autogen_crud_dir}._
        |
        |import scala.concurrent.{ExecutionContext, Future}
        |
        |@Singleton
        |class ${getAgController(nowTable)} @Inject()(val ${getFieldAgDao(nowTable)}: ${getAgDaoLike(nowTable)},
        |                                  val messagesApi: MessagesApi)(implicit executor: ExecutionContext)
        |  extends Controller with $default_im_const with I18nSupport {
        |
        |  def $METHOD_CONTRL_SHOWINDEX = Action.async { implicit request =>
        |    ${getFieldAgDao(nowTable)}.$METHOD_DAO_ALL.map { result =>
        |      Ok(${getTableFieldName(nowTable)}.$CRUD_FILENAME_LIST(${default_ag_resultViewDtos(dtos)}(None, ${getAgDtoFieldList(nowTable)}} = result)))
        |    }.recover {
        |      case _ => InternalServerError
        |    }
        |  }
        |
        |  def $METHOD_CONTRL_SHOWDETAIL(id: Int) = Action.async { implicit request =>
        |    ${getFieldAgDao(nowTable)}.$METHOD_DAO_FIND(id).map {
        |      case Some(result) => Ok(${getTableFieldName(nowTable)}.$CRUD_FILENAME_DETAIL(${default_ag_resultViewDtos(dtos)}(None, ${getAgEditFieldForm(nowTable)} = ${getAgMappingForm(nowTable)}.toEditForm(id, result))))
        |      case None         => Redirect(routes.${getAgController(nowTable)}.$METHOD_CONTRL_SHOWINDEX())
        |    }.recover {
        |      case _ => Redirect(routes.${getAgController(nowTable)}.$METHOD_CONTRL_SHOWINDEX())
        |    }
        |  }
        |
        |  def $METHOD_CONTRL_SHOWCREATE = Action.async { implicit request =>
        |    Future.successful(Ok(${getTableFieldName(nowTable)}.$CRUD_FILENAME_CREATE(${default_ag_resultViewDtos(dtos)}(None))))
        |  }
        |
        |  def $METHOD_CONTRL_STORE = Action.async { implicit request =>
        |    ${getAgCreateFieldForm(nowTable)}.bindFromRequest.fold(
        |      errorForm =>
        |        Future.successful(Ok(${getTableFieldName(nowTable)}.$CRUD_FILENAME_CREATE(${default_ag_resultViewDtos(dtos)}(None, ${getAgCreateFieldForm(nowTable)} = errorForm)))),
        |      form => {
        |        ${getFieldAgDao(nowTable)}.$METHOD_DAO_STORE(${getAgMappingForm(nowTable)}.toEntity(form)).map { result =>
        |          Redirect(routes.${getAgController(nowTable)}.$METHOD_CONTRL_SHOWINDEX()).flashing(AG_SUCCESS -> messagesApi(SUCCESS_CREATE))
        |        }.recover {
        |          case _ => Redirect(routes.${getAgController(nowTable)}.$METHOD_CONTRL_SHOWINDEX()).flashing(AG_ERROR -> messagesApi(ERROR_CREATE_BY_DB))
        |        }
        |      })
        |  }
        |
        |  def $METHOD_CONTRL_SHOWEDIT(id: Int) = Action.async { implicit request =>
        |    ${getFieldAgDao(nowTable)}.$METHOD_DAO_FIND(id).map {
        |      case Some(result) => Ok(${getTableFieldName(nowTable)}.$CRUD_FILENAME_EDIT(${default_ag_resultViewDtos(dtos)}(None, ${getAgEditFieldForm(nowTable)} = ${getAgMappingForm(nowTable)}.toEditForm(id, result))))
        |      case None         => Redirect(routes.${getAgController(nowTable)}.$METHOD_CONTRL_SHOWINDEX()).flashing(AG_ERROR -> messagesApi(ERROR_CREATE_BY_DB))
        |    }.recover {
        |      case _ => Redirect(routes.${getAgController(nowTable)}.$METHOD_CONTRL_SHOWINDEX()).flashing(AG_ERROR -> messagesApi(ERROR_CREATE_BY_DB))
        |    }
        |  }
        |
        |  def $METHOD_CONTRL_UPDATE(id: Int) = Action.async { implicit request =>
        |    ${getAgEditFieldForm(nowTable)}.bindFromRequest.fold(
        |      errorForm => {
        |        Future.successful(Ok(${getTableFieldName(nowTable)}.$CRUD_FILENAME_EDIT(${default_ag_resultViewDtos(dtos)}(None, ${getAgEditFieldForm(nowTable)} = errorForm))))
        |      },
        |      form => {
        |        ${getFieldAgDao(nowTable)}.$METHOD_DAO_EDIT(${getAgMappingForm(nowTable)}.toEntity(id, form)).map { result =>
        |          Redirect(routes.${getAgController(nowTable)}.$METHOD_CONTRL_SHOWINDEX()).flashing(AG_SUCCESS -> messagesApi(SUCCESS_UPDATE))
        |        }.recover {
        |          case _ => Redirect(routes.${getAgController(nowTable)}.$METHOD_CONTRL_SHOWINDEX()).flashing(AG_ERROR -> messagesApi(ERROR_EDIT_BY_DB))
        |        }
        |      })
        |  }
        |
        |  def $METHOD_CONTRL_DESTROY(id: Int) = Action.async { implicit request =>
        |    ${getFieldAgDao(nowTable)}.$METHOD_DAO_DELETE(id).map { result =>
        |      Redirect(routes.${getAgController(nowTable)}.$METHOD_CONTRL_SHOWINDEX()).flashing(AG_SUCCESS -> messagesApi(SUCCESS_DELETE))
        |    }.recover {
        |      case _ => Redirect(routes.${getAgController(nowTable)}.$METHOD_CONTRL_SHOWINDEX()).flashing(AG_ERROR -> messagesApi(ERROR_DELETE_BY_DB))
        |    }
        |  }
        |
        |}
        |""".stripMargin
  }

}

