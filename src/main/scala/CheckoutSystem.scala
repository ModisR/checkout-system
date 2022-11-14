package uk.softar

import scala.io.StdIn.readLine

object CheckoutSystem extends App {
  private val prices = Map[String, BigDecimal](
    "apple" ->  .60,
    "orange" -> .25
  )

  println("Please enter a space-separated list of products.")

  val totalCost = readLine
    .toLowerCase
    .split("\\s+")
    .map(prices)
    .sum
    .setScale(2)

  println(s"Total cost of products is £$totalCost.")
}
