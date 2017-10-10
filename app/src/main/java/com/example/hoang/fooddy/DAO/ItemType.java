package com.example.hoang.fooddy.DAO;

/**
 * Created by VT-99 on 10/10/2017.
 */

public class ItemType {

    String name;
    int imageLarge;
    int imageSmall;

    public ItemType(String name, int imageLarge, int imageSmall) {
        this.name = name;
        this.imageLarge = imageLarge;
        this.imageSmall = imageSmall;
    }

    public ItemType() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(int imageLarge) {
        this.imageLarge = imageLarge;
    }

    public int getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(int imageSmall) {
        this.imageSmall = imageSmall;
    }
}
