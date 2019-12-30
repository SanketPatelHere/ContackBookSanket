package com.example.sanket.contactbooksanket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.bluetooth.BluetoothHidDeviceAppQosSettings.MAX;

public class DatabaseManager extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "ContactBookDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "usercontact";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_SECONDNAME = "secondname";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_JOIN_DATE = "joiningdate";
    DatabaseManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_FIRSTNAME + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_SECONDNAME + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_PHONE + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_JOIN_DATE + " datetime NOT NULL\n" +
                ");";
        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS "+TABLE_NAME+";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public boolean addEmployee(String firstname, String secondname, String phone, String joiningdate)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRSTNAME, firstname);
        cv.put(COLUMN_SECONDNAME, secondname);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_JOIN_DATE, joiningdate);
        /*cv.put(COLUMN_FIRSTNAME, "aaaa");
        cv.put(COLUMN_SECONDNAME, "bbbb");
        cv.put(COLUMN_PHONE, "9723031228");
        cv.put(COLUMN_JOIN_DATE, joiningdate);*/
        SQLiteDatabase db = getWritableDatabase();
        //db.insert(TABLE_NAME, null , cv);
        return db.insert(TABLE_NAME, null, cv)!=-1;

        /*String query = "SELECT last_insert_rowid()";
        SQLiteDatabase database2 = getReadableDatabase();
        Cursor c = database2.rawQuery(query, null);
        int id = c.getInt(c.getColumnIndex(COLUMN_ID));
        Log.i("My iddddddddd = ",id+"");
        return id;*/
        //retrun SELECT * from SQLITE_SEQUENCE;
    }
    static int i = 0;
    public ArrayList<DataPojo> getAllUsers()
    {
        Log.i("DBHelper = ","getAllEmployee");
        i++;
        /*long c;
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CODE, i);
        values.put(KEY_NAME, "aaa");
        values.put(KEY_EMAIL, "sanketramani0@gmail.com");
        values.put(KEY_ADDRESS, "tirupati, rajkot");
        c = database.insert(TABLE_EMP, null, values);*/

        String query = "select * from "+TABLE_NAME;
        ArrayList<DataPojo> users = new ArrayList<>();
        SQLiteDatabase database2 = getReadableDatabase();
        Cursor c2 = database2.rawQuery(query, null);
        if(c2!=null)
        {
            while (c2.moveToNext())
            {
                int id = c2.getInt(c2.getColumnIndex(COLUMN_ID));
                String firstname = c2.getString(c2.getColumnIndex(COLUMN_FIRSTNAME));
                String secondname = c2.getString(c2.getColumnIndex(COLUMN_SECONDNAME));
                String phone = c2.getString(c2.getColumnIndex(COLUMN_PHONE));

                DataPojo e = new DataPojo();
                e.setId(id);
                e.setFirstname(firstname);
                e.setSecondname(secondname);
                e.setPhone(phone);

                Log.i("DBHelper = ","FirstName = "+firstname);
                Log.i("DBHelper = ","Id = "+id);
                Log.i("DBHelper = ","SecondName = "+secondname);
                Log.i("DBHelper = ","Phone = "+phone);

                users.add(e);
            }
        }
        return users;
    }
    public int deleteUser(int id) //id = position
    {
        Log.i("Remove = ","Item = "+id);
        SQLiteDatabase database = getWritableDatabase();
        int delete = database.delete(DatabaseManager.TABLE_NAME, DatabaseManager.COLUMN_ID+" = "+id, null);
        if(delete!=-1)
        {
            Log.i("Remove = ","Record "+delete+" Deleted Successfully");
        }
        return (id);
    }
    boolean updateEmployee(int id, String firstname, String secondname, String phone)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FIRSTNAME, firstname);
        cv.put(COLUMN_SECONDNAME, secondname);
        cv.put(COLUMN_PHONE, phone);
        return db.update(TABLE_NAME, cv, COLUMN_ID+"=?", new String[]{String.valueOf(id)})==1;
    }
    boolean deleteEmployee(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID+"=?", new String[]{String.valueOf(id)})==1;
    }

    public int getLastId()
    {   int lastId = 0;
        String query = "SELECT "+COLUMN_ID+" from "+TABLE_NAME+" order by "+COLUMN_ID+" DESC limit 1";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c != null && c.moveToFirst()) {
            lastId = (int) c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId;
    }
}
