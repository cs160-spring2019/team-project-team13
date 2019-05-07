package com.example.petplant.addplant;

public class PlantPostJson {
    private static final String key = "Tc2uLB6qixr1IfRQGbkCAmNJjvuHnvAl9agxGpDT1fbkOM74lc";
    private String identJson;
    private String suggeJson;

    PlantPostJson(){
        identJson = null;
        suggeJson = null;
    }

    public void setIdentJson (String encoded){
        identJson = "{" +
                "\"key\": \"" + key + "\"," +
                "\"images\": [\"" + encoded + "\"]}";
    }

    public void setSuggeJson (String id){
        suggeJson = "{" +
                "\"key\": \"" + key + "\"," +
                "\"ids\": [" + id + "]}";
    }

    public String getIdentJson (){
        return identJson;
    }

    public String getSuggeJson (){
        return suggeJson;
    }
}
