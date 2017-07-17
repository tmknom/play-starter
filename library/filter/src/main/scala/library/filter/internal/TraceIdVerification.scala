package library.filter.internal

import library.request.{CorrelationId, RequestHeaderKey, RequestId}
import play.api.Logger
import play.api.mvc.RequestHeader

private[internal] final case class TraceIdVerification(requestHeader: RequestHeader, correlationId: CorrelationId, requestId: RequestId) {

  /**
    * ログトレース用IDが初期化されているかチェックする
    *
    * 初期化されていない場合はWarningを出力をする。
    * ただし、処理自体は継続させる。
    */
  def verify(): Unit = {
    if (!isSuppressWarning) {
      verifyCorrelationId()
      verifyRequestId()
    }
  }

  /**
    * 警告の抑制が必要か
    *
    * @return 抑制が必要ならtrue
    */
  private def isSuppressWarning: Boolean = {
    SuppressWarning.isSuppressWarning(requestHeader.path)
  }

  /**
    * 相関IDが初期化されているかチェックする
    */
  private def verifyCorrelationId(): Unit = {
    if (!correlationId.isInitialized) {
      Logger.warn(s"相関IDがリクエストヘッダーにセットされていません。${RequestHeaderKey.CorrelationId}を付与してリクエストしてください。")
    }
  }

  /**
    * リクエストIDが初期化されているかチェックする
    */
  private def verifyRequestId(): Unit = {
    if (!requestId.isInitialized) {
      Logger.warn(s"リクエストIDがリクエストヘッダーにセットされていません。${RequestHeaderKey.RequestId}を付与してリクエストしてください。")
    }
  }
}

private[internal] object SuppressWarning {
  /**
    * 相関ID／リクエストIDのチェックを抑制するパス
    */
  private val Path: Seq[String] = Seq[String](
    "/health_check",
    "/swagger",
    "/api-docs",
    "/assets"
  )

  /**
    * 警告の抑制が必要か
    *
    * @return 抑制が必要ならtrue
    */
  def isSuppressWarning(path: String): Boolean = {
    Path.exists(path.contains(_))
  }
}
