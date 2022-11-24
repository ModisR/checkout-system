package uk.softar

import ShoppingCart.{Offer, ProductName}

import org.scalacheck.{Arbitrary, Gen}

case class OfferScenario(
                          offersList: Map[ProductName, Offer],
                          originalCart: ShoppingCart,
                          reducedCart: ShoppingCart
                        )

object OfferScenario {
  def apply(
             offersList: Map[ProductName, Offer],
             reducedCart: ShoppingCart
           ): OfferScenario = {
    val invertedOffers = offersList.view.mapValues(_.swap).toMap
    val originalCart = reducedCart withApplied invertedOffers

    apply(offersList, originalCart, reducedCart)
  }

  private val maxAmount = math.sqrt(Int.MaxValue).toInt

  private val shoppingCartGen =
    ShoppingCart.prices.keys
      .foldLeft(Gen const Map.empty[ProductName, Int]) {
        (accGen, product) =>
          for {
            acc <- accGen
            amount <- Gen.choose(0, maxAmount)
          } yield
            if (amount < 1) acc
            else acc.updated(product, amount)
      }
      .map(ShoppingCart.apply)

  private val offerGen = for {
    quantity <- Gen.choose(2, maxAmount)
    forThePriceOf <- Gen.chooseNum(1, quantity - 1)
  } yield quantity -> forThePriceOf

  val offersGen: Gen[Map[ProductName, Offer]] =
    ShoppingCart.prices.keys
      .foldLeft(Gen const Map.empty[ProductName, Offer]) {
        (accGen, product) =>
          for {
            acc <- accGen
            offer <- offerGen
          } yield acc.updated(product, offer)
      }

  implicit val arbOfferScenario: Arbitrary[OfferScenario] =
    Arbitrary(
      for {
        reducedCart <- shoppingCartGen
        offers <- offersGen
      } yield apply(offers, reducedCart)
    )
}
