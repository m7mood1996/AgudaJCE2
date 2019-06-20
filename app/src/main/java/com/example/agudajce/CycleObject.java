package com.example.agudajce;

public class CycleObject {

    private String des;

    private String imageURL;

    private String skill;

    public CycleObject(String des, String imageURL ,String skill) {
        this.des = des;
        this.imageURL = imageURL;
        this.skill = skill;
    }

    public CycleObject() {
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public String toString() {
        return "CycleObject{" +
                "des='" + des + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", skill='" + skill + '\'' +
                '}';
    }
}

