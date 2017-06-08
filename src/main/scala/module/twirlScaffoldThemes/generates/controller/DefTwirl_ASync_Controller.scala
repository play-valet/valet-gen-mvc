package module.twirlScaffoldThemes.generates.controller

import org.valet.common._

object DefTwirl_ASync_Controller extends ValetUtility  {

  def getAll(nowTable: GeneratedTable, dtos: ScaffoldDtos): String = {
    s"""|package controllers.autogen
        |
        |import javax.inject._
        |
        |import common.implement.utils.Const
        |import forms.autogen.AgMappingPostsForm
        |import forms.autogen.AgPostsCreateFormObj.agPostsCreateForm
        |import forms.autogen.AgPostsEditFormObj.agPostsEditForm
        |import models.autogen.daos.AgPostsDaoLike
        |import models.autogen.dtos.AgResultDtos
        |import play.api.i18n.{I18nSupport, MessagesApi}
        |import play.api.mvc._
        |import views.html.autogen.simple_admin._
        |
        |import scala.concurrent.{ExecutionContext, Future}
        |
        |@Singleton
        |class AgPostsController @Inject()(val agPostsDao: AgPostsDaoLike,
        |                                  val messagesApi: MessagesApi)(implicit executor: ExecutionContext)
        |  extends Controller with Const with I18nSupport {
        |
        |  def showIndex = Action.async { implicit request =>
        |    agPostsDao.all.map { result =>
        |      Ok(posts.list(AgResultDtos(None, postsDtoList = Some(result))))
        |    }.recover {
        |      case _ => InternalServerError
        |    }
        |  }
        |
        |  def showDetail(id: Int) = Action.async { implicit request =>
        |    agPostsDao.findById(id).map {
        |      case Some(result) => Ok(posts.detail(AgResultDtos(None, agPostsEditForm = AgMappingPostsForm.toEditForm(id, result))))
        |      case None         => Redirect(routes.AgPostsController.showIndex())
        |    }.recover {
        |      case _ => Redirect(routes.AgPostsController.showIndex())
        |    }
        |  }
        |
        |  def showCreate = Action.async { implicit request =>
        |    Future.successful(Ok(posts.create(AgResultDtos(None))))
        |  }
        |
        |  def store = Action.async { implicit request =>
        |    agPostsCreateForm.bindFromRequest.fold(
        |      errorForm =>
        |        Future.successful(Ok(posts.create(AgResultDtos(None, agPostsCreateForm = errorForm)))),
        |      form => {
        |        agPostsDao.store(AgMappingPostsForm.toEntity(form)).map { result =>
        |          Redirect(routes.AgPostsController.showIndex()).flashing(AG_SUCCESS -> messagesApi(SUCCESS_CREATE))
        |        }.recover {
        |          case _ => Redirect(routes.AgPostsController.showIndex()).flashing(AG_ERROR -> messagesApi(ERROR_CREATE_BY_DB))
        |        }
        |      })
        |  }
        |
        |  def showEdit(id: Int) = Action.async { implicit request =>
        |    agPostsDao.findById(id).map {
        |      case Some(result) => Ok(posts.edit(AgResultDtos(None, agPostsEditForm = AgMappingPostsForm.toEditForm(id, result))))
        |      case None         => Redirect(routes.AgPostsController.showIndex()).flashing(AG_ERROR -> messagesApi(ERROR_CREATE_BY_DB))
        |    }.recover {
        |      case _ => Redirect(routes.AgPostsController.showIndex()).flashing(AG_ERROR -> messagesApi(ERROR_CREATE_BY_DB))
        |    }
        |  }
        |
        |  def update(id: Int) = Action.async { implicit request =>
        |    agPostsEditForm.bindFromRequest.fold(
        |      errorForm => {
        |        Future.successful(Ok(posts.edit(AgResultDtos(None, agPostsEditForm = errorForm))))
        |      },
        |      form => {
        |        agPostsDao.edit(AgMappingPostsForm.toEntity(id, form)).map { result =>
        |          Redirect(routes.AgPostsController.showIndex()).flashing(AG_SUCCESS -> messagesApi(SUCCESS_UPDATE))
        |        }.recover {
        |          case _ => Redirect(routes.AgPostsController.showIndex()).flashing(AG_ERROR -> messagesApi(ERROR_EDIT_BY_DB))
        |        }
        |      })
        |  }
        |
        |  def destroy(id: Int) = Action.async { implicit request =>
        |    agPostsDao.delete(id).map { result =>
        |      Redirect(routes.AgPostsController.showIndex()).flashing(AG_SUCCESS -> messagesApi(SUCCESS_DELETE))
        |    }.recover {
        |      case _ => Redirect(routes.AgPostsController.showIndex()).flashing(AG_ERROR -> messagesApi(ERROR_DELETE_BY_DB))
        |    }
        |  }
        |
        |}
        |""".stripMargin
  }

}

