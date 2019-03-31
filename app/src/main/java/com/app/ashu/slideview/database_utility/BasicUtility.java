package com.app.ashu.slideview.database_utility;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.ashu.slideview.Fragment.ContactsFragment;
import com.app.ashu.slideview.utility.User;

import java.util.ArrayList;

public class BasicUtility extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Contacts.sqlite";

    public static final String TABLE_NAME = "contact_list";

    public BasicUtility(Context ctx)
    {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean createDB(SQLiteDatabase db)
    {
        try
        {
            db.execSQL("CREATE TABLE IF NOT EXISTS contact_list(id integer,unique_id VARCHAR,phone_number VARCHAR,name VARCHAR);");

        }catch (Exception e)
        {
            System.out.println("Error in database creation"+e);
            return false;
        }
        return true;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDB(db);


    }

    public void insertData(ArrayList<User> data)
    {
        try
        {
            SQLiteDatabase db=this.getReadableDatabase();
            int c=0;
            db.beginTransaction();
            for(User usr:data)
            {
                String sql="Insert into contact_list values("+usr.getId()+",'"+
                        usr.getUnique_id()+"','"+usr.getPhoneNumber()+"','"+
                        usr.getName()+"')";
                db.execSQL(sql);


                System.out.println("Insertion complete "+c++);
            }

            db.endTransaction();
        }catch (Exception e)
        {
            System.out.println("Error in insertion "+e);
        }
    }

    public int getContactCount()
    {
        int ret=0;
        try
        {
            SQLiteDatabase db=this.getReadableDatabase();
            String sql="select * from contact_list";
            db.execSQL(sql);

            Cursor crs=db.rawQuery(sql,new String[]{});

            ret=crs.getCount();

            crs.close();
        }catch (Exception e)
        {
            System.out.println("Error in get counts"+e);
        }

        return ret;
    }

    public ArrayList<String> getContacts()
    {
        ArrayList<String> data=new ArrayList<String>();
        try
        {
            SQLiteDatabase db=this.getReadableDatabase();
            String sql="select * from contact_list";
            db.execSQL(sql);

            Cursor crs=db.rawQuery(sql,new String[]{});
            if(crs.moveToFirst())
              while(crs.moveToNext())
            {
                System.out.println("Data is "+crs.getString(2));
                data.add(crs.getString(2));
            }

            crs.close();
        }catch (Exception e)
        {
            System.out.println(e);
        }

        return data;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
