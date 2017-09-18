package library.errorhandler.internal.renderer

import play.api.libs.json.{JsObject, Json}

/**
  * クライアントエラー用のJSONを生成するクラス
  */
private[internal] final case class ClientErrorRenderer(
                                                        private val message: String,
                                                        private val statusCode: Int,
                                                        private val requestId: String
                                                      ) {
  /**
    * クライアントエラーでは例外がスローされるわけではないので、固定値を返す
    */
  private val ClientErrorCode = "ClientError"

  def render: JsObject = {
    Json.obj(
      "errors" -> Json.arr(
        Json.obj(
          "message" -> message,
          "code" -> ClientErrorCode
        )
      ),
      "status_code" -> statusCode,
      "request_id" -> requestId
    )
  }

}
