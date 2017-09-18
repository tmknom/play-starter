package library.flyway

import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.PlaySpec
import play.api.inject.guice.GuiceApplicationBuilder

class FlywaySpec extends PlaySpec with BeforeAndAfterEach {

  private val config: Map[String, Any] = Map[String, Any](
    "db.default.driver" -> "com.mysql.jdbc.Driver",
    "db.default.url" -> "jdbc:mysql://127.0.0.1:3306/db_test?characterEncoding=UTF8&connectionCollation=utf8mb4_bin&useSSL=false",
    "db.default.username" -> "root",
    "db.default.password" -> "",
    "flyway.outOfOrder" -> true,
    "flyway.locations" -> Seq("filesystem:./conf/db/migration/default")
  )
  private val app = new GuiceApplicationBuilder().configure(config).build()
  private val flyway = Flyway(app.injector)

  override def afterEach(): Unit = {
    flyway.clean()
  }

  "Flyway#migrate" should {
    "例外がスローされないこと" in {
      flyway.migrate() mustBe 0
    }
  }

  "Flyway#info" should {
    "例外がスローされないこと" in {
      flyway.info() must include("No migrations found")
    }
  }

}
