package com.example.sanket.contactbooksanket;
//import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        //getSupportActionBar().setDisplayShowTitleEnabled(false);
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
            public void myOnClick(int position, int id, int imgUser, String firstname, String secondname, String userPhone) {
                //my useful method
                Log.i("My new activity = ","opened");

                Intent i = new Intent(MainActivity.this, ContactShow2.class);
                i.putExtra("position", position);
                i.putExtra("data", new DataPojo(id, imgUser, firstname+"", secondname+"", userPhone+""));
                startActivity(i);

                /*Intent i = new Intent(MainActivity.this, EditUser.class);
                i.putExtra("position", position);
                i.putExtra("data", new DataPojo(id, imgUser, firstname+"", secondname+"", userPhone+""));
                startActivity(i);*/


            }
        };




        lst = mDatabase.getAllUsers();
        adapter = new CustomAdapter(this, lst, listener, mDatabase);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        //adapter.notifyDataSetChanged();  //not working

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addEmployee();  //for add records
                Intent i = new Intent(MainActivity.this, ContactAdd.class);
                //startActivityForResult(i, 1);
                i.putExtra("array",lst);
                startActivity(i);


            }
        });
    }

    //not used
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            Log.i("My request = ",requestCode+"");
            if(resultCode== Activity.RESULT_OK)
            {
                Log.i("My result ok = ",requestCode+"");
                Bundle b = getIntent().getExtras();
                //ArrayList<DataPojo> newlist = (ArrayList<DataPojo>)data.getSerializableExtra("results");
                //lst = getIntent().getExtras().getParcelableArrayList("newarray");
                lst = data.getParcelableArrayListExtra("newarray");
                Log.i("My arrival list = ",lst+"");
                Log.i("My arrival list size = ",lst.size()+"");
                //lst = new ArrayList<>();
                lst = mDatabase.getAllUsers();
                Log.i("My loaded list size2 = ",lst.size()+"");

                //adapter.setFilter(lst);
                adapter.notifyDataSetChanged();  //not working
                //rv.getAdapter().notifyDataSetChanged();
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
        if(rv.getAdapter()!=null)
        {
            Log.i("My resume = ","run");
            lst = mDatabase.getAllUsers();
            adapter.notifyDataSetChanged();  //for search list
            Log.i("My list size adapter2=",lst.size()+"");
            adapter.reloadDatabase();  //for refresh data
        }
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
}
