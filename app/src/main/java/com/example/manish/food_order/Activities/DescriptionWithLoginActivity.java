package com.example.manish.food_order.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.manish.food_order.R;
import com.example.manish.food_order.database.DataSource;
import com.example.manish.food_order.model.DataItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manish on 4/3/2018.
 */

public class DescriptionWithLoginActivity extends AppCompatActivity {

    DataSource mDataSource;
    TextView textView;
    List<DataItems>  listCategorizedItems ;
    ListView listView;
    int timeZone;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_accounts_layout);
        textView=(TextView)findViewById(R.id.textViewName);
        String name = getIntent().getStringExtra("name");
        String timezone = getIntent().getStringExtra("timezone");
        timeZone=Integer.parseInt(timezone);
        textView.setText(name);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("un", name); //InputString: from the EditText
        editor.commit();


//        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("nameOfUser", name);
    }

    public void DispAllItems(View view) {
        startActivity(new Intent(DescriptionWithLoginActivity.this,ItemsActivity.class));
    }

    public void DispSuggestedItems(View view) {
        mDataSource = new DataSource(this);
        mDataSource.open();
        if(timeZone==-7){
         category="Chinese";
        }
        listCategorizedItems = mDataSource.getAllItems(category);
        Intent intent = new Intent(this, ItemsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("mylist", (ArrayList<? extends Parcelable>) listCategorizedItems);
        intent.putExtras(bundle);
        this.startActivity(intent);

    }
}
