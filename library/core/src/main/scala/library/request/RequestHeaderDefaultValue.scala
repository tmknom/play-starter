package library.request

/**
  * リクエストヘッダーのデフォルト値を定数定義するクラス
  */
private[library] object RequestHeaderDefaultValue {
  val CorrelationId: String = "uninitialized-correlation-id"
  val RequestId: String = "uninitialized-request-id"
}
