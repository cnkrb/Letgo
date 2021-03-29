package com.cenkkaraboa.myapplication.models;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Product  implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sell")
    @Expose
    private Integer sell;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("statement")
    @Expose
    private String statement;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("image_two")
    @Expose
    private String imageTwo;
    @SerializedName("image_three")
    @Expose
    private String imageThree;
    @SerializedName("image_four")
    @Expose
    private String imageFour;
    @SerializedName("image_five")
    @Expose
    private String imageFive;
    @SerializedName("image_six")
    @Expose
    private String imageSix;
    @SerializedName("image_seven")
    @Expose
    private String imageSeven;
    @SerializedName("image_eight")
    @Expose
    private Object imageEight;
    @SerializedName("image_nine")
    @Expose
    private Object imageNine;
    @SerializedName("image_ten")
    @Expose
    private Object imageTen;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSell() {
        return sell;
    }

    public void setSell(Integer sell) {
        this.sell = sell;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageTwo() {
        return imageTwo;
    }

    public void setImageTwo(String imageTwo) {
        this.imageTwo = imageTwo;
    }

    public String getImageThree() {
        return imageThree;
    }

    public void setImageThree(String imageThree) {
        this.imageThree = imageThree;
    }

    public String getImageFour() {
        return imageFour;
    }

    public void setImageFour(String imageFour) {
        this.imageFour = imageFour;
    }

    public String getImageFive() {
        return imageFive;
    }

    public void setImageFive(String imageFive) {
        this.imageFive = imageFive;
    }

    public String getImageSix() {
        return imageSix;
    }

    public void setImageSix(String imageSix) {
        this.imageSix = imageSix;
    }

    public String getImageSeven() {
        return imageSeven;
    }

    public void setImageSeven(String imageSeven) {
        this.imageSeven = imageSeven;
    }

    public Object getImageEight() {
        return imageEight;
    }

    public void setImageEight(Object imageEight) {
        this.imageEight = imageEight;
    }

    public Object getImageNine() {
        return imageNine;
    }

    public void setImageNine(Object imageNine) {
        this.imageNine = imageNine;
    }

    public Object getImageTen() {
        return imageTen;
    }

    public void setImageTen(Object imageTen) {
        this.imageTen = imageTen;
    }

}
