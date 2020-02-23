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
public class SetConnectionReceive extends Fragment {
    Button okay;
    static EditText ip;
    TextView connected;
    static File file;


    public SetConnectionReceive() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_connection_receive, container, false);
        okay = (Button) view.findViewById(R.id.Okay3);
        ip = (EditText) view.findViewById(R.id.getIP3);
        connected = (TextView) view.findViewById(R.id.textViewConnected2);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            Receive.socket = new Socket(ip.getText().toString(), 7000);
            Receive.connectedDeviceName = Receive.socket.getInetAddress().getHostName();
            Receive receive = new Receive();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, receive);
            fragmentTransaction.commit();
        } catch (IOException e) {
            try {
                if (Receive.socket != null)
                    Receive.socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (ip.getText().equals("")) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connected.setText("Field is empty");
                    }
                });

            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        connected.setText("Incorrect IP address");
                    }
                });

            }
        }
    }

}
