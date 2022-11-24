package uk.softar

import scala.io.StdIn.readLine

object CheckoutSystem extends App {

  private val offers = Map(
    "apple" -> (2, 1), // 2 for 1 - buy 1 get 1 free
    "orange" -> (3, 2), // 3 for the price of 2
    "banana" -> (2, 1)
  )

  println("Please enter a space-separated list of products.")

  private val totalCost = ShoppingCart
    .parse(readLine())
    .withApplied(offers)
    .totalCost

  println(s"Total cost of products is £$totalCost.")
}
