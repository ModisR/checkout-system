package uk.softar

import Tables.{parsingScenarios, pricingScenarios}

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class ShoppingCartSpec extends AnyFlatSpec with ScalaCheckPropertyChecks {
  "Companion class" must "compute correct total cost" in {
    forAll(pricingScenarios){
      (input, expected) =>
        val actual = ShoppingCart(input).totalCost
        assert(actual == expected)
    }
  }

  "Companion object" must "correctly parse input" in {
    forAll(parsingScenarios){
      (input, expected) =>
        val actual = ShoppingCart parse input
        assert(actual == ShoppingCart(expected))
    }
  }
}
