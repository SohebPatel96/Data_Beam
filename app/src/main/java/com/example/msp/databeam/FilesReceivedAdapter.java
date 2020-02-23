package com.example.msp.databeam;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by murta on 1/15/2017.
 */

public class FilesReceivedAdapter extends ArrayAdapter<String> {
    Bitmap[] tnail = {};
    String[] fileName = {};
    String[] fileSize = {};
    String[] fileDate = {};
    Context c;
    LayoutInflater inflater;

    public FilesReceivedAdapter(Context context, String[] fileName, String[] fileSize, String[] fileDate, Bitmap[] tnail) {
       super(context, R.layout.receivedlist,fileName);
        this.c = context;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileDate = fileDate;
        this.tnail = tnail;


    }

    public class ViewHolder {
        TextView fname;
        TextView fsize;
        TextView fdate;
        ImageView fimg;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.receivedlist, null);
        }

       ViewHolder holder = new ViewHolder();

        holder.fname = (TextView) convertView.findViewById(R.id.filename);
        holder.fsize = (TextView) convertView.findViewById(R.id.filesize);
        holder.fdate = (TextView) convertView.findViewById(R.id.filedate);
        holder.fimg = (ImageView) convertView.findViewById(R.id.imageView1);


        holder.fimg.setImageBitmap(tnail[position]);
        holder.fname.setText(fileName[position]);
        holder.fsize.setText(fileSize[position]);
        holder.fdate.setText(fileDate[position]);

        return convertView;
    }
}
