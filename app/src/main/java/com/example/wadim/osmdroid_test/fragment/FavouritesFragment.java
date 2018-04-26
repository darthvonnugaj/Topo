package com.example.wadim.osmdroid_test.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wadim.osmdroid_test.R;
import com.example.wadim.osmdroid_test.app.MyApplication;

public class FavouritesFragment extends Fragment {


    private TextView lat, lon;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    public static FavouritesFragment newInstance(String param1, String param2) {
        FavouritesFragment fragment = new FavouritesFragment();
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
        // Inflate the layout for this fragment
        View MyFragmentView = inflater.inflate(R.layout.fragment_cart, container, false);


        lat = (TextView) MyFragmentView.findViewById(R.id.lat);
        lon = (TextView) MyFragmentView.findViewById(R.id.lon);
        String stringdouble= Double.toString(((MyApplication) getActivity().getApplication()).getLat());
        lat.setText(stringdouble);
        stringdouble= Double.toString(((MyApplication) getActivity().getApplication()).getLon());
        lon.setText(stringdouble);

        return MyFragmentView;
    }


}
