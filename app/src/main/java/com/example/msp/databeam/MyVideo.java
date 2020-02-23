package com.example.msp.databeam;

import android.database.Cursor;
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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class MyVideo extends Fragment {
    int size = 0;
    ArrayList<File> list;
    String[] name;
    Bitmap[] thumbnail;
    ListView listView;
    CheckBox[] cBox;
    boolean[] rememberstate;
    ArrayList<String> filelist = new ArrayList<String>();
    ArrayList<String> filepath = new ArrayList<String>();
    static ArrayList<File> a = new ArrayList<>();

    public MyVideo() {
        // Required empty public constructor
    }

    public ArrayList<String> getAllMedia() {
        HashSet<String> videoItemHashSet = new HashSet<>();
        String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do {
                videoItemHashSet.add((cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))));
            } while (cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> downloadedList = new ArrayList<>(videoItemHashSet);
        for (int i = 0; i < downloadedList.size(); i++) {
            size = size + 1;
            Log.d("checkvideos", "" + downloadedList.get(i));
            File f = new File(downloadedList.get(i));
            filelist.add(f.getName());
            filepath.add(f.getAbsolutePath());
            a.add(f);
        }
        return downloadedList;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllMedia();
        cBox = new CheckBox[size];
        name = new String[size];
        thumbnail = new Bitmap[size];
        rememberstate = new boolean[size];
        for (int i = 0; i < size; i++) {
            name[i] = filelist.get(i).toString();
            thumbnail[i] = ThumbnailUtils.createVideoThumbnail(filepath.get(i), MediaStore.Video.Thumbnails.MICRO_KIND);
            if (thumbnail[i] == null) {
                thumbnail[i] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_folder_open);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_video, null);
        listView = (ListView) view.findViewById(R.id.fileList);
        VideoAdapter customAdapterVideo = new VideoAdapter(getContext(), name, thumbnail, cBox, rememberstate);
        listView.setAdapter(customAdapterVideo);
        return view;
    }
}