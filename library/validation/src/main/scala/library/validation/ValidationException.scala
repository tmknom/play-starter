package library.validation

/**
  * バリデーション例外
  *
  * 例外クラスの定義では、デフォルト引数＋初期値nullを許容する。
  * コンストラクタを追加で明示的に定義すれば、デフォルト引数の警告は消せるが、
  * 特段リーダブルにならないので、警告を抑制する方針にした。
  */
@SuppressWarnings(Array("org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Null"))
final class ValidationException(val errors: Seq[ErrorDetail], message: String = null, cause: Throwable = null) // scalastyle:ignore
  extends RuntimeException(message, cause)
