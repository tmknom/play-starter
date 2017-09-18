package library.errorhandler.internal.renderer

import play.api.libs.json.{JsObject, Json}

/**
  * サーバエラー用のJSONを生成するクラス
  */
private[internal] final case class ServerErrorRenderer(
                                                        private val throwable: Throwable,
                                                        private val statusCode: Int,
                                                        private val requestId: String
                                                      ) {

  def render: JsObject = {
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

}
