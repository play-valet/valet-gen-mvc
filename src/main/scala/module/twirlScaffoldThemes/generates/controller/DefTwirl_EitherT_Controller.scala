package module.twirlScaffoldThemes.generates.controller

import module.twirlScaffoldThemes.utils.{TwirlConst, TwirlPathDto}
import org.valet.common._

object DefTwirl_EitherT_Controller extends TwirlConst  {

  def getAllByResultDto(nowTable: GeneratedTable, dtos: ScaffoldDtos, pathDto: TwirlPathDto): String = {
""

  }

}

//
//package controllers
//
//import javax.inject._
//
//import cats.implicits._
//import common.auth.AuthConfigLike
//import common.utils.MyConst._
//import common.utils.MyUtils
//import dto.{FormException, PlayDto}
//import forms.FormMapping
//import forms.FormValidation._
//import jp.t2v.lab.play2.auth.OptionalAuthElement
//import models.businesslogic.autogen.{AgPostsSyncServiceLike, AgUserServiceLike, AgUtilsServiceLike}
//import models.dbIO.autogen.AgPostsDaoLike
//import play.api.i18n.{I18nSupport, MessagesApi}
//import play.api.mvc._
//
//import scala.concurrent.ExecutionContext
//
//@Singleton
//class PostsController @Inject()(val userService: AgUserServiceLike,
//                                val postService: AgPostsSyncServiceLike,
//                                val agPostsDao: AgPostsDaoLike,
//                                val agUtilsService: AgUtilsServiceLike,
//                                val messagesApi: MessagesApi)(implicit executor: ExecutionContext)
//  extends Controller with OptionalAuthElement with AuthConfigLike with I18nSupport {
//
//  def showIndex = AsyncStack { implicit request =>
//    (for {
//      values <- agPostsDao.allT
//    } yield values).fold(
//      fa => Redirect(routes.CorporateController.index()),
//      fb => Ok(views.html.autogen.posts.detail(PlayDto(loggedIn, postsDtoList = MyUtils.wrapOpt(fb))))
//    )
//    //    postService.all(PlayDto(loggedIn)) match {
//    //      case Right(viewDto) => Ok(views.html.autogen.posts.detail(viewDto))
//    //      case Left(e)        => Redirect(routes.PostsController.showIndex)
//    //    }
//  }
//
//  def showDetail(id: Int) = StackAction { implicit request =>
//    postService.findById(PlayDto(loggedIn), id) match {
//      case Right(viewDto) => Ok(views.html.autogen.posts.detail(viewDto))
//      case Left(e)        => Redirect(routes.PostsController.showIndex)
//    }
//  }
//
//  def showCreate = StackAction { implicit request =>
//    Ok(views.html.autogen.posts.store(PlayDto(loggedIn)))
//  }
//
//  def store = AsyncStack { implicit request =>
//    (for {
//      params <- agUtilsService.tryEitherTO(postsForm.bindFromRequest().text)(t => Left(FormException()))
//      action <- postService.storeT(PlayDto(loggedIn), FormMapping.toEntity(loggedIn, params))
//    } yield SUCCESS_CREATE).fold(
//      fa => fa match {
//        case FormException(c) => Redirect(routes.PostsController.showIndex).flashing(AG_ERROR -> messagesApi(ERROR_FORM_DATE))
//        case _                => Redirect(routes.PostsController.showIndex).flashing(AG_ERROR -> messagesApi(ERROR_CREATE_BY_DB))
//      },
//      fb => Redirect(routes.PostsController.showIndex).flashing(AG_SUCCESS -> messagesApi(fb))
//    )
//  }
//
//  def showEdit(id: Int) = StackAction { implicit request =>
//    postService.findById(PlayDto(loggedIn), id) match {
//      case Right(viewDto) => Ok(views.html.autogen.posts.edit(viewDto))
//      case Left(e)        => Redirect(routes.PostsController.showIndex)
//    }
//  }
//
//  def update = StackAction { implicit request =>
//    postsForm.bindFromRequest.fold(
//      errorForm => Redirect(routes.PostsController.showIndex).flashing(AG_ERROR -> messagesApi(ERROR_FORM)),
//      form => {
//        postService.edit(PlayDto(loggedIn), FormMapping.toEntity(loggedIn, form)) match {
//          case Right(viewDto) => Redirect(routes.PostsController.showIndex).flashing(AG_SUCCESS -> messagesApi(SUCCESS_UPDATE))
//          case Left(e)        => Redirect(routes.PostsController.showIndex).flashing(AG_ERROR -> messagesApi(ERROR_EDIT_BY_DB))
//        }
//      }
//    )
//  }
//
//  def destroy(id: Int) = StackAction { implicit request =>
//    postService.delete(PlayDto(loggedIn), id) match {
//      case Right(viewDto) => Redirect(routes.PostsController.showIndex).flashing(AG_SUCCESS -> messagesApi(SUCCESS_DELETE))
//      case Left(e)        => Redirect(routes.PostsController.showIndex).flashing(AG_ERROR -> messagesApi(ERROR_DELETE_BY_DB))
//    }
//  }
//
//
//}
