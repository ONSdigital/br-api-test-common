package uk.gov.ons.br.test.hbase

import com.github.tomakehurst.wiremock.http.RequestMethod.{GET, PUT}
import com.github.tomakehurst.wiremock.matching.{EqualToPattern, MultiValuePattern}
import org.scalatest.{FreeSpec, Matchers}
import play.api.http.HeaderNames.{ACCEPT, AUTHORIZATION, CONTENT_TYPE}
import play.api.http.MimeTypes.JSON
import uk.gov.ons.br.test.hbase.HBaseJsonRequestBuilderSpec._

class HBaseJsonRequestBuilderSpec extends FreeSpec with Matchers {

  "A request specification can be defined" - {
    "that represents a HBase REST query" in {
      val mappingBuilder = HBaseJsonRequestBuilder.getHBaseJson(Namespace, TableName, RowKey, BasicAuthentication)

      val request = mappingBuilder.build().getRequest

      request.getMethod shouldBe GET
      request.getUrl shouldBe s"/$Namespace:$TableName/$RowKey"
      request.getHeaders.get(ACCEPT) shouldBe MultiValuePattern.of(new EqualToPattern(JSON))
      request.getHeaders.get(AUTHORIZATION) shouldBe MultiValuePattern.of(new EqualToPattern(AuthHeaderValue))
    }

    "that represents a HBase REST checked update" in {
      val mappingBuilder = HBaseJsonRequestBuilder.checkedPutHBaseJson(Namespace, TableName, RowKey, BasicAuthentication)

      val request = mappingBuilder.build().getRequest

      request.getMethod shouldBe PUT
      request.getUrl shouldBe s"/$Namespace:$TableName/$RowKey/?check=put"
      request.getHeaders.get(CONTENT_TYPE) shouldBe MultiValuePattern.of(new EqualToPattern(JSON))
      request.getHeaders.get(AUTHORIZATION) shouldBe MultiValuePattern.of(new EqualToPattern(AuthHeaderValue))
    }
  }
}

private object HBaseJsonRequestBuilderSpec {
  import HBaseJsonRequestBuilder.Authorization

  val Namespace = "namespace"
  val TableName = "tableName"
  val RowKey = "rowKey"
  val BasicAuthentication = Authorization("username", "password")
  val AuthHeaderValue = Authorization.value(BasicAuthentication)
}