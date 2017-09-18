package library.errorhandler.internal.renderer

import library.exception.validation.ErrorDetail
import library.trace.RequestId
import org.scalatestplus.play.PlaySpec
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.FakeRequest

class ValidationErrorRendererSpec extends PlaySpec {

  "ValidationErrorRenderer#render" should {
    "正しくJsonが作られること" in {
      val renderer = ValidationErrorRenderer(
        Seq(ErrorDetail("dummy_message", "dummy_code")),
        Status.UNPROCESSABLE_ENTITY,
        RequestId(FakeRequest())
      )
      val actual = Json.prettyPrint(renderer.render)

      actual mustBe
        """{
          |  "errors" : [ [ {
          |    "message" : "dummy_message",
          |    "code" : "dummy_code"
          |  } ] ],
          |  "status_code" : 422,
          |  "request_id" : "uninitialized-request-id"
          |}""".stripMargin
    }
  }

}
