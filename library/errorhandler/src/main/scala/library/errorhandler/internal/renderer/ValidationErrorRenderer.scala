package library.errorhandler.internal.renderer

import library.exception.validation.ErrorDetail
import library.trace.RequestId
import play.api.libs.json.{JsObject, Json}

/**
  * バリデーションエラー用のJSONを生成するクラス
  *
  * @todo バリデーションエラー時に、複数のerrorsを返せるように修正する
  */
private[internal] final case class ValidationErrorRenderer(
                                                            private val errors: Seq[ErrorDetail],
                                                            private val statusCode: Int,
                                                            private val requestId: RequestId
                                                          ) {

  def render: JsObject = {
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
      "request_id" -> requestId.value
    )
  }

}
