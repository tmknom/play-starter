package library.filter.internal

import java.util

import library.trace.{CorrelationId, RequestId}
import play.api.mvc.Result

import scala.collection.JavaConverters._

/**
  * HTTPリクエストの終了時のログ詳細
  */
private[internal] final case class EndDetail(
                                              correlationId: CorrelationId,
                                              requestId: RequestId,
                                              result: Result,
                                              requestTime: RequestTime
                                            ) {
  def toMap: util.Map[String, Object] = {
    Map(
      "correlation_id" -> correlationId.value,
      "request_id" -> requestId.value,
      "response" -> Map(
        "request_time_millis" -> requestTime.value.toString,
        "status_code" -> result.header.status.toString
      ).asJava
    ).asJava
  }

}
