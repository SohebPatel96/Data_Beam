package com.example.msp.databeam;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by MSP on 1/6/2017.
 */
public class AudioAdapter extends ArrayAdapter<String> {
    Context c;
    CheckBox[] cBox;
    boolean[] rememberState;
    String[] filename = {};
    LayoutInflater inflater;
    Bitmap[] video_thumbnail = {};


    public AudioAdapter(Context context, String[] filename, Bitmap[] video_thumbnail,CheckBox[] cBox,boolean[] rememberState) {
        super(context, R.layout.audioadapter, filename);
        c = context;
        this.filename = filename;
        this.video_thumbnail = video_thumbnail;
        this.cBox = cBox;
        this.rememberState = rememberState;
    }

    public class ViewHolder {
        TextView fname;
        ImageView thumbnail;
        CheckBox cBox;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.audioadapter, null);
        }
        final ViewHolder holder = new ViewHolder();

        holder.fname = (TextView) convertView.findViewById(R.id.textView);
        holder.thumbnail = (ImageView) convertView.findViewById(R.id.imageView);
        holder.cBox = (CheckBox) convertView.findViewById(R.id.checkBox3);

        holder.cBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Send.socket == null) {
                    Toast.makeText(getContext(), "Cannot Select File, No receiving device found", Toast.LENGTH_SHORT).show();
                    holder.cBox.setChecked(false);
                }
                if (holder.cBox.isChecked()) {
                    rememberState[position] = true;
                    TabbedLayout.fab.show();
                    Send.selectedFile = MyAudio.a.get(position);
                } else {
                    rememberState[position] = false;
                }
            }
        });
        holder.cBox.setChecked(rememberState[position]);
        holder.fname.setText(filename[position]);
        holder.thumbnail.setImageBitmap(video_thumbnail[position]);
        return convertView;
    }

}
