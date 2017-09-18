package library.errorhandler.internal

import library.trace.RequestId
import org.scalatestplus.play.PlaySpec
import play.api.http.Status
import play.api.test.FakeRequest

class ClientErrorHttpResponseSpec extends PlaySpec {

  "ClientErrorHttpResponse#response" should {
    "境界値400で正しくレスポンスが作られること" in {
      val actual = ClientErrorHttpResponse(
        RequestId(FakeRequest()),
        Status.BAD_REQUEST,
        "dummy_message"
      ).response

      actual.body.contentType mustBe Some("application/json")
      actual.header.status mustBe Status.BAD_REQUEST
    }

    "境界値499で正しくレスポンスが作られること" in {
      val actual = ClientErrorHttpResponse(
        RequestId(FakeRequest()),
        499, // scalastyle:off
        "dummy_message"
      ).response

      actual.body.contentType mustBe Some("application/json")
      actual.header.status mustBe 499 // scalastyle:off
    }

    "境界値399で例外が発生すること" in {
      intercept[IllegalArgumentException] {
        ClientErrorHttpResponse(
          RequestId(FakeRequest()),
          399, // scalastyle:off
          "dummy_message"
        ).response
      }
    }

    "境界値500で例外が発生すること" in {
      intercept[IllegalArgumentException] {
        ClientErrorHttpResponse(
          RequestId(FakeRequest()),
          Status.INTERNAL_SERVER_ERROR,
          "dummy_message"
        ).response
      }
    }
  }

}
