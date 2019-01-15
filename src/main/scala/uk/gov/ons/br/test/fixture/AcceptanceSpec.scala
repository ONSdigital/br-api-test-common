package uk.gov.ons.br.test.fixture

import org.scalatest.{GivenWhenThen, Matchers, OptionValues, fixture}

trait AcceptanceSpec extends fixture.FeatureSpec with GivenWhenThen with Matchers with OptionValues
