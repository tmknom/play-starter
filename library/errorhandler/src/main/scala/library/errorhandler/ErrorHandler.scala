package library.errorhandler

import javax.inject.{Inject, Provider, Singleton}

import library.errorhandler.internal.{ClientErrorHttpResponse, ErrorLogger, ErrorNotification, ServerErrorHttpResponse}
import library.trace.RequestId
import play.api.http.DefaultHttpErrorHandler
import play.api.mvc.{RequestHeader, Result}
import play.api.routing.Router
import play.api.{Configuration, Environment, OptionalSourceMapper, UsefulException}

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
  * @see https://www.playframework.com/documentation/2.6.x/ScalaErrorHandling#Extending-the-default-error-handler
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
    * @param requestHeader リクエストヘッダー
    * @param statusCode    HTTPステータスコード
    * @param message       エラーメッセージ
    */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  override def onClientError(requestHeader: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    val response = ClientErrorHttpResponse(RequestId(requestHeader), statusCode, message).response
    Future.successful(response)
  }

  /**
    * 本番環境でサーバーエラーが発生したときに実行
    *
    * @param requestHeader リクエストヘッダー
    * @param exception     スローされた例外
    */
  override protected def onProdServerError(requestHeader: RequestHeader, exception: UsefulException): Future[Result] = {
    val throwable = exception.cause

    ErrorNotification(requestHeader, throwable).notifyTrace()

    val response = ServerErrorHttpResponse(RequestId(requestHeader), throwable).response
    Future.successful(response)
  }

  /**
    * 開発環境でサーバーエラーが発生したときに実行
    *
    * とりあえず本番環境と同じ処理をすることにしているが、
    * 開発環境だけ、ハンドリング方法を変更することも可能。
    * たぶんデバッグしやすいようにカスタマイズできる余地を残しているんだと思う。
    *
    * @param requestHeader リクエストヘッダー
    * @param exception     スローされた例外
    */
  override protected def onDevServerError(requestHeader: RequestHeader, exception: UsefulException): Future[Result] = {
    onProdServerError(requestHeader, exception)
  }

  /**
    * エラーログの出力
    *
    * UsefulException しかログ出力してくれないが、それでいいんかいなって気持ちになる。
    * このメソッドをオーバーライドするんじゃなくて、フツーにログ出力処理を呼び出したほうがいいかもしれない。
    *
    * @param requestHeader   リクエストヘッダー
    * @param usefulException スローされた例外
    */
  override protected def logServerError(requestHeader: RequestHeader, usefulException: UsefulException) {
    ErrorLogger(requestHeader, usefulException.cause).log()
  }
}
