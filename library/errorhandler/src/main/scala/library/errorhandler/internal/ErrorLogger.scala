package library.errorhandler.internal

import library.request.{CorrelationId, RequestId}
import net.logstash.logback.marker.Markers
import org.slf4j.Marker
import play.api.Logger
import play.api.mvc.RequestHeader

import scala.collection.JavaConverters._

/**
  * エラー用のロガー
  */
private[errorhandler] object ErrorLogger {

  /**
    * エラーログの出力
    *
    * @param requestHeader リクエストヘッダー
    * @param throwable     スローされた例外
    */
  def error(requestHeader: RequestHeader, throwable: Throwable): Unit = {
    val message = createMessage(requestHeader, throwable)
    val detail = createDetail(requestHeader, throwable)
    Logger.logger.error(detail, message, throwable)
  }

  /**
    * エラーメッセージの作成
    *
    * @param requestHeader リクエストヘッダー
    * @param throwable     スローされた例外
    * @return エラーメッセージ
    */
  private def createMessage(requestHeader: RequestHeader, throwable: Throwable): String = {
    s"Failed ${requestHeader.method} ${requestHeader.path} caused by ${throwable.getClass.getSimpleName}(${throwable.getMessage})"
  }

  /**
    * エラー詳細の作成
    *
    * @param requestHeader リクエストヘッダー
    * @param throwable     スローされた例外
    * @return エラー詳細
    */
  private def createDetail(requestHeader: RequestHeader, throwable: Throwable): Marker = {
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
