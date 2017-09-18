package library.errorhandler.internal

import library.trace.{CorrelationId, RequestId}
import net.logstash.logback.marker.Markers
import org.slf4j.Marker
import play.api.Logger
import play.api.mvc.RequestHeader

import scala.collection.JavaConverters._

/**
  * エラー用のロガー
  */
private[errorhandler] final case class ErrorLogger(
                                                    private val requestHeader: RequestHeader,
                                                    private val throwable: Throwable
                                                  ) {

  /**
    * エラーログの出力
    */
  def log(): Unit = {
    val message = createMessage
    val detail = createDetail
    Logger.logger.error(detail, message, throwable)
  }

  /**
    * エラーメッセージの作成
    */
  private def createMessage: String = {
    s"Failed ${requestHeader.method} ${requestHeader.path} caused by ${throwable.getClass.getSimpleName}(${throwable.getMessage})"
  }

  /**
    * エラー詳細の作成
    *
    * @return エラー詳細
    */
  private def createDetail: Marker = {
    val detail = Map(
      "correlation_id" -> CorrelationId(requestHeader).value,
      "request_id" -> RequestId(requestHeader).value,
      "request" -> Map(
        "path" -> requestHeader.path,
        "method" -> requestHeader.method,
        "raw_query_string" -> requestHeader.rawQueryString,
        "host" -> requestHeader.host,
        "remote_ip_address" -> requestHeader.remoteAddress
      ).asJava,
      "exception" -> Map(
        "name" -> throwable.getClass.getSimpleName,
        "full_name" -> throwable.getClass.getName,
        "message" -> throwable.getMessage
      ).asJava
    )
    Markers.append("detail", detail.asJava)
  }
}
