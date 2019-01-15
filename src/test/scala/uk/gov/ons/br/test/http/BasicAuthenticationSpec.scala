package uk.gov.ons.br.test.http

import org.scalatest.{FreeSpec, Matchers}

class BasicAuthenticationSpec extends FreeSpec with Matchers {
  /*
   * Using the example from: https://en.wikipedia.org/wiki/Basic_access_authentication
   *
   * '...Aladdin as the username and OpenSesame as the password, then the field's value is the base64-encoding of
   * Aladdin:OpenSesame, or QWxhZGRpbjpPcGVuU2VzYW1l'
   */
  "The value of the Authorization header for Basic Authentication can be determined" in {
    val credentials = BasicAuthentication.Authorization(username = "Aladdin", password = "OpenSesame")

    BasicAuthentication.Authorization.value(credentials) shouldBe "Basic QWxhZGRpbjpPcGVuU2VzYW1l"
  }
}
