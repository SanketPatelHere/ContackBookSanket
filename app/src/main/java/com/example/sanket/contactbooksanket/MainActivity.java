package com.example.sanket.contactbooksanket;
//import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ActionBar actionBar;

    RecyclerView rv;
    ArrayList<DataPojo> lst;
    ArrayList<DataPojo> filterList;
    DataPojo dp;
    CustomAdapter fa;
    MyClickListener listener;

    MenuItem search;
    SearchView searchView;
    //EditText etsearch;
    DatabaseManager mDatabase;
    FloatingActionButton btnAdd;

    CustomAdapter adapter;

    //for edit
    AlertDialog.Builder builder;
    AlertDialog dialog;
    String firstname = "", secondname = "", phone = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        rv = (RecyclerView) findViewById(R.id.rv);
        btnAdd = (FloatingActionButton)findViewById(R.id.btnAdd);
        lst = new ArrayList<>();
        /*lst.add(new DataPojo(R.drawable.ic_launcher_background, "aaaa", "patel", "5555"));
        lst.add(new DataPojo(R.drawable.ic_launcher_foreground, "bbbb","kanabi", "6666"));
        lst.add(new DataPojo(R.drawable.ic_launcher_background, "cccc","patel", "7777"));
        lst.add(new DataPojo(R.drawable.ic_launcher_foreground, "dddd","kanabi", "8888"));
        */
        mDatabase = new DatabaseManager(this);




        listener = new MyClickListener() {
            @Override
            public void myOnClick(int position) {
                //Toast.makeText(getApplicationContext(), "MyOnClick = " + position, Toast.LENGTH_SHORT).show();
                Log.i("My activity position = ",position+"");

            }

            @Override
            public void myOnClick(int position, int imgUser, String userName, String userPhone) {

            }

            @Override
            public void myOnClick(int position, int id, int imgUser, String firstname, String secondname, String userPhone) {
                //my useful method
                Log.i("My new activity = ","opened");
                Log.i("My adapter id get = ",id+"");


                Intent i = new Intent(MainActivity.this, EditUser.class);
                i.putExtra("position", position);
                i.putExtra("data", new DataPojo(id, imgUser, firstname+"", secondname+"", userPhone+""));
                startActivity(i);
            }

            @Override
            public void myOnClick(int position, int id, DataPojo dp) {

            }
        };




        lst = mDatabase.getAllUsers();
        adapter = new CustomAdapter(this, lst, listener, mDatabase);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();  //not working

        //or
        //fa = new CustomAdapter(this, lst);
        /*fa = new CustomAdapter(this, lst, listener);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(fa);*/



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addEmployee();  //for add records
                Intent i = new Intent(MainActivity.this, ContactShow.class);
                startActivity(i);
                adapter.notifyDataSetChanged();  //not working = intent, used load data again
                //loadEmployeesFromDatabase();

            }
        });
    }

    private void InitUpdateDialog(final int position, View view) {
        final EditText etFirstName2, etSecondName2, etPhone2;
        Button btnEdit, btnCancel;
        etFirstName2 = view.findViewById(R.id.etFirstName2);
        etSecondName2 = view.findViewById(R.id.etSecondName2);
        etPhone2 = view.findViewById(R.id.etPhone2);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnCancel = view.findViewById(R.id.btnCancel);

        etFirstName2.setText(firstname);
        etSecondName2.setText(secondname);
        etPhone2.setText(phone);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstname = etFirstName2.getText().toString();
                secondname = etSecondName2.getText().toString();
                phone = etPhone2.getText().toString();

                DataPojo userData = new DataPojo();

                userData.setFirstname(firstname);
                userData.setSecondname(secondname);
                userData.setPhone(phone);

                //mDatabase.updateEmployee(position,userData);
                boolean n = mDatabase.updateEmployee(userData.getId(), firstname, secondname, phone);
                Toast.makeText(MainActivity.this,"User Updated..",Toast.LENGTH_SHORT).show();
                if(n)
                {
                    Log.i("My updated = ","done");
                }
                else
                {
                    Log.i("My updated = ","not done");
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }



        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater mi = getMenuInflater();
            mi.inflate(R.menu.search_menu, menu);


            search = menu.findItem(R.id.search);
            //etsearch = (EditText)findViewById(R.id.etsearch);
            searchView  = (SearchView) MenuItemCompat.getActionView(search);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchView.clearFocus();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Toast.makeText(MainActivity.this, "Searching for = "+newText, Toast.LENGTH_SHORT).show();
                    if(newText.isEmpty())
                    {
                        filterList = lst;
                    }
                    else
                    {
                        ArrayList<DataPojo> filteredList = new ArrayList<>();
                        for(DataPojo row:lst)
                        {
                            Log.i("My row.getFoodName() = ",row.getName()+"");
                            //if(row.getName().toLowerCase().contains(newText.toLowerCase()) || row.getPhone().toLowerCase().contains(newText.toLowerCase()))
                            if(row.getFirstname().toLowerCase().contains(newText.toLowerCase()) || row.getSecondname().toLowerCase().contains(newText.toLowerCase()) || row.getPhone().toLowerCase().contains(newText.toLowerCase()))
                            {
                                filteredList.add(row);
                            }
                            filterList = filteredList;
                        }
                    }


                    //fa.setFilter(filterList);
                    adapter.setFilter(filterList);
                    return false;
                }
            });
            return true;
        }
        //@TargetApi(Build.VERSION_CODES.O)
        //@RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            switch (item.getItemId()) {
                //int id = item.getItemId();
                case android.R.id.home:
                    finish();
                    return true;


                default:
                    return super.onOptionsItemSelected(item);
            }

    }


    /*public void loadEmployeesFromDatabase()
    {
        Cursor cursor = mDatabase.getAllEmployees();
        if(cursor.moveToFirst())
        {
            do{
                //Log.i("My cursor = ",cursor.getString(1)+"");
                lst.add(new DataPojo(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)

                ));
            }
            while (cursor.moveToNext());
            Log.i("My lst = ",lst.toString()+"");

            adapter = new CustomAdapter(this, lst, listener, mDatabase);
            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();  //not working
        }
    }*/

}
