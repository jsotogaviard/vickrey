package com.vickrey

trait TVickreyBid{
  def bidder: String
  def price: Int 
  override def toString = "bidder " + bidder + " price " + price 
}

case class VickreyBid(val bidder: String, val price: Int) extends TVickreyBid{}

object VickreyWDP {

  /**
   * Winner determination problem
   */
	def WDP(bids : List[VickreyBid], referencePrice : Double) = {

		val sortedAbovePrice = bids
				// Filter all the bids
				// Keep only the above references prices
				.filter(_.price >= referencePrice)

				// Sort them by price
				.sortBy(_.price)

		// The winning bidder
		val winningPerson = sortedAbovePrice.last.bidder
    
    // Remove all the bids related
    // the bidding winner
		val sortedBidsExceptWinner = sortedAbovePrice
      .filter(!_.bidder.equals(winningPerson))

		val winningPrice = sortedBidsExceptWinner.last.price

    // Return the winning person and the winning price
		(winningPerson, winningPrice)
	}

}

