package library.trace

import library.trace.internal.{Equality, TraceId}
import play.api.mvc.RequestHeader

/**
  * 相関ID
  *
  * HTTPリクエストされるときに、APIクライアントから送られてくる想定。
  */
private[library] sealed trait CorrelationId extends TraceId

/**
  * 相関IDのコンパニオンクラス
  */
private[library] object CorrelationId {
  /**
    * 未初期化時のデフォルト値
    */
  val UninitializedCorrelationId: String = "uninitialized-correlation-id"

  /**
    * 相関IDをリクエストヘッダーから取得
    *
    * @param requestHeader リクエストヘッダー
    * @return 相関ID
    */
  def apply(requestHeader: RequestHeader): CorrelationId = {
    val value = requestHeader.headers.get(RequestHeaderKey.CorrelationId).getOrElse(UninitializedCorrelationId)
    CorrelationIdImpl(value)
  }
}

private final case class CorrelationIdImpl(value: String) extends CorrelationId {
  override def isInitialized: Boolean = {
    !Equality.typeSafeEquals(value, CorrelationId.UninitializedCorrelationId)
  }
}
