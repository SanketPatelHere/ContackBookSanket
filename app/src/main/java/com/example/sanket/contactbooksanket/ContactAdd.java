package com.example.sanket.contactbooksanket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
public class ContactAdd extends AppCompatActivity {  //for add record
    ImageView img2;
    EditText etFirstName2, etSecondName2, etPhone2;
    Button btnCancel, btnEdit;
    ArrayList<DataPojo> lstShopFood;
    ArrayList<DataPojo> filterList;
    DatabaseManager mDatabase;
    DataPojo dp;
    ArrayList<DataPojo> ddd;
    int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);
        img2 = (ImageView)findViewById(R.id.imgv2);
        etFirstName2 = (EditText) findViewById(R.id.etFirstName2);
        etSecondName2 = (EditText) findViewById(R.id.etSecondName2);
        etPhone2 = (EditText) findViewById(R.id.etPhone2);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        mDatabase = new DatabaseManager(this);
        ddd = new ArrayList<>();
        ddd = getIntent().getParcelableArrayListExtra("array");
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactAdd.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEmployee();

            }
        });


    }


    public void addEmployee()
    {
        String firstname = etFirstName2.getText().toString().trim();
        String secondname = etSecondName2.getText().toString().trim();
        String phone = etPhone2.getText().toString().trim();


        /*String firstname = "aaaa";
        String secondname = "bbbb";
        String phone = "9888525789";*/

        Log.i("My firstname = ",firstname);
        Log.i("My secondname = ",secondname);
        Log.i("My phone = ",phone);

        if(firstname.isEmpty())
        {
            etFirstName2.setError("Name can't be empty");
            etFirstName2.requestFocus();
            return;
        }
        if (secondname.isEmpty()) {
            etSecondName2.setError("Salary can't be empty");
            etSecondName2.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            etPhone2.setError("Salary can't be empty");
            etPhone2.requestFocus();
            return;
        }
        if(phone.length()!=10)
        {
            etPhone2.setError("Enter valid phone number - 0-10");
            etPhone2.requestFocus();
            return;
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy--mm-dd hh:mm:ss");
        String joiningdate = sdf.format(cal.getTime());
        //int iddd = mDatabase.addEmployee(firstname, secondname, phone, joiningdate);
        //Log.i("My added = ",iddd+"");
       // if(iddd!=-1)
        if(mDatabase.addEmployee(firstname, secondname, phone, joiningdate))
        {

            Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_SHORT).show();
            finish();



            /*Intent i = new Intent(ContactAdd.this, MainActivity.class);
            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            //finish();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();  //main for close all activity = only main activity present
            }*/
            //for not open activity again

           /* int iddd = mDatabase.getLastId();
            Log.i("My added = ",iddd+""+firstname+secondname+phone);
            Log.i("My list = ",ddd.toString());
            Log.i("My list before size = ",ddd.size()+"");

            ddd.add(new DataPojo(iddd, R.drawable.admin, firstname, secondname, phone));
            Log.i("My list after size = ",ddd.size()+"");
            Intent returnIntent = new Intent();
            returnIntent.putExtra("newarray",ddd);
            setResult(RESULT_OK, returnIntent);
            finish();*/
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Could not add User", Toast.LENGTH_SHORT).show();
        }


    }


}
