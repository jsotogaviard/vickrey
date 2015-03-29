package com.vickrey.generalized

import org.junit.Test
import org.junit.Assert

class TestGeneralizedVickreyWDP {
	
  @Test
	def testNominal = {
    //Bidder A wants one apple and bids $5 for that apple.
    //Bidder B wants one apple and is willing to pay $2 for it.
    //Bidder C wants two apples and is willing to pay $6 to have both of them but is uninterested in buying only one without the other.
		val bids = collection.mutable.ArrayBuffer(
        GeneralizedVickreyBid(1, "A", 5),
        GeneralizedVickreyBid(1, "B", 2),
        GeneralizedVickreyBid(2, "C", 6)
    )	
    
    val winners = GeneralizedVickreyWDP.WDP(bids, 2, 2)
    for(winner <- winners)
      println(winner)
    Assert.assertEquals(winners(0).bidder, "A")
    Assert.assertEquals(winners(0).price, 4)
    Assert.assertEquals(winners(0).numWantedElements, 1)
    Assert.assertEquals(winners(1).bidder, "B")
    Assert.assertEquals(winners(1).price, 1)
    Assert.assertEquals(winners(1).numWantedElements, 1)
	}
  
  
  @Test
  def testGrayCode = {
    var result = GeneralizedVickreyWDP.gray(4);
    for(r <- result){
      println(r);
    }
  }
  
}