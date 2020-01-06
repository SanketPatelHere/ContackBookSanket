package com.example.sanket.contactbooksanket;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public static int BACK_PRESSED_CLICKED = 1;

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
        lst.add(new DataPojo(System.currentTimeMillis(), R.drawable.admin,"sanket", "ramani", "9723031228"));
        lst.add(new DataPojo(System.currentTimeMillis(), R.drawable.admin,"raj", "patel", "9723031228"));

        listener = new MyClickListener() {
            @Override
            public void myOnClick(int position) {
                Log.i("My activity position = ",position+"");
            }

            @Override
            public void myOnClick(int position, long id, int imgUser, String firstname, String secondname, String userPhone) {
                Log.i("My new activity = ","opened");
                Log.i("My new position = ",position+" and id = "+id);

                Intent i = new Intent(MainActivity.this, ContactShowActivity.class);
                i.putExtra("position", position);
                final ArrayList<String> arr = new ArrayList<>();

                arr.add(firstname);
                arr.add(secondname);
                arr.add(userPhone);
                //i.putExtra("array_list", arr);
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


    public int getPosition(long id)
    {
        int position = -1;
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).getId() == id) {
                position = i;
            }
        }
        return position;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("My back onActivityRes=","called");

        if(requestCode==1)
        {
            Log.i("My request = ",requestCode+"");
            if(resultCode== Activity.RESULT_OK) //for edit
            {
                Log.i("My result ok = ",requestCode+"");
                if(data.getExtras()!=null)
                {
                    int rposition = data.getExtras().getInt("rposition");
                    DataPojo dp3 = (DataPojo)data.getExtras().getParcelable("data"); //whole data pass
                    Log.i("My dp dp3 position = ",getPosition(dp3.getId())+"");
                    Log.i("My dp name = ",dp3.getFirstname());
                    lst.set(getPosition(dp3.getId()), new DataPojo(dp3.getId(),1,dp3.getFirstname(), dp3.getSecondname(), dp3.getPhone()));


                    HashMap<Long, DataPojo> s = new HashMap();
                    for(DataPojo p : lst){
                        s.put(p.getId() , p);
                    }
                    Log.i("My Hashmap name1 = ",s.get(dp3.getId()).getFirstname()+"");
                    s.put(s.get(dp3.getId()).getId(), new DataPojo(dp3.getId(),1,dp3.getFirstname(), dp3.getSecondname(), dp3.getPhone()));
                    Log.i("My Hashmap name2 = ",s.get(dp3.getId()).getFirstname()+"");

                  /* ArrayList<DataPojo> entrySet = s.entrySet();
                    ArrayList<Map.Entry<String, String>> listOfEntry = new ArrayList<Map.Entry<String, String>>(entrySet);
                    Log.i("My Hashmap name2 = ",listOfEntry.get(0).getKey()+"");*/
                    ArrayList<DataPojo> list = new ArrayList<DataPojo>(s.values());
                    //lst.clear();
                    //lst.addAll(s.values());
                }
                Log.i("My arrival list size = ",lst.size()+"");
            }
            else if(resultCode==2)  //for add
            {
                Log.i("My add =",resultCode+"");
                DataPojo dp3 = (DataPojo)data.getExtras().getParcelable("data"); //whole data pass
                Log.i("My dp add name = ",dp3.getFirstname());
                Log.i("My dp add id time = ",System.currentTimeMillis()+"");
                lst.add(new DataPojo(System.currentTimeMillis(),1,dp3.getFirstname(), dp3.getSecondname(), dp3.getPhone()));
                Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show();

            }
            else if(resultCode==3)  //for delete
            {
                Log.i("My result delete = ",requestCode+"");
                int rposition = data.getExtras().getInt("rposition");
                Log.i("My dp rposition = ",rposition+"");
                lst.remove(rposition);
            }
            if(resultCode==Activity.RESULT_CANCELED)  //for cancel
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
        MainActivity.BACK_PRESSED_CLICKED = 1;
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
