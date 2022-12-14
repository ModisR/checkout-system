package uk.softar

import ShoppingCart.{Offer, ProductName, prices}

import scala.math.Integral.Implicits.infixIntegralOps

case class ShoppingCart(cart: Map[String, Int]) {
  def totalCost: BigDecimal =
    cart
      .map { case (product, amount) => prices(product) * amount }
      .sum
      .setScale(2)

  def withApplied(offers: Map[ProductName, Offer]): ShoppingCart =
    ShoppingCart(
      cart map {
        case (product, originalAmount) =>
          val adjustedAmount =
            offers get product match {
              case Some((quantity, forThePriceOf)) =>
                val (quot, rem) = originalAmount /% quantity
                quot * forThePriceOf + rem
              case None => originalAmount
            }
          product -> adjustedAmount
      }
    )

  def withAppleBananaOffer: ShoppingCart = ShoppingCart {
    val cheaperOf2 = List("apple", "banana") minBy { product =>
      cart.getOrElse(product, 0) * ShoppingCart.prices(product)
    }

    cart - cheaperOf2
  }
}

object ShoppingCart {
  type ProductName = String
  type Offer = (Int, Int) // 1st quantity for the price of the 2nd quantity

  val prices: Map[ProductName, BigDecimal] = Map(
    "apple" -> .60,
    "orange" -> .25,
    "banana" -> .20
  )

  def parse(input: String): ShoppingCart = apply(
    input
      .toLowerCase
      .split("\\s+")
      .filter(_.nonEmpty)
      .foldLeft(Map.empty[ProductName, Int]) { (acc, product) =>
        acc.updatedWith(product) {
          case Some(amount) => Some(amount + 1)
          case None => Some(1)
        }
      }
  )
}
