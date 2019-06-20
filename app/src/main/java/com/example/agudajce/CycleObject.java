package com.example.agudajce;

public class CycleObject {

    private String des;

    private String imageURL;

    public CycleObject(String des, String imageURL) {
        this.des = des;
        this.imageURL = imageURL;
    }

    public String getImageName() {
        return des;
    }

    public void setImageName(String des) {
        this.des = des;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
