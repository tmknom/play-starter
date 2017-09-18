package library.filter.internal

import library.trace.{CorrelationId, RequestId}
import net.logstash.logback.marker.Markers
import play.api.Logger
import play.api.mvc.{RequestHeader, Result}

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
    val endDetail = EndDetail(correlationId, requestId, result, requestTime)
    Logger.logger.info(Markers.append("detail", endDetail.toMap), message)
  }
}
