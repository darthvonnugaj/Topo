package com.example.wadim.osmdroid_test.helper;

/**
 * Created by Agata on 24.05.2018.
 */

public class Note {
    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ROUTEID = "routeId";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_GRADE = "grade";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LON = "lon";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private int routeId;
    private String name;
    private int type;
    private String grade;
    private String img;
    private double lat;
    private double lon;
    private String timestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ROUTEID + " INTEGER,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_TYPE + " INTEGER,"
                    + COLUMN_GRADE + " TEXT,"
                    + COLUMN_IMG + " TEXT,"
                    + COLUMN_LAT + " DOUBLE,"
                    + COLUMN_LON + " DOUBLE,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Note() {
    }

    public Note(int id, int routeId, String name, int type, String grade, String img, double lat, double lon, String timestamp) {
        this.id = id;
        this.routeId = routeId;
        this.name = name;
        this.type = type;
        this.grade = grade;
        this.img = img;
        this.lat = lat;
        this.lon = lon;
        this.timestamp = timestamp;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLat() {
        return lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLon() {
        return lon;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}