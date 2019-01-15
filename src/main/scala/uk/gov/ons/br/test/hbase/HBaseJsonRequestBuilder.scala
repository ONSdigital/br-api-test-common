package uk.gov.ons.br.test.hbase

import com.github.tomakehurst.wiremock.client.MappingBuilder
import com.github.tomakehurst.wiremock.client.WireMock.{equalTo, get, urlEqualTo}
import play.api.http.HeaderNames.ACCEPT
import play.mvc.Http.MimeTypes.JSON
import uk.gov.ons.br.test.http.BasicAuthentication

trait HBaseJsonRequestBuilder extends BasicAuthentication {
  def getHBaseJson(namespace: String, tableName: String, rowKey: String,
                   auth: Authorization = Authorization.DummyAuthorization): MappingBuilder =
    get(urlEqualTo(getUrlFor(namespace, tableName, rowKey))).
      withHeader(ACCEPT, equalTo(JSON)).
      withHeader(Authorization.HeaderName, equalTo(Authorization.value(auth)))

  private def getUrlFor(namespace: String, tableName: String, rowKey: String): String =
    s"/$namespace:$tableName/$rowKey"
}

object HBaseJsonRequestBuilder extends HBaseJsonRequestBuilder