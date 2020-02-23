package com.example.msp.databeam;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;


public class MyDocument extends Fragment {
    int size = 0;
    ArrayList<File> list;
    String[] name;
    Bitmap[] thumbnail;
    ListView listView;
    CheckBox[] cBox;
    boolean[] rememberstate;
    ArrayList<String> filelist = new ArrayList<String>();
    ArrayList<String> filepath = new ArrayList<String>();
    static  ArrayList<File> docs = new ArrayList<>();


    ArrayList<File> imageReader(File root) {
          ArrayList<File> a = new ArrayList<>();
        File[] files = root.listFiles();
        name = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                a.addAll(imageReader(files[i]));
            } else {

                if (files[i].getName().endsWith(".pdf") || files[i].getName().endsWith(".doc") || files[i].getName().endsWith(".docx") || files[i].getName().endsWith(".txt")) {
                    size = size + 1;
                    docs.add(files[i]);
                    filelist.add(files[i].getName());
                    filepath.add(files[i].getAbsolutePath());
                }

            }
        }
        return a;
    }

    public MyDocument() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = imageReader(new File(Environment.getExternalStorageDirectory().getPath()));
        cBox = new CheckBox[size];
        name = new String[size];
        thumbnail = new Bitmap[size];
        rememberstate = new boolean[size];
        for (int i = 0; i < size; i++) {
            name[i] = filelist.get(i).toString();
            thumbnail[i] = ThumbnailUtils.createVideoThumbnail(filepath.get(i), MediaStore.Video.Thumbnails.MICRO_KIND);
            if (thumbnail[i] == null) {
                thumbnail[i] = BitmapFactory.decodeResource(getResources(), R.drawable.document);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("myvideo123", "myvideo");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_document, null);
        listView = (ListView) view.findViewById(R.id.fileListdocument);
        DocumentAdapter customAdapterVideo = new DocumentAdapter(getContext(), name, thumbnail, cBox, rememberstate);
        listView.setAdapter(customAdapterVideo);
        return view;
    }
}
