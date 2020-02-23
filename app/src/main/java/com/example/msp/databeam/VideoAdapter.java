package com.example.msp.databeam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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

/**
 * Created by MSP on 1/4/2017.
 */

public class VideoAdapter extends ArrayAdapter<String> {
    Context c;
    CheckBox[] cBox;
    boolean[] rememberState;
    String[] filename = {};
    LayoutInflater inflater;
    Bitmap[] video_thumbnail = {};

    public VideoAdapter(Context context, String[] filename, Bitmap[] video_thumbnail,CheckBox[] cBox,boolean[] rememberState) {
        super(context, R.layout.videoadapter, filename);
        c = context;
        this.filename = filename;
        this.video_thumbnail = video_thumbnail;
        this.cBox = cBox;
        this.rememberState = rememberState;
        //  this.video_thumbnail = video_thumbnail;
    }

    public class ViewHolder {
        TextView fname;
        ImageView thumbnail;
        CheckBox cBox;
        //   ImageView thumbnail;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.videoadapter, null);
        }
        final ViewHolder holder = new ViewHolder();

        holder.fname = (TextView) convertView.findViewById(R.id.videoname);
        holder.thumbnail = (ImageView) convertView.findViewById(R.id.imageVideo);
        holder.cBox = (CheckBox) convertView.findViewById(R.id.checkBox);
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
                    Send.selectedFile = MyVideo.a.get(position);
                } else {
                    rememberState[position] = false;
                    //  holder.cbox.setChecked(rememberState[position]);
                    //MyFiles.dialog_ListView.getChildAt(position).setBackgroundColor(Color.WHITE);
                    Log.d("customadapter123", "this is False:" + position);
                }
                //MyFiles.dialog_ListView.getChildAt(position).setBackgroundColor(Color.GRAY);
            }
        });
        holder.cBox.setChecked(rememberState[position]);
        holder.fname.setText(filename[position]);
        holder.thumbnail.setImageBitmap(video_thumbnail[position]);
        //  holder.thumbnail.setImageBitmap(video_thumbnail[position]);
        return convertView;
    }
}