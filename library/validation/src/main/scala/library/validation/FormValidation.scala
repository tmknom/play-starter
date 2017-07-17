package library.validation

import play.api.data.{Form, FormError}
import play.api.mvc.Request

/**
  * 入力フォームのバリデーション
  */
object FormValidation {
  /**
    * 入力バリデーションを行い、フォームオブジェクトからドメインオブジェクトへマッピングする
    *
    * バリデーションエラーの場合は例外をスローする
    *
    * @param form    フォームオブジェクト
    * @param request リクエスト
    * @tparam A フォームオブジェクトからマッピングするクラス
    * @return バリデーションエラーのないオブジェクト
    */
  @SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter"))
  def validate[A](form: Form[A])(implicit request: Request[_]): A = {
    form.bindFromRequest.fold(
      formWithErrors => {
        throw new ValidationException(convertErrors(formWithErrors))
      },
      validObject => validObject
    )
  }

  private def convertErrors[A](formWithErrors: Form[A]): Seq[ErrorDetail] = {
    formWithErrors.errors.map { error =>
      ErrorDetail(createMessage(error), ValidationExceptionCode)
    }
  }

  private def createMessage(formError: FormError): String = {
    Map(
      "key" -> formError.key,
      "detail" -> s"${formError.message}${formError.args.mkString("(", ", ", ")")}"
    ).mkString("{", ", ", "}")
  }

  private val ValidationExceptionCode = "ValidationException"
}
