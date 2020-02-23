package com.example.msp.databeam;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tabs extends Fragment implements TabHost.OnTabChangeListener {
    private static TabHost host;
    TabHost.TabSpec tab1;
    TabHost.TabSpec tab2;
    TabHost.TabSpec tab3;
    TabHost.TabSpec tab4;
    TabHost.TabSpec tab5;
    TabHost.TabSpec tab6;
static ImageButton back;

    static boolean selected = false;
    FragmentManager childFragment;
    static FragmentTransaction childTrans;

    TextView t1;
    Toolbar toolbar;
    View view;

    public Tabs() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity.toolbar.setVisibility(View.GONE);
        view = inflater.inflate(R.layout.fragment_tabs, null);
        toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        toolbar.setBackgroundColor(Color.rgb(FileChooser.cred, FileChooser.cgreen, FileChooser.cblue));
        back = (ImageButton) view.findViewById(R.id.back);
        back.setBackgroundColor(Color.rgb(FileChooser.cred, FileChooser.cgreen, FileChooser.cblue));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser fileChooser = new FileChooser();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left,R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_container, fileChooser);
                fragmentTransaction.commit();

            }
        });


        //creating tab UI
        tabCreation();
        //fbutton is used to check the files selected
        return view;
    }

    void tabCreation() {
        host = (TabHost) view.findViewById(R.id.tabHost);
        host.setup();
        //Create fm object to add fragment within a fragment


        //Tab 1
        tab1 = host.newTabSpec("files");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("All Files");
        host.addTab(tab1);
        //    childFragment = getChildFragmentManager();
        childTrans = getChildFragmentManager().beginTransaction();
        //   MyFiles myFiles = new MyFiles();
        //    childTrans.add(R.id.tab1, myFiles).commit();
        //Tab 2
        tab2 = host.newTabSpec("audio");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("Audio");
        host.addTab(tab2);

        //Tab 3
        tab3 = host.newTabSpec("image");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("Image");
        host.addTab(tab3);

        //Tab 4
        tab4 = host.newTabSpec("application");
        tab4.setContent(R.id.tab4);
        tab4.setIndicator("App");
        host.addTab(tab4);
        //  MyApp appFilter = new MyApp();
        //  childTrans.add(R.id.tab4, appFilter);

        //Tab 5
        tab5 = host.newTabSpec("video");
        tab5.setContent(R.id.tab5);
        tab5.setIndicator("Video");
        host.addTab(tab5);

        //Tab 6
        tab6 = host.newTabSpec("document");
        tab6.setContent(R.id.tab6);
        tab6.setIndicator("Docs");
        host.addTab(tab6);

        int tabNo = FileChooser.staticX;
        host.setCurrentTab(tabNo);
        if (tabNo == 0) {
            MyFiles myFiles = new MyFiles();
            childTrans.add(R.id.tab1, myFiles).commit();
            Log.d("TABS", "myfiles");
        }else if (tabNo == 2) {
                Images images = new Images();
                childTrans.add(R.id.tab3, images).commit();
                Log.d("TABS", "images");
        } else if (tabNo == 3) {
            MyApp myApp = new MyApp();
            childTrans.add(R.id.tab4, myApp).commit();
            Log.d("TABS", "appfilter");
        }else if (tabNo == 4) {
            MyVideo myVideo = new MyVideo();
            childTrans.add(R.id.tab5, myVideo).commit();
            Log.d("TABS", "MYVIDEO");
        }
        setTabColor(Color.rgb(FileChooser.cred, FileChooser.cgreen, FileChooser.cblue));
        host.setOnTabChangedListener(this);

    }

    public static void setTabColor(int color) {
        host.getTabWidget().setBackgroundColor(color);
    }


    @Override
    public void onTabChanged(String s) {
        if (s.equals(tab1.getTag())) {
            MyFiles myFiles = new MyFiles();
               //childTrans.replace(R.id.tab1, myFiles).commit();
            childTrans.show(myFiles);
            Log.d("TABS", "myfilesin tabevent");
            setTabColor(Color.rgb(255, 187, 51));
            toolbar.setBackgroundColor(Color.rgb(255, 187, 51));
            back.setBackgroundColor(Color.rgb(255, 187, 51));
            //  loadFiles();
        } else if (s.equals(tab2.getTag())) {
            setTabColor(Color.rgb(255, 136, 0));
            toolbar.setBackgroundColor(Color.rgb(255, 136, 0));
            back.setBackgroundColor(Color.rgb(255, 136, 0));
        } else if (s.equals(tab3.getTag())) {
            MyApp myApp = new MyApp();
            setTabColor(Color.rgb(255, 68, 68));
            toolbar.setBackgroundColor(Color.rgb(255, 68, 68));
            back.setBackgroundColor(Color.rgb(255, 68, 68));
        } else if (s.equals(tab4.getTag())) {
            setTabColor(Color.rgb(153, 204, 0));
            toolbar.setBackgroundColor(Color.rgb(153, 204, 0));
            back.setBackgroundColor(Color.rgb(153, 204, 0));

        } else if (s.equals(tab5.getTag())) {
            setTabColor(Color.rgb(51, 181, 229));
            toolbar.setBackgroundColor(Color.rgb(51, 181, 229));
            back.setBackgroundColor(Color.rgb(51, 181, 229));
        } else if (s.equals(tab6.getTag())) {
            setTabColor(Color.rgb(170, 102, 204));
            toolbar.setBackgroundColor(Color.rgb(170, 102, 204));
            back.setBackgroundColor(Color.rgb(170, 102, 204));
        }
    }

}
