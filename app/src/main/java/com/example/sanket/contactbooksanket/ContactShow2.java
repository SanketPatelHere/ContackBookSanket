package com.example.sanket.contactbooksanket;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ContactShow2 extends AppCompatActivity {
    DataPojo dp;
    TextView tvName, tvPhone;
    ImageView imgvShow;
    String firstnameShow, secondnameShow, phoneShow;
    int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_show2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.rgb(10,20,30));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvName = (TextView)findViewById(R.id.tvName);
        tvPhone = (TextView)findViewById(R.id.tvPhone);
        imgvShow = (ImageView) findViewById(R.id.imgvShow);

        Bundle data = getIntent().getExtras();
        dp = (DataPojo)data.getParcelable("data"); //whole data pass

        n = getIntent().getExtras().getInt("position");
        tvName.setText("Name = " +dp.getFirstname()+" "+dp.getSecondname());
        tvPhone.setText("Phone Number = "+dp.getPhone());
        imgvShow.setImageResource(R.drawable.admin);
        firstnameShow = dp.getFirstname();
        secondnameShow = dp.getSecondname();
        phoneShow = dp.getPhone();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                Intent i = new Intent(ContactShow2.this, EditUser.class);
                i.putExtra("position", n);
                i.putExtra("data", new DataPojo(dp.getId(), R.drawable.admin, firstnameShow+"", secondnameShow+"", phoneShow+""));
                //startActivity(i);
                startActivityForResult(i,1);
                //finish();

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            Log.i("My ContactShow2 result=",data+"");
            if(resultCode==RESULT_OK)
            {
                Log.i("My result cancel = ",resultCode+"");
                //Bundle extras = getIntent().getExtras();
                //String newname = extras.getString("firstname");
                //String newname = getIntent().getStringExtra("firstname");
                String newfirstname = data.getStringExtra("firstname");
                String newdsecondname = data.getStringExtra("secondname");
                String newphone = data.getStringExtra("phone");

                tvName.setText("Name = " + newfirstname + " " + newdsecondname);
                tvPhone.setText("Phone Number = " + newphone);
            }
            else
            {
                Log.i("My result cancel = ",resultCode+"");
            }
        }

    }
}
