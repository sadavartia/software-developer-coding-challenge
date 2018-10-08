package com.traderev.auction.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Bid
{
    private User user;

    private Car car;

    private Double bidPrice;

    public Bid()
    {
    }

    public Bid(User user, Car car, Double bidPrice)
    {
        this.user = user;
        this.car = car;
        this.bidPrice = bidPrice;
    }

    public Bid(Double bidPrice)
    {
        this.bidPrice = bidPrice;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Car getCar()
    {
        return car;
    }

    public void setCar(Car car)
    {
        this.car = car;
    }

    public Double getBidPrice()
    {
        return bidPrice;
    }

    public void setBidPrice(Double bidPrice)
    {
        this.bidPrice = bidPrice;
    }
}
