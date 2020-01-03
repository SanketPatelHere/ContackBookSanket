package com.example.sanket.contactbooksanket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ContactViewHolder> {
    Activity activity;
    ArrayList<DataPojo> mylst;
    MyClickListener listener;

    public CustomAdapter(Activity activity, ArrayList<DataPojo> mylst) {
        this.activity = activity;
        this.mylst = mylst;
    }
    public CustomAdapter(Activity activity, ArrayList<DataPojo> mylst, MyClickListener listener) {
        this.activity = activity;
        this.mylst = mylst;
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return this.mylst.size();
    }

    @Override
    public CustomAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = activity.getLayoutInflater();
        View v = li.inflate(R.layout.rv_layout, parent, false);
        return new ContactViewHolder(v);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        ImageView imgv;
        TextView tvName, tvFirstName, tvSecondName, tvPhone;
        public ContactViewHolder(View itemView) {
            super(itemView);
            imgv = (ImageView)itemView.findViewById(R.id.imgv);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.i("My itemview clicked = ","opened");

                }
            });
        }
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ContactViewHolder holder, final int position) {
        final DataPojo dp = this.mylst.get(position);

        holder.imgv.setImageResource(R.drawable.admin);
        holder.tvName.setText(dp.getFirstname()+" "+dp.getSecondname());
        holder.tvPhone.setText(dp.getPhone());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.myOnClick((position),0, R.drawable.ic_launcher_background, dp.getFirstname(), dp.getSecondname(), dp.getPhone());
            }
        });
    }

    public void setFilter(ArrayList<DataPojo> f)
    {
        this.mylst = f;
        Log.i("My mylst2 = ",this.mylst+"");
        notifyDataSetChanged();
    }

}
