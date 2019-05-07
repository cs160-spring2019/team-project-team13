package com.example.petplant.addplant;

public class PlantInfo {
    private String path;
    private String name;
    private String probability;
    private String confidence;

    PlantInfo (String path, String name, String probability, String confidence){
        this.name = name;
        this.path = path;
        this.probability = probability;
        this.confidence = confidence;
    }

    public String getPath (){ return path; }
    public String getName (){ return name; }
    public String getProbability (){
        return probability;
    }
    public String getConfidence () { return confidence; }
}
