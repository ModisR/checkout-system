package uk.softar

import ShoppingCart.{Offer, ProductName}
import ShoppingCartSpec.{offerScenarios, offers, parsingScenarios, pricingScenarios}

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor2}
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class ShoppingCartSpec extends AnyFlatSpec with ScalaCheckPropertyChecks {
  "Companion class" must "compute correct total cost" in {
    forAll(pricingScenarios) {
      (input, expected) =>
        val actual = ShoppingCart(input).totalCost
        assert(actual == expected)
    }
  }

  "Companion class" must "compute correct discounts" in {
    forAll(offerScenarios) {
      (input, expected) =>
        val actual = ShoppingCart(input).withApplied(offers).totalCost
        assert(actual == expected)
    }
  }

  "Companion object" must "correctly parse input" in {
    forAll(parsingScenarios) {
      (input, expected) =>
        val actual = ShoppingCart parse input
        assert(actual == ShoppingCart(expected))
    }
  }
}

object ShoppingCartSpec extends TableDrivenPropertyChecks {
  val offers: Map[ProductName, Offer] = Map(
    "apple" -> (2, 1), // 2 for 1 - buy 1 get 1 free
    "orange" -> (3, 2) // 3 for the price of 2
  )
  val offerScenarios: TableFor2[Map[ProductName, Int], BigDecimal] = Table(
    "Input" -> "Expected Output",
    Map("apple" -> 3, "orange" -> 1) -> 1.45,
    Map("apple" -> 2) -> .60,
    Map("orange" -> 3) -> .50,
    Map.empty -> .00
  )

  val pricingScenarios: TableFor2[Map[ProductName, Int], BigDecimal] = Table(
    "Input" -> "Expected Output",
    Map("apple" -> 3, "orange" -> 1) -> 2.05,
    Map("apple" -> 2) -> 1.20,
    Map("orange" -> 3) -> .75,
    Map.empty -> .00
  )

  val parsingScenarios: TableFor2[String, Map[ProductName, Int]] = Table(
    "Input" -> "Expected Output",
    "Apple Apple Orange Apple" -> Map("apple" -> 3, "orange" -> 1),
    " apple    Apple         " -> Map("apple" -> 2),
    "  Orange orange   Orange" -> Map("orange" -> 3),
    "                        " -> Map.empty
  )
}