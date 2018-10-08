package com.traderev.auction.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.traderev.auction.model.Bid;

@Service
public class BidService implements IBidService
{
    /**
     * Maintain a cache of Cars & List of Bids.
     */
    private Cache<Long, List<Bid>> cachedBids =
            Caffeine.newBuilder().maximumSize(10000).expireAfterWrite(90000000, TimeUnit.DAYS).build();

    @Override
    public Bid recordBid(Bid bidMade)
    {
        Long carId = bidMade.getCar() != null ? bidMade.getCar().getId() : 0L;

        List<Bid> bidList = getAllBidsFromCacheForCarId(carId);

        bidList.add(bidMade);

        cachedBids.put(carId, bidList);

        return bidMade;
    }

    @Override
    public Bid getCurrentWinningBid(Long carId)
    {
        List<Bid> bidList = getAllBidsFromCacheForCarId(carId);

        // Default in case no bid has been placed yet / Car doesn't exist
        Bid maxBid = new Bid(-1.0);

        /**
         * In case there is a tie, the winning bid would be the one which was placed first.
         */
        for (Bid bid : bidList)
        {
            if (bid.getBidPrice() > maxBid.getBidPrice())
            {
                maxBid.setBidPrice(bid.getBidPrice());
                maxBid.setCar(bid.getCar());
                maxBid.setUser(bid.getUser());
            }
        }

        return maxBid;

    }

    @Override
    public List<Bid> getListOfBids(Long carId)
    {
        return getAllBidsFromCacheForCarId(carId);
    }

    /**
     * Helper method to fetch from Cache.
     * @param carId
     * @return
     */
    private List<Bid> getAllBidsFromCacheForCarId(Long carId)
    {
        if (CollectionUtils.isEmpty(cachedBids.getIfPresent(carId)))
        {
            return new ArrayList<>();
        }
        return cachedBids.getIfPresent(carId);
    }
}
