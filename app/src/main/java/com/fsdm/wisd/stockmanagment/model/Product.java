package com.fsdm.wisd.stockmanagment.model;
/*
 **    *** Stock Managment ***
 **   Created by EL KHARROUBI HASSAN
 **   At Saturday January 2021 16H 01MIN
 */


public class Product {
    private byte[] mProductImage;
    private String mTitle;
    private String mDescription;
    private int mInStock;
    private int mProductId;
    private double mPrice;


    public Product(){

    }

    public Product(byte[] productImage, String title, String description, int inStock, int productId, double price) {
        mProductImage = productImage;
        mTitle = title;
        mDescription = description;
        mInStock = inStock;
        mProductId = productId;
        mPrice = price;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public byte[] getProductImage() {
        return mProductImage;
    }

    public void setProductImage(byte[] productImage) {
        mProductImage = productImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getInStock() {
        return mInStock;
    }

    public void setInStock(int inStock) {
        mInStock = inStock;
    }

    public int getProductId() {
        return mProductId;
    }

    public void setProductId(int productId) {
        mProductId = productId;
    }
}
