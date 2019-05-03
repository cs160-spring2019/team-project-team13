package com.example.petplant.addplant;

public class PlantProfileCard {
    public Integer picture;
    public String name;
    public String title;
    public String image_filename;

    PlantProfileCard(Integer picture, String name, String title, String image_filename) {
        this.name = name;
        this.picture = picture;
        this.title = title;
        this.image_filename = image_filename;
    }

    public String getName() {
        return name;
    }

    public Integer getPicture() {
        return picture;
    }

    public String getTitle() {
        return title;
    }


    public String getImage_filename() { return image_filename; }
    PlantProfileCard() {

    }
    }
