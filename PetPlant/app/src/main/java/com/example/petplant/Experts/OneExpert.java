package com.example.petplant.Experts;

public class OneExpert {
    public Integer picture;
    public String name;
    public String title;
    public String specialties;

    OneExpert(Integer picture, String name, String title, String specialties) {
        this.name = name;
        this.picture = picture;
        this.title = title;
        this.specialties = specialties;
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
}
