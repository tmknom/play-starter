package library.errorhandler.internal

import library.errorhandler.internal.renderer.{ServerErrorRenderer, ValidationErrorRenderer}
import library.exception.validation.ValidationException
import library.trace.RequestId
import play.api.http.Status.{INTERNAL_SERVER_ERROR, UNPROCESSABLE_ENTITY}
import play.api.mvc.Result
import play.api.mvc.Results.{InternalServerError, UnprocessableEntity}

/**
  * サーバエラー用のHTTPレスポンスを生成するクラス
  */
private[errorhandler] final case class ServerErrorHttpResponse(
                                                                private val requestId: RequestId,
                                                                private val throwable: Throwable
                                                              ) {

  def response: Result = {
    throwable match {
      case validationException: ValidationException =>
        UnprocessableEntity(ValidationErrorRenderer(validationException.errors, UNPROCESSABLE_ENTITY, requestId).render)
      case _ =>
        InternalServerError(ServerErrorRenderer(throwable, INTERNAL_SERVER_ERROR, requestId).render)
    }
  }

}
