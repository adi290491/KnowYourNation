package com.countryinfo.knowyournation.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.caverock.androidsvg.SVG;
import com.countryinfo.knowyournation.pojo.Currency;
import com.countryinfo.knowyournation.pojo.RegionalBloc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by aditya.sawant on 24-11-2017.
 */

public final class CountryDataUtils {

    public static Currency getFormattedCurrency(Context context, String currencyString) {
        try {
            JSONObject currencyJson = new JSONObject(currencyString);
            Currency currency = new Currency();
            String code = currencyJson.has("code") ? currencyJson.getString("code") : "";
            String name = currencyJson.has("name") ? currencyJson.getString("name") : "";
            String symbol = currencyJson.has("symbol") ? currencyJson.getString("symbol") : "";

            currency.setCode(code);
            currency.setName(name);
            currency.setSymbol(symbol);

            return currency;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<RegionalBloc> getFormattedRegionalBloc(Context context, String regionalBloc) {
        List<RegionalBloc> regionalBlocList = new ArrayList<>();
        try {
            JSONArray regBlocArr = new JSONArray(regionalBloc);
            for(int i=0;i<regBlocArr.length();i++) {
                JSONObject rbJson = regBlocArr.getJSONObject(i);
                RegionalBloc rb = new RegionalBloc();
                String acronym = rbJson.has("acronym") ? rbJson.getString("acronym") : "N/A";
                String name = rbJson.has("name") ? rbJson.getString("name") : "N/A";
                rb.setAcronym(acronym);
                rb.setName(name);
                regionalBlocList.add(rb);
                return regionalBlocList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getFormattedLanguage(Context context, String languageString) {
        List<String> languageList = new ArrayList<>();
        try {
            JSONArray languageJsonArr = new JSONArray(languageString);
            for(int i=0; i < languageJsonArr.length(); i++){
                JSONObject languageJson = languageJsonArr.getJSONObject(i);
                String name = languageJson.getString("name");
                languageList.add(name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            languageList.add("N/A");
        }
        return languageList;
    }

    public static List<String> getFormattedCallingCode(Context context, String callingCode) {
        List<String> callingCodeList = new ArrayList<>();
        try {
            JSONArray callingCodeArr = new JSONArray(callingCode);
            for(int i=0;i<callingCodeArr.length();i++){
                callingCodeList.add(callingCodeArr.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callingCodeList.add("N/A");
        }
        return callingCodeList;
    }


    public static List<String> getFormattedTopLevelDomain(Context context, String topLevelDomain) {
        List<String> topLevelDomains = new ArrayList<>();
        try {
            JSONArray topLevelDomArr = new JSONArray(topLevelDomain);
            for(int i=0;i<topLevelDomArr.length();i++){
                topLevelDomains.add(topLevelDomArr.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            topLevelDomains.add("N/A");
        }
        return null;
    }

    public static void getFormattedDemonym(Context context, String demonym) {
    }

    public static void loadCountryFlag(Context context, String flagUrl, ImageView imgView, String country){
        File imgFile = new File(context.getCacheDir()+ "Know Your Nation", country + ".png");
        if(imgFile.exists()){
            Log.d("img_exist", true +"");
            Bitmap bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imgView.setImageBitmap(bmp);
        }else{
            Log.d("img_exist", false +"");
            fetchCountryFlag(context, flagUrl, imgView, country);
        }
    }

    private static void fetchCountryFlag(final Context context, final String flagUrl, final ImageView imgView, final String country) {
        //imgView.setImageURI(Uri.parse(flagUrl));
        AsyncTask<Void, Void, Drawable> HttpImageRequestTask = new AsyncTask<Void, Void, Drawable>() {
            @Override
            protected Drawable doInBackground(Void... params) {
                try {


                    final URL url = new URL(flagUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    SVG svg = SVG.getFromInputStream(inputStream);
                    Drawable drawable = new PictureDrawable(svg.renderToPicture());
                    Bitmap bm = drawableToBitmap(new PictureDrawable(svg.renderToPicture()));
                    saveBitmapToFile(context, country + ".png", bm, Bitmap.CompressFormat.PNG, 0);
                    return null;
                } catch (Exception e) {
                    Log.e("MainActivity", e.getMessage(), e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Drawable drawable) {
                super.onPostExecute(drawable);
                Bitmap bmp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+File.separator + "country.png");
                imgView.setImageBitmap(bmp);
                //imgView.setImageDrawable(drawable);
            }
        };

        HttpImageRequestTask.execute();
//        Glide.with(context)
//                .load("https://restcountries.eu/data/col.svg")
//                .into(imgView);
//

    }

    private static Bitmap drawableToBitmap(PictureDrawable pd){
        Bitmap bm = Bitmap.createBitmap(pd.getIntrinsicWidth(), pd.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        canvas.drawPicture(pd.getPicture());
        return bm;
    }

    private static boolean saveBitmapToFile(Context context, String filename, Bitmap bmp, Bitmap.CompressFormat format, int quality){
        File imgFile = new File(context.getCacheDir() + File.separator + "Know Your Nation", filename);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imgFile);
            bmp.compress(format, quality, fos);
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return false;
    }
}
