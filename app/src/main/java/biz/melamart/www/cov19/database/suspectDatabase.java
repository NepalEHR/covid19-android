package biz.melamart.www.cov19.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import biz.melamart.www.cov19.models.emergencyContact.emergencyContacts;

public class suspectDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "cov19Suspect.db";
    public static final String CONTACTS_TABLE_NAME = "suspect_report";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_CONTACT = "contactNumber";
    public static final String CONTACTS_COLUMN_ADDRESS = "address";
    public static final String CONTACTS_COLUMN_DESCRIPTION = "description";
    public static final String CONTACTS_COLUMN_REPORTER = "reporter";
    public static final String CONTACTS_COLUMN_RCONTACT = "reporterContact";
    public static final String CONTACTS_COLUMN_STATUS = "status";


    public suspectDatabase(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table suspect_report " +
                        "(id integer PRIMARY KEY AUTOINCREMENT NOT NULL, contactNumber text ,name text, address text, description text,reporter text,reporterContact text,status boolean)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS suspect_report");
        onCreate(db);
    }

    public boolean insertContact ( String name, String contactNumber,String address,String description,String reporter, String reporterContact,Boolean status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contactNumber", contactNumber);
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("description", description);
        contentValues.put("reporter", reporter);
        contentValues.put("reporterContact", reporterContact);
        contentValues.put("status", status);
        db.insert("suspect_report", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from suspect_report where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContract (Integer id, String name, String contactNumber,String address,String description,String reporter, String reporterContact,Boolean status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contactNumber", contactNumber);
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("description", description);
        contentValues.put("reporter", reporter);
        contentValues.put("reporterContact", reporterContact);
        contentValues.put("status", status);
        db.update("suspect_report", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContract (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("suspect_report",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public String[][] getAlldata (){

        SQLiteDatabase db=getReadableDatabase();
        int a = numberOfRows();
        //  Log.d("cite",a+"");
        String[][] data = new String[a][8];
        Cursor c=db.rawQuery("select * from suspect_report",null);
        c.move(-1);
        int count =0;
        while(c.moveToNext()){
            data[count][0] = c.getInt(0) + "";
            data[count][1] = c.getString(1);
            data[count][2] = c.getString(2);
            data[count][3] = c.getString(3);
            data[count][4] = c.getString(4);
            data[count][2] = c.getString(5);
            data[count][3] = c.getString(6);
            data[count][4] = c.getString(7);
            count ++;
        }
        return data;
    }


    public void truncateBike()
    {
        SQLiteDatabase db=getWritableDatabase();
        String strSQL = "DELETE FROM suspect_report";
        db.execSQL(strSQL);
        SQLiteDatabase db1=getWritableDatabase();
        String strSQL1 = "VACUUM";
        db1.execSQL(strSQL1);
    }
}