package com.example.wadim.osmdroid_test.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


public class MapFragment extends Fragment  {
    private SharedPreferences mPrefs;
    private MapView map;
    private MyLocationNewOverlay mLocationOverlay;
    private MinimapOverlay mMinimapOverlay;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Note! we are programmatically construction the map view
        //be sure to handle application lifecycle correct (see note in on pause)
        map = new MapView(inflater.getContext());
        // Call this method to turn off hardware acceleration at the View level but only if you run into problems ( please report them too!)
        // setHardwareAccelerationOff();
        //update, no longer needed, the mapView is hardware acceleration off by defaul tnow

        return map;
    }

    public  void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Context context = this.getActivity();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        map.setTileSource(TileSourceFactory.OpenTopo);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(13);
        GeoPoint startPoint = new GeoPoint(49.1875, 20.0625);
        mapController.setCenter(startPoint);

        //@TODO Grid -> to settings
        //LatLonGridlineOverlay2 overlay = new LatLonGridlineOverlay2();
        //map.getOverlays().add(overlay);

        mMinimapOverlay = new MinimapOverlay(context, map.getTileRequestCompleteHandler());
        mMinimapOverlay.setWidth(dm.widthPixels / 5);
        mMinimapOverlay.setHeight(dm.heightPixels / 5);
        mMinimapOverlay.setPadding(50);
        mMinimapOverlay.setZoomDifference(4);
        //optionally, you can set the minimap to a different tile source
        //mMinimapOverlay.setTileSource(....);
        map.getOverlays().add(this.mMinimapOverlay);
    }
}
