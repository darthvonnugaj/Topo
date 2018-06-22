package com.example.wadim.osmdroid_test.fragment;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wadim.osmdroid_test.R;


public class SettingsFragment extends Fragment {

    public static final String PREFERENCES_NAME = "myPreferences";
    public static final String PREFERENCES_RADIUS = "radiuskm";
    public static final String GRID_FIELD = "grid";
    private EditText etToSave;
    private SeekBar radiusBar;

    private RadioGroup radioGridGroup;
    private RadioButton radioGridButton;
    private int selectedId;

    private SharedPreferences preferences;
    private TextView tvProgressLabel;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = this.getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private void restoreData(final View fragmentView) {
        Integer radius = preferences.getInt(PREFERENCES_RADIUS, 20);
        radiusBar.setProgress(radius);

        int intGrid = preferences.getInt(GRID_FIELD, 0);
        if (intGrid == 0)
        {
            radioGridButton = (RadioButton) fragmentView.findViewById(R.id.radioMale);
            radioGridButton.setChecked(true);
        }
        else {
           radioGridButton = (RadioButton) fragmentView.findViewById(R.id.radioFemale);
           radioGridButton.setChecked(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        radioGridGroup = (RadioGroup) view.findViewById(R.id.radioSex);
        radioGridGroup.setOnCheckedChangeListener(radioChangeListener);

        radiusBar =  view.findViewById(R.id.seekBar);
        radiusBar.setOnSeekBarChangeListener(seekBarChangeListener);

        int progress = radiusBar.getProgress();
        tvProgressLabel = view.findViewById(R.id.radius_progress);
        tvProgressLabel.setText("Route search radius: " + progress + " km");

        restoreData(view);
        return view;
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            tvProgressLabel.setText("Route search radius: " + progress + " km");
            SharedPreferences.Editor preferencesEditor = preferences.edit();
            preferencesEditor.putInt(PREFERENCES_RADIUS, progress);
            preferencesEditor.apply();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };

    RadioGroup.OnCheckedChangeListener radioChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            selectedId = radioGroup.getCheckedRadioButtonId();
            SharedPreferences.Editor preferencesEditor = preferences.edit();
            switch (selectedId){
                case R.id.radioMale:
                    preferencesEditor.putInt(GRID_FIELD, 0);
                    break;
                case R.id.radioFemale:
                    preferencesEditor.putInt(GRID_FIELD, 1);
                    break;
            }
            preferencesEditor.apply();
        }
    };
}
