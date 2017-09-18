package library.errorhandler.internal

import library.exception.validation.{ErrorDetail, ValidationException}
import library.trace.RequestId
import org.scalatestplus.play.PlaySpec
import play.api.http.Status
import play.api.test.FakeRequest

class ServerErrorHttpResponseSpec extends PlaySpec {

  "ServerErrorHttpResponse#response" should {
    "ValidationExceptionで正しくレスポンスが作られること" in {
      val actual = ServerErrorHttpResponse(
        RequestId(FakeRequest()),
        new ValidationException(Seq(ErrorDetail("dummy_message", "dummy_code")))
      ).response

      actual.body.contentType mustBe Some("application/json")
      actual.header.status mustBe Status.UNPROCESSABLE_ENTITY
    }

    "RuntimeExceptionで正しくレスポンスが作られること" in {
      val actual = ServerErrorHttpResponse(
        RequestId(FakeRequest()),
        new RuntimeException("dummy_message")
      ).response

      actual.body.contentType mustBe Some("application/json")
      actual.header.status mustBe Status.INTERNAL_SERVER_ERROR
    }
  }

}
