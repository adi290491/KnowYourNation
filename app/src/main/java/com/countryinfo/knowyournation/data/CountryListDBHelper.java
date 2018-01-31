package com.countryinfo.knowyournation.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aditya.sawant on 23-11-2017.
 */

public class CountryListDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "country.db";

    public CountryListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + CountryContract.CountryEntry.TABLE_NAME  + "(" +
                CountryContract.CountryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CountryContract.CountryEntry.NAME + " TEXT, " +
                CountryContract.CountryEntry.TOP_LEVEL_DOMAIN + " TEXT, " +
                CountryContract.CountryEntry.ALPHA_2_CODE + " TEXT, " +
                CountryContract.CountryEntry.ALPHA_3_CODE + " TEXT, " +
                CountryContract.CountryEntry.CALLING_CODE + " TEXT, " +
                CountryContract.CountryEntry.CAPITAL + " TEXT, " +
                CountryContract.CountryEntry. ALT_SPELLINGS + " TEXT, " +
                CountryContract.CountryEntry.REGION + " TEXT, " +
                CountryContract.CountryEntry.SUB_REGION + " TEXT, " +
                CountryContract.CountryEntry.POPULATION + " TEXT, " +
                CountryContract.CountryEntry.LAT_LNG + " TEXT, " +
                CountryContract.CountryEntry.DEMONYM + " TEXT, " +
                CountryContract.CountryEntry.AREA + " TEXT, " +
                CountryContract.CountryEntry.GINI + " TEXT, " +
                CountryContract.CountryEntry.TIMEZONES + " TEXT, " +
                CountryContract.CountryEntry.BORDERS + " TEXT, " +
                CountryContract.CountryEntry.NATIVE_NAME + " TEXT, " +
                CountryContract.CountryEntry.NUMERIC_CODE + " TEXT, " +
                CountryContract.CountryEntry.CURRENCIES + " TEXT, " +
                CountryContract.CountryEntry.LANGUAGES + " TEXT, " +
                CountryContract.CountryEntry.TRANSLATIONS + " TEXT, " +
                CountryContract.CountryEntry.FLAG + " TEXT, " +
                CountryContract.CountryEntry.REGIONAL_BLOCS + " TEXT, " +
                CountryContract.CountryEntry.CIOC + " TEXT);";
       /* String createTableQuery = "CREATE TABLE " + CountryContract.CountryEntry.TABLE_NAME + "(" +
                CountryContract.CountryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CountryContract.CountryEntry.NAME + " TEXT);";
*/
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CountryContract.CountryEntry.TABLE_NAME);
        onCreate(db);
    }
}
