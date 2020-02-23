package com.example.msp.databeam;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.net.ServerSocket;

import static com.example.msp.databeam.Send.serverSocket;
import static com.example.msp.databeam.Send.socket;


/**
 * A simple {@link Fragment} subclass.
 */
public class SendConfirm extends Fragment {

    Button Okay, Choose;
    static EditText IP;
    TextView Connected;
    static File file;
    SharedPreferences preferences;

    public SendConfirm() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = this.getActivity().getSharedPreferences("sadas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_confirm, container, false);
        try {
            Send.serverSocket = new ServerSocket(7000);
            new Thread() {
                public void run() {
                    try {
                        Send.socket = Send.serverSocket.accept();

                        Send send = new Send();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, send);
                        fragmentTransaction.commit();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }


       /* IP = (EditText) view.findViewById(R.id.getIP);
        Okay = (Button) view.findViewById(R.id.Okay);
        Choose = (Button) view.findViewById(R.id.Choose);
        Choose.setVisibility(View.GONE);
        Connected = (TextView) view.findViewById(R.id.textView13);
        Connected.setVisibility(View.GONE);
        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connected.setVisibility(View.VISIBLE);
                Choose.setVisibility(View.VISIBLE);
            }
        });
        Choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser fileChooser = new FileChooser();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fileChooser);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
    public static String getIP()
    {
        return IP.getText().toString();
    }*/

}
