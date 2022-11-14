package uk.softar

import ShoppingCart.prices

case class ShoppingCart(cart: Map[String, Int]) {
  def totalCost: BigDecimal =
    cart
      .map { case (product, amount) => prices(product) * amount }
      .sum
      .setScale(2)
}

object ShoppingCart {
  val prices: Map[String, BigDecimal] = Map(
    "apple" -> .60,
    "orange" -> .25
  )

  val empty: Map[String, Int] = prices.view.mapValues(_ => 0).toMap

  def parse(input: String): ShoppingCart = apply(
    input
      .toLowerCase
      .split("\\s+")
      .foldLeft(empty){
        (acc, product) =>
          acc.updatedWith(product){
            case Some(amount) => Some(amount + 1)
            case None => Some(1)
          }
      }
  )
}
