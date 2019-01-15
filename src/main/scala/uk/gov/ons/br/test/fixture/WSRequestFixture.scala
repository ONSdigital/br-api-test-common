package uk.gov.ons.br.test.fixture

import org.scalatest.{Outcome, fixture}
import play.api.http.Port
import play.api.libs.ws.WSClient
import play.api.test.WsTestClient

trait WSRequestFixture { this: fixture.TestSuite =>
  override type FixtureParam = WSClient
  val port: Int

  override protected def withFixture(test: OneArgTest): Outcome =
    WsTestClient.withClient { wsClient =>
      withFixture(test.toNoArgTest(wsClient))
    }(new Port(port))
}
