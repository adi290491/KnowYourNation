package com.example.android.countryinfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.ImageView;

import com.caverock.androidsvg.SVG;

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

    private static SpannableStringBuilder getFormattedString(Context context, int stringResId, String value) {
        String resString = context.getString(stringResId);
        SpannableStringBuilder spannableString = new SpannableStringBuilder(resString + value);
        StyleSpan boldStyle = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(boldStyle, 0, resString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    public static SpannableStringBuilder getFormattedCapital(Context context, String capital) {
        return getFormattedString(context, R.string.capital, capital);
    }

    public static SpannableStringBuilder getFormattedArea(Context context, String area) {
        return getFormattedString(context, R.string.area, area);
    }

    public static SpannableStringBuilder getFormattedRegion(Context context, String region) {
        return getFormattedString(context, R.string.region, region);
    }

    public static SpannableStringBuilder getFormattedSubRegion(Context context, String subRegion) {
        return getFormattedString(context, R.string.subRegion, subRegion);
    }

    public static SpannableStringBuilder getCoordinateString(Context context, String coord) {
        return getFormattedString(context, R.string.coordinates, coord);
    }

    public static SpannableStringBuilder getFormattedBorder(Context context, String border) {
        return getFormattedString(context, R.string.borders, border);
    }

    public static SpannableStringBuilder getFormattedPopulation(Context context, String population) {
        return getFormattedString(context, R.string.population, population);
    }

    public static SpannableStringBuilder getFormattedTimezones(Context context, String timezone) {
        return getFormattedString(context, R.string.timezone, timezone);
    }

    public static SpannableStringBuilder getFormattedNativeName(Context context, String nativeName) {
        return getFormattedString(context, R.string.native_name, nativeName);
    }

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
            }
            return regionalBlocList;
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
            return languageList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getFormattedCallingCode(Context context, String callingCode) {
        List<String> callingCodeList = new ArrayList<>();
        try {
            JSONArray callingCodeArr = new JSONArray(callingCode);
            for(int i=0;i<callingCodeArr.length();i++){
                callingCodeList.add(callingCodeArr.getString(i));
            }
            return callingCodeList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<String> getFormattedTopLevelDomain(Context context, String topLevelDomain) {
        List<String> topLevelDomains = new ArrayList<>();
        try {
            JSONArray topLevelDomArr = new JSONArray(topLevelDomain);
            for(int i=0;i<topLevelDomArr.length();i++){
                topLevelDomains.add(topLevelDomArr.getString(i));
            }
            return topLevelDomains;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SpannableStringBuilder getFormattedTranslations(Context context, String translation) {
        return getFormattedString(context, R.string.translation, translation);
    }

    public static SpannableStringBuilder getFormattedDemonym(Context context, String demonym) {
        return getFormattedString(context, R.string.demonym, demonym);
    }

    public static void loadCountryFlag(final Context context, final String flagUrl, final ImageView imgView) {
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
                    saveBitmapToFile(Environment.getExternalStorageDirectory(), "country.png", bm, Bitmap.CompressFormat.PNG, 0);
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

    private static boolean saveBitmapToFile(File dir, String filename, Bitmap bmp, Bitmap.CompressFormat format, int quality){
        File imgFile = new File(dir, filename);
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
