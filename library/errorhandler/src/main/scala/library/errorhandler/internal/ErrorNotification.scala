package library.errorhandler.internal

import play.api.Logger
import play.api.mvc.RequestHeader

/**
  * エラー用の通知
  */
private[errorhandler] final case class ErrorNotification(
                                                          private val requestHeader: RequestHeader,
                                                          private val throwable: Throwable
                                                        ) {

  /**
    * エラーを通知する
    */
  def notifyTrace(): Unit = {
    Logger.trace(s"please implement me! - ${requestHeader.toString} ${throwable.toString}")
  }
}
