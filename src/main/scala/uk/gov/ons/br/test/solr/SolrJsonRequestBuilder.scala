package uk.gov.ons.br.test.solr


import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock._
import com.github.tomakehurst.wiremock.matching.UrlPattern

import scala.util.matching.Regex

trait SolrJsonRequestBuilder {
  /*
   * This assumes that the client library used to query Solr will use the query parameter API rather than the
   * request document API.  The different approaches are outlined here:
   * https://lucene.apache.org/solr/guide/7_5/json-request-api.html#json-request-api
   */
  def aSearchRequest(ofCollection: String)(forValue: String): MappingBuilder =
    get(searchUrlMatching(ofCollection, forValue))

  private def searchUrlMatching(collection: String, forValue: String): UrlPattern = {
    val selectQuery = raw"""/select\?(q?[^=\&]++\&)*q=${Regex.quote(forValue)}\&?.*"""
    urlMatching(urlPathFor(collection) + selectQuery)
  }

  private def urlPathFor(collection: String): String =
    s"/solr/$collection"
}

object SolrJsonRequestBuilder extends SolrJsonRequestBuilder