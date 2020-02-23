package com.example.msp.databeam;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaBrowserCompatUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;


public class MyAudio extends Fragment {
    int size = 0;
    ListView listView;
    ArrayList<String> filelist = new ArrayList<String>();
    ArrayList<String> filepath = new ArrayList<String>();
    static ArrayList<File> a = new ArrayList<>();
    String[] name;
    ArrayList<String> duration = new ArrayList<String>();
    CheckBox[] cBox;

    ArrayList<String> audioList = new ArrayList<>();
    boolean[] rememberState;
    int count;
    MediaPlayer mp;
    Bitmap[] thumbnail;


    public MyAudio() {
        // Required empty public constructor
    }

    public ArrayList<String> getAllMedia() {
        HashSet<String> videoItemHashSet = new HashSet<>();
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.Media.DISPLAY_NAME};
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do {
                videoItemHashSet.add((cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))));
            } while (cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("checkaudios", "dfsdd");

        ArrayList<String> downloadedList = new ArrayList<>(videoItemHashSet);
        for (int i = 0; i < downloadedList.size(); i++) {
            size = size + 1;
            Log.d("checkaudios", "" + downloadedList.get(i));
            File f = new File(downloadedList.get(i));
            filelist.add(f.getName());
            filepath.add(f.getAbsolutePath());
            a.add(f);
        }
        return downloadedList;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioList = getAllMedia();
        cBox = new CheckBox[size];
        name = new String[size];
        thumbnail = new Bitmap[size];
        rememberState = new boolean[size];
        for (int i = 0; i < size; i++) {
            name[i] = filelist.get(i).toString();
            thumbnail[i] = BitmapFactory.decodeResource(getResources(), R.drawable.music_icon);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_audio, null);
        listView = (ListView) view.findViewById(R.id.fileListaudio);
        AudioAdapter audioAdapter = new AudioAdapter(getContext(), name, thumbnail, cBox, rememberState);
        listView.setAdapter(audioAdapter);
        return view;
    }
}