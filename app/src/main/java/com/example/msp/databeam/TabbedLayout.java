package com.example.msp.databeam;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabbedLayout extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageButton back;
    static FloatingActionButton fab;

    int cred, cgreen, cblue;

    public TabbedLayout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tabbed_layout, null);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        MainActivity.toolbar.setVisibility(View.GONE);
        fab = (FloatingActionButton) view.findViewById(R.id.fabTab);
        back = (ImageButton) view.findViewById(R.id.backky);
        back.setBackgroundColor(Color.rgb(FileChooser.cred, FileChooser.cgreen, FileChooser.cblue));
        tabLayout.setBackgroundColor(Color.rgb(FileChooser.cred, FileChooser.cgreen, FileChooser.cblue));
        //tabLayout.setBackgroundColor(Color.rgb(cred, cgreen, cblue));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser mediaSelect = new FileChooser();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.fragment_container, mediaSelect);
                fragmentTransaction.commit();

            }
        });
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(new CustomAdapterPager(getActivity().getSupportFragmentManager(), getActivity().getApplicationContext()));
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                viewPager.setCurrentItem(FileChooser.staticX, true);
                if (FileChooser.staticX == 0) {
                    viewPager.setCurrentItem(0);
                }

            }
        });
        //tabLayout.setBackgroundColor(Color.rgb(FileChooser.cred, FileChooser.cgreen, FileChooser.cblue));
        tabLayout.setupWithViewPager(viewPager);
        // viewPager.setCurrentItem(FileChooser.staticX);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                checkTab(tab.getPosition());
                tabLayout.setBackgroundColor(Color.rgb(cred, cgreen, cblue));
                back.setBackgroundColor(Color.rgb(cred, cgreen, cblue));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabLayout.setBackgroundColor(Color.rgb(cred, cgreen, cblue));
                back.setBackgroundColor(Color.rgb(cred, cgreen, cblue));
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Soheb12345", "fab clicked");
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                ((MyFiles.OnFileSelectedListener) getActivity()).selectedFiles("Hello");
                //  Send send = new Send();
                String storeName = Send.selectedFile.getName();
                String size = Send.selectedFile.length() / (1024 * 1024) + "MB";
                DatabaseManager db = new DatabaseManager(getContext());
                db.insertData((byte) R.drawable.ic_action_document, storeName, size);
                Fragment fragment = new Send();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }


    private class CustomAdapterPager extends FragmentStatePagerAdapter {
        private String[] fragments = {"All FILES", "AUDIO", "IMAGE", "APP", "VIDEO", "DOCS"};

        public CustomAdapterPager(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MyFiles();
                case 1:
                    return new MyAudio();
                case 2:
                    return new Images();
                case 3:
                    return new MyApp();
                case 4:
                    return new MyVideo();
                case 5:
                    return new MyDocument();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }
    }

    public void checkTab(int tabNo) {
        switch (tabNo) {
            case 0:
                cred = 255;
                cgreen = 187;
                cblue = 0;
                break;
            case 1:
                cred = 255;
                cgreen = 136;
                cblue = 0;
                break;
            case 2:
                cred = 255;
                cgreen = 68;
                cblue = 68;
                break;
            case 3:
                cred = 153;
                cgreen = 204;
                cblue = 0;
                break;
            case 4:
                cred = 51;
                cgreen = 181;
                cblue = 229;
                break;
            case 5:
                cred = 170;
                cgreen = 102;
                cblue = 204;
                break;
        }
    }
}
