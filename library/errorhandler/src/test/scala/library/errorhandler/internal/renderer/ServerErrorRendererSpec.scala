package library.errorhandler.internal.renderer

import library.trace.RequestId
import org.scalatestplus.play.PlaySpec
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.FakeRequest

class ServerErrorRendererSpec extends PlaySpec {

  "ServerErrorRenderer#render" should {
    "正しくJsonが作られること" in {
      val renderer = ServerErrorRenderer(
        new RuntimeException("dummy_message"),
        Status.INTERNAL_SERVER_ERROR,
        RequestId(FakeRequest())
      )
      val actual = Json.prettyPrint(renderer.render)

      actual mustBe
        """{
          |  "errors" : [ {
          |    "message" : "dummy_message",
          |    "code" : "RuntimeException"
          |  } ],
          |  "status_code" : 500,
          |  "request_id" : "uninitialized-request-id"
          |}""".stripMargin
    }
  }

}
