
package com.vickrey.generalized

import scala.collection.mutable.ArrayBuffer

import com.vickrey.TVickreyBid

case class GeneralizedVickreyBid(val numWantedElements: Int, override val bidder: String, override val price: Int) extends TVickreyBid() with Equals {
  override def toString =  super.toString + " numElements " + numWantedElements
}

object GeneralizedVickreyWDP {

  /**
   * The generalized vickrey bids
   * The number of element in the auction
   * The reference price
   * 
   * The resulting bids for each bider and the price
   */
	def WDP(bids : ArrayBuffer[GeneralizedVickreyBid], numElements : Int, referencePrice : Double): ArrayBuffer[GeneralizedVickreyBid] = {

    // 1.
		// Keep only the bids
		// that are above the price market
		val abovePriceBids = bids.filter(bid => bid.numWantedElements * referencePrice <= bid.price)

    // 2.
    // Search for the correct solution
    val solution = WDP(bids, numElements)
    
    // 3.
    // Calculate the price to pay
    val realSolution = ArrayBuffer[GeneralizedVickreyBid]()
    for( bid <- solution ){
      
        // Calculate the price 
        // of the winner without the current
        // bid solution
        val clonedBids = bids.clone;
        clonedBids.remove(clonedBids.indexOf(bid))
        val solWithoutCurrentBidder = WDP(clonedBids, numElements)
        val solWithoutCurrentBidderPrice = totalPrice(solWithoutCurrentBidder)
       
        // Calculate the price
        // that are paying the others 
        // in the current solution
        val clonedSolution = solution.clone
        clonedSolution.remove(clonedSolution.indexOf(bid))
        val priceToPay = totalPrice(clonedSolution)
        
        // The current price
        // paid is the price for the current solution
        // minus the price to pay
        val price = solWithoutCurrentBidderPrice - priceToPay
        realSolution += GeneralizedVickreyBid(bid.numWantedElements, bid.bidder, price)
    }
    
    realSolution
	}

	def WDP(bids : ArrayBuffer[GeneralizedVickreyBid], numElements : Int) :  ArrayBuffer[GeneralizedVickreyBid]= {
    
    // All possible solutions
    val numBids = bids.length
    val possibilities = gray(numBids)
    
    // Loop over all the solutions
    var maxPrice = -1
    var maxPossibility : ArrayBuffer[Boolean] = null
    for(possibility <- possibilities){
      var currentPrice = 0;
      var currentNumElements = 0;
      var idx = 0
      for(present <- possibility){
        if (present){
          val bid = bids(idx)
        	currentPrice += bid.price
          currentNumElements += bid.numWantedElements
        }
        idx += 1
      }
      
      if (currentNumElements <= numElements && currentPrice >= maxPrice) {
        maxPrice = currentPrice
        maxPossibility = possibility
      }
    }
    
    // Filter to render
    // only the bidders paying
    var idx = 0
    val winningPossibility  = ArrayBuffer[GeneralizedVickreyBid]()
    for(possibility <- maxPossibility){
       if(possibility)
         winningPossibility += bids(idx)
       idx += 1
     }
    
    return winningPossibility
  }
  
  def totalPrice(bids : ArrayBuffer[GeneralizedVickreyBid]) : Int = {
    var totalPrice = 0
    bids.foreach { bid => totalPrice += bid.price }
    return totalPrice
  }
  
  def gray(i: Int) : ArrayBuffer[ArrayBuffer[Boolean]] = {
    if(i == 1){
      return ArrayBuffer(ArrayBuffer(true), ArrayBuffer(false))
    } else {
      var tmpGray = gray(i - 1)
      var result = ArrayBuffer[ArrayBuffer[Boolean]]()
      for(tmp <- tmpGray){
        var currentTrue = ArrayBuffer(true)
        currentTrue ++= tmp
        result += currentTrue
        var currentFalse = ArrayBuffer(false)
        currentFalse ++= tmp
        result += currentFalse
      }
      return result
    }

  }
}