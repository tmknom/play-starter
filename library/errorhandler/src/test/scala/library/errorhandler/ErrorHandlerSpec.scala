package library.errorhandler

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.UsefulException
import play.api.http.Status
import play.api.test.{DefaultAwaitTimeout, FakeRequest, FutureAwaits, Injecting}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class ErrorHandlerSpec extends PlaySpec with GuiceOneAppPerSuite with Injecting with FutureAwaits with DefaultAwaitTimeout {

  "ErrorHandler#onClientError" should {
    "例外がスローされないこと" in {
      val actual = inject[ErrorHandler].onClientError(FakeRequest(), Status.BAD_REQUEST, "dummy_message")

      Await.result(actual, Duration.Inf).header.status mustBe Status.BAD_REQUEST
    }
  }

  "ErrorHandler#onServerError" should {
    "例外がスローされないこと" in {
      val throwable = new DummyUsefulException("useful_exception_message", new RuntimeException("runtime_exception_message"))
      val actual = inject[ErrorHandler].onServerError(FakeRequest(), throwable)

      await(actual).header.status mustBe Status.INTERNAL_SERVER_ERROR
    }
  }

  class DummyUsefulException(message: String, cause: Throwable) extends UsefulException(message, cause)

}
