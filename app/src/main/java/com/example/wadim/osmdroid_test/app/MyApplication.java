package com.example.wadim.osmdroid_test.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.osmdroid.util.GeoPoint;

public class MyApplication extends Application {

    public static final String TAG = MyApplication.class
            .getSimpleName();

    private RequestQueue mRequestQueue;

    private static MyApplication mInstance;

    public static final String FAV_SETTING = "FavouriteRoutes";

    private double lat;
    private double lon;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void addFavourite(int id){
        int[] favs = getFavourites();
        int cnt = favs.length+1;
        SharedPreferences.Editor edit = getInstance().getApplicationContext().getSharedPreferences("NAME", Context.MODE_PRIVATE).edit();
        edit.putInt(FAV_SETTING, cnt);
        int count = 0;
        for (int i: favs){
            edit.putInt("IntValue_" + FAV_SETTING + count++, favs[i]);
        }
        edit.putInt("IntValue_" + FAV_SETTING + count++, id);
        edit.commit();
    }

    public void storeFavourites(int[] array){
        SharedPreferences.Editor edit = getInstance().getApplicationContext().getSharedPreferences("NAME", Context.MODE_PRIVATE).edit();
        edit.putInt(FAV_SETTING, array.length);
        int count = 0;
        for (int i: array){
            edit.putInt("IntValue_" + FAV_SETTING + count++, i);
        }
        edit.commit();
    }

    public int[] getFavourites(){
        int[] ret;
        SharedPreferences prefs = getInstance().getApplicationContext().getSharedPreferences("NAME", Context.MODE_PRIVATE);
        int count = prefs.getInt(FAV_SETTING, 0);
        ret = new int[count];
        for (int i = 0; i < count; i++){
            ret[i] = prefs.getInt("IntValue_"+ FAV_SETTING + i, i);
        }
        return ret;
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

    public GeoPoint getGeoPoint() {
        return new GeoPoint(this.lat, this.lon);
    }
}
