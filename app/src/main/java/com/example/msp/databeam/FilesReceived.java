package com.example.msp.databeam;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class FilesReceived extends Fragment {
    String root = "/sdcard/DataBeam/";
    ListView received;
    static public ListView dialog_ListView;
    static File[] files;
    static File check = new File("/sdcard/DataBeam/");


    File curFolder;
    static CheckBox[] cbox;
    static int lengthCheck;
    static String[] fdate, fname, fsize;
    TextView changethis;
    static int counter = 0;
    static DatabaseManager db;
    static boolean[] checkState;
    static ImageView img;
    private List<String> fileList = new ArrayList<String>();
    public FilesReceived() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_files_received, container, false);
        received = (ListView) view.findViewById(R.id.receivelist);
        ListDir(new File(root));
        // Inflate the layout for this fragment
        return view;
    }
    void ListDir(File f) {
        curFolder = f;
        //Declaration
        files = f.listFiles();
        Bitmap[] thumbnailimage;
        thumbnailimage = new Bitmap[files.length];
        fname = new String[files.length];
        fsize = new String[files.length];
        fdate = new String[files.length];
        lengthCheck = files.length;
        fileList.clear();


        for (int i = 0; i < files.length; i++) {
            fname[i] = files[i].getName();

            //  cbox[i] = new CheckBox(getContext());
            if (files[i].isDirectory()) {
                thumbnailimage[i] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_folder_open);
                fsize[i] = fileCount(files[i]);
            } else if (files[i].isFile()) {
                thumbnailimage[i] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_document);
                fsize[i] = fileSize(files[i]);
            } else {
                thumbnailimage[i] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_document);
            }

            fdate[i] = getDate(files[i]);
            fileList.add(files[i].getPath());
            FilesReceivedAdapter filesReceivedAdapter = new FilesReceivedAdapter(getContext(),fname,fsize,fdate,thumbnailimage);
            received.setAdapter(filesReceivedAdapter);
        }

    }
    public static String fileSize(File file) {

        long size = file.length();
        //for kB
        size = size / 1024;
        return size + "kB";
        //for mB

    }

    public String fileCount(File file) {
        File[] temp = file.listFiles();
        int buf = 0;
        if (temp != null) {
            buf = temp.length;
        } else {
            buf = 0;
        }
        String no_items = String.valueOf(buf);
        if (buf == 0) {
            no_items = no_items + " items";
        } else {
            no_items = no_items + " items";
        }
        return no_items;
    }

    public String getDate(File file) {
        Date lastModDate = new Date(file.lastModified());
        DateFormat formatter = DateFormat.getDateInstance();
        return formatter.format(lastModDate);
    }

}
