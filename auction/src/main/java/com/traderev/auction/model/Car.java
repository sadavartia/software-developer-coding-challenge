package com.traderev.auction.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Car
{
    private long id;

    private int sellerId;

    private String model;

    private String yearManufactured;

    private String color;

    public Car()
    {
    }

    public Car(long id, int sellerId, String model, String yearManufactured, String color)
    {
        this.id = id;
        this.sellerId = sellerId;
        this.model = model;
        this.yearManufactured = yearManufactured;
        this.color = color;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public int getSellerId()
    {
        return sellerId;
    }

    public void setSellerId(int sellerId)
    {
        this.sellerId = sellerId;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getYearManufactured()
    {
        return yearManufactured;
    }

    public void setYearManufactured(String yearManufactured)
    {
        this.yearManufactured = yearManufactured;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }
}
