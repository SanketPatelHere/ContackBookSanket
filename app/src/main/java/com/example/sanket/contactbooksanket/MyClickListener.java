package com.example.sanket.contactbooksanket;

import android.view.View;

import java.util.ArrayList;

public interface MyClickListener {
    public void myOnClick(int position);
    public void myOnClick(int position, int id, int imgUser, String firstname, String secondname, String userPhone);
    public void myOnClick(int position, int imgUser, String firstname, String secondname, String userPhone);


}
