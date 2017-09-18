package library.request

import library.request.internal.{Equality, TraceId}
import play.api.mvc.RequestHeader

/**
  * リクエストID
  *
  * HTTPリクエストされるたびに、ユニークなIDを払い出す。
  * ログのトラーサビリティ向上が目的。
  */
private[library] sealed trait RequestId extends TraceId

/**
  * リクエストIDのコンパニオンクラス
  */
private[library] object RequestId {
  /**
    * 未初期化時のデフォルト値
    */
  val UninitializedRequestId: String = "uninitialized-request-id"

  /**
    * リクエストIDをリクエストヘッダーから取得
    *
    * @param requestHeader リクエストヘッダー
    * @return 相関ID
    */
  def apply(requestHeader: RequestHeader): RequestId = {
    val value = requestHeader.headers.get(RequestHeaderKey.RequestId).getOrElse(UninitializedRequestId)
    RequestIdImpl(value)
  }
}

private final case class RequestIdImpl(value: String) extends RequestId {
  override def isInitialized: Boolean = {
    !Equality.typeSafeEquals[String](value, RequestId.UninitializedRequestId)
  }
}
