package library.filter

import akka.stream.Materializer
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.mvc._
import play.api.test.{DefaultAwaitTimeout, FakeRequest, FutureAwaits, Injecting}

import scala.concurrent.Future

class RequestLoggingFilterSpec extends PlaySpec with GuiceOneAppPerSuite with Injecting with FutureAwaits with DefaultAwaitTimeout {

  implicit lazy val materializer: Materializer = app.materializer

  "RequestLoggingFilter#apply" should {
    "例外がスローされないこと" in {
      val filter: Filter = inject[RequestLoggingFilter]

      val next = inject[DefaultControllerComponents].actionBuilder(Results.Ok("success"))
      val action: EssentialAction = filter.apply(next) // actionとactualを一行で書くとIntelliJが怒るので意図的に二行にしている
      val actual: Future[Result] = action(FakeRequest()).run()

      await(actual).header.status mustBe Status.OK
    }
  }

}
