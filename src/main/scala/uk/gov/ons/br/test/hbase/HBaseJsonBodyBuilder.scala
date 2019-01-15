package uk.gov.ons.br.test.hbase

import uk.gov.ons.br.test.http.Base64

trait HBaseJsonBodyBuilder {
  /*
   * Cloudera currently returns a 200 OK Response containing an "empty row".
   * Note that the latest version of Apache HBase returns a 404 NOT FOUND Response -
   * so we may encounter a change in behaviour with a future Cloudera upgrade.
   */
  val NoMatchFoundResponse = """{"Row":[]}"""

  val hbaseEncode: String => String =
    Base64.encode

  def aBodyWith(rows: String*): String =
    s"""{"Row": ${rows.mkString("[", ",", "]")}}"""

  def aRowWith(key: String, column: String, otherColumns: String*): String =
    s"""|{"key": "${hbaseEncode(key)}",
        | "Cell": ${(column +: otherColumns).mkString("[", ",", "]")}
        |}""".stripMargin

  /*
   * A timestamp will exist on a read, but not on a write.
   */
  def aColumnWith(family: String, qualifier: String, value: String, timestamp: Option[Long] = Some(1520333985745L)): String = {
    val name = columnName(family, qualifier)
    timestamp.fold(
      s"""|{"column": "${hbaseEncode(name)}",
          | "$$": "${hbaseEncode(value)}"
          |}""".stripMargin
    ) { timestampValue =>
      s"""|{"column": "${hbaseEncode(name)}",
          | "timestamp": $timestampValue,
          | "$$": "${hbaseEncode(value)}"
          |}""".stripMargin
    }
  }

  private def columnName(family: String, qualifier: String): String =
    s"$family:$qualifier"
}

object HBaseJsonBodyBuilder extends HBaseJsonBodyBuilder