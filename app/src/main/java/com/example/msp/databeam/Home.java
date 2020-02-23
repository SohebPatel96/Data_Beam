package com.example.msp.databeam;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.zip.Inflater;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements View.OnClickListener {
    Button send, receive;
    ImageButton mfile, mConnectPC;


    public Home() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, null);
        send = (Button) view.findViewById(R.id.send);
        receive = (Button) view.findViewById(R.id.receive);
        mfile = (ImageButton) view.findViewById(R.id.imageButton1);
        mConnectPC = (ImageButton) view.findViewById(R.id.imageButton2);
        mfile.setOnClickListener(this);
        mConnectPC.setOnClickListener(this);
        send.setOnClickListener(this);
        receive.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.send) {
            SendConfirm send = new SendConfirm();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, send);
            fragmentTransaction.commit();
        }
        if (id == R.id.receive) {
            SetConnectionReceive setConnectionReceive = new SetConnectionReceive();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, setConnectionReceive);
            fragmentTransaction.commit();
        }
        if (id == R.id.imageButton1) {
            if(FilesReceived.check.exists()){
                FilesReceived filesReceived = new FilesReceived();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, filesReceived);
                fragmentTransaction.commit();
            }
            else {
                Toast.makeText(getContext(),"No files received",Toast.LENGTH_SHORT).show();

            }
        }
        if(id == R.id.imageButton2){
            SetConnectionPC setConnectionPC = new SetConnectionPC();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, setConnectionPC).addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}