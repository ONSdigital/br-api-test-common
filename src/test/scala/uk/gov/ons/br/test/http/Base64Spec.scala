package uk.gov.ons.br.test.http

import org.scalatest.{FreeSpec, Matchers}

class Base64Spec extends FreeSpec with Matchers {
  /*
   * Examples here are the Base64 "test vectors" from RFC4648.
   * See: https://tools.ietf.org/html/rfc4648#page-5
   */
  "A string can be Base64 encoded" in {
    Base64.encode("") shouldBe ""
    Base64.encode("f") shouldBe "Zg=="
    Base64.encode("fo") shouldBe "Zm8="
    Base64.encode("foo") shouldBe "Zm9v"
    Base64.encode("foob") shouldBe "Zm9vYg=="
    Base64.encode("fooba") shouldBe "Zm9vYmE="
    Base64.encode("foobar") shouldBe "Zm9vYmFy"
  }
}
