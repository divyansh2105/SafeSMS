package com.example.dahiya.safesms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mobiledatabase.db";
    public static final String TABLE_PRODUCTS = "mobile";
    public static final String MOBILE = "mobile";
    public static final String N = "n";
    public static final String E = "e";

    public Databasehandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                        MOBILE + " TEXT PRIMARY KEY, " +
                        N + " TEXT, " +
                        E + " TEXT" +
                        ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addVideo(Structure v)
    {
        ContentValues values = new ContentValues();
        values.put(MOBILE, v.getMobile());
        values.put(N, v.getN_string());
        values.put(E, v.getE_string());

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_PRODUCTS, null, values);
//        Log.d("mytag1","addvideo func");
        db.close();
    }

    public void deleteVideo(String mobile){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + MOBILE + "=\"" + mobile  + "\";");
    }

//    public void deleteVideo(String word, String url){
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + MOBILE + "=\"" + word + "\" AND "  + N + "=\""
//                + url + "\";");
//    }

    public Cursor getcursor()
    {
        SQLiteDatabase db=getWritableDatabase();
        Cursor cur=db.rawQuery("SELECT * FROM "+ TABLE_PRODUCTS,null);
        return cur;
    }

//    public Cursor getcursorURL(String key)
//    {
//        SQLiteDatabase db=getWritableDatabase();
//        Cursor cur=db.rawQuery("SELECT * FROM "+ TABLE_PRODUCTS + " WHERE "+ WORD+ "=" + "\""+ key + "\"",null);
//        return cur;
//    }
//
//    public Cursor getcursorAlphabets(String alpha)
//    {
//        SQLiteDatabase db=getWritableDatabase();
//        Cursor cur=db.rawQuery("SELECT * FROM "+ TABLE_PRODUCTS + " WHERE "+ WORD+ " LIKE " + "\""+ alpha + "%\"",null);
//        return cur;
//    }


}
