package com.example.agudajce;

public class Gallery_row {

    private String firstImage;
    private String secondImage;

    public Gallery_row(String firstImage, String secondImage) {
        this.firstImage = firstImage;
        this.secondImage = secondImage;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public String getSecondImage() {
        return secondImage;
    }

    public void setSecondImage(String secondImage) {
        this.secondImage = secondImage;
    }
}
