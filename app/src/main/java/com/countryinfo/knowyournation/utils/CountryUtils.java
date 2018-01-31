package com.countryinfo.knowyournation.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.countryinfo.knowyournation.data.CountryContract;
import com.countryinfo.knowyournation.data.CountryListDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by aditya.sawant on 23-11-2017.
 */

public final class CountryUtils {


    public static void fetchCountryList(final Context context){
        AsyncTask countryListAsyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                URL url = NetworkUtils.buildUrl();
                Log.d("tag", url.toString());
                try {
                    String jsonString = NetworkUtils.fetchCountryList(context, url);
                    insertIntoDatabase(context, jsonString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

        };

        countryListAsyncTask.execute();

    }

   /* public static Bitmap fetchCountryFlag(final Context context, final String url){
        Bitmap bitmap;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL flagUrl = new URL(url);
                    bitmap = NetworkUtils.fetchCountryFlag(context, flagUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        return bitmap;
    }*/

    public static void insertIntoDatabase(final Context context, String jsonString) {
        try {
            JSONArray jsonArray = new JSONArray(jsonString);
            for(int i = 0; i < jsonArray.length(); i++){

                JSONObject countryObj = jsonArray.getJSONObject(i);
                /*Country Name*/
                String name = countryObj.getString("name");

                /*Top Level Domain*/
                JSONArray topLevelDomainArray = countryObj.getJSONArray("topLevelDomain");
                String topLevelDomainString =  "";
                if(topLevelDomainArray.length() > 0) {
                   /* for (int j = 0; j < topLevelDomainArray.length(); j++) {
                        topLevelDomainString += topLevelDomainArray.getString(j) + " , ";
                    }

                    topLevelDomainString = topLevelDomainString.substring(0, topLevelDomainString.length() - 1);*/
                   topLevelDomainString = topLevelDomainArray.toString();
                }else{
                    topLevelDomainString = "null";
                }

                /*Alpha Code*/
                String alpha2Code = countryObj.getString("alpha2Code");
                String alpha3Code = countryObj.getString("alpha3Code");

                /*Calling Code*/
                JSONArray callingCodes = countryObj.getJSONArray("callingCodes");
                String callingCode = "";
                if(callingCodes.length() > 0) {
//                    for (int k = 0; k < callingCodes.length(); k++) {
//                        callingCode = callingCodes.getString(k) + " , ";
//                    }
//
//                    callingCode = callingCode.substring(0, callingCode.length() - 1);
                    callingCode = callingCodes.toString();
                }else{
                    callingCode = "null";
                }

                /*Capital*/
                String capital = countryObj.getString("capital");

                /*Alternate Spelling*/
                JSONArray altSpellingArr = countryObj.getJSONArray("altSpellings");
                String altSpelling = "";
                if(altSpellingArr.length() > 0) {
                    for (int l = 0; l < altSpellingArr.length(); l++) {
                        altSpelling += altSpellingArr.getString(l) + " , ";
                    }

                    altSpelling = altSpelling.substring(0, altSpelling.length() - 1);
                }else{
                    altSpelling = "null";
                }

                /*Region*/
                String region = countryObj.getString("region");

                /*Sub Region*/
                String subRegion = countryObj.getString("subregion");

                /*Population*/
                long population = countryObj.getLong("population");

                /*Latitude, Longitude*/
                JSONArray latlng = countryObj.getJSONArray("latlng");
                Log.d("tag", "Coord: " + latlng.getDouble(0) + latlng.getDouble(1) + " : " + latlng.length());
                String coord = "";
                if(latlng.length() > 0) {
                    coord = latlng.toString();
                }else{
                    coord = "null";
                }

                /*Demonym*/
                String demonym = countryObj.getString("demonym");

                /*Area*/
                double area = countryObj.getDouble("area");

                /*Gini*/
                Double gini;
                if(countryObj.isNull("gini")){
                    gini = 0.0;
                }else {
                    gini = countryObj.getDouble("gini");
                }

                /*Timezones*/
                JSONArray timeZoneArr = countryObj.getJSONArray("timezones");
                String timezone = "";
                if(timeZoneArr.length() > 0) {
                    for (int n = 0; n < timeZoneArr.length(); n++) {
                        timezone += timeZoneArr.get(n) + " , ";
                    }
                    timezone = timezone.substring(0, timezone.length() - 1);
                }else{
                    timezone = "null";
                }

                /*Border*/
                JSONArray borders = countryObj.getJSONArray("borders");
                String border = "";
                if(borders.length() > 0) {
                    for (int o = 0; o < borders.length(); o++) {
                        border += borders.getString(o) + " , ";
                    }
                    border = border.substring(0, border.length() - 1);
                }else{
                    border = "null";
                }

                /*Native Name*/
                String nativeName = countryObj.getString("nativeName");

                /*Numeric Code*/
                String numericCode = countryObj.getString("numericCode");

                /*Currency*/
                JSONArray currencyArr = countryObj.getJSONArray("currencies");
                String currency = "";
                if(currencyArr.length() > 0) {
                    for (int p = 0; p < currencyArr.length(); p++) {
                        JSONObject currencyJson = currencyArr.getJSONObject(p);
                        currency += currencyJson.toString() + " ; ";
                    }
                    currency = currency.substring(0, currency.length() - 1);
                }else{
                    currency = "null";
                }

                /*Languages*/
                JSONArray languageArr = countryObj.getJSONArray("languages");
                String language = "";
                if(languageArr.length() > 0) {
                   /* for (int q = 0; q < languageArr.length(); q++) {
                        JSONObject languageJson = languageArr.getJSONObject(q);
                        language += languageJson.toString() + " ; ";
                    }

                    language = language.substring(0, language.length() - 1);*/
                    language = languageArr.toString();
                }else{
                    language = "null";
                }

                /*Translation*/
                JSONObject translationArr = countryObj.getJSONObject("translations");
                String translations = translationArr.toString();

                /*Flag*/
                String flagUrl = countryObj.getString("flag");

                /*Regional Bloc*/
                JSONArray regionalBlocArr = countryObj.getJSONArray("regionalBlocs");
                String regionalBloc = "";
                if(regionalBlocArr.length() > 0) {
                   /* for (int r = 0; r < regionalBlocArr.length(); r++) {
                        JSONObject regionalBlocJson = regionalBlocArr.getJSONObject(r);
                        regionalBloc += regionalBlocJson.toString() + ";";
                    }*/
                   regionalBloc = regionalBlocArr.toString();
                }else{
                    regionalBloc = "null";
                }

                /*CIOC*/
                String cioc = countryObj.getString("cioc");

                ContentValues values = new ContentValues();
                values.put(CountryContract.CountryEntry.NAME , name);
                values.put(CountryContract.CountryEntry.TOP_LEVEL_DOMAIN, topLevelDomainString);
                values.put(CountryContract.CountryEntry.ALPHA_2_CODE, alpha2Code);
                values.put(CountryContract.CountryEntry.ALPHA_3_CODE, alpha3Code);
                values.put(CountryContract.CountryEntry.CALLING_CODE, callingCode);
                values.put(CountryContract.CountryEntry.CAPITAL, capital);
                values.put(CountryContract.CountryEntry.ALT_SPELLINGS, altSpelling);
                values.put(CountryContract.CountryEntry.REGION, region);
                values.put(CountryContract.CountryEntry.SUB_REGION, subRegion);
                values.put(CountryContract.CountryEntry.POPULATION, String.valueOf(population));
                values.put(CountryContract.CountryEntry.LAT_LNG, coord);
                values.put(CountryContract.CountryEntry.DEMONYM, demonym);
                values.put(CountryContract.CountryEntry.AREA, String.valueOf(area));
                values.put(CountryContract.CountryEntry.GINI, String.valueOf(gini));
                values.put(CountryContract.CountryEntry.TIMEZONES, timezone);
                values.put(CountryContract.CountryEntry.BORDERS, border);
                values.put(CountryContract.CountryEntry.NATIVE_NAME, nativeName);
                values.put(CountryContract.CountryEntry.NUMERIC_CODE, numericCode);
                values.put(CountryContract.CountryEntry.CURRENCIES,currency);
                values.put(CountryContract.CountryEntry.LANGUAGES, language);
                values.put(CountryContract.CountryEntry.TRANSLATIONS, translations);
                values.put(CountryContract.CountryEntry.FLAG, flagUrl);
                values.put(CountryContract.CountryEntry.REGIONAL_BLOCS, regionalBloc);
                values.put(CountryContract.CountryEntry.CIOC, cioc);
                final long rowInserted = insert(context, values);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static boolean isDatabaseEmpty(Context context){
        CountryListDBHelper dbHelper = new CountryListDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CountryContract.CountryEntry.TABLE_NAME, null);
        cursor.moveToFirst();
        Log.d("tag", "cursor count: " + cursor.getCount());
        return cursor.getCount() == 0;
    }

    private static long insert(Context context, ContentValues values){
        CountryListDBHelper dbHelper = new CountryListDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insert(CountryContract.CountryEntry.TABLE_NAME, null, values);
        return rowId;
    }

    public static List<String> fetchCountryNames(Context context){
        List<String> countryNameList = new ArrayList<>();
        CountryListDBHelper dbHelper = new CountryListDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] column = {CountryContract.CountryEntry.NAME};
        Cursor cursor = db.query(CountryContract.CountryEntry.TABLE_NAME, column, null, null, null, null, null);
        //cursor.moveToFirst();
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.NAME));
            if(name != null){
                countryNameList.add(name);
            }
        }
        Log.d("tag", countryNameList.toString());
        return countryNameList;
    }

    public static Cursor getCountryDetailsByName(Context context, String countryName) {
        CountryListDBHelper dbHelper = new CountryListDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = CountryContract.CountryEntry.NAME + " = ? ";
        String[] selectionArgs = {countryName};
        Cursor cursor = db.query(CountryContract.CountryEntry.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
        }
        return cursor;
    }
}
