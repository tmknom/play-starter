package library.migration

/**
  * Flyway設定例外
  *
  * Flywayの設定が読み込めなかったり不正な値の場合にthrowされる。
  * これが発生するときはapplication.confの設定値がおかしいか、
  * そもそも読み込めていない可能性がある。
  *
  * 例外クラスの定義では、デフォルト引数＋初期値nullを許容する。
  * コンストラクタを追加で明示的に定義すれば、デフォルト引数の警告は消せるが、
  * 特段リーダブルにならないので、警告を抑制する方針にした。
  */
@SuppressWarnings(Array("org.wartremover.warts.DefaultArguments", "org.wartremover.warts.Null"))
final class FlywayConfigurationException(message: String = null, cause: Throwable = null) extends RuntimeException(message, cause) // scalastyle:ignore
