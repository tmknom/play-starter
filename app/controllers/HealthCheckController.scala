package controllers

import javax.inject._
import play.api.mvc._

/**
  * ヘルスチェックAPI
  */
@Singleton
final class HealthCheckController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    Ok("ok")
  }

}
