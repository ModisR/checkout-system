package uk.softar

import org.scalatest.prop.{TableDrivenPropertyChecks, TableFor2}

object Tables extends TableDrivenPropertyChecks {
  val pricingScenarios: TableFor2[Map[String, Int], BigDecimal] = Table(
    "Input" -> "Expected Output",
    Map("apple" -> 3, "orange" -> 1) -> 2.05
  )
  val parsingScenarios: TableFor2[String, Map[String, Int]] = Table(
    "Input" -> "Expected Output",
    "Apple Apple Orange Apple" -> Map("apple" -> 3, "orange" -> 1)
  )
}
