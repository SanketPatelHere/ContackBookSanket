package com.example.sanket.contactbooksanket;
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
    FloatingActionButton btnAdd;

    CustomAdapter adapter;
    SharedPreferences sharedPreferences;

    //for edit
    AlertDialog.Builder builder;
    AlertDialog dialog;
    String firstname = "", secondname = "", phone = "";
    //PreferenceHelper myPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv = (RecyclerView) findViewById(R.id.rv);
        btnAdd = (FloatingActionButton)findViewById(R.id.btnAdd);
        lst = new ArrayList<>();
        lst.add(new DataPojo(R.drawable.admin,"sanket", "ramani", "9723031228"));

        listener = new MyClickListener() {
            @Override
            public void myOnClick(int position) {
                Log.i("My activity position = ",position+"");
            }

            @Override
            public void myOnClick(int position, int id, int imgUser, String firstname, String secondname, String userPhone) {
                Log.i("My new activity = ","opened");

                Intent i = new Intent(MainActivity.this, ContactShowActivity.class);
                i.putExtra("position", position);
                i.putExtra("array", lst);
                i.putExtra("data", new DataPojo(id, imgUser, firstname+"", secondname+"", userPhone+""));
                startActivityForResult(i, 1);

            }
        };

        adapter = new CustomAdapter(this, lst, listener);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ContactAddActivity.class);
                i.putExtra("array",lst);
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("My back new intent","called");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("My back onActivityRes=","called");

        if(requestCode==1)
        {
            Log.i("My request = ",requestCode+"");
            if(resultCode== Activity.RESULT_OK)
            {
                Log.i("My result ok = ",requestCode+"");
                Bundle b = getIntent().getExtras();
                lst.clear();
                lst = data.getParcelableArrayListExtra("newarray");
                adapter = new CustomAdapter(this, lst, listener);
                rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                rv.setAdapter(adapter);

                Log.i("My arrival list size = ",lst.size()+"");
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
        adapter.notifyDataSetChanged();

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
                            Log.i("My row.getName() = ",row.getName()+"");
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


}
