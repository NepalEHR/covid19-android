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

public class contactDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "cov19Contact.db";
    public static final String CONTACTS_TABLE_NAME = "contact_trace";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_CONTACT = "contact";
    public static final String CONTACTS_COLUMN_ADDRESS = "address";
    public static final String CONTACTS_COLUMN_RELATION = "relation";



    public contactDatabase(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table contact_trace " +
                        "(id integer PRIMARY KEY AUTOINCREMENT NOT NULL, contact text ,name text, address text, relation text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contact_trace");
        onCreate(db);
    }

    public boolean insertContact ( String name, String contact,String address,String relation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact", contact);
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("relation", relation);
        db.insert("contact_trace", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contact_trace where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContract (Integer id, String name, String contact,String address,String relation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("contact", contact);
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("relation", relation);
        db.update("contact_trace", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContract (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contact_trace",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
    public String[][] getAlldata (){

        SQLiteDatabase db=getReadableDatabase();
        int a = numberOfRows();
        //  Log.d("cite",a+"");
        String[][] data = new String[a][18];
        Cursor c=db.rawQuery("select * from contact_trace",null);
        c.move(-1);
        int count =0;
        while(c.moveToNext()){
            data[count][0] = c.getInt(0) + "";
            data[count][1] = c.getString(1);
            data[count][2] = c.getString(2);
            data[count][3] = c.getString(3);
            data[count][4] = c.getString(4);
            count ++;
        }
        return data;
    }

    public List<emergencyContacts> getTraceContacts()
    {
        List<emergencyContacts> contactsList = new ArrayList<>();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contact_trace", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            emergencyContacts contact = new emergencyContacts(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)),res.getString(res.getColumnIndex(CONTACTS_COLUMN_ADDRESS)),res.getString(res.getColumnIndex(CONTACTS_COLUMN_CONTACT)),"","","","1");

            contactsList.add(contact);
            res.moveToNext();
        }
        return  contactsList;

    }
    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contact_trace", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public void truncateBike()
    {
        SQLiteDatabase db=getWritableDatabase();
        String strSQL = "DELETE FROM contact_trace";
        db.execSQL(strSQL);
        SQLiteDatabase db1=getWritableDatabase();
        String strSQL1 = "VACUUM";
        db1.execSQL(strSQL1);
    }
}