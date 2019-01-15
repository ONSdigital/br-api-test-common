package uk.gov.ons.br.test.hbase

import com.github.tomakehurst.wiremock.client.MappingBuilder
import org.slf4j.{Logger, LoggerFactory}
import uk.gov.ons.br.test.wiremock.{ApiResponse, WireMockSupport}

trait WireMockHBase extends ApiResponse {

  private lazy val logger: Logger = LoggerFactory.getLogger(this.getClass)
  private var wireMockSupportContainer: Option[WireMockSupport] = None

  def startMockHBase(port: Int): Unit = {
    logger.debug("Starting WireMockHBase on port [{}].", port)
    wireMockSupportContainer = Some(WireMockSupport.start(port))
  }

  def stopMockHBase(): Unit = {
    wireMockSupportContainer.foreach { wm =>
      logger.debug("Stopping WireMockHBase on port [{}].", WireMockSupport.port(wm))
      WireMockSupport.stop(wm)
    }
    wireMockSupportContainer = None
  }

  def withWireMockHBase[A](port: Int)(fn: () => A): A = {
    startMockHBase(port)
    try fn()
    finally stopMockHBase()
  }

  def stubHBaseFor(mappingBuilder: MappingBuilder): Unit = {
    require(wireMockSupportContainer.isDefined, "WireMockHBase must be started before it can be stubbed")
    wireMockSupportContainer.foreach(wm => WireMockSupport.registerMapping(wm)(mappingBuilder))
  }
}
