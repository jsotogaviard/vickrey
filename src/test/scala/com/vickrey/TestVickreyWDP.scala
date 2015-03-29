package com.vickrey

import org.junit.Assert
import org.junit.Test

class TestVickrey {
	
  @Test
	def testNominal = {
    //A: 2 bids of 110 and 130 euros
    //B: 0 bid
    //C: 1 bid of 125 euros
    //D: 3 bids of 105, 115 and 90 euros
    //E: 3 bids of 132, 135 and 140 euros
		val bids = List(
        VickreyBid("A", 110),
        VickreyBid("A", 130),
        VickreyBid("C", 125),
        VickreyBid("D", 105),
        VickreyBid("D", 115),
        VickreyBid("D", 90),
        VickreyBid("E", 132),
        VickreyBid("E", 135),
        VickreyBid("E", 140)
    )	
    
    val winner = VickreyWDP.WDP(bids, 100)
    println(winner)
    Assert.assertEquals(winner._1, "E")
    Assert.assertEquals(winner._2, 130)
	}
}