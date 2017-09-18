package library.trace

import org.scalatestplus.play.PlaySpec
import play.api.test.FakeRequest

class CorrelationIdSpec extends PlaySpec {

  "CorrelationId#apply" should {
    "RequestHeaderにX-Correlation-Idがセットされていた場合" in {
      val requestHeader = FakeRequest().withHeaders(RequestHeaderKey.CorrelationId -> "fake_correlation_id")
      val actual = CorrelationId(requestHeader).value
      actual mustBe "fake_correlation_id"
    }

    "RequestHeaderにX-Correlation-Idがセットされていなかった場合" in {
      val requestHeader = FakeRequest()
      val actual = CorrelationId(requestHeader).value
      actual mustBe "uninitialized-correlation-id"
    }
  }

  "CorrelationId#isInitialized" should {
    "CorrelationIdが初期化済みの場合" in {
      val requestHeader = FakeRequest().withHeaders(RequestHeaderKey.CorrelationId -> "fake_correlation_id")
      val actual = CorrelationId(requestHeader).isInitialized
      actual mustBe true
    }

    "CorrelationIdが初期化していない場合" in {
      val requestHeader = FakeRequest()
      val actual = CorrelationId(requestHeader).isInitialized
      actual mustBe false
    }
  }

}
