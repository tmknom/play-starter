package library.flyway.internal

import javax.sql.DataSource

import org.flywaydb.core.Flyway
import play.api.Configuration
import play.api.db.DBApi
import play.api.inject.Injector

import scala.collection.JavaConverters._

private[flyway] final case class FlywayConfiguration(injector: Injector) {
  // Flywayのマイグレーションをライブラリ呼び出しで使う
  // https://flywaydb.org/documentation/api/javadoc.html
  def configure: Flyway = {
    val flyway = new Flyway()
    flyway.setDataSource(dataSource)
    flyway.setLocations(locations: _*)
    flyway.setOutOfOrder(outOfOrder)
    flyway
  }

  private def dataSource: DataSource = {
    // http://stackoverflow.com/questions/33392905/how-to-apply-manually-evolutions-in-tests-with-slick-and-play-2-4
    injector.instanceOf[DBApi].database("default").dataSource
  }

  private def locations: Seq[String] = {
    conf.underlying.getStringList("flyway.locations").asScala
  }

  private def outOfOrder: Boolean = {
    conf.get[Boolean]("flyway.outOfOrder")
  }

  // conf/test.confを経由してapplication.confからFlyway関連の設定値を読み込む
  private def conf: Configuration = {
    injector.instanceOf[Configuration]
  }
}
