package com.example.manish.food_order.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.manish.food_order.R;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

public class Confirmation extends AppCompatActivity {

    String userName;
    SQLiteDatabase db;
    String foodname;
    double dishprice;
    Button btnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation2);
        btnContinue = (Button)findViewById(R.id.buttonContinue);
        int id=getIntent().getIntExtra("id",0);
        db=openOrCreateDatabase("foodorderdb.db",MODE_PRIVATE,null);

        String browseGrade = "SELECT * FROM orderTable";
        try{
            Cursor myCursor = db.rawQuery(browseGrade,null);
            //browseResource.append("Displaying student records...");
            if(myCursor != null){
                myCursor.moveToFirst();
                do{
                   if(id==myCursor.getInt(0)){
                       foodname=myCursor.getString(1);
                       userName=myCursor.getString(2);
                       dishprice=myCursor.getDouble(3);
                   }
                }while (myCursor.moveToNext());
            }
        }catch (Exception ex){
            //browseResource.append(ex.getMessage() + "\n");
        }

      //  final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);


        //String nameOfDish = sharedPref.getString("Name", null);
        //userName = sharedPref.getString("nameOfUser",null);


        TextView txtTile2 = (TextView)findViewById(R.id.txtTitle2);
        txtTile2.setText("You have ordered  " + foodname+ "." + "\n\n" +"Thank You For Your Order "+ userName + "." + "\n\n" +
                "Your Total Bill Amount is "+ dishprice+"\n\n"+"GO BACK TO ORDER AGAIN");

        Button goToMenu = (Button)findViewById(R.id.btnGoMain);

        goToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(Confirmation.this, MainActivity.class));
            }
        });

        Button btnPayment = (Button)findViewById(R.id.btnPayment);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Confirmation.this, Payment.class));
            }
        });
    }
}
