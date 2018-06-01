package com.example.wadim.osmdroid_test.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.example.wadim.osmdroid_test.R;
import com.example.wadim.osmdroid_test.Route;
import com.example.wadim.osmdroid_test.app.MyApplication;
import com.example.wadim.osmdroid_test.helper.DatabaseHelper;
import com.example.wadim.osmdroid_test.helper.Note;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class DetailsFragment extends Fragment {

    private DatabaseHelper db;

    private int id;
    private long dbId;
    private TextView idTextView;
    public TextView name, grade, lon, lat;
    public ImageView thumbnail, type;

    private Route itemsList;
    private static final String TAG = DetailsFragment.class.getSimpleName();


    private static String URL0 = "http://ec2-18-197-4-23.eu-central-1.compute.amazonaws.com/api/routes/";
    private static String URL = "http://ec2-18-197-4-23.eu-central-1.compute.amazonaws.com/api/routes/";


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance( int id, long dbId) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putLong("dbId", dbId);
        fragment.setArguments(args);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            dbId = bundle.getLong("dbId");
            id = bundle.getInt("id");
            StringBuilder stringBuilder = new StringBuilder(URL0);
            stringBuilder.append(id);
            URL = stringBuilder.toString();
        }
    }

    private void fetchStoreItems() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //mTextView.setText("Response: " + response.toString());
                        Toast.makeText(getActivity(), "success", Toast.LENGTH_LONG).show();
                        try {
                            name.setText((String)response.get("name"));
                            grade.setText((String)response.get("grade"));
                            String stringDouble= Double.toString((Double)response.get("lat"));
                            lat.setText(stringDouble);
                            stringDouble= Double.toString((Double)response.get("lon"));
                            lon.setText(stringDouble);
                            Glide.with(getActivity())
                                    .load((String)response.get("img"))
                                    .into(thumbnail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        MyApplication instance = MyApplication.getInstance();
        instance.addToRequestQueue(jsonObjectRequest);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View MyFragmentView = inflater.inflate(R.layout.fragment_details, container, false);


        readBundle(getArguments());

        name = MyFragmentView.findViewById(R.id.title);
        grade = MyFragmentView.findViewById(R.id.grade);
        thumbnail = MyFragmentView.findViewById(R.id.thumbnail);
        lat = MyFragmentView.findViewById(R.id.lat);
        lon = MyFragmentView.findViewById(R.id.lon);

        if(dbId!=0)
        {
            db = new DatabaseHelper(getContext());
            Note route = db.getNote(dbId);
            name.setText((String)route.getName());
            grade.setText((String)route.getGrade());
            String stringDouble= Double.toString((Double)route.getLat());
            lat.setText(stringDouble);
            stringDouble= Double.toString((Double)route.getLon());
            lon.setText(stringDouble);
            Glide.with(getActivity())
                    .load((String)route.getImg())
                    .into(thumbnail);
        }
        else
        {
            fetchStoreItems();

        }



        //type = MyFragmentView.findViewById(R.id.type);

        return  MyFragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        readBundle(getArguments());
        fetchStoreItems();
    }

    @Override
    public void onStart() {
        super.onStart();
        readBundle(getArguments());
        fetchStoreItems();
    }


}
