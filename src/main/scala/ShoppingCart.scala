package uk.softar

import ShoppingCart.PRICES

case class ShoppingCart(cart: Map[String, Int]) {
  def totalCost: BigDecimal =
    cart
      .map { case (product, amount) => PRICES(product) * amount }
      .sum
      .setScale(2)
}

object ShoppingCart {
  val PRICES: Map[String, BigDecimal] = Map(
    "apple" -> .60,
    "orange" -> .25
  )

  private val EMPTY_CART = PRICES.view.mapValues(_ => 0).toMap

  def parse(input: String): ShoppingCart = apply(
    input
      .toLowerCase
      .split("\\s+")
      .foldLeft(EMPTY_CART){
        (acc, product) =>
          acc.updatedWith(product){
            case Some(amount) => Some(amount + 1)
            case None => Some(1)
          }
      }
  )
}
