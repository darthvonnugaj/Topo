package com.example.wadim.osmdroid_test.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wadim.osmdroid_test.R;


public class DetailsFragment extends Fragment {

    private int id;
    private TextView idTextView;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(int id) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        fragment.setArguments(args);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            id = bundle.getInt("id");
        }
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

        idTextView = (TextView) MyFragmentView.findViewById(R.id.idTextView);

        readBundle(getArguments());

        idTextView.setText(String.format("%d", id));


        return  MyFragmentView;
    }

}
