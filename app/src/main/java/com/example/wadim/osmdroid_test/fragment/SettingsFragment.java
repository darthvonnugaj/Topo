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

    private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_TEXT_FIELD = "textField";
    private static final String GENDER_FIELD = "gender";
    private EditText etToSave;
    private Button btnSave;

    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
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
                //etToSave.setText("dddvsv");
                selectedId = radioSexGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioSexButton = (RadioButton) fragmentView.findViewById(selectedId);

                saveData();
                showToast("Data saved");
            }
        });
    }

    private void saveData() {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        String editTextData = etToSave.getText().toString();
        preferencesEditor.putString(PREFERENCES_TEXT_FIELD, editTextData);
        preferencesEditor.putInt(GENDER_FIELD, selectedId);
        preferencesEditor.apply();
    }

    private void restoreData( View fragmentView) {
        String textFromPreferences = preferences.getString(PREFERENCES_TEXT_FIELD, "");
        etToSave.setText(textFromPreferences);
        int intGender = preferences.getInt(GENDER_FIELD, 0);
       if (intGender ==0)
        {
            radioSexButton = (RadioButton) fragmentView.findViewById(R.id.radioMale);
            radioSexButton.setChecked(true);
        }
        else {
           radioSexButton = (RadioButton) fragmentView.findViewById(intGender);
           radioSexButton.setChecked(true);
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
        radioSexGroup = (RadioGroup) MyFragmentView.findViewById(R.id.radioSex);


        initButtonOnClick(MyFragmentView);
        restoreData(MyFragmentView);
        return MyFragmentView;
        //return inflater.inflate(R.layout.fragment_settings, container, false);
    }

}
