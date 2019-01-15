package uk.gov.ons.br.test.http

import play.mvc.Http.HeaderNames.AUTHORIZATION

trait BasicAuthentication {
  case class Authorization(username: String, password: String)

  object Authorization {
    val HeaderName = AUTHORIZATION
    val DummyAuthorization: Authorization = Authorization("", "")

    def value(auth: Authorization): String =
      s"Basic ${Base64.encode(authValue(auth))}"

    private def authValue(auth: Authorization): String =
      s"${auth.username}:${auth.password}"
  }
}

object BasicAuthentication extends BasicAuthentication