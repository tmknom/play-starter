package library.migration

import org.flywaydb.core.internal.info.MigrationInfoDumper
import org.flywaydb.core.{Flyway => coreFlyway}
import play.api._
import play.api.db.DBApi

import scala.collection.JavaConverters._

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
class Flyway(app: Application) {

  // Prepare and clean database
  // http://stackoverflow.com/questions/33392905/how-to-apply-manually-evolutions-in-tests-with-slick-and-play-2-4
  private lazy val injector = app.injector

  private lazy val databaseApi = injector.instanceOf[DBApi] //here is the important line

  // conf/test.confを経由してapplication.confからFlyway関連の設定値を読み込む
  private lazy val conf = injector.instanceOf[Configuration]

  // Flywayのマイグレーションをライブラリ呼び出しで使う
  // https://flywaydb.org/documentation/api/javadoc.html
  private lazy val flyway = new coreFlyway()

  private lazy val dataSource = databaseApi.database("default").dataSource

  // 設定の初期化
  flyway.setDataSource(dataSource)
  flyway.setLocations(locations: _*)
  flyway.setOutOfOrder(outOfOrder)

  private def locations(): Seq[String] = {
    conf.getStringList("flyway.locations") match {
      case Some(locations) => locations.asScala // java.util.List[String] => Seq[String]
      // 設定が読めないのは想定外のなので例外で落とす
      case None => throw new FlywayConfigurationException("flyway.locationsが読み込めませんでした。application.confを確認して下さい。")
    }
  }

  private def outOfOrder(): Boolean = {
    conf.getBoolean("flyway.outOfOrder") match {
      case Some(outOfOrder) => outOfOrder
      // 設定が読めないのは想定外のなので例外で落とす
      case None => throw new FlywayConfigurationException("flyway.outOfOrderが読み込めませんでした。application.confを確認して下さい。")
    }
  }

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
