package library.flyway

import library.flyway.internal.FlywayConfiguration
import org.flywaydb.core.internal.info.MigrationInfoDumper
import play.api.Application

/**
  * DBマイグレーションツールFlyway
  *
  * production/stagingのマイグレーションやtestでflywayを単発実行したいときがあるので
  * 設定の読み込みなどを共通化するクラス
  *
  * コンストラクタがplay.api.Applicationに依存しているが
  * 純粋なFlywayの設定項目だけのクラスを用意しても
  * playのapplication.confに設定項目を書いてて、結局injectが必要で煩雑なので
  * inject含めてこのクラスの中で設定値の読み出しまでやることにした
  */
private[library] final case class Flyway(app: Application) {
  private lazy val flyway = FlywayConfiguration(app.injector).configure

  // マイグレーション状態の表示
  def info(): String = {
    val info = flyway.info()
    // flyway-sbtでflywayInfoに使用されている表示用のテーブル整形メソッド
    // interalなAPIなので注意が必要だけど自分で整形するのもだるいので使う
    MigrationInfoDumper.dumpToAsciiTable(info.all())
  }

  // マイグレーション実行
  def migrate(): Int = {
    flyway.migrate()
  }

  // マイグレーション全消し
  // テーブルが全部消えるので実行注意！！
  def clean(): Unit = {
    flyway.clean()
  }
}
