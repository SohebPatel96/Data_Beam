package com.example.msp.databeam;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyFiles.OnFileSelectedListener {
    static String IP;
    WifiP2pManager.PeerListListener myPeerListListener;
    static Toolbar toolbar;
    DatabaseManager db;
    static FloatingActionButton fab;
    static DrawerLayout drawer;
    NavigationView navigationView;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "dark_theme";
    //Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        setContentView(R.layout.app_bar_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //button = (Button) findViewById(R.id.button);
        //android.app.ActionBar actionbar = getActionBar();
        //actionbar.hide();
        //MainActivity.toolbar.setBackgroundColor(Color.rgb(255,187,51));
        // db = new DatabaseManager(this);
//        IP = SendConfirm.getIP();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        //  setSupportActionBar(toolbar);

        //  setTheme(R.style.BlackTheme);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  selectedFiles();
                //    MyFiles myFiles = new MyFiles();
                //   myFiles.selectedfiles();
                //  ((MyFiles) myFiles).showStates(getApplicationContext());
               /* boolean isInserted = db.insertData("1","!");
                if (isInserted = true)
                {
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                }
               Log.d("TABS","Calling floatingbutton");
*/
            }
        });
        fab.setVisibility(View.GONE);
        Home home = new Home();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, home);
        fragmentTransaction.commit();


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(PREF_DARK_THEME, false);

        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        // MainActivity.toolbar.setVisibility(View.INVISIBLE);
        /*Switch toggle1 = (Switch) findViewById(R.id.switch2);
        toggle1.setChecked(useDarkTheme);
        toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                toggleTheme(isChecked);
            }
        });
*/
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            Settings settings = new Settings();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, settings);
            fragmentTransaction.commit();
            navigationView.setCheckedItem(R.id.nav_settings);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        toolbar.setVisibility(View.VISIBLE);
        fab.setVisibility(View.GONE);
       // toolbar.setBackgroundColor(Color.rgb(38, 50, 56));
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Home home = new Home();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, home).addToBackStack(null);
            fragmentTransaction.commit();
            navigationView.setCheckedItem(R.id.nav_home);
        } else if (id == R.id.nav_myfiles) {

            FileChooser fileChooser = new FileChooser();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fileChooser).addToBackStack(null);
            fragmentTransaction.commit();
            navigationView.setCheckedItem(R.id.nav_myfiles);
        } else if (id == R.id.nav_history) {
            History history = new History();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, history).addToBackStack(null);
            fragmentTransaction.commit();
            navigationView.setCheckedItem(R.id.nav_history);
        } else if (id == R.id.nav_feedback) {
            Feedback feedback = new Feedback();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, feedback).addToBackStack(null);
            fragmentTransaction.commit();
            navigationView.setCheckedItem(R.id.nav_feedback);
        } else if (id == R.id.nav_about) {
            About about = new About();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, about).addToBackStack(null);
            fragmentTransaction.commit();
            navigationView.setCheckedItem(R.id.nav_about);

        } else if (id == R.id.nav_connectpc) {
            // ConnectPC connectPC = new ConnectPC();

            SetConnectionPC setConnectionPC = new SetConnectionPC();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, setConnectionPC).addToBackStack(null);
            fragmentTransaction.commit();

            navigationView.setCheckedItem(R.id.nav_connectpc);
        }


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void selectedFiles(String str) {
        //  MyFiles myFiles = new MyFiles();
        //  myFiles.selectedfiles();
        Log.d("Soheb12345", "from my files:" + str);
        MyFiles myFiles = new MyFiles();
        myFiles.selectedfiles();
    }
}
