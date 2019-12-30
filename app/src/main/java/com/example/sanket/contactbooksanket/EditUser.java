package com.example.sanket.contactbooksanket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class EditUser extends AppCompatActivity {
    EditText etFirstName3, etSecondName3, etPhone3;
    Button btnEdit3, btnCancel3, btnDelete3;
    DataPojo dp;
    DatabaseManager mDatabase;
    String firstname, secondname, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        etFirstName3 = (EditText)findViewById(R.id.etFirstName3);
        etSecondName3 = (EditText)findViewById(R.id.etSecondName3);
        etPhone3 = (EditText)findViewById(R.id.etPhone3);
        btnCancel3 = (Button) findViewById(R.id.btnCancel3);
        btnEdit3 = (Button) findViewById(R.id.btnEdit3);
        btnDelete3 = (Button) findViewById(R.id.btnDelete3);
        mDatabase = new DatabaseManager(this);



        Bundle data = getIntent().getExtras();
        dp = (DataPojo)data.getParcelable("data"); //whole data pass

        int n = getIntent().getExtras().getInt("position");
        Log.i("My position", n+"");
        Log.i("My data", dp+"");
        Log.i("My getId", dp.getId()+"");
        Log.i("My Image", dp.getImg()+"");
        Log.i("My getFirstname", dp.getFirstname()+"");
        Log.i("My getSecondname", dp.getSecondname()+"");
        Log.i("My getPhone", dp.getPhone()+"");


        etFirstName3.setText(dp.getFirstname());
        etFirstName3.setSelection(etFirstName3.getText().length());  //place cursor at end
        etSecondName3.setText(dp.getSecondname());
        etPhone3.setText(dp.getPhone());


        //Log.i("My id2 = ",n+"");
        //Log.i("My id = ",user.getId()+"");
        btnEdit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String firtname2 = getIntent().getExtras().getString("")
                firstname = etFirstName3.getText().toString().trim();
                secondname = etSecondName3.getText().toString().trim();
                phone = etPhone3.getText().toString().trim();

                if(firstname.isEmpty())
                {
                    etFirstName3.setError("Firstname can't be empty");
                    etFirstName3.requestFocus();
                    return;
                }
                if (secondname.isEmpty()) {
                    etSecondName3.setError("Secondname can't be empty");
                    etSecondName3.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    etPhone3.setError("Phone can't be empty");
                    etPhone3.requestFocus();
                    return;
                }
                if(mDatabase.updateEmployee(dp.getId(), firstname, secondname, phone))
                {
                    Toast.makeText(EditUser.this, "User Updated", Toast.LENGTH_SHORT).show();
                    //loadEmployeedFromDatabaseAgain();
                    Log.i("My Updated = ","done");
                    Intent i = new Intent(EditUser.this, MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Log.i("My Updated = ","not done");
                }
            }
        });
        btnCancel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditUser.this, MainActivity.class);
                startActivity(i);
            }
        });
        btnDelete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("My id for delete = ",dp.getId()+"");
                //deleteEmployee(position, dp);  //for delete
                //notifyDataSetChanged();
                int delete = mDatabase.deleteUser(dp.getId());
                if(delete!=-1)
                {
                    Toast.makeText(EditUser.this, "User Deleted", Toast.LENGTH_SHORT).show();
                    Log.i("My Deleted = ","done");
                    Intent i = new Intent(EditUser.this, MainActivity.class);
                    startActivity(i);
                }
                else
                {
                    Log.i("My Deleted = ","not done");
                }

            }
        });
    }


}
