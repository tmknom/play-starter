/**
  * 依存ライブラリの定義
  *
  * sbt では Ivy を使ってライブラリ依存性の制御を実装している。
  * また sbt は、デフォルトで Maven の標準リポジトリからライブラリを取得しようとする。
  * もし Ivy が依存ライブラリそ見つけられない場合 resolver 設定を追加すること。
  *
  * @see http://www.scala-sbt.org/0.13/docs/ja/Library-Dependencies.html
  */

import play.sbt.PlayImport.{guice, jdbc}
import sbt._

object Version {
  // DB関連
  val MysqlConnectorJava = "5.1.42"
  val SkinnyOrm = "2.3.7"
  val ScalikejdbcPlayInitializer = "2.6.0"
  val ScalikejdbcJsr310 = "2.5.2"

  // テスト関連
  val ScalatestplusPlay = "3.0.0"
}

// noinspection TypeAnnotation
// IntelliJの警告が鬱陶しいので抑制
object Library {
  // MySQL用のJDBCドライバ
  // なお、少し情報が古いがトラップもあるようなので、本番稼働前にある程度確認したほうがよさそう
  // http://saiya-moebius.hatenablog.com/entry/2014/08/20/230445
  val MysqlConnectorJava = "mysql" % "mysql-connector-java" % Version.MysqlConnectorJava

  // O/Rマッパー
  // http://skinny-framework.org/documentation/orm.html
  val SkinnyOrm = "org.skinny-framework" %% "skinny-orm" % Version.SkinnyOrm

  // コネクションプールの作成に必要
  //
  // Skinny-ORM は内部的に ScalikeJDBC を使っており、コネクションプールを初期化する必要がある
  // そのコネクションプールの初期化を担ってくれるようだ
  //
  // アプリケーション起動時に、コネクションプールの初期化するには application.conf に下記記述が必要
  // play.modules.enabled += "scalikejdbc.PlayModule"
  //
  // 余談だが、ScalikeJDBC 自体は日本人が開発しているようで、日本語ドキュメントが開発者によって公開されている
  // https://github.com/scalikejdbc/scalikejdbc-cookbook/tree/master/ja
  //
  // また ScalikeJDBC は近々メジャーバージョンアップが予定されているもよう
  // サポート対象をJava8以上のみとして、JSR-310（ZonedDateTimeとか）にデフォルトで対応するらしい
  // よく分からんけど Reactive Streams というのにも標準対応するらしい
  // https://github.com/scalikejdbc/scalikejdbc/blob/master/notes/3.0.0.markdown
  val ScalikejdbcPlayInitializer = "org.scalikejdbc" %% "scalikejdbc-play-initializer" % Version.ScalikejdbcPlayInitializer

  // scalikejdbc で ZonedDateTime を使うためのライブラリ
  // 3系がリリースされるといらない子になるが、まだ2系なので入れておく
  // https://github.com/scalikejdbc/scalikejdbc-cookbook/blob/master/ja/06_samples.md#joda-time-ではなく-java-se-8-の-date-time-api-を使う
  val ScalikejdbcJsr310 = "org.scalikejdbc" %% "scalikejdbc-jsr310" % Version.ScalikejdbcJsr310

  // xUnit用ライブラリ
  // playの標準テストライブラリなので、そのまま採用する
  val ScalatestplusPlay = "org.scalatestplus.play" %% "scalatestplus-play" % Version.ScalatestplusPlay % Test
}

// noinspection TypeAnnotation
object Dependencies {

  import Library._

  // 最低限必要なライブラリ
  val Base = Seq(
    guice,
    jdbc,
    MysqlConnectorJava,
    SkinnyOrm,
    ScalikejdbcPlayInitializer,
    ScalikejdbcJsr310
  )

  // テスト関連
  val Test = Seq(
    ScalatestplusPlay
  )

  // アプリケーションの依存関係
  val Application = Base ++ Test
}
