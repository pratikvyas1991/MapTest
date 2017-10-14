package com.tasol.indolytics.maptest;

/**
 * Created by tasol on 14/10/17.
 */

public class MapsPojo {
    String id;
    String longitude;
    String name;
    String icon;
    String latitude;

    public MapsPojo(String id, String longitude, String name, String icon, String latitude) {
        this.id = id;
        this.longitude = longitude;
        this.name = name;
        this.icon = icon;
        this.latitude = latitude;
    }

    public MapsPojo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
