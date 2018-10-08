package com.traderev.auction.service;

import java.util.List;

import com.traderev.auction.model.Bid;

public interface IBidService
{
    Bid recordBid(Bid bidMade);

    Bid getCurrentWinningBid(Long carId);

    List<Bid> getListOfBids(Long carId);
}
