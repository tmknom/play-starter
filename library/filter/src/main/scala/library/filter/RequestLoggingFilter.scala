package library.filter

import javax.inject.{Inject, Singleton}

import akka.stream.Materializer
import library.filter.internal.{RequestLogger, RequestTime}
import library.trace.{CorrelationId, RequestId}
import play.api.mvc.{Filter, RequestHeader, Result}

import scala.concurrent.{ExecutionContext, Future}

/**
  * HTTPリクエストとレスポンスをロギングするクラス
  */
@Singleton
@SuppressWarnings(Array("org.wartremover.warts.ImplicitParameter"))
final class RequestLoggingFilter @Inject()(implicit override val mat: Materializer,
                                           exec: ExecutionContext) extends Filter {

  def apply(nextFilter: RequestHeader => Future[Result])
           (requestHeader: RequestHeader): Future[Result] = {

    val startTime = System.currentTimeMillis
    val correlationId = CorrelationId(requestHeader)
    val requestId = RequestId(requestHeader)
    RequestLogger.logStart(requestHeader, correlationId, requestId)

    nextFilter(requestHeader).map { result =>
      val requestTime = RequestTime(System.currentTimeMillis - startTime)
      RequestLogger.logEnd(requestHeader, correlationId, requestId, result, requestTime)
      result
    }
  }
}
