package com.example.petplant.experts;

public class OneExpert {
    public Integer picture;
    public String name;
    public String title;
    public String specialties;
    public String image_filename;

    OneExpert(Integer picture, String name, String title, String specialties, String image_filename) {
        this.name = name;
        this.picture = picture;
        this.title = title;
        this.specialties = specialties;
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

    public String getSpecialties() {
        return specialties;
    }

    public String getImage_filename() { return image_filename; }
}
