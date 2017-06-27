package com.rbricks.appsharing.concept.listviews;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rbricks.appsharing.R;

public class MyPerformanceArrayAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] names;

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }

    public MyPerformanceArrayAdapter(Activity context, String[] names) {
        super(context, R.layout.rowlayout, names);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {  // to support multiple viewTypes we check ViewHolder of tag current same as the names[position] ViewHolder value or not.
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.rowlayout, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();  // use viewholder to bypass the findviewById calls.
            viewHolder.text = (TextView) rowView.findViewById(R.id.TextView01);
            viewHolder.image = (ImageView) rowView
                    .findViewById(R.id.ImageView01);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = names[position];
        holder.text.setText(s);
        if (s.startsWith("Windows7") || s.startsWith("iPhone")
                || s.startsWith("Solaris")) {
            holder.image.setImageResource(R.drawable.ic_launcher);
        } else {
            holder.image.setImageResource(R.drawable.notes_icon);
        }

        return rowView;
    }
}