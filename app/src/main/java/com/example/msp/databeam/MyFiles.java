package com.example.msp.databeam;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.lucasr.smoothie.AsyncListView;

import java.io.File;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MyFiles extends Fragment {

    OnFileSelectedListener onFileSelectedListener;

    public ListView dialog_ListView;
    public static CustomAdapter customAdapter;
    static File[] files;
    static ArrayList<File> file = new ArrayList<File>();
    File root;
    File curFolder;
    ArrayList<CheckBox> cbox = new ArrayList<CheckBox>();
    static int lengthCheck;
    ArrayList<String> fname = new ArrayList<String>();
    ArrayList<String> fsize = new ArrayList<String>();
    ArrayList<String> fdate = new ArrayList<String>();
    ArrayList<Bitmap> thumbnail = new ArrayList<Bitmap>();
    TextView changethis;
    int counter = 0;
    DatabaseManager db;
    static boolean[] checkState;
    ImageView img;
    static File directory;
    private static List<String> fileList = new ArrayList<String>();


    public MyFiles() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myfiles, null);
        db = new DatabaseManager(getContext());
        img = new ImageView(getContext());
        img.setImageResource(R.drawable.ic_action_android);
        TabbedLayout.fab.setVisibility(View.GONE);
        root = new File("/sdcard");
        curFolder = root;
        directory = root;
        dialog_ListView = (ListView) view.findViewById(R.id.dialoglist);
          ListDir(curFolder);

        dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                File selected = new File(fileList.get(position));
                directory = selected;
                if (directory.isDirectory()) {
                    ListDir(directory);
                }

            }

        });
        return view;
    }

    void ListDir(File f) {
        file.clear();
     fname.clear();
         fsize.clear();
        fdate.clear();
        thumbnail.clear();
        curFolder = f;
        //Declaration
        files = f.listFiles();
        for(int i =0;i<files.length;i++){
            file.add(files[i]);
        }
        checkState = new boolean[files.length];
        lengthCheck = files.length;
        Log.d("showstates", "->" + lengthCheck);
        fileList.clear();

        for (int i = 0; i < files.length; i++) {
            fname.add(files[i].getName());
            if (files[i].isDirectory()) {
                thumbnail.add(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_folder_open));
                fsize.add(fileCount(files[i]));
            } else if (files[i].isFile()) {
                thumbnail.add(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_document));
                fsize.add(fileSize(files[i]));

            } else {
                thumbnail.add(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_document));

            }
            fdate.add(getDate(files[i]));
            fileList.add(files[i].getPath());
            checkState[i] = false;
        }
        CustomAdapter customAdapter = new CustomAdapter(getContext(), fname, fsize, fdate, thumbnail, cbox, checkState);
        dialog_ListView.setAdapter(customAdapter);
    }

    public String fileSize(File file) {

        long size = file.length();
        if (size < 1000000) {
            //for kB
            size = size / 1024;
            return size + "kB";
        }
        else{
            size = size / (1024* 1024);
            return size + "MB";
        }
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

    public void showStates(Context context) {
        for (int i = 0; i < lengthCheck; i++) {
            Log.d("showstates", "->" + i + checkState[i]);
            if (checkState[i] == true) {
                Log.d("showstates1234", "->" + i + checkState[i]);
                boolean isInserted = db.insertData((byte) R.drawable.ic_action_folder_open, files[i].getName(), fileSize(files[i]));
                if (isInserted = true) {
                    Toast.makeText(context.getApplicationContext(), "Data Inserted", Toast.LENGTH_LONG).show();
                }
            } else if (!checkState[i]) {
                Log.d("showstates1234", "->" + i + checkState[i]);
            }
        }
    }

    public void setAdapt() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog_ListView.setAdapter(MyFiles.customAdapter);
                customAdapter.notifyDataSetChanged();
            }
        });
    }


    public void selectedfiles() {
        Log.d("showstatessss", lengthCheck + "->");
        for (int i = 0; i < lengthCheck; i++) {
            Log.d("showstates", "->" + i + checkState[i]);
            if (checkState[i]) {
                Log.d("showstates1234", "->" + i + checkState[i]);
                // TabbedLayout.fab.show();
                /*boolean isInserted = db.insertData(fname[1],fsize[1]);
                if (isInserted = true)
                {
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                }*/
            } else if (!checkState[i]) {
                // TabbedLayout.fab.hide();
                Log.d("showstates1234", "->" + i + checkState[i]);
            }
        }
    }


    public interface OnFileSelectedListener {
        public void selectedFiles(String str);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity;
        activity = (Activity) context;
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            onFileSelectedListener = (OnFileSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        onFileSelectedListener = null; // => avoid leaking
        super.onDetach();
    }

    public class MyFilesAsync extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            customAdapter = new CustomAdapter(getContext(), fname, fsize, fdate, thumbnail, cbox, checkState);
            setAdapt();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //  curFolder = f;
            //Declaration

            File f = directory;
            //File f = new File("/sdcard");
            files = f.listFiles();
            file.clear();
            for (int i = 0; i < files.length; i++) {
                file.add(files[i]);
                Log.d("Myfiles12345", file.get(i).getName() + "");
            }
            //Bitmap[] thumbnailimage = new Bitmap[files.length];
            //fname = new String[files.length];
            //fsize = new String[files.length];
            checkState = new boolean[files.length];
            //fdate = new String[files.length];
            //cbox = new CheckBox[files.length];
            lengthCheck = files.length;
            Log.d("showstates", "->" + lengthCheck);
            fileList.clear();


            for (int i = 0; i < files.length; i++) {
                //fname[i] = files[i].getName();
                fname.add(files[i].getName());
                //  cbox[i] = new CheckBox(getContext());
                if (files[i].isDirectory()) {
                    //thumbnailimage[i] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_folder_open);
                    thumbnail.add(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_folder_open));
                    //fsize[i] = fileCount(files[i]);
                    fsize.add(fileCount(files[i]));
                } else if (files[i].isFile()) {
                    //thumbnailimage[i] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_document);
                    thumbnail.add(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_document));
                    //fsize[i] = fileSize(files[i]);
                    fsize.add(fileSize(files[i]));

                } else {
                    //thumbnailimage[i] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_document);
                    thumbnail.add(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_document));

                }
                //fdate[i] = getDate(files[i]);
                fdate.add(getDate(files[i]));
                fileList.add(files[i].getPath());
                checkState[i] = false;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customAdapter.notifyDataSetChanged();
                    }
                });

            }

            return null;
        }
    }

}
