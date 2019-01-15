package uk.gov.ons.br.test.json

import org.scalatest.{FreeSpec, Matchers}
import play.api.libs.json.{JsNumber, JsObject, JsString, Json}
import uk.gov.ons.br.test.json.JsonString._

class JsonStringSpec extends FreeSpec with Matchers {

  "A JSON representation can be built" - {
    "of an object with a mandatory string field" in {
      val expectedJson = JsObject(Seq("name" -> JsString("value")))

      Json.parse(JsonString.ofObject(withString(named = "name", withValue = "value"))) shouldBe expectedJson
    }

    "of an object with an optional string field" - {
      "when the optional field is defined" in {
        val expectedJson = JsObject(Seq("name" -> JsString("value")))

        Json.parse(JsonString.ofObject(withOptionalString(named = "name", withValue = Some("value")))) shouldBe expectedJson
      }

      "when the optional field is not defined" in {
        Json.parse(JsonString.ofObject(withOptionalString(named = "name", withValue = None))) shouldBe JsObject.empty
      }
    }

    "of an object with a mandatory integer field" in {
      val expectedJson = JsObject(Seq("name" -> JsNumber(42)))

      Json.parse(JsonString.ofObject(withInt(named = "name", withValue = 42))) shouldBe expectedJson
    }

    "of an object with an optional integer field" - {
      "when the optional field is defined" in {
        val expectedJson = JsObject(Seq("name" -> JsNumber(42)))

        Json.parse(JsonString.ofObject(withOptionalInt(named = "name", withValue = Some(42)))) shouldBe expectedJson
      }

      "when the optional field is not defined" in {
        Json.parse(JsonString.ofObject(withOptionalInt(named = "name", withValue = None))) shouldBe JsObject.empty
      }
    }

    "of an object with a mandatory sub-object" - {
      "when the sub-object has fields" in {
        val expectedJson = JsObject(Seq("name" -> JsObject(Seq("greeting" -> JsString("hello")))))

        Json.parse(JsonString.ofObject(withObject(named = "name", values =
          withString(named = "greeting", withValue = "hello"),
          withOptionalString(named = "parting", withValue = None)))) shouldBe expectedJson
      }

      "when the sub-object has no fields" in {
        val expectedJson = JsObject(Seq("name" -> JsObject.empty))

        Json.parse(JsonString.ofObject(withObject(named = "name", values = None))) shouldBe expectedJson
      }
    }

    "of an object with an optional sub-object" - {
      "when the sub-object has fields" in {
        val expectedJson = JsObject(Seq("name" -> JsObject(Seq("greeting" -> JsString("hello")))))

        Json.parse(JsonString.ofObject(withOptionalObject(named = "name", values =
          withString(named = "greeting", withValue = "hello"),
          withOptionalString(named = "parting", withValue = None)))) shouldBe expectedJson
      }

      "when the sub-object has no fields" in {
        Json.parse(JsonString.ofObject(withOptionalObject(named = "name", values = None))) shouldBe JsObject.empty
      }
    }
  }
}
