package com.example.msp.databeam;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyApp extends Fragment {
    ListView dialog_ListView;
    File root;
    ArrayList<String> fname = new ArrayList<String>();
    ArrayList<String> fdesc = new ArrayList<String>();
    ArrayList<String> fdate = new ArrayList<String>();
    ArrayList<Bitmap> thumbnailimage = new ArrayList<Bitmap>();
    boolean[] rememberStates;
    ArrayList<CheckBox> cbox = new ArrayList<CheckBox>();
    // Flags: See below
    int flags = PackageManager.GET_META_DATA |
            PackageManager.GET_SHARED_LIBRARY_FILES |
            PackageManager.GET_UNINSTALLED_PACKAGES;


    private List<String> fileList = new ArrayList<String>();


    public MyApp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myapp, null);
        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        // root = new File("/system/app/chrome");
        getAppName(getContext());
        dialog_ListView = (ListView) view.findViewById(R.id.appList);
        //  ArrayAdapter<String> arrayList = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,fileList);
        CustomAdapter customAdapter = new CustomAdapter(getContext(), fname, fdesc, fdate, thumbnailimage, cbox, rememberStates);
        dialog_ListView.setAdapter(customAdapter);
        return view;
    }


    public void getAppName(Context ctx) {


        PackageManager pm = ctx.getPackageManager();
        List<ApplicationInfo> pi = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        ApplicationInfo applicationInfo;
        int x = 0;
        boolean[] userApp = new boolean[pi.size()];

        for (int i = 0; i < pi.size(); i++) {
            userApp[i] = false;
            applicationInfo = pi.get(i);
            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                userApp[i] = true;
                x = x + 1;
            }
        }

        //thumbnailimage = new Bitmap[x];
        rememberStates = new boolean[x];
        //cbox = new CheckBox[x];
        //fname = new String[x];
        //fdesc = new String[x];
        //fdate = new String[x];
        int counter = 0;

        for (int i = 0; i < pi.size(); i++) {
            if (userApp[i]) {
                applicationInfo = pi.get(i);
                //thumbnailimage[counter] = getThumbnail(applicationInfo, pm);
                thumbnailimage.add(getThumbnail(applicationInfo, pm));
                //fname[counter] = applicationInfo.loadLabel(pm).toString();
                fname.add(applicationInfo.loadLabel(pm).toString());
                //fdesc[counter] = "";
                fdesc.add("");
                //fdate[counter] = "";
                fdate.add("");
                counter = counter + 1;
            }
        }

    }

    public Bitmap getThumbnail(ApplicationInfo applicationInfo, PackageManager pm) {
        Drawable image = applicationInfo.loadIcon(pm);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) image;
        return ((BitmapDrawable) image).getBitmap();

    }
}
