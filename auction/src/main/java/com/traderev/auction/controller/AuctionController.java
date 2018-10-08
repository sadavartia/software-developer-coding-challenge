package com.traderev.auction.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traderev.auction.model.Bid;
import com.traderev.auction.service.IBidService;

@RestController
@Api(description="Operations which can be performed on Simple Car Auction System")
public class AuctionController
{
    @Autowired
    private IBidService bidService;

    @ApiOperation(value = "Place a Bid on a Car", response = Bid.class)
    @RequestMapping(value = "/bid", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Bid> bid(@RequestBody Bid bid)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(bidService.recordBid(bid));
    }

    @ApiOperation(value = "Get the Winning Bid for a Car by CarId", response = Bid.class)
    @RequestMapping(value = "/winningBid/car/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Bid> getCurrentWinningBid(@PathVariable("id") Long carId)
    {
        return ResponseEntity.ok(bidService.getCurrentWinningBid(carId));
    }

    @ApiOperation(value = "Get All the Bids for a Car by CarId", response = Iterable.class)
    @RequestMapping(value = "/allBids/car/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Iterable<Bid>> getListOfBids(@PathVariable("id") Long carId)
    {
        return ResponseEntity.ok(bidService.getListOfBids(carId));
    }
}
