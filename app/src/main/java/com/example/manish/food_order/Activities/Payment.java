package com.example.manish.food_order.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;
import com.example.manish.food_order.R;
import com.facebook.login.LoginManager;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        CardForm cardform = (CardForm) findViewById(R.id.cardform);
        TextView textDes = (TextView) findViewById(R.id.payment_amount);
        Button btnPay = (Button) findViewById(R.id.btn_pay);

        textDes.setText("$200");
        btnPay.setText(String.format("Payer %s",textDes.getText()));

        cardform.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {

                Toast.makeText(Payment.this, "Name : " + card.getName()+ " | CVC : " + card.getCVC(),Toast.LENGTH_LONG).show();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(Payment.this, MainActivity.class));
            }
        });
    }
}
