package com.example.msp.databeam;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class History extends Fragment {
    DatabaseManager db;
    static String storeName;
    static ListView listView;
    ArrayList<String> filename = new ArrayList<String>();
    ArrayList<String> filesize = new ArrayList<String>();
    HistoryAdapter historyAdapter;
    TextView empty;
    String[] fname;
    String[] fsize;
    Bitmap[] tnail;
    ImageButton[] bin;
    Button clear;
    int counter = 0;


    public History() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DatabaseManager(getContext());
        Cursor res = db.getAllData();
        if (res.getCount() == 0) {
            //ShowMessage
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            filename.add(res.getString(1));
            filesize.add(res.getString(2));
            counter += 1;
        }
        fname = new String[counter];
        fsize = new String[counter];
        tnail = new Bitmap[counter];
        bin = new ImageButton[counter];

        for (int i = 0; i < filename.size(); i++) {
            fname[i] = filename.get(i);
            fsize[i] = filesize.get(i);

//            bin[i].setImageResource(R.drawable.ic_action_trash);
            if (bin[i] == null) {
                Log.d("BIN", "It is null");
            }
            //   tnail[i] = BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_document);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        empty = (TextView) view.findViewById(R.id.textView9);
        clear = (Button) view.findViewById(R.id.button4);
        listView = (ListView) view.findViewById(R.id.listView);
        if (fname == null && fsize == null && tnail == null) {
            empty.setVisibility(View.VISIBLE);

        } else {
            empty.setVisibility(View.GONE);
            historyAdapter = new HistoryAdapter(getContext(), filename, filesize, tnail);
            listView.setAdapter(historyAdapter);
            listView.invalidateViews();

        }
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManager db = new DatabaseManager(getContext());
                db.deleteAll();
                filename.clear();
                historyAdapter.notifyDataSetChanged();
                if (filename.isEmpty()) {
                    empty.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

    //NEW CLASS
    public class HistoryAdapter extends ArrayAdapter<String> {
        Context c;
        ArrayList<String> filesize;
        ArrayList<String> filename;
        Bitmap[] thumbnail;
        LayoutInflater inflater;
        Button btnclear;


        public HistoryAdapter(Context context, ArrayList<String> filename, ArrayList<String> filesize, Bitmap[] thumbnail) {
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
            holder.fname = (TextView) convertView.findViewById(R.id.textView7);
            holder.fsize = (TextView) convertView.findViewById(R.id.textView8);
            holder.tnail = (ImageView) convertView.findViewById(R.id.imageView2);
            holder.bin = (ImageButton) convertView.findViewById(R.id.imageView3);
            holder.fname.setText(filename.get(position));
            holder.fsize.setText(filesize.get(position));
            holder.tnail.setImageBitmap(thumbnail[position]);
            holder.bin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //   Log.d("TAGGG", filename[position] + "");
                    DatabaseManager db = new DatabaseManager(getContext());
                    int deletedrows = db.deleteData(filename.get(position));
                    filename.remove(position);
                    filesize.remove(position);
                    historyAdapter.notifyDataSetChanged();
                    if (deletedrows > 0) {
                        Toast.makeText(getContext(), "Data Deleted", Toast.LENGTH_LONG).show();
                    } else
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

}
