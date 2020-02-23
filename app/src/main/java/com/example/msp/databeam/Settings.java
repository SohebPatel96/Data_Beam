package com.example.msp.databeam;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.example.msp.databeam.R;

import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {
    Switch toggle;
    static EditText port,IP;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";

    public Settings() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);


        View view = inflater.inflate(R.layout.fragment_settings, null);
        port = (EditText) view.findViewById(R.id.editText4);
        IP = (EditText) view.findViewById(R.id.editText3);
        toggle = (Switch) view.findViewById(R.id.switch1);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleTheme(isChecked);

            }
        });

        // myswitch.setOnCheckedChangeListener();
        return view;

    }
    public void toggleTheme(boolean darkTheme){
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_DARK_THEME, darkTheme);
        editor.apply();
    }
public static int getPortNo()
{
    String str = port.getText().toString();
    return(Integer.parseInt(str));
}
    public static String getIP()
    {
        return((IP.getText().toString()));
    }
}
