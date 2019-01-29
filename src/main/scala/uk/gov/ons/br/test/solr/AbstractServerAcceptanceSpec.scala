package uk.gov.ons.br.test.solr

import org.scalatest.Outcome
import uk.gov.ons.br.test.fixture.{ServerAcceptanceSpec, WSRequestFixture}

abstract class AbstractServerAcceptanceSpec extends ServerAcceptanceSpec with WireMockSolr with WSRequestFixture {
  val SolrPort: Int

  override protected def withFixture(test: OneArgTest): Outcome =
    withWireMockSolr(SolrPort) { () =>
      super.withFixture(test)
    }
}
