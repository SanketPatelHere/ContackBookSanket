package com.example.sanket.contactbooksanket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    DatabaseManager mDatabase;

    public CustomAdapter(Activity activity, ArrayList<DataPojo> mylst) {
        this.activity = activity;
        this.mylst = mylst;
    }
    public CustomAdapter(Activity activity, ArrayList<DataPojo> mylst, MyClickListener listener) {
        this.activity = activity;
        this.mylst = mylst;
        this.listener = listener;
    }
    public CustomAdapter(Activity activity, ArrayList<DataPojo> mylst, MyClickListener listener, DatabaseManager mDatabase) {
        this.activity = activity;
        this.mylst = mylst;
        this.listener = listener;
        this.mDatabase = mDatabase;
    }
    @Override
    public int getItemCount() {
        return mylst.size();
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
            //tvFirstName = (TextView) itemView.findViewById(R.id.tvFirstName);
            //tvSecondName = (TextView) itemView.findViewById(R.id.tvSecondName);
            tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //DataPojo dp = mylst.get(position);
                    //listener.myOnClick(v,10);
                    //listener.myOnClick((position+1), dp.getImg(), dp.getFirstname(), dp.getSecondname(), dp.getPhone());
                    //listener.myOnClick(imgv, tvFirstName, tvSecondName, tvPhone);
                    //Intent i = new Intent(CustomAdapter.this, ContactAdd.class);
                    Log.i("My itemview clicked = ","opened");
                    //listener.myOnClick(10, imgv, tvFirstName, tvSecondName, tvPhone);

                }
            });
        }
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ContactViewHolder holder, final int position) {
        final DataPojo dp = mylst.get(position);

        Log.i("My Img2 = ",dp.getImg()+"");
        Log.i("My Name2 = ",dp.getFirstname()+" "+dp.getSecondname());
        Log.i("My Phone2 = ",dp.getPhone());
        //holder.imgv.setImageResource(dp.getImg());
        holder.imgv.setImageResource(R.drawable.ic_launcher_background);
        holder.tvName.setText(dp.getFirstname()+" "+dp.getSecondname());
        holder.tvPhone.setText(dp.getPhone());

        //holder.tvName.setText(dp.getName());
        //holder.tvFirstName.setText(dp.getFirstname());
        //holder.tvSecondName.setText(dp.getSecondname());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("My itemview clicked2 = ","opened");
                listener.myOnClick((position+1),dp.getId(), R.drawable.ic_launcher_background, dp.getFirstname(), dp.getSecondname(), dp.getPhone());

                //Log.i("My id for delete = ",dp.getId()+"");
                //deleteEmployee(position, dp);  //for delete
                //notifyDataSetChanged();


                //Intent i = new Intent(activity, ContactAdd.class);
                //startActivity(i);
                //listener.myOnClick((position+1));
                //listener.myOnClick((position+1), dp.getImg(), dp.getFirstname(), dp.getSecondname(), dp.getPhone());
                //listener.myOnClick(dp.getImg(), dp.getFirstname(), dp.getSecondname(), dp.getPhone());

            }
        });
    }

    public void setFilter(ArrayList<DataPojo> f)
    {
        mylst = f;
        Log.i("My mylst2 = ",mylst+"");
        notifyDataSetChanged();
    }

    public void deleteEmployee(final int position, final DataPojo user)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Are You Sure");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                DataPojo e = new DataPojo();
                int myid = mylst.get(position).getId();

                Log.i("My deleted myid=",myid+"");
                Log.i("My deleted id = ",user.getId()+"");
                int res = mDatabase.deleteUser(myid);
                if(res!=-1)
                {
                    Log.i("My delete = ","yes");
                    mylst.remove(position);
                    notifyDataSetChanged();
                    Log.i("My delete = ","done successfully");
                }
                else if(res!=-1)
                {
                    Log.i("My delete = ","no");
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("My delete = ","no");
                Toast.makeText(activity, "Cancel Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
