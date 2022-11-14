package uk.softar

import scala.io.StdIn.readLine

object CheckoutSystem extends App {

  println("Please enter a space-separated list of products.")

  val totalCost = ShoppingCart
    .parse(readLine())
    .totalCost

  println(s"Total cost of products is £$totalCost.")
}
