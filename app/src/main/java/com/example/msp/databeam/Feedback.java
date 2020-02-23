package com.example.msp.databeam;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class Feedback extends Fragment {


    public Feedback() {
        // Required empty public constructor
    }

    Button btnSend;

    EditText txt1,txt2,txt3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, null);
        btnSend = (Button) view.findViewById(R.id.button3);

        txt2 = (EditText) view.findViewById(R.id.editText2);
        txt3 = (EditText) view.findViewById(R.id.editText6);
        btnSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String[] to = {"murtaz1996@gmail.com","msohebp@gmail.com"};
                String sub = txt2.getText().toString();
                String body = txt3.getText().toString();
                sendEmail(to, sub, body);
            }


        });
        return view;
    }

    private void sendEmail(String[] to, String sub,
                           String body) {
        // TODO Auto-generated method stub
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));

        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, sub);

        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        emailIntent.setType("message/rfc822");

        startActivity(Intent.createChooser(emailIntent, "Email"));
    }

}