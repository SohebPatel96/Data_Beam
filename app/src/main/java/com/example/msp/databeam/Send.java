package com.example.msp.databeam;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Send extends Fragment {
    public static final String TAG = "send1234";
    static ServerSocket serverSocket;
    static Socket socket;
    Button sendFile,disconnect;
    static File selectedFile;
    static int totalFile;
    String fileName;
    int fileSize;
    File Sendfile = SendConfirm.file;
    DataOutputStream dos;
    boolean isSending = false;

    ArrayList<String> fileNameList = new ArrayList<String>();
    ArrayList<String> dataReceived = new ArrayList<String>();
    ArrayList<ProgressBar> progressBars = new ArrayList<ProgressBar>();
    ArrayList<Integer> progressMax = new ArrayList<>();
    ArrayList<Integer> progress = new ArrayList<>();
    int index = -1;
    public ListView list;
    SendAdapter sendAdapter;

    //State remember
    SharedPreferences preferences;
    ArrayList<String> fnames = new ArrayList<String>();
    String msg = "100% complete";
    DecimalFormat df = new DecimalFormat("0.00");


    public Send() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        MainActivity.toolbar.setVisibility(View.VISIBLE);
        setRetainInstance(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void rememberState(Context ctx) {
        preferences = this.getActivity().getSharedPreferences("sadas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> set = new HashSet<String>();
        if (fnames != null) {
            set.addAll(fnames);//save all filenames
        }
        editor.putBoolean("run", true);
        editor.putStringSet("fname", set);
        editor.apply();
        if (preferences != null) {
            Log.d(TAG, "shared pref is not null in pause");
        }
    }

    public void restoreState() {
        Log.d(TAG, "inside restore states");
        preferences = this.getActivity().getSharedPreferences("sadas", Context.MODE_PRIVATE);
        boolean runCode = preferences.getBoolean("run", false);
        if (runCode) {
            Set<String> set = preferences.getStringSet("fname", null);
            Log.d(TAG, "inside restore states2");
            fnames.addAll(set);
            index = -1;
            for (int i = 0; i < fnames.size(); i++) {
                index = index + 1;
                fileNameList.add(fnames.get(i));
                dataReceived.add("100% complete");
                progressMax.add(100);
                progress.add(100);
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sendAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onpause called");
        rememberState(getContext());
        super.onPause();
    }


    @Override
    public void onResume() {
        Log.d(TAG, "onResumeCalled called");
       // restoreState();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        View view = inflater.inflate(R.layout.fragment_send, container, false);
        list = (ListView) view.findViewById(R.id.androidList3);
        sendFile = (Button) view.findViewById(R.id.sendButton);
        disconnect = (Button) view.findViewById(R.id.btn_disconnect2);
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try {
                        if(serverSocket != null)
                            serverSocket.close();
                        if(socket != null)
                            socket.close();
                        if(dos != null)
                            dos.close();

                        Home home = new Home();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, home);
                        fragmentTransaction.commit();
                        MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        MainActivity.toolbar.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        });
        if (selectedFile != null) {
            new Thread() {
                public void run() {
                    transferFile();
                }
            }.start();
        }
        sendAdapter = new SendAdapter(getContext(), fileNameList, progressBars, dataReceived, progressMax, progress);
        list.setAdapter(sendAdapter);
      //  restoreState();

        sendFile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(isSending){
                    Toast.makeText(getContext(),"Cannot select file while transfer is in progress",Toast.LENGTH_SHORT).show();
                }
                else {
                    selectFile();
                }
            }
        });
        return view;
    }

    public void selectFile() {

        Log.d(TAG, "Button Clicked");
        selectedFile = null;
        Fragment fragment = new FileChooser();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.hide(Send.this);
        fragmentTransaction.addToBackStack(Send.class.getName());
        fragmentTransaction.commit();

    }

    public void transferFile() {
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            index = index + 1;
            dos.writeUTF(selectedFile.getName());
            dos.writeInt((int) selectedFile.length());
            fileNameList.add(selectedFile.getName());
            fileName = selectedFile.getName();
            fnames.add(selectedFile.getName());
            fileSize = (int) selectedFile.length();
            dataReceived.add("--");
            progressMax.add(fileSize);
            progress.add(0);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sendAdapter.notifyDataSetChanged();
                }
            });
            FileInputStream fileInputStream = new FileInputStream(selectedFile);
            byte[] buffer = new byte[64 * 1024];
            int count;
            int transfer = 0;
            isSending = true;
            while ((count = fileInputStream.read(buffer)) > 0) {
                transfer = transfer + count;
                dos.write(buffer, 0, count);
                progress.set(index, transfer);
                dataReceived.set(index, setinMB(transfer) + "/" + setinMB(fileSize));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sendAdapter.notifyDataSetChanged();
                    }
                });

            }
            isSending = false;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String setinMB(float recieved) {
        if (recieved < 1000000) {
            float f = recieved / 1000;
            return df.format(f) + "KB";
        } else {
            float f = recieved / 1000000;
            return df.format(f) + "MB";
        }
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("fname", fnames);
    }
}
