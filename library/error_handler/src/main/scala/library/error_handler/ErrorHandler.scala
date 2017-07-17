package library.error_handler

import javax.inject.{Inject, Provider, Singleton}

import library.error_handler.internal.{ErrorLogger, ErrorNotification, ErrorRenderer}
import library.exception.validation.ValidationException
import library.request.RequestId
import play.api._
import play.api.http.DefaultHttpErrorHandler
import play.api.http.Status.{BAD_REQUEST, INTERNAL_SERVER_ERROR, UNPROCESSABLE_ENTITY}
import play.api.mvc.Results.{InternalServerError, UnprocessableEntity}
import play.api.mvc.{RequestHeader, Result, Results}
import play.api.routing.Router

import scala.concurrent.Future

/**
  * エラーハンドラー
  *
  * 例外がスローされると最終的にここでハンドリングを行う。
  * 例外ハンドラーの主な責務は3つ。
  * ・エラーJSONを返す
  * ・エラーログを出力する
  * ・エラーを通知する
  *
  * 本エラーハンドラーを使用するようアプリケーションに組み込むには、明示的に設定ファイルへの記述が必要。
  * 多くの場合 conf/application.conf ファイルに記述することになる。
  * 設定箇所は play.http の errorHandler の項目である。
  *
  * @see https://www.playframework.com/documentation/2.5.x/ScalaErrorHandling#Extending-the-default-error-handler
  * @param env          The environment for the application.
  * @param config       A full configuration set.
  * @param sourceMapper provides source code to be displayed on error pages
  * @param router       A router.
  */
@Singleton
final class ErrorHandler @Inject()(
                                    env: Environment,
                                    config: Configuration,
                                    sourceMapper: OptionalSourceMapper,
                                    router: Provider[Router]
                                  ) extends DefaultHttpErrorHandler(env, config, sourceMapper, router) {

  /**
    * クライアントエラーが発生したときに実行
    *
    * 例外をスローするんじゃないと WartRemover に怒られるが、意図的なので、警告は抑制している。
    *
    * @param request    リクエストヘッダー
    * @param statusCode HTTPステータスコード
    * @param message    エラーメッセージ
    */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    statusCode match {
      case clientErrorStatusCode if statusCode >= BAD_REQUEST && statusCode < INTERNAL_SERVER_ERROR => {
        val requestId = RequestId(request).value
        val body = ErrorRenderer.renderClientError(message, clientErrorStatusCode, requestId)
        Future.successful(Results.Status(statusCode)(body))
      }
      case _ =>
        throw new IllegalArgumentException(s"onClientError invoked with non client error status code $statusCode: $message")
    }
  }

  /**
    * 本番環境でサーバーエラーが発生したときに実行
    *
    * @param request   リクエストヘッダー
    * @param exception スローされた例外
    */
  override protected def onProdServerError(request: RequestHeader, exception: UsefulException): Future[Result] = {
    val throwable = exception.cause

    ErrorNotification.notify(request, throwable)
    renderServerError(request, throwable)
  }

  private def renderServerError(requestHeader: RequestHeader, throwable: Throwable): Future[Result] = {
    val requestId = RequestId(requestHeader).value
    throwable match {
      case validationException: ValidationException => {
        Future.successful(UnprocessableEntity(
          ErrorRenderer.renderValidationError(validationException.errors, UNPROCESSABLE_ENTITY, requestId)
        ))
      }
      case _ => {
        Future.successful(InternalServerError(
          ErrorRenderer.renderServerError(throwable, INTERNAL_SERVER_ERROR, requestId)
        ))
      }
    }
  }

  /**
    * 開発環境でサーバーエラーが発生したときに実行
    *
    * とりあえず本番環境と同じ処理をすることにしているが、
    * 開発環境だけ、ハンドリング方法を変更することも可能。
    * たぶんデバッグしやすいようにカスタマイズできる余地を残しているんだと思う。
    *
    * @param request   リクエストヘッダー
    * @param exception スローされた例外
    */
  override protected def onDevServerError(request: RequestHeader, exception: UsefulException): Future[Result] = {
    onProdServerError(request, exception)
  }

  /**
    * エラーログの出力
    *
    * UsefulException しかログ出力してくれないが、それでいいんかいなって気持ちになる。
    * このメソッドをオーバーライドするんじゃなくて、フツーにログ出力処理を呼び出したほうがいいかもしれない。
    *
    * @param request         リクエストヘッダー
    * @param usefulException スローされた例外
    */
  override protected def logServerError(request: RequestHeader, usefulException: UsefulException) {
    ErrorLogger.error(request, usefulException.cause)
  }
}
