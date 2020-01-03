package com.example.sanket.contactbooksanket;

import android.os.Parcel;
import android.os.Parcelable;

public class DataPojo implements Parcelable{
    int id;
    int img;
    String firstname, secondname, name, phone;
    public DataPojo() {
    }
    public DataPojo(int img, String name, String phone) {
        this.img = img;
        this.name = name;
        this.phone = phone;
    }
    public DataPojo(int img, String firstname, String secondname, String phone) {
        this.img = img;
        this.firstname = firstname;
        this.secondname = secondname;
        this.phone = phone;
    }
    public DataPojo(int id, int img, String firstname, String secondname, String phone) {
        this.id = id;
        this.img = img;
        this.firstname = firstname;
        this.secondname = secondname;
        this.phone = phone;
    }
   /* public int getId() {

        if(!setId())
        {

        }
        return id;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //parcelling part
    public DataPojo(Parcel in)
    {
        String data[] = new String[5];
        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.img = Integer.parseInt(data[1]);
        this.firstname = data[2];
        this.secondname = data[3];
        this.phone = data[4];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.id+"", this.img+"", this.firstname, this.secondname, this.phone});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public DataPojo createFromParcel(Parcel in) {
            return new DataPojo(in);
        }

        @Override
        public DataPojo[] newArray(int size) {
            return new DataPojo[size];
        }
    };
}
