package com.example.msp.databeam;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by murta on 2/10/2017.
 */

public class SendAdapter extends ArrayAdapter<String> {
    ArrayList<String> fileName = new ArrayList<String>();
    ArrayList<ProgressBar> progressBars = new ArrayList<ProgressBar>();
    ArrayList<String> dataReceived = new ArrayList<String>();
    ArrayList<Integer> progressMax = new ArrayList<>();
    ArrayList<Integer> progress = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public SendAdapter(Context context, ArrayList<String> fileName, ArrayList<ProgressBar> progressBars, ArrayList<String> dataReceived, ArrayList<Integer> progressMax, ArrayList<Integer> progress) {
        super(context, R.layout.send_adapter, fileName);
        this.context = context;
        this.fileName = fileName;
        this.progressBars = progressBars;
        this.dataReceived = dataReceived;
        this.progress = progress;
        this.progressMax = progressMax;
    }

    public class ViewHolder {
        TextView fname;
        ProgressBar prg;
        TextView dReceived;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.send_adapter, null);
        }

        final ViewHolder holder = new ViewHolder();
        holder.fname = (TextView) convertView.findViewById(R.id.pctextView3);
        holder.prg = (ProgressBar) convertView.findViewById(R.id.progressBarAndroid2);
        holder.dReceived = (TextView) convertView.findViewById(R.id.dataReceived3);
        holder.prg.getProgressDrawable().setColorFilter(
                Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);
        holder.prg.setMax(progressMax.get(position));
        holder.prg.setProgress(progress.get(position));
        holder.fname.setText(fileName.get(position));
        holder.dReceived.setText(dataReceived.get(position));

        return convertView;
    }

}
