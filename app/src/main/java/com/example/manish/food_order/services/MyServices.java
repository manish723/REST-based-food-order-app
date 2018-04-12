package com.example.manish.food_order.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.manish.food_order.model.DataItems;
import com.example.manish.food_order.utils.HttpConnection;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

/**
 * Created by manish on 2/28/2018.
 */

public class MyServices extends IntentService {

    public static final String msg="MyMessage";
    public static final String payload="MyPayload";//this is acting like a key of data or value
    DataItems[] dataItems;
    public MyServices(){
        super("MyServices");
    }
    List<DataItems> listItems;

    //background thread and is sending the data asynch as teh data from http her is being sent sync
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Uri uri=intent.getData();
        String response;

        try {
            response = HttpConnection.downloadUrl(uri.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Gson gson=new Gson();
        dataItems=gson.fromJson(response,DataItems[].class);//convert json to java object

        Intent messageIntent = new Intent(msg);
        messageIntent.putExtra("DataItems", dataItems);
        LocalBroadcastManager manager =
                LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);


    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
