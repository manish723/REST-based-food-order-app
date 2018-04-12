package com.example.manish.food_order.Activities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manish.food_order.R;
import com.example.manish.food_order.model.DataItems;
import java.util.List;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
/**
 * Created by manish on 4/2/2018.
 */


public class CustomAdaptor extends BaseAdapter {

    Activity Context;
    List<DataItems> listItems;



    public CustomAdaptor(Activity Context, List<DataItems> listItems) {
        this.Context = Context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = Context.getLayoutInflater();
            view = inflater.inflate(R.layout.layout, null);
        }

        TextView textView = (TextView) view.findViewById(R.id.textViewItems);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewItems);

        textView.setText(listItems.get(i).getItemName());
        String url="http://10.0.2.2/foodorderdb/images"+"/"+listItems.get(i).getImage();
        Glide
                .with(Context)
                .load(url)
                .into(imageView);
        return view;
    }
}
