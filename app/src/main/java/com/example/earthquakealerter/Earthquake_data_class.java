package com.example.earthquakealerter;

public class Earthquake_data_class {

    private String magnitude;
    private String place;
    private String date;


    public Earthquake_data_class(Double Magnitude, String Place, String Date){
        magnitude = Double.toString(Magnitude);
        place = Place;
        date = Date;
    }

    public String GetMagnitude(){
        String magnitude = this.magnitude;
        return magnitude;
    }

    public String GetPlace(){
        String place = this.place;
        return place;
    }

    public String GetDate(){
        String date = this.date;
        return date;
    }

}
