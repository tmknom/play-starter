package library.spray

import library.spray.internal.StringConversion
import spray.json._

import scala.reflect.ClassTag

/**
  * JSON のキー名をスネークケース（アンダースコア区切り）へ変換
  *
  * JsonProtocol のクラス作成時に、本トレイトをミックスインして使う。
  * 自前の JsonProtocol を定義していない場合 import library.spray.SnakeCaseJsonNaming._ する。
  *
  * なお、クラス名は play-json を参考に命名している。
  *
  * @see http://qiita.com/suin/items/70b1d1ee99a595cc07fe
  * @see https://github.com/playframework/play-json/blob/master/play-json/shared/src/main/scala/JsonConfiguration.scala
  */
trait SnakeCaseJsonNaming extends DefaultJsonProtocol {

  /**
    * This is the most important piece of code in this object!
    * It overrides the default naming scheme used by spray-json and replaces it with a scheme that turns camelcased
    * names into snakified names (i.e. using underscores as word separators).
    */
  override protected def extractFieldNames(classTag: ClassTag[_]): Array[String] = {
    super.extractFieldNames(classTag).map(StringConversion.underscore)
  }
}

object SnakeCaseJsonNaming extends SnakeCaseJsonNaming
