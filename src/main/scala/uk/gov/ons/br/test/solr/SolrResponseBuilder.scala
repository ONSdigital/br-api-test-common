package uk.gov.ons.br.test.solr


import java.io.ByteArrayOutputStream

import org.apache.solr.client.solrj.SolrResponse
import org.apache.solr.client.solrj.response.SimpleSolrResponse
import org.apache.solr.common.util.{JavaBinCodec, NamedList}
import org.apache.solr.common.{SolrDocument, SolrDocumentList}

import scala.collection.JavaConverters._
import scala.collection.mutable

/*
 * If you query solr via HTTP, by default the response is Json.
 * However, the client library that we are using ultimately delegates to solrj, which uses a binary protocol (javabin)
 * for efficiency reasons.  As we want to stub Solr responses via Wiremock and have the client library interpret
 * them as if they had been received from a real Solr, we must generate stub responses in the expected binary format.
 * See https://wiki.apache.org/solr/javabin
 */
object SolrResponseBuilder {
  // provides a nicer language construct for tests than 'Map(...)'
  def aDocument(withFields: (String, AnyRef)*): Map[String, AnyRef] =
    Map(withFields: _*)

  /*
   * Note that while values are typed as AnyRef here they must be serializable in accordance with the javabin format.
   */
  def ofSolrJavaBinResponseFor(query: String)(withMatches: Map[String, AnyRef]*): Array[Byte] = {
    val solrResponse = toSolrResponseFor(query)(withMatches)
    marshal(solrResponse)
  }

  def toSolrResponseFor(query: String)(withMatches: Seq[Map[String, AnyRef]]): SolrResponse = {
    val namedList = new NamedList[AnyRef]()
    namedList.add("responseHeader", newResponseHeader(newParams(query)))
    namedList.add("response", toSolrDocumentList(withMatches))

    val solrResponse = new SimpleSolrResponse()
    solrResponse.setElapsedTime(7L)
    solrResponse.setResponse(namedList)
    solrResponse
  }

  private def newParams(query: String): NamedList[AnyRef] = {
    val params = new NamedList[AnyRef]()
    params.add("q", query)
    params
  }

  private def newResponseHeader(params: NamedList[AnyRef]): NamedList[AnyRef] = {
    val responseHeader = new NamedList[AnyRef]()
    responseHeader.add("status", Int.box(0))
    responseHeader.add("QTime", Int.box(5))
    responseHeader.add("params", params)
    responseHeader
  }

  private def toSolrDocumentList(matches: Seq[Map[String, AnyRef]]): SolrDocumentList = {
    val solrDocumentList = new SolrDocumentList()
    solrDocumentList.setNumFound(matches.size)
    solrDocumentList.setStart(0L)
    matches.map(toSolrDocument).foreach {
      solrDocumentList.add
    }
    solrDocumentList
  }

  private def toSolrDocument(valuesByKey: Map[String, AnyRef]): SolrDocument = {
    val fields = mutable.Map(valuesByKey.toSeq: _*).asJava
    new SolrDocument(fields)
  }

  private def marshal(solrResponse: SolrResponse): Array[Byte] = {
    val codec = new JavaBinCodec()
    val baos = new ByteArrayOutputStream()
    codec.marshal(solrResponse.getResponse, baos)
    baos.toByteArray
  }
}