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
import android.widget.Toast;

import com.example.wadim.osmdroid_test.R;


public class SettingsFragment extends Fragment {

    public static final String PREFERENCES_NAME = "myPreferences";
    public static final String PREFERENCES_TEXT_FIELD = "radius";
    public static final String GRID_FIELD = "grid";
    private EditText etToSave;
    private Button btnSave;

    private RadioGroup radioGridGroup;
    private RadioButton radioGridButton;
    private int selectedId;

    private SharedPreferences preferences;

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

    private void initButtonOnClick(final View fragmentView) {
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            selectedId = radioGridGroup.getCheckedRadioButtonId();
            radioGridButton = (RadioButton) fragmentView.findViewById(selectedId);

            saveData();
            showToast("Data saved");
            }
        });
    }

    private void saveData() {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        String editTextData = etToSave.getText().toString();
        preferencesEditor.putString(PREFERENCES_TEXT_FIELD, editTextData);
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

    private void restoreData(final View fragmentView) {
        String textFromPreferences = preferences.getString(PREFERENCES_TEXT_FIELD, "3");
        etToSave.setText(textFromPreferences);
        int intGrid = preferences.getInt(GRID_FIELD, 0);
       if (intGrid == 0)
        {
            radioGridButton = (RadioButton) fragmentView.findViewById(R.id.radioMale);
            radioGridButton.setChecked(true);
        }
        else {
           radioGridButton = (RadioButton) fragmentView.findViewById(intGrid);
           radioGridButton.setChecked(true);
        }


    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View MyFragmentView = inflater.inflate(R.layout.fragment_settings, container, false);
        // Inflate the layout for this fragment
        etToSave = (EditText) MyFragmentView.findViewById(R.id.etToSave);
        btnSave = (Button) MyFragmentView.findViewById(R.id.btnSave);
        radioGridGroup = (RadioGroup) MyFragmentView.findViewById(R.id.radioSex);

        initButtonOnClick(MyFragmentView);
        restoreData(MyFragmentView);
        return MyFragmentView;
        //return inflater.inflate(R.layout.fragment_settings, container, false);
    }

}
