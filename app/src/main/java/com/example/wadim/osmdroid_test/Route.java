package com.example.wadim.osmdroid_test;


public class Route {
    int id;
    String name;
    int type;
    String grade;
    String img;
    double lat;
    double lon;

    private static String URL0 = "http://ec2-18-197-4-23.eu-central-1.compute.amazonaws.com/api/routes/";
    private static String URL = "http://ec2-18-197-4-23.eu-central-1.compute.amazonaws.com/api/routes/";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }


    public String getImage() {
        return img;
    }

    public void setImage(String img) {
        this.img = img;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }



}
