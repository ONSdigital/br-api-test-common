package uk.gov.ons.br.test.hbase

import org.scalatest.Outcome
import uk.gov.ons.br.test.fixture.{ServerAcceptanceSpec, WSRequestFixture}

abstract class AbstractServerAcceptanceSpec extends ServerAcceptanceSpec with WireMockHBase with WSRequestFixture {
  val HBasePort: Int

  override protected def withFixture(test: OneArgTest): Outcome =
    withWireMockHBase(HBasePort) { () =>
      super.withFixture(test)
    }
}
