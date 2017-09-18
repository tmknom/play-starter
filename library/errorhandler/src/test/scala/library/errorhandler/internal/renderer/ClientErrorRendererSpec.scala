package library.errorhandler.internal.renderer

import library.trace.RequestId
import org.scalatestplus.play.PlaySpec
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.FakeRequest

class ClientErrorRendererSpec extends PlaySpec {

  "ClientErrorRenderer#render" should {
    "正しくJsonが作られること" in {
      val renderer = ClientErrorRenderer(
        "dummy_message",
        Status.BAD_REQUEST,
        RequestId(FakeRequest())
      )
      val actual = Json.prettyPrint(renderer.render)

      actual mustBe
        """{
          |  "errors" : [ {
          |    "message" : "dummy_message",
          |    "code" : "ClientError"
          |  } ],
          |  "status_code" : 400,
          |  "request_id" : "uninitialized-request-id"
          |}""".stripMargin
    }
  }

}
