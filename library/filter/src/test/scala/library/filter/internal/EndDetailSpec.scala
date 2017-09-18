package library.filter.internal

import library.trace.{CorrelationId, RequestHeaderKey, RequestId}
import org.scalatestplus.play.PlaySpec
import play.api.http.HttpEntity
import play.api.mvc.{ResponseHeader, Result}
import play.api.test.FakeRequest

import scala.collection.JavaConverters._

class EndDetailSpec extends PlaySpec {
  "EndDetail#toMap" should {
    "ログ詳細がMapに変換できること" in {
      val requestHeader = FakeRequest()
        .withHeaders(RequestHeaderKey.CorrelationId -> "fake_correlation_id")
        .withHeaders(RequestHeaderKey.RequestId -> "fake_request_id")

      val result = Result(ResponseHeader(200, requestHeader.headers.toSimpleMap), HttpEntity.NoEntity) // scalastyle:off
      val actual = EndDetail(CorrelationId(requestHeader), RequestId(requestHeader), result, RequestTime(100)).toMap // scalastyle:off

      actual mustBe Map(
        "correlation_id" -> "fake_correlation_id",
        "request_id" -> "fake_request_id",
        "response" -> Map(
          "request_time_millis" -> "100",
          "status_code" -> "200"
        ).asJava
      ).asJava
    }
  }
}
