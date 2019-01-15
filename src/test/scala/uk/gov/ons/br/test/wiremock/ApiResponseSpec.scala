package uk.gov.ons.br.test.wiremock

import org.scalatest.{FreeSpec, Matchers}
import play.api.http.Status._
import uk.gov.ons.br.test.wiremock.ApiResponse._

class ApiResponseSpec extends FreeSpec with Matchers {

  "An Api Response can be specified with status" - {
    "OK" in {
      anOkResponse().build().getStatus shouldBe OK
    }

    "NO_CONTENT" in {
      aNoContentResponse().build().getStatus shouldBe NO_CONTENT
    }

    "NOT_MODIFIED" in {
      aNotModifiedResponse().build().getStatus shouldBe NOT_MODIFIED
    }

    "NOT_FOUND" in {
      aNotFoundResponse().build().getStatus shouldBe NOT_FOUND
    }

    "CONFLICT" in {
      aConflictResponse().build().getStatus shouldBe CONFLICT
    }

    "UNPROCESSABLE_ENTITY" in {
      anUnprocessableEntityResponse().build().getStatus shouldBe UNPROCESSABLE_ENTITY
    }

    "INTERNAL_SERVER_ERROR" in {
      anInternalServerErrorResponse().build().getStatus shouldBe INTERNAL_SERVER_ERROR
    }

    "SERVICE_UNAVAILABLE" in {
      aServiceUnavailableResponse().build().getStatus shouldBe SERVICE_UNAVAILABLE
    }
  }
}
