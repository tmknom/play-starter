package library.exception.validation

/**
  * エラー詳細
  *
  * @param message エラーメッセージ
  * @param code エラーコード
  */
private[library] final case class ErrorDetail(message: String, code: String)
