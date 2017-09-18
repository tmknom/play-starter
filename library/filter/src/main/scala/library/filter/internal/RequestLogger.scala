package library.filter.internal

import library.trace.{CorrelationId, RequestId}
import net.logstash.logback.marker.Markers
import org.slf4j.Marker
import play.api.Logger
import play.api.mvc.{RequestHeader, Result}

import scala.collection.JavaConverters._

/**
  * HTTPリクエストの開始／終了のロガー
  */
private[filter] object RequestLogger {

  /**
    * HTTPリクエストの開始時のログ出力を行う
    *
    * @param requestHeader リクエストヘッダー
    * @param correlationId 相関ID
    */
  def logStart(requestHeader: RequestHeader, correlationId: CorrelationId, requestId: RequestId): Unit = {
    // ログトレース用IDが初期化されているかチェック
    TraceIdVerification(requestHeader, correlationId, requestId).verify()

    // ログ出力
    val message = s"Started ${requestHeader.method} ${requestHeader.path}"
    val startDetail = StartDetail(requestHeader, correlationId, requestId)
    Logger.logger.info(Markers.append("detail", startDetail.toMap), message)
  }

  /**
    * HTTPリクエストの終了時のログ出力を行う
    *
    * @param requestHeader リクエストヘッダー
    * @param correlationId 相関ID
    * @param result        HTTPレスポンス
    * @param requestTime   リクエスト実行時間
    */
  def logEnd(requestHeader: RequestHeader, correlationId: CorrelationId, requestId: RequestId, result: Result, requestTime: RequestTime): Unit = {
    val message = s"Completed ${requestHeader.method} ${requestHeader.path}"
    Logger.logger.info(createEndDetail(correlationId, requestId, result, requestTime), message)
  }

  /**
    * HTTPリクエストの終了時の詳細の作成
    *
    * @param correlationId 相関ID
    * @param requestId     リクエストID
    * @param result        HTTPレスポンス
    * @param requestTime   リクエスト実行時間
    * @return ログメッセージ
    */
  private def createEndDetail(correlationId: CorrelationId, requestId: RequestId, result: Result, requestTime: RequestTime): Marker = {
    val value = Map(
      "correlation_id" -> correlationId.value,
      "request_id" -> requestId.value,
      "response" -> Map(
        "request_time_millis" -> requestTime.value.toString,
        "status_code" -> result.header.status.toString
      ).asJava
    )
    Markers.append("detail", value.asJava)
  }
}
