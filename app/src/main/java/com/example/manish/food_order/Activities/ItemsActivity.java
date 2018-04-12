package com.example.manish.food_order.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manish.food_order.R;
import com.example.manish.food_order.database.DataSource;
import com.example.manish.food_order.model.DataItems;
import com.example.manish.food_order.services.MyServices;
import com.example.manish.food_order.utils.NetworkHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by manish on 4/2/2018.
 */

public class ItemsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<DataItems> listItems;
    List<DataItems> listItems1;
    Button button;
    public boolean network;
    ListView listView;
    String[] mCategories;
    DataSource mDataSource;
    List<DataItems> listItems2;

    CustomAdaptor mItemAdapter;
    TextView textView;


    //private final String JSON_URL= "file:///D:/DouglasCollege/CSIS3175/Project%20Copy/FoodOrderDB/a.json";
    private final String JSON_URL="http://10.0.2.2/foodorderdb/a.json";

    private BroadcastReceiver broadcastReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(listItems2==null){
                DataItems[] dataItems;
                dataItems=(DataItems[])intent.getParcelableArrayExtra("DataItems");
                listItems= Arrays.asList(dataItems);
                displayDataItems();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_items);

        listView=(ListView)findViewById(R.id.listViewItems);
        Bundle bundle = getIntent().getExtras();
        ArrayList<DataItems> listItems2 = bundle.getParcelableArrayList("mylist");
        if(listItems2!=null)
        a(listItems2);

        if(listItems2==null){

            network=NetworkHelper.hasNetworkAccess(this);


            if(network){
                Intent intent = new Intent(this, MyServices.class);
                intent.setData(Uri.parse(JSON_URL));
                startService(intent);

            }
            else {
                Toast.makeText(ItemsActivity.this,"Internet Not Available",
                        Toast.LENGTH_SHORT).show();
            }



            LocalBroadcastManager.getInstance(ItemsActivity.this).registerReceiver(broadcastReceiver,new IntentFilter(MyServices.msg));

        }

    }

    private void displayDataItems() {


        if (listItems != null) {
             mDataSource = new DataSource(this);
             mDataSource.open();
             mDataSource.seedDatabase(listItems);
             listItems1 = mDataSource.getAllItems(null);
             listView.setAdapter(new CustomAdaptor(this, listItems1));
        }

        try{
            listView.setOnItemClickListener(this);
        }

        catch (Exception e){
            Log.e("err3",e.getMessage());
        }
    }

    private void a(List<DataItems> listItems2){

        CustomAdaptor customAdaptor=new CustomAdaptor(this, listItems2);

        listView.setAdapter(customAdaptor);
        listView.setOnItemClickListener(this);
        listItems1=listItems2;
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(ItemsActivity.this).unregisterReceiver(broadcastReceiver);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent intent = new Intent(this, DescriptionActivity.class);
            intent.putExtra("name", listItems1.get(i).getItemName());
            intent.putExtra("img",listItems1.get(i).getImage());
            intent.putExtra("price",listItems1.get(i).getPrice());
            intent.putExtra("desc",listItems1.get(i).getDescription());
            startActivity(intent);
    }
}



