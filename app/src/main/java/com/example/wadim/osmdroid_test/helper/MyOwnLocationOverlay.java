package com.example.wadim.osmdroid_test.helper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

/**
 * Created by wadim on 22.06.2018.
 */

public class MyOwnLocationOverlay extends MyLocationNewOverlay {
    private MapView mapView;
    private Paint circlePainter;
    private Point screenCurrentPoint;
    private GeoPoint geoCurrentPoint;
    private int meters;

    public MyOwnLocationOverlay(IMyLocationProvider myLocationProvider, MapView mapView) {
        super(myLocationProvider, mapView);
        this.mapView = mapView;
    }

    public void setMeters(int meters) {
        this.meters = meters;
    }

    public void setKmeters(int kmeters){
        this.meters = 1000*kmeters;
    }

    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow) {
        // Set the painter to paint our circle. setColor = blue, setAlpha = 70 so the background
        // can still be seen. Feel free to change these settings
        circlePainter = new Paint();
        circlePainter.setAntiAlias(true);
        circlePainter.setStrokeWidth(8.0f);
        circlePainter.setColor(0xff6666ff);
        circlePainter.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePainter.setAlpha(50);

        // Get projection from the mapView.
        Projection projection = mapView.getProjection();
        // Get current location
        geoCurrentPoint = getMyLocation();
        screenCurrentPoint = new Point();
        // Project the gps coordinate to screen coordinate
        projection.toPixels(geoCurrentPoint, screenCurrentPoint);

        int radius = metersToRadius(geoCurrentPoint.getLatitudeE6() /1000000);
        // draw the blue circle
        canvas.drawCircle(screenCurrentPoint.x, screenCurrentPoint.y, radius, circlePainter);
        super.draw(canvas,mapView,shadow);
    }

    // hack to get more accurate radius, because the accuracy is changing as the location
    // getting further away from the equator
    public int metersToRadius(double latitude) {
        return (int) (mapView.getProjection().metersToEquatorPixels(meters) * (1/ Math.cos(Math.toRadians(latitude))));
    }
}
