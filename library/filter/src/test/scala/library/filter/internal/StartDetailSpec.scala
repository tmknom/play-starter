package library.filter.internal

import library.trace.{CorrelationId, RequestHeaderKey, RequestId}
import org.scalatestplus.play.PlaySpec
import play.api.test.FakeRequest

import scala.collection.JavaConverters._

class StartDetailSpec extends PlaySpec {
  "StartDetail#toMap" should {
    "ログ詳細がMapに変換できること" in {
      val requestHeader = FakeRequest()
        .withHeaders(RequestHeaderKey.CorrelationId -> "fake_correlation_id")
        .withHeaders(RequestHeaderKey.RequestId -> "fake_request_id")

      val actual = StartDetail(requestHeader, CorrelationId(requestHeader), RequestId(requestHeader)).toMap

      actual mustBe Map(
        "correlation_id" -> "fake_correlation_id",
        "request_id" -> "fake_request_id",
        "request" -> Map(
          "path" -> requestHeader.path,
          "method" -> requestHeader.method,
          "raw_query_string" -> requestHeader.rawQueryString,
          "host" -> requestHeader.host,
          "remote_ip_address" -> requestHeader.remoteAddress
        ).asJava
      ).asJava
    }
  }
}
