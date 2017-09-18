package library.filter.internal

import java.util

import library.trace.{CorrelationId, RequestId}
import play.api.mvc.RequestHeader

import scala.collection.JavaConverters._

/**
  * HTTPリクエストの開始時のログ詳細
  */
private[internal] final case class StartDetail(
                                                requestHeader: RequestHeader,
                                                correlationId: CorrelationId,
                                                requestId: RequestId
                                              ) {

  def toMap: util.Map[String, Object] = {
    Map(
      "correlation_id" -> correlationId.value,
      "request_id" -> requestId.value,
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
