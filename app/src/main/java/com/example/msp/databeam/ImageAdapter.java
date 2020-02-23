
package com.example.msp.databeam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import static com.example.msp.databeam.Images.a;
import static java.security.AccessController.getContext;

/**
 * Created by MSP on 1/4/2017.
 */
public class ImageAdapter extends BaseAdapter {
    Context c;
    LayoutInflater inflater;
    GridView gv;
    ArrayList<File> list;

    ImageAdapter(Context c, GridView gv, ArrayList<File> list) {
        this.gv = gv;
        this.list = list;
        this.c = c;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.size();
    }

    public class ViewHolder {
        CheckBox cbox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.single_grid, parent, false);
        } else {
            v = convertView;
        }


        final ViewHolder holder = new ViewHolder();
        holder.cbox = (CheckBox) v.findViewById(R.id.checkImage);
        holder.cbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.cbox.isChecked()) {
                    if (Send.socket == null) {
                        Toast.makeText(c, "Cannot Select File, No receiving device found", Toast.LENGTH_SHORT).show();
                        holder.cbox.setChecked(false);
                    } else {
                        TabbedLayout.fab.show();
                        Send.selectedFile = Images.a.get(position);
                        Log.d("checkornot", "Checked " + position);
                    }
                }
            }
        });
        ImageView iv = (ImageView) v.findViewById(R.id.gridImg);
        Picasso.with(c).load(new File(Images.a.get(position).getPath()))
                .resize(96, 96).centerCrop().into(iv);
        return v;
    }
}