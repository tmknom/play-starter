package library.controller.internal

import spray.json._

/**
  * ヘルスチェック
  */
private[controller] final case class HealthCheck(status: String)

private[controller] object HealthCheck {
  def ok(): HealthCheck = {
    HealthCheck("ok")
  }
}

private[controller] object HealthCheckProtocol extends DefaultJsonProtocol {
  implicit val format: RootJsonFormat[HealthCheck] = jsonFormat1(HealthCheck.apply)
}
