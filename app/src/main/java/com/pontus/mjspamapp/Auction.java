package com.pontus.mjspamapp;

import android.util.Base64;

import java.util.Date;

/**
 * Created by Pontus on 2016-03-21.
 */

public class Auction {

    private int id;
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private int categoryID;
    private int supplierID;
    private double acceptPrice;
    private boolean sold;

    public Auction(int id, String name, String description, String startTime, String endTime, int categoryID, int supplierID, double acceptPrice, boolean sold) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.categoryID = categoryID;
        this.supplierID = supplierID;
        this.acceptPrice = acceptPrice;
        this.sold = sold;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public double getAcceptPrice() {
        return acceptPrice;
    }

    public boolean isSold() {
        return sold;
    }

    @Override
    public String toString() {
        return name;
    }
}

/*
`Id` INT NOT NULL AUTO_INCREMENT,
    `Name` VARCHAR(45) NULL,
    `Description` TEXT(256) NULL,
    `StartTime` DATETIME NULL,
    `EndTime` DATETIME NULL,
    `Image` BLOB NULL,
    `CategoryId` INT NOT NULL,
    `SupplierId` INT NOT NULL,
    `AcceptPrice` DECIMAL(10,2) NOT NULL,
    `Sold` BOOL,
 */