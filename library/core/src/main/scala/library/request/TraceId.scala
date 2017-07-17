package library.request

/**
  * ログトレース用のID
  */
private[library] trait TraceId {
  def value: String

  /**
    * 正常に初期化したかどうか
    *
    * @return 初期化していたらtrue
    */
  def isInitialized: Boolean
}
