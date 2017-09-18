package library.controller

import javax.inject._

import library.controller.internal.HealthCheck
import library.controller.internal.HealthCheckProtocol._
import play.api.mvc._
import spray.json._

/**
  * ヘルスチェックAPI
  */
@Singleton
final class HealthCheckController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index: Action[AnyContent] = Action {
    val res = HealthCheck.ok()
    Ok(res.toJson.compactPrint).as(JSON)
  }

}
