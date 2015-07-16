package controllers

import models.UserLogin
import org.slf4j.{LoggerFactory, Logger}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{Action, Controller}
import views.html


/**
 * Created by afynn on 7/7/15.
 */
object Application extends Controller{


  private final val logger: Logger = LoggerFactory.getLogger(classOf[Controller])

  val loginForm: Form[UserLogin] = Form(
    mapping(
      "email" -> nonEmptyText,
      "password" -> optional(text)
    )(UserLogin.apply)(UserLogin.unapply)
  )

  def getLogin = Action {
    //Ok(html.login(loginForm))
    Ok
  }

  def login = Action { implicit request =>
    logger.info("submit ..." + loginForm.bindFromRequest)

    loginForm.bindFromRequest.fold(
      errors => BadRequest(html.login(errors)),

      userLogin =>
        /*Users.findByEmail(userLogin.email) match  {
            case Some(user) if user.userType.name == UserTypes.ADMIN =>
              //TODO: send to admin page
              Ok(html.supervisor.supervisor(user))
            case Some(user) if user.userType.name == UserTypes.SUPERVISOR =>
              Ok(html.supervisor.supervisor(Users.findByEmail(userLogin.email).get))
            case Some(user) if user.userType.name == UserTypes.WORKER =>
              Ok(html.worker.worker(Users.findByEmail(userLogin.email).get))
            case _ =>
              Ok(html.login(loginForm))
        }*/
      Ok
    )
  }

  def index = Action {
    //Ok(html.index);
    Ok;
  }
}
