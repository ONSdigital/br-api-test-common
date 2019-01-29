package uk.gov.ons.br.test.solr


import com.github.tomakehurst.wiremock.client.MappingBuilder
import org.slf4j.{Logger, LoggerFactory}
import uk.gov.ons.br.test.wiremock.{ApiResponse, WireMockSupport}

trait WireMockSolr extends ApiResponse {

  private lazy val logger: Logger = LoggerFactory.getLogger(this.getClass)
  private var wireMockSupportContainer: Option[WireMockSupport] = None

  def startMockSolr(port: Int): Unit = {
    logger.debug("Starting WireMockSolr on port [{}].", port)
    wireMockSupportContainer = Some(WireMockSupport.start(port))
  }

  def stopMockSolr(): Unit = {
    wireMockSupportContainer.foreach { wm =>
      logger.debug("Stopping WireMockSolr on port [{}].", WireMockSupport.port(wm))
      WireMockSupport.stop(wm)
    }
    wireMockSupportContainer = None
  }

  def withWireMockSolr[A](port: Int)(fn: () => A): A = {
    startMockSolr(port)
    try fn()
    finally stopMockSolr()
  }

  def stubSolrFor(mappingBuilder: MappingBuilder): Unit = {
    require(wireMockSupportContainer.isDefined, "WireMockSolr must be started before it can be stubbed")
    wireMockSupportContainer.foreach(wm => WireMockSupport.registerMapping(wm)(mappingBuilder))
  }
}
