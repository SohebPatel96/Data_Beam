package com.example.msp.databeam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by murta on 12/3/2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {
    ArrayList<Bitmap> tnail = new ArrayList<Bitmap>();
    boolean[] rememberState = {};
    ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
    ArrayList<String> fileName = new ArrayList<String>();
    ArrayList<String> fileSize = new ArrayList<String>();
    ArrayList<String> fileDate = new ArrayList<String>();
    Context c;
    LayoutInflater inflater;

    public CustomAdapter(Context context, ArrayList<String> fileName, ArrayList<String> fileSize, ArrayList<String> fileDate, ArrayList<Bitmap> tnail, ArrayList<CheckBox> checkBoxes, boolean[] rememberState) {
        super(context, R.layout.customlistview, fileName);
        this.c = context;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileDate = fileDate;
        this.tnail = tnail;
        this.checkBoxes = checkBoxes;
        this.rememberState = rememberState;

    }

    public class ViewHolder {
        TextView fname;
        TextView fsize;
        TextView fdate;
        ImageView fimg;
        CheckBox cbox;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.customlistview, null);
        }

        final ViewHolder holder = new ViewHolder();

        holder.fname = (TextView) convertView.findViewById(R.id.filename);
        holder.fsize = (TextView) convertView.findViewById(R.id.filesize);
        holder.fdate = (TextView) convertView.findViewById(R.id.filedate);
        holder.fimg = (ImageView) convertView.findViewById(R.id.imageView1);
        holder.cbox = (CheckBox) convertView.findViewById(R.id.checkbox);

        holder.fimg.setImageBitmap(tnail.get(position));
        holder.fname.setText(fileName.get(position));
        holder.fsize.setText(fileSize.get(position));
        holder.fdate.setText(fileDate.get(position));
        holder.cbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.cbox.isChecked()) {
                    rememberState[position] = true;
                    if (Send.socket == null) {
                        Toast.makeText(getContext(), "Cannot Select File, No receiving device found", Toast.LENGTH_SHORT).show();
                        TabbedLayout.fab.setVisibility(View.INVISIBLE);
                        holder.cbox.setChecked(false);
                    }
                    if (MyFiles.files[position].isDirectory()) {
                        Toast.makeText(getContext(), "Cannot Select Directory", Toast.LENGTH_SHORT).show();
                        TabbedLayout.fab.setVisibility(View.INVISIBLE);
                        holder.cbox.setChecked(false);
                    } else {
                        Log.d("customadapter123", "this is True:" + position);
                        if(Send.socket!=null)
                            TabbedLayout.fab.show();

                        Send.selectedFile = MyFiles.file.get(position);
                    }
                } else {
                    rememberState[position] = false;
                    //  holder.cbox.setChecked(rememberState[position]);
                    //       MyFiles.dialog_ListView.getChildAt(position).setBackgroundColor(Color.WHITE);
                    Log.d("customadapter123", "this is False:" + position);
                    TabbedLayout.fab.hide();
                    //MainActivity.fab.setVisibility(View.INVISIBLE);

                }
                //   MyFiles.dialog_ListView.getChildAt(position).setBackgroundColor(Color.GRAY);
            }
        });
        holder.cbox.setChecked(rememberState[position]);
        return convertView;
    }
}
