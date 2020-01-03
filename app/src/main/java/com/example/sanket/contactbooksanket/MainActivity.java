package com.example.sanket.contactbooksanket;
//import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.annotation.Nullable;
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
    //DatabaseManager mDatabase;
    FloatingActionButton btnAdd;

    CustomAdapter adapter;
    SharedPreferences sharedPreferences;

    //for edit
    AlertDialog.Builder builder;
    AlertDialog dialog;
    String firstname = "", secondname = "", phone = "";
    PreferenceHelper myPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myPref = new PreferenceHelper(getApplicationContext());
        //sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);
        rv = (RecyclerView) findViewById(R.id.rv);
        btnAdd = (FloatingActionButton)findViewById(R.id.btnAdd);
        lst = new ArrayList<>();
        lst.add(new DataPojo(R.drawable.admin,"sanket", "ramani", "9723031228"));


        //if (json.isEmpty())
        //if(json.equalsIgnoreCase("null"))
        fetchNewList();
        /*if(sharedPreferences.getString("contactlist", null)==null)
        {
            //Toast.makeText(MainActivity.this,"There is something error1",Toast.LENGTH_LONG).show();
            Log.i("My Error in pref = ","not change list");

        }
        else
        {
            ArrayList<DataPojo> fetchList = getArrayList("contactlist");
            Log.i("My fetchList = ",fetchList.size()+"");
            lst = fetchList;
        }*/






        //mDatabase = new DatabaseManager(this);

        listener = new MyClickListener() {
            @Override
            public void myOnClick(int position) {
                //Toast.makeText(getApplicationContext(), "MyOnClick = " + position, Toast.LENGTH_SHORT).show();
                Log.i("My activity position = ",position+"");

            }

            @Override
            public void myOnClick(int position, int id, int imgUser, String firstname, String secondname, String userPhone) {
                //my used method
                Log.i("My new activity = ","opened");

                Intent i = new Intent(MainActivity.this, ContactShowActivity.class);
                i.putExtra("position", position);
                i.putExtra("array", lst);
                i.putExtra("data", new DataPojo(id, imgUser, firstname+"", secondname+"", userPhone+""));
                startActivity(i);
            }



            @Override
            public void myOnClick(int position, int imgUser, String firstname, String secondname, String userPhone) {
                Log.i("My new activity = ","opened");

                Intent i = new Intent(MainActivity.this, ContactShowActivity.class);
                i.putExtra("position", position);
                i.putExtra("data", new DataPojo(position, imgUser, firstname+"", secondname+"", userPhone+""));
                startActivity(i);
            }
        };

        adapter = new CustomAdapter(this, lst, listener);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);

       // lst = mDatabase.getAllUsers();
        //adapter = new CustomAdapter(this, lst, listener, mDatabase);

        //adapter.notifyDataSetChanged();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ContactAddActivity.class);
                i.putExtra("array",lst);
                //startActivity(i);
                startActivityForResult(i, 1);



            }
        });
    }

    //not used
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("My back onActivityResult=","called");

        if(requestCode==1)
        {
            Log.i("My request = ",requestCode+"");
            if(resultCode== Activity.RESULT_OK)
            {
                Log.i("My result ok = ",requestCode+"");
                Bundle b = getIntent().getExtras();

                lst.clear();
                //lst.add(new DataPojo(R.drawable.admin,"raj", "patel", "9723031228"));

                lst = data.getParcelableArrayListExtra("newarray");
                ////////////////////////
                ///for store in sharef preference
                myPref.saveArrayList(lst, "contactlist");



                /////////////////////////
                /*adapter = new CustomAdapter(this, lst, listener);
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                rv.setAdapter(adapter);*/
                Log.i("My arrival list = ",lst+"");
                Log.i("My arrival list size = ",lst.size()+"");

             //   lst = mDatabase.getAllUsers();
               // Log.i("My loaded list size2 = ",lst.size()+"");
               // adapter.notifyDataSetChanged();  //not working
                Log.i("My refresh work = ","done");

            }
            if(resultCode==Activity.RESULT_CANCELED)
            {
                Log.i("My result cancel = ",requestCode+"");

            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("My back onResume = ","called");
        fetchNewList();
        adapter = new CustomAdapter(this, lst, listener);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        
    }
    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater mi = getMenuInflater();
            mi.inflate(R.menu.search_menu, menu);
            search = menu.findItem(R.id.search);
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
                            if(row.getFirstname().toLowerCase().contains(newText.toLowerCase()) || row.getSecondname().toLowerCase().contains(newText.toLowerCase()) || row.getPhone().toLowerCase().contains(newText.toLowerCase()))
                            {
                                filteredList.add(row);
                            }
                            filterList = filteredList;
                        }
                    }
                    adapter.setFilter(filterList);
                    return false;
                }
            });
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
         }



         public void fetchNewList()
         {
             if(myPref.getArrayList("contactlist")==null)
             {
                 //Toast.makeText(MainActivity.this,"There is something error",Toast.LENGTH_LONG).show();
                 Log.i("My Error in pref = ","not change list");
             }
             else
             {
                 ArrayList<DataPojo> fetchList = myPref.getArrayList("contactlist");

                 Log.i("My fetchList = ",fetchList.size()+"");
                 lst = fetchList;
             }
         }
}
