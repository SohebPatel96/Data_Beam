package com.example.msp.databeam;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class FileChooser extends Fragment implements View.OnClickListener {
    public static int staticX = -1;
    static int cred, cgreen, cblue;
    ImageButton filebutton, audiobutton, picturebutton, appbutton, videobutton, documentbutton;


    public FileChooser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.toolbar.setVisibility(View.VISIBLE);
        View view = inflater.inflate(R.layout.file_chooser, null);
        filebutton = (ImageButton) view.findViewById(R.id.imageFile);
        audiobutton = (ImageButton) view.findViewById(R.id.imageAudio);
        picturebutton = (ImageButton) view.findViewById(R.id.imagePhoto);
        appbutton = (ImageButton) view.findViewById(R.id.imageApp);
        videobutton = (ImageButton) view.findViewById(R.id.imageVideo);
        documentbutton = (ImageButton) view.findViewById(R.id.imageDocument);
        Log.d("myaudio123", "data:");
        filebutton.setOnClickListener(this);
        audiobutton.setOnClickListener(this);
        picturebutton.setOnClickListener(this);
        appbutton.setOnClickListener(this);
        videobutton.setOnClickListener(this);
        documentbutton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        MainActivity.fab.setVisibility(View.GONE);
        int id = v.getId();
        if (id == R.id.imageFile) {
            staticX = 0;
            cred = 255;
            cgreen = 187;
            cblue = 0;
            TabbedLayout tabbedLayout = new TabbedLayout();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            fragmentTransaction.replace(R.id.fragment_container, tabbedLayout);
            fragmentTransaction.commit();


        } else if (id == R.id.imageAudio) {
            TabbedLayout tabbedLayout = new TabbedLayout();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            fragmentTransaction.replace(R.id.fragment_container, tabbedLayout);
            fragmentTransaction.commit();
            staticX = 1;
            cred = 255;
            cgreen = 136;
            cblue = 0;
        } else if (id == R.id.imagePhoto) {
            TabbedLayout tabbedLayout = new TabbedLayout();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            fragmentTransaction.replace(R.id.fragment_container, tabbedLayout);
            fragmentTransaction.commit();

            staticX = 2;
            cred = 255;
            cgreen = 68;
            cblue = 68;

        } else if (id == R.id.imageApp) {
            TabbedLayout tabbedLayout = new TabbedLayout();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            fragmentTransaction.replace(R.id.fragment_container, tabbedLayout);
            fragmentTransaction.commit();

            staticX = 3;
            cred = 153;
            cgreen = 204;
            cblue = 0;
        } else if (id == R.id.imageVideo) {
            TabbedLayout tabbedLayout = new TabbedLayout();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            fragmentTransaction.replace(R.id.fragment_container, tabbedLayout);
            fragmentTransaction.commit();
            staticX = 4;
            cred = 51;
            cgreen = 181;
            cblue = 229;
        } else if (id == R.id.imageDocument) {
            TabbedLayout tabbedLayout = new TabbedLayout();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            fragmentTransaction.replace(R.id.fragment_container, tabbedLayout);
            fragmentTransaction.commit();
            staticX = 5;
            cred = 170;
            cgreen = 102;
            cblue = 204;
        }
    }

    public int getX() {
        return staticX;
    }
}
