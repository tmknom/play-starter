package library.error_handler.internal

import library.validation.ErrorDetail
import play.api.libs.json.{JsObject, Json}

/**
  * エラーレスポンス用のJSONを生成するクラス
  */
private[error_handler] object ErrorRenderer {
  /**
    * クライアントエラーでは例外がスローされるわけではないので、固定値を返す
    */
  private val ClientErrorCode = "ClientError"

  /**
    * クライアントエラーレスポンス用のJSONを生成
    *
    * @param message    エラーメッセージ
    * @param statusCode HTTPステータスコード
    * @param requestId  リクエストID
    * @return エラーJSON
    */
  def renderClientError(message: String, statusCode: Int, requestId: String): JsObject = {
    Json.obj(
      "errors" -> Json.arr(
        Json.obj(
          "code" -> ClientErrorCode,
          "message" -> message
        )
      ),
      "status_code" -> statusCode,
      "request_id" -> requestId
    )
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
    Json.obj(
      "errors" -> Json.arr(
        Json.obj(
          "message" -> throwable.getMessage,
          "code" -> throwable.getClass.getSimpleName
        )
      ),
      "status_code" -> statusCode,
      "request_id" -> requestId
    )
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
    Json.obj(
      "errors" -> Json.arr(
        errors.map { error =>
          Json.obj(
            "message" -> error.message,
            "code" -> error.code
          )
        }
      ),
      "status_code" -> statusCode,
      "request_id" -> requestId
    )
  }
}
