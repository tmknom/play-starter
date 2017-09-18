package library.test

import library.flyway.Flyway
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite

trait DatabaseSpec extends PlaySpec with GuiceOneAppPerSuite with BeforeAndAfterEach {
  private lazy val flyway = Flyway(app.injector)

  override def beforeEach(): Unit = {
    val _ = flyway.migrate()
  }

  override def afterEach(): Unit = {
    flyway.clean()
  }
}
