package com.example.msp.databeam;


import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Receive extends Fragment {
    final String FILE_TO_RECEIVED = Environment.getExternalStorageDirectory() + "/DataBeam";
    static String TAG = "pc123";
    static Socket socket = null;
    static String connectedDeviceName;
    File directory;
    InputStream in = null;
    OutputStream out = null;
    File files[];
    File file;
    String fileName;
    int fileSize;
    DataInputStream clientData;
    DataOutputStream dos;
    ArrayList<String> fileNameList = new ArrayList<String>();
    ArrayList<String> dataReceived = new ArrayList<String>();
    ArrayList<ProgressBar> progressBars = new ArrayList<ProgressBar>();
    ArrayList<Integer> progressMax = new ArrayList<>();
    ArrayList<Integer> progress = new ArrayList<>();

    DecimalFormat df = new DecimalFormat("0.00");

    public ListView list;
    Button disconnect;
    TextView connectedDevice;

    int index = -1;


    ReceiveAdapter receiveAdapter;


    public Receive() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receive, container, false);
        MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        connectedDevice = (TextView) view.findViewById(R.id.textView_connectedDevice2);
        connectedDevice.setText("Connected :" + connectedDeviceName);
        disconnect = (Button) view.findViewById(R.id.btn_disconnect2);
        disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeConnection();
            }
        });
        list = (ListView) view.findViewById(R.id.receivedList2);
        receiveAdapter = new ReceiveAdapter(getContext(), fileNameList, progressBars, dataReceived, progressMax, progress);
        list.setAdapter(receiveAdapter);
        new Thread() {
            public void run() {
                try {
                    receive();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return view;
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

    public void receive() throws IOException {
        try {
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        directory = new File(FILE_TO_RECEIVED);
        directory.mkdirs();

        while (!socket.isClosed()) {
            clientData = new DataInputStream(in);
            try {
                fileName = clientData.readUTF();
                fileNameList.add(fileName);
                fileSize = clientData.readInt();
                index = index + 1;
                dataReceived.add("--");
                progressMax.add(fileSize);
                progress.add(0);
                ProgressBar progressBar = new ProgressBar(getContext());
                progressBars.add(progressBar);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        receiveAdapter.notifyDataSetChanged();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

            File f = new File(directory, fileName);
            try {
                out = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            int received = 0;
            byte[] bytes = new byte[64 * 1024];
            int count;
            int x = 0;

            try {
                while ((count = in.read(bytes)) > 0) {

                    x = x + 1;

                    out.write(bytes, 0, count);
                    received = received + count;
                    //  progressBars.get(index).setProgress(received);
                    progress.set(index, received);
                    dataReceived.set(index, setinMB(received) + "/" + setinMB(fileSize));
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            receiveAdapter.notifyDataSetChanged();
                        }
                    });
                    if (received == fileSize) {
                        Log.d(TAG, "Transfer complete");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        try {
            if (clientData != null)
                clientData.close();
            if (in != null)
                in.close();
            if(out != null)
                out.close();
            if (socket != null)
                socket.close();

            Home home = new Home();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, home);
            fragmentTransaction.commit();
            MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
