package com.example.msp.databeam;


import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class Images extends Fragment {
    int imageCount = 0;
    GridView gv;
    ArrayList<File> list;
    static ImageAdapter imageAdapter;
    static File root;
    static ArrayList<File> a = new ArrayList<File>();

    public Images() {
        // Required empty public constructor
    }

    ArrayList<File> imageReader(File root) {
        ArrayList<File> a = new ArrayList<>();
        File[] files = root.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                a.addAll(imageReader(files[i]));
            } else {
                if (files[i].getName().endsWith(".jpg") || files[i].getName().endsWith(".png")) {
                    a.add(files[i]);
                    imageCount = imageCount + 1;
                }
            }
        }
        return a;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, null);
        gv = (GridView) view.findViewById(R.id.gridView);

        new MyImagesAsync().execute();
        return view;
    }

    public class MyImagesAsync extends AsyncTask {

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            imageAdapter = new ImageAdapter(getContext(), gv, Images.a);
            gv.setAdapter(imageAdapter);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            root = new File(Environment.getExternalStorageDirectory().getPath());
            File[] files = root.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    Images.a.addAll(imageReader(files[i]));
                } else {
                    if (files[i].getName().endsWith(".jpg") || files[i].getName().endsWith(".png")) {
                        Images.a.add(files[i]);
                    }
                }
            }
            return null;
        }
    }
}