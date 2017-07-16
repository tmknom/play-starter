package domains

import spray.json._

/**
  * ヘルスチェック
  */
final case class HealthCheck(status: String)

object HealthCheck {
  def ok(): HealthCheck = {
    HealthCheck("ok")
  }
}

object HealthCheckProtocol extends DefaultJsonProtocol {
  implicit val format: RootJsonFormat[HealthCheck] = jsonFormat1(HealthCheck.apply)
}
