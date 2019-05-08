package com.example.petplant.addplant;

public class PlantInfo {
    private String path;
    private String name;
    private String probability;
    private String confidence;
    private String family;
    private String genus;
    private String species;

    PlantInfo (String path, String name, String probability, String confidence){
        this.name = name;
        this.path = path;
        this.probability = probability;
        this.confidence = confidence;
    }

    PlantInfo (){

    }

    public String getPath (){ return path; }
    public String getName (){ return name; }
    public String getProbability (){
        return probability;
    }
    public String getConfidence () { return confidence; }
    public String getFamily () { return family; }
    public String getGenus () { return genus; }
    public String getSpecies () { return species; }

    public void setPath (String path){
        this.path = path;
    }

    public void setName (String name){
        this.name = name;
    }

    public void setProbability (String probability){
        this.probability = probability;
    }

    public void setConfidence (String confidence){
        this.confidence = confidence;
    }

    public void setFamily (String family){
        this.family = family;
    }
    public void setGenus (String genus){
        this.genus = genus;
    }
    public void setSpecies (String species){
        this.species = species;
    }
}
