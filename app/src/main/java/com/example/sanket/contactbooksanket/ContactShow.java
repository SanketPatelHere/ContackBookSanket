package com.example.sanket.contactbooksanket;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
public class ContactShow extends AppCompatActivity {  //for add record
    ImageView img2;
    EditText etFirstName2, etSecondName2, etPhone2;
    Button btnCancel, btnEdit;
    ArrayList<DataPojo> lstShopFood;
    ArrayList<DataPojo> filterList;
    DatabaseManager mDatabase;
    DataPojo dp;
    int n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_show);
        img2 = (ImageView)findViewById(R.id.imgv2);
        etFirstName2 = (EditText) findViewById(R.id.etFirstName2);
        etSecondName2 = (EditText) findViewById(R.id.etSecondName2);
        etPhone2 = (EditText) findViewById(R.id.etPhone2);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        mDatabase = new DatabaseManager(this);
        //addEmployee();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactShow.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEmployee();
                //loadEmployeedFromDatabaseAgain();
                //updateEmployee(dp);
            }
        });
        /*Bundle data = getIntent().getExtras();
        dp = (DataPojo)data.getParcelable("data"); //whole data pass

        n = getIntent().getExtras().getInt("position");
        Log.i("My position", n+"");
        Log.i("My data", dp+"");
        Log.i("My getFirstname", dp.getFirstname()+"");
        Log.i("My getSecondname", dp.getSecondname()+"");
        Log.i("My getPhone", dp.getPhone()+"");

        img2.setImageResource(dp.getImg());
        etFirstName2.setText(dp.getFirstname());
        etSecondName2.setText(dp.getSecondname());
        etPhone2.setText(dp.getPhone());


    */

    }

    public void updateEmployee(final DataPojo user) {
        Log.i("My user = ",user+"");
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.dialog_update_user, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final EditText editTextFirstName = view.findViewById(R.id.etFirstName2);
        final EditText editTextSecondName = view.findViewById(R.id.etSecondName2);
        final EditText editTextPhone = view.findViewById(R.id.etPhone2);

        view.findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstname = etFirstName2.getText().toString().trim();
                String secondname = etSecondName2.getText().toString().trim();
                String phone = etPhone2.getText().toString().trim();
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
                Log.i("My id2 = ",n+"");
                //Log.i("My id = ",user.getId()+"");
                if(mDatabase.updateEmployee(user.getId(), firstname, secondname, phone))
                {
                    Toast.makeText(ContactShow.this, "Employee Updated", Toast.LENGTH_SHORT).show();
                    //loadEmployeedFromDatabaseAgain();
                }
                alertDialog.dismiss();
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

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy--mm-dd hh:mm:ss");
        String joiningdate = sdf.format(cal.getTime());
        if(mDatabase.addEmployee(firstname, secondname, phone, joiningdate))
        {
            Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ContactShow.this, MainActivity.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Could not add Employee", Toast.LENGTH_SHORT).show();
        }


    }
}
