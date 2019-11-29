package com.example.game;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ImageAdapter extends BaseAdapter {
    private Context context;
    private int length;
    private int background;

    // Gets the context so it can be used later
    public ImageAdapter(Context c, int numCards, int background) {
        this.context = c;
        this.background = background;
        this.length = numCards;
    }

    // Total number of things contained within the adapter
    public int getCount() {
        return length;
    }

    // Required for structure, not used in code.
    public Object getItem(int position) {
        return null;
    }

    // Required for structure, not used in code
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position,
                        View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null) {
            imageView = new ImageView(this.context);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(background);
        imageView.setId(position);
        imageView.setTag("Not Flipped");


        return imageView;
    }
}