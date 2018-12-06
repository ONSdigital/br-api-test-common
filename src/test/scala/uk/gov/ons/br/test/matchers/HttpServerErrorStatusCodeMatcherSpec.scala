package uk.gov.ons.br.test.matchers

import org.scalatest.{FreeSpec, Matchers}
import play.api.http.Status._
import uk.gov.ons.br.test.matchers.HttpServerErrorStatusCodeMatcher.aServerError

class HttpServerErrorStatusCodeMatcherSpec extends FreeSpec with Matchers {

  "Informational statuses are not server errors" in {
    CONTINUE should not be aServerError
    SWITCHING_PROTOCOLS should not be aServerError
  }

  "Successful statuses are not server errors" in {
    OK should not be aServerError
    CREATED should not be aServerError
    ACCEPTED should not be aServerError
    NON_AUTHORITATIVE_INFORMATION should not be aServerError
    NO_CONTENT should not be aServerError
    RESET_CONTENT should not be aServerError
    PARTIAL_CONTENT should not be aServerError
  }

  "Redirection statuses are not server errors" in {
    MULTIPLE_CHOICES should not be aServerError
    MOVED_PERMANENTLY should not be aServerError
    FOUND should not be aServerError
    SEE_OTHER should not be  aServerError
    NOT_MODIFIED should not be aServerError
    USE_PROXY should not be aServerError
    TEMPORARY_REDIRECT should not be aServerError
  }

  "Client Error statuses are not server errors" in {
    BAD_REQUEST should not be aServerError
    UNAUTHORIZED should not be aServerError
    PAYMENT_REQUIRED should not be aServerError
    FORBIDDEN should not be aServerError
    NOT_FOUND should not be aServerError
    METHOD_NOT_ALLOWED should not be aServerError
    NOT_ACCEPTABLE should not be aServerError
    PROXY_AUTHENTICATION_REQUIRED should not be aServerError
    REQUEST_TIMEOUT should not be aServerError
    CONFLICT should not be aServerError
    GONE should not be aServerError
    LENGTH_REQUIRED should not be aServerError
    PRECONDITION_FAILED should not be aServerError
    REQUEST_ENTITY_TOO_LARGE should not be aServerError
    REQUEST_URI_TOO_LONG should not be aServerError
    UNSUPPORTED_MEDIA_TYPE should not be aServerError
    REQUESTED_RANGE_NOT_SATISFIABLE should not be aServerError
    EXPECTATION_FAILED should not be aServerError
    UNPROCESSABLE_ENTITY should not be aServerError
  }

  "Server Error statuses are server errors" in {
    INTERNAL_SERVER_ERROR shouldBe aServerError
    NOT_IMPLEMENTED shouldBe aServerError
    BAD_GATEWAY shouldBe aServerError
    SERVICE_UNAVAILABLE shouldBe aServerError
    GATEWAY_TIMEOUT shouldBe aServerError
    HTTP_VERSION_NOT_SUPPORTED shouldBe aServerError
    INSUFFICIENT_STORAGE shouldBe aServerError
  }
}
