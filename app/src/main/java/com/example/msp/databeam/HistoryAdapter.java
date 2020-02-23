package com.example.msp.databeam;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by murta on 1/11/2017.
 */


    public class HistoryAdapter extends ArrayAdapter<String> {
        Context c;
       String[] filesize = {};
        String[] filename = {};
        Bitmap[] thumbnail;
        LayoutInflater inflater;
        Button btnclear;



    public HistoryAdapter(Context context, String[] filename, String[] filesize, Bitmap[] thumbnail) {
            super(context, R.layout.historyadapter, filename);
            c = context;
            this.filename = filename;
            this.filesize = filesize;
            this.thumbnail = thumbnail;

        }

        public class ViewHolder {
            TextView fname;
            TextView fsize;
            ImageView tnail;
            Button clear;
            ImageButton bin;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.historyadapter, null);
            }
            final ViewHolder holder = new ViewHolder();
            btnclear = (Button) convertView.findViewById(R.id.button4);

            holder.fname = (TextView) convertView.findViewById(R.id.textView7);
            holder.fsize = (TextView) convertView.findViewById(R.id.textView8);
            holder.tnail = (ImageView) convertView.findViewById(R.id.imageView2);
            holder.bin = (ImageButton) convertView.findViewById(R.id.imageView3);
            holder.fname.setText(filename[position]);
            holder.fsize.setText(filesize[position]);
            holder.tnail.setImageBitmap(thumbnail[position]);
            holder.bin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("TAGGG",filename[position]+"");
                    DatabaseManager db = new DatabaseManager(getContext());
                    int deletedrows = db.deleteData(filename[position]);


                    if(deletedrows >=0 )
                    {
                        Toast.makeText(getContext(), "Data Deleted", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(getContext(), "Data Not Deleted", Toast.LENGTH_LONG).show();
                }
            });
            return convertView;
        }

   /*public void deleteData()
    {
        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedrows =
            }
        });
    }*/
    }

