package uk.gov.ons.br.test.hbase

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock.{equalTo, get, put, urlEqualTo}
import play.api.http.HeaderNames.{ACCEPT, CONTENT_TYPE}
import play.mvc.Http.MimeTypes.JSON
import uk.gov.ons.br.test.http.BasicAuthentication

trait HBaseJsonRequestBuilder extends BasicAuthentication {
  def getHBaseJson(namespace: String, tableName: String, rowKey: String,
                   auth: Authorization = Authorization.DummyAuthorization): MappingBuilder =
    get(urlEqualTo(urlForRow(namespace, tableName, rowKey))).
      withHeader(ACCEPT, equalTo(JSON)).
      withHeader(Authorization.HeaderName, equalTo(Authorization.value(auth)))

  def checkedPutHBaseJson(namespace: String, tableName: String, rowKey: String,
                          auth: Authorization = Authorization.DummyAuthorization): MappingBuilder =
    putHBaseJson(urlForRow(namespace, tableName, rowKey).concat("/?check=put"), auth)

  private def putHBaseJson(url: String, auth: Authorization): MappingBuilder =
    put(urlEqualTo(url)).
      withHeader(CONTENT_TYPE, equalTo(JSON)).
      withHeader(Authorization.HeaderName, equalTo(Authorization.value(auth)))

  private def urlForRow(namespace: String, tableName: String, rowKey: String): String =
    s"/$namespace:$tableName/$rowKey"
}

object HBaseJsonRequestBuilder extends HBaseJsonRequestBuilder