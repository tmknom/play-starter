package library.errorhandler.internal

import library.errorhandler.internal.renderer.{ClientErrorRenderer, ServerErrorRenderer, ValidationErrorRenderer}
import library.exception.validation.ErrorDetail
import play.api.libs.json.JsObject

/**
  * エラーレスポンス用のJSONを生成するクラス
  */
private[errorhandler] object ErrorRenderer {
  /**
    * クライアントエラーレスポンス用のJSONを生成
    *
    * @param message    エラーメッセージ
    * @param statusCode HTTPステータスコード
    * @param requestId  リクエストID
    * @return エラーJSON
    */
  def renderClientError(message: String, statusCode: Int, requestId: String): JsObject = {
    ClientErrorRenderer(message, statusCode, requestId).render
  }

  /**
    * サーバエラーレスポンス用のJSONを生成
    *
    * @param throwable  スローされた例外
    * @param statusCode HTTPステータスコード
    * @param requestId  リクエストID
    * @return エラーJSON
    */
  def renderServerError(throwable: Throwable, statusCode: Int, requestId: String): JsObject = {
    ServerErrorRenderer(throwable, statusCode, requestId).render
  }

  /**
    * バリデーションエラーレスポンス用のJSONを生成
    *
    * @param errors     エラー詳細のコレクション
    * @param statusCode HTTPステータスコード
    * @param requestId  リクエストID
    * @return エラーJSON
    */
  def renderValidationError(errors: Seq[ErrorDetail], statusCode: Int, requestId: String): JsObject = {
    ValidationErrorRenderer(errors, statusCode, requestId).render
  }
}
