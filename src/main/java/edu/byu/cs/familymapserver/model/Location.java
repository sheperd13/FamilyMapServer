package edu.byu.cs.familymapserver.model;

/**
 * Created by christian on 11/5/17.
 * Contains the information needed for a location
 */

public class Location {
    String country;
    String city;
    float latitude;
    float longitude;

    public Location(){
        country = null;
        city = null;
        latitude = (float) 0.0;
        longitude = (float) 0.0;
    }

    public Location(String country, String city, float latitude, float longitude){
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

}
