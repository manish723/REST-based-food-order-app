package com.example.manish.food_order.Activities;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.manish.food_order.R;
import com.example.manish.food_order.database.DataSource;
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

/**
 * Created by manish on 3/5/2018.
 */

public class DescriptionActivity extends AppCompatActivity {

    TextView textView;
    TextView textViewPrice;
    TextView textViewIngredient;
    DataSource d;
    ImageView imageView;
    SQLiteDatabase db;
    String i;
    String dishIngredient;
    Double dishPrice;
    String userName=null;
    Button btnContinue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_layout);

     final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//     sharedPref.getString("nameOfUser",userName);

        textView=(TextView)findViewById(R.id.textView);
        i = getIntent().getStringExtra("name");
        textView.setText(i);
        //textView.setText(i);
        if(Profile.getCurrentProfile() != null && AccessToken.getCurrentAccessToken() != null){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            userName = prefs.getString("un",null);
        }


         //userName=getIntent().getStringExtra("username");

        imageView=(ImageView)findViewById(R.id.imageView);
        String img=getIntent().getStringExtra("img");
        String url="http://10.0.2.2/foodorderdb/images"+"/"+img;
        Glide
                .with(this)
                .load(url)
                .into(imageView);

        textViewPrice = (TextView)findViewById(R.id.txtPrice);
        dishPrice = getIntent().getDoubleExtra("price",0);
        textViewPrice.setText(String.valueOf(dishPrice) + " $");

        textViewIngredient = (TextView)findViewById(R.id.txtDescription);
        dishIngredient = getIntent().getStringExtra("desc");
        textViewIngredient.setText(dishIngredient);


//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putString("Name", i);
//        //editor.putString("Desc", dishIngredient);
        //editor.putString("Price",dishPrice);
     //   editor.commit();
    }

    public void ConfirmOrder(View view) {
        if(userName==null)
            userName="Guest";
        int id=0;
        db=openOrCreateDatabase("foodorderdb.db",MODE_PRIVATE,null);

        String craeteStudents="CREATE TABLE IF NOT EXISTS orderTable " + "(id Integer primary key autoincrement,foodname text, userName text, dishPrice real)";
        db.execSQL(craeteStudents);
        ContentValues StudentCv=new ContentValues();
        StudentCv.put("foodname",i);
        StudentCv.put("userName",userName);
        StudentCv.put("dishPrice", dishPrice);
        db.insert("orderTable",null,StudentCv);
        String sql="SELECT * FROM orderTable ORDER BY id DESC LIMIT 1";

        //String browseStudents = "SELECT * FROM students;";
        try{
            Cursor myCursor = db.rawQuery(sql,null);
          //  browseResource.append("Displaying student records...");
            if(myCursor != null){
                myCursor.moveToFirst();
                do{
                     id=myCursor.getInt(0);
                    //  browseResource.append(eachStudentRec);
                }
                while (myCursor.moveToNext());
              //  Toast newToast = Toast.makeText(DescriptionActivity.this, String.valueOf(id),Toast.LENGTH_LONG);
               // newToast.show();
            }
        }catch (Exception ex){
            //browseResource.append(ex.getMessage() + "\n");
        }
        Intent intent = new Intent(DescriptionActivity.this, Confirmation.class);
        intent.putExtra("id", id);
       // LoginManager.getInstance().logOut();
        startActivity(intent);
    }
}
