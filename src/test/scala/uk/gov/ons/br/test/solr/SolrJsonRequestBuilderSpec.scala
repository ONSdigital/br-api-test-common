package uk.gov.ons.br.test.solr


import com.github.tomakehurst.wiremock.http.RequestMethod.GET
import org.scalatest.{FreeSpec, Matchers}
import uk.gov.ons.br.test.solr.SolrJsonRequestBuilderSpec.Collection

class SolrJsonRequestBuilderSpec extends FreeSpec with Matchers {

  "A request specification can be defined" - {
    "that represents a Solr search query for a single term" in {
      val mappingBuilder = SolrJsonRequestBuilder.aSearchRequest(ofCollection = Collection)(forValue = "abc123")

      val request = mappingBuilder.build().getRequest

      request.getMethod shouldBe GET
      request.getUrlMatcher.`match`(s"/solr/$Collection/select?q=abc123").isExactMatch shouldBe true
      request.getUrlMatcher.`match`(s"/solr/${Collection.dropRight(1)}/select?q=abc123").isExactMatch shouldBe false
      request.getUrlMatcher.`match`(s"/solr/$Collection/select?q=abc12").isExactMatch shouldBe false
    }

    "that represents a Solr search query for a single term that contains a space" in {
      val mappingBuilder = SolrJsonRequestBuilder.aSearchRequest(ofCollection = Collection)(forValue = "abc+123")

      val request = mappingBuilder.build().getRequest

      request.getMethod shouldBe GET
      request.getUrlMatcher.`match`(s"/solr/$Collection/select?q=abc+123").isExactMatch shouldBe true
      request.getUrlMatcher.`match`(s"/solr/${Collection.dropRight(1)}/select?q=abc+123").isExactMatch shouldBe false
      request.getUrlMatcher.`match`(s"/solr/$Collection/select?q=abcc123").isExactMatch shouldBe false
    }
  }
}

private object SolrJsonRequestBuilderSpec {
  val Collection = "collection"
}