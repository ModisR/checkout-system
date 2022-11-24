package uk.softar

import ShoppingCart.ProductName
import ShoppingCartSpec.{appleBananaScenarios, parsingScenarios, pricingScenarios}

import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor2}
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class ShoppingCartSpec extends AnyPropSpec with ScalaCheckPropertyChecks {
  propertiesFor {
    forAll(pricingScenarios) {
      (input, expected) =>
        val actual = ShoppingCart(input).totalCost
        assert(actual == expected)
    }
  }

  propertiesFor {
    forAll { scenario: OfferScenario =>
      import scenario._
      val actual = originalCart withApplied offersList
      assert(actual == reducedCart)
    }
  }

  propertiesFor {
    forAll(parsingScenarios) {
      (input, expected) =>
        val actual = ShoppingCart parse input
        assert(actual == ShoppingCart(expected))
    }
  }

  propertiesFor {
    forAll(appleBananaScenarios) {
      (input, expected) =>
        println(s"$input -> $expected")
        val actual = ShoppingCart(input).withAppleBananaOffer
        assert(actual == ShoppingCart(expected))
    }
  }
}

object ShoppingCartSpec extends TableDrivenPropertyChecks {

  val pricingScenarios: TableFor2[Map[ProductName, Int], BigDecimal] = Table(
    "Input" -> "Expected Output",
    Map("apple" -> 3, "orange" -> 1) -> 2.05,
    Map("apple" -> 2) -> 1.20,
    Map("banana" -> 2) -> 0.40,
    Map("banana" -> 1) -> 0.20,
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

  val appleBananaScenarios: TableFor2[Map[ProductName, Int], Map[ProductName, Int]] = Table(
    "Input" -> "Expected Output",
    Map("apple" -> 1, "banana" -> 1) -> Map("apple" -> 1),
    Map("apple" -> 1, "banana" -> 4) -> Map("banana" -> 4),
    Map("apple" -> 2, "banana" -> 5) -> Map("apple" -> 2),
    Map("apple" -> 1) -> Map("apple" -> 1),
    Map("banana" -> 1) -> Map("banana" -> 1),
  )
}
