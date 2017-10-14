package com.tasol.indolytics.maptest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by tasol on 14/10/17.
 */

public class MapDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "imsDataBase";
    private static final int DATABASE_VERSION = 1;
    public String TABLE_NAME = "Maps";
    Context context;

    public MapDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addTable() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(id TEXT ,name TEXT ,icon TEXT,lat TEXT,long TEXT)";
        sqLiteDatabase.execSQL(TABLE_CREATE);
        Log.v("@@@WWE", "Table Created sucessfully");
    }

    public boolean isTableExist(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                if (c.getString(0).equals(tableName)) {
                    Log.v("@WWE", "Table Name=> " + c.getString(0));
                    return true;
                }
                c.moveToNext();
            }
        }
        return false;
    }

    public void updateRecord(String tableName, String valueKey, String valueValue, String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(valueKey, valueValue);
        int i = db.update(tableName, values, "id = ?", new String[]{String.valueOf(userName)});
        if (i == 0) {
            Log.v("@@@WE", " updation failed");
        } else {
            Log.v("@@@WE", " updated sucessfully");
        }
        db.close();
    }

    public void addTableRow(String id,String name,String icon,String lat,String lon) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",id);
        contentValues.put("name",name);
        contentValues.put("icon",icon);
        contentValues.put("lat",lat);
        contentValues.put("long",lon);
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public int countAllRows(){
        int count=0;
        String selectQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do {
                count++;
            }while (cursor.moveToNext());
        }
        return count;
    }

    public ArrayList<MapsPojo> getAllRows(){

        ArrayList<MapsPojo> allRec= new ArrayList<>();
        MapsPojo details= new MapsPojo();
        allRec.clear();
        String selectQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        MapsPojo detailsF;
        if(cursor.moveToFirst()) {
            do {
                detailsF = new MapsPojo();
                detailsF.setId(cursor.getString(cursor.getColumnIndex("id")));
                detailsF.setName(cursor.getString(cursor.getColumnIndex("name")));
                detailsF.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
                detailsF.setLatitude(cursor.getString(cursor.getColumnIndex("lat")));
                detailsF.setLongitude(cursor.getString(cursor.getColumnIndex("long")));
                allRec.add(detailsF);
            } while (cursor.moveToNext());
        }
        return allRec;
    }


}
