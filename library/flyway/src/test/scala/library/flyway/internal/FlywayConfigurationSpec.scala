package library.flyway.internal

import org.scalatestplus.play.PlaySpec
import play.api.inject.guice.GuiceApplicationBuilder

class FlywayConfigurationSpec extends PlaySpec {

  "FlywayConfiguration#configure" should {
    "正しくオブジェクトが作られること" in {
      val config: Map[String, Any] = Map[String, Any](
        "db.default.driver" -> "com.mysql.jdbc.Driver",
        "db.default.url" -> "jdbc:mysql://127.0.0.1:3306/db_test?characterEncoding=UTF8&connectionCollation=utf8mb4_bin&useSSL=false",
        "db.default.username" -> "root",
        "db.default.password" -> "",
        "flyway.outOfOrder" -> true,
        "flyway.locations" -> Seq("filesystem:./conf/db/migration/default")
      )
      val app = new GuiceApplicationBuilder().configure(config).build()

      val actual = FlywayConfiguration(app.injector).configure

      actual.isOutOfOrder mustBe true
      actual.getLocations.apply(0) mustBe "filesystem:./conf/db/migration/default"
      actual.getDataSource.getConnection.getMetaData.getDriverName mustBe "MySQL Connector Java"
    }
  }

}
