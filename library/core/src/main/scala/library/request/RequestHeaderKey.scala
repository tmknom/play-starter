package library.request

/**
  * リクエストヘッダーのキーを定数定義するクラス
  */
private[library] object RequestHeaderKey {
  val CorrelationId: String = "X-Correlation-Id"
  val RequestId: String = "X-Request-Id"
}
