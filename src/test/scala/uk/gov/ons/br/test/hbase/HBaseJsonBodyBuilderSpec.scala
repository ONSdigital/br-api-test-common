package uk.gov.ons.br.test.hbase

import org.scalatest.{FreeSpec, Matchers}
import play.api.libs.json.Json
import uk.gov.ons.br.test.hbase.HBaseJsonBodyBuilder.NoMatchFoundResponse
import uk.gov.ons.br.test.hbase.HBaseJsonBodyBuilderSpec._
import HBaseJsonBodyBuilder.hbaseEncode

class HBaseJsonBodyBuilderSpec extends FreeSpec with Matchers {

  "A HBase JsonBodyBuilder" - {
    "can build an empty body" in {
      Json.parse(HBaseJsonBodyBuilder.aBodyWith()) shouldBe Json.parse(NoMatchFoundResponse)
    }

    "can build a body containing one row" - {
      "containing one cell" in {
        val expectedStr =
          s"""|{
              |  "Row": [
              |    {
              |      "key": "${hbaseEncode(RowKey1)}",
              |      "Cell": [
              |        {
              |          "column": "${hbaseEncode(Family + ":" + Qualifier1)}",
              |          "timestamp": $Timestamp1,
              |          "$$": "${hbaseEncode(Value1)}"
              |        }
              |      ]
              |    }
              |  ]
              |}""".stripMargin

        val actualStr = HBaseJsonBodyBuilder.aBodyWith(
          HBaseJsonBodyBuilder.aRowWith(key = RowKey1,
            HBaseJsonBodyBuilder.aColumnWith(family = Family, qualifier = Qualifier1, value = Value1, timestamp = Some(Timestamp1))
          )
        )

        Json.parse(actualStr) shouldBe Json.parse(expectedStr)
      }

      "containing multiple cells" in {
        val expectedStr =
          s"""|{
              |  "Row": [
              |    {
              |      "key": "${hbaseEncode(RowKey1)}",
              |      "Cell": [
              |        {
              |          "column": "${hbaseEncode(Family + ":" + Qualifier1)}",
              |          "timestamp": $Timestamp1,
              |          "$$": "${hbaseEncode(Value1)}"
              |        },
              |        {
              |          "column": "${hbaseEncode(Family + ":" + Qualifier2)}",
              |          "timestamp": $Timestamp2,
              |          "$$": "${hbaseEncode(Value2)}"
              |        }
              |      ]
              |    }
              |  ]
              |}""".stripMargin

        val actualStr = HBaseJsonBodyBuilder.aBodyWith(
          HBaseJsonBodyBuilder.aRowWith(key = RowKey1,
            HBaseJsonBodyBuilder.aColumnWith(family = Family, qualifier = Qualifier1, value = Value1, timestamp = Some(Timestamp1)),
            HBaseJsonBodyBuilder.aColumnWith(family = Family, qualifier = Qualifier2, value = Value2, timestamp = Some(Timestamp2))
          )
        )

        Json.parse(actualStr) shouldBe Json.parse(expectedStr)
      }
    }

    "can build a body containing multiple rows" in {
      val expectedStr =
        s"""|{
            |  "Row": [
            |    {
            |      "key": "${hbaseEncode(RowKey1)}",
            |      "Cell": [
            |        {
            |          "column": "${hbaseEncode(Family + ":" + Qualifier1)}",
            |          "timestamp": $Timestamp1,
            |          "$$": "${hbaseEncode(Value1)}"
            |        }
            |      ]
            |    },
            |    {
            |      "key": "${hbaseEncode(RowKey2)}",
            |      "Cell": [
            |        {
            |          "column": "${hbaseEncode(Family + ":" + Qualifier1)}",
            |          "timestamp": $Timestamp2,
            |          "$$": "${hbaseEncode(Value2)}"
            |        }
            |      ]
            |    }
            |  ]
            |}""".stripMargin

      val actualStr = HBaseJsonBodyBuilder.aBodyWith(
        HBaseJsonBodyBuilder.aRowWith(key = RowKey1,
          HBaseJsonBodyBuilder.aColumnWith(family = Family, qualifier = Qualifier1, value = Value1, timestamp = Some(Timestamp1))
        ),
        HBaseJsonBodyBuilder.aRowWith(key = RowKey2,
          HBaseJsonBodyBuilder.aColumnWith(family = Family, qualifier = Qualifier1, value = Value2, timestamp = Some(Timestamp2))
        )
      )

      Json.parse(actualStr) shouldBe Json.parse(expectedStr)
    }

    /*
     * Examples here are the Base64 "test vectors" from RFC4648.
     * See: https://tools.ietf.org/html/rfc4648#page-5
     */
    "encodes values in Base64" in {
      hbaseEncode("") shouldBe ""
      hbaseEncode("f") shouldBe "Zg=="
      hbaseEncode("fo") shouldBe "Zm8="
      hbaseEncode("foo") shouldBe "Zm9v"
      hbaseEncode("foob") shouldBe "Zm9vYg=="
      hbaseEncode("fooba") shouldBe "Zm9vYmE="
      hbaseEncode("foobar") shouldBe "Zm9vYmFy"
    }
  }
}

object HBaseJsonBodyBuilderSpec {
  val RowKey1 = "some-key1"
  val RowKey2 = "some-key2"
  val Family = "some-family"
  val Qualifier1 = "some-qualifier1"
  val Qualifier2 = "some-qualifier2"
  val Value1 = "some-value1"
  val Value2 = "some-value2"
  val Timestamp1 = System.currentTimeMillis()
  val Timestamp2 = Timestamp1 + 100L
}
