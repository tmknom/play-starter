package library.trace

import org.scalatestplus.play.PlaySpec
import play.api.test.FakeRequest

class RequestIdSpec extends PlaySpec {

  "RequestId#apply" should {
    "RequestHeaderにX-Request-Idがセットされていた場合" in {
      val requestHeader = FakeRequest().withHeaders(RequestHeaderKey.RequestId -> "fake_request_id")
      val actual = RequestId(requestHeader).value
      actual mustBe "fake_request_id"
    }

    "RequestHeaderにX-Request-Idがセットされていなかった場合" in {
      val requestHeader = FakeRequest()
      val actual = RequestId(requestHeader).value
      actual mustBe "uninitialized-request-id"
    }
  }

  "RequestId#isInitialized" should {
    "RequestIdが初期化済みの場合" in {
      val requestHeader = FakeRequest().withHeaders(RequestHeaderKey.RequestId -> "fake_request_id")
      val actual = RequestId(requestHeader).isInitialized
      actual mustBe true
    }

    "RequestIdが初期化していない場合" in {
      val requestHeader = FakeRequest()
      val actual = RequestId(requestHeader).isInitialized
      actual mustBe false
    }
  }

}
