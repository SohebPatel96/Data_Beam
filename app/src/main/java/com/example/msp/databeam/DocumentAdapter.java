package com.example.msp.databeam;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
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
 * Created by MSP on 5/3/2017.
 */

public class DocumentAdapter extends ArrayAdapter<String> {
    Context c;
    CheckBox[] cBox;
    boolean[] rememberState;
    String[] filename = {};
    LayoutInflater inflater;
    Bitmap[] video_thumbnail = {};

    public DocumentAdapter(Context context, String[] filename, Bitmap[] video_thumbnail,CheckBox[] cBox,boolean[] rememberState) {
        super(context, R.layout.documentadapter, filename);
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
        //   ImageView thumbnail;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.documentadapter, null);
        }
        final ViewHolder holder = new ViewHolder();
        holder.fname = (TextView) convertView.findViewById(R.id.videoname6);
        holder.thumbnail = (ImageView) convertView.findViewById(R.id.imageVideo6);
        holder.cBox = (CheckBox) convertView.findViewById(R.id.checkBox6);
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
                    Send.selectedFile = MyDocument.docs.get(position);
                } else {
                    rememberState[position] = false;
                    Log.d("customadapter123", "this is False:" + position);
                }
            }
        });
        holder.cBox.setChecked(rememberState[position]);
        holder.fname.setText(filename[position]);
        holder.thumbnail.setImageBitmap(video_thumbnail[position]);
        return convertView;
    }
}
