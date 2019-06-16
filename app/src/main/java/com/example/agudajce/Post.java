package com.example.agudajce;



public class Post {

    private String description;
    private String image;

    private String video; // adding video


    public Post(String description, String image,String video) {

        this.description = description;
        this.image = image;
        this.video=video;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
