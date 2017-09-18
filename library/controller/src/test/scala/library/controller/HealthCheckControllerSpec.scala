package library.controller

import akka.stream.Materializer
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.Json
import play.api.mvc.ControllerComponents
import play.api.test.Helpers._
import play.api.test._

class HealthCheckControllerSpec extends PlaySpec with GuiceOneAppPerSuite with Injecting {

  implicit lazy val materializer: Materializer = app.materializer

  "HealthCheckController#show" should {
    "正常なレスポンスを返すこと" in {
      val sut = new HealthCheckController(inject[ControllerComponents])
      val actual = call(sut.index, FakeRequest(GET, "/health_check"))

      status(actual) mustBe OK
      contentType(actual) mustBe Some("application/json")
      contentAsJson(actual) mustBe Json.toJson(Map("status" -> "ok"))
    }
  }
}
