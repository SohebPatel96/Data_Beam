package com.example.msp.databeam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.Socket;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetConnectionPC extends Fragment {
    Button Okay;
    static EditText IP;
    TextView Connected;
    static File file;


    public SetConnectionPC() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_connection_pc, container, false);
        IP = (EditText) view.findViewById(R.id.getIP2);
        Okay = (Button) view.findViewById(R.id.Okay2);
        Connected = (TextView) view.findViewById(R.id.textViewConnected);

        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        establishConnection();
                    }
                }.start();
            }
        });
        return view;
    }

    public void establishConnection() {
        try {
            ConnectPC.socket = new Socket(IP.getText().toString(), 7000);
            ConnectPC.connectedDeviceName = ConnectPC.socket.getInetAddress().getHostName();
            ConnectPC connectPC = new ConnectPC();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, connectPC);
            fragmentTransaction.commit();
        } catch (IOException e) {
            try {
                if (ConnectPC.socket != null)
                    ConnectPC.socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (IP.getText() == null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Connected.setText("Field is empty");
                    }
                });

            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Connected.setText("Incorrect IP address");
                    }
                });

            }
        }
    }


}
