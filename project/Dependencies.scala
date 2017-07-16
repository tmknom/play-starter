/**
  * 依存ライブラリの定義
  *
  * sbt では Ivy を使ってライブラリ依存性の制御を実装している。
  * また sbt は、デフォルトで Maven の標準リポジトリからライブラリを取得しようとする。
  * もし Ivy が依存ライブラリそ見つけられない場合 resolver 設定を追加すること。
  *
  * @see http://www.scala-sbt.org/0.13/docs/ja/Library-Dependencies.html
  */

import play.sbt.PlayImport.guice
import sbt._

object Version {
  // テスト関連
  val ScalatestplusPlay = "3.0.0"
}

// noinspection TypeAnnotation
// IntelliJの警告が鬱陶しいので抑制
object Library {
  // xUnit用ライブラリ
  // playの標準テストライブラリなので、そのまま採用する
  val ScalatestplusPlay = "org.scalatestplus.play" %% "scalatestplus-play" % Version.ScalatestplusPlay % Test
}

// noinspection TypeAnnotation
object Dependencies {

  import Library._

  // 最低限必要なライブラリ
  val Base = Seq(
    guice
  )

  // テスト関連
  val Test = Seq(
    ScalatestplusPlay
  )

  // アプリケーションの依存関係
  val Application = Base ++ Test
}
