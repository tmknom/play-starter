package library.errorhandler.internal

import library.errorhandler.internal.renderer.ClientErrorRenderer
import library.trace.RequestId
import play.api.http.Status.{BAD_REQUEST, INTERNAL_SERVER_ERROR}
import play.api.mvc.{Result, Results}

/**
  * クライアントエラー用のHTTPレスポンスを生成するクラス
  */
private[errorhandler] final case class ClientErrorHttpResponse(
                                                                private val requestId: RequestId,
                                                                private val statusCode: Int,
                                                                private val message: String
                                                              ) {

  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  def response: Result = {
    statusCode match {
      case clientErrorStatusCode if statusCode >= BAD_REQUEST && statusCode < INTERNAL_SERVER_ERROR =>
        Results.Status(statusCode)(ClientErrorRenderer(message, clientErrorStatusCode, requestId.value).render)
      case _ =>
        throw new IllegalArgumentException(s"onClientError invoked with non client error status code $statusCode: $message")
    }
  }

}
