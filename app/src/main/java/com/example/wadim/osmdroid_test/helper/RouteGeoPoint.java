package com.example.wadim.osmdroid_test.helper;

import com.example.wadim.osmdroid_test.Route;

import org.osmdroid.views.overlay.simplefastpoint.StyledLabelledGeoPoint;

/**
 * Created by wadim on 21.06.2018.
 */

public class RouteGeoPoint extends StyledLabelledGeoPoint {
    private Route _route;

    public RouteGeoPoint(Route route) {
        super(route.getLat(), route.getLon(), route.getName());
        this._route = route;
    }

    public int getId(){
        return this._route.getId();
    }
}
