package controllers

import javax.inject._

import domains.HealthCheck
import domains.HealthCheckProtocol._
import play.api.mvc._
import spray.json._

/**
  * ヘルスチェックAPI
  */
@Singleton
final class HealthCheckController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def index = Action {
    val res = HealthCheck.ok()
    Ok(res.toJson.prettyPrint).as(JSON)
  }

}
