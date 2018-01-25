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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


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

    public static SpannableStringBuilder getFormattedCurrency(Context context, String currency) {
        return getFormattedString(context, R.string.currency, currency);
    }

    public static SpannableStringBuilder getFormattedRegionalBloc(Context context, String regionalBloc) {
        return getFormattedString(context, R.string.regional_bloc, regionalBloc);
    }

    public static SpannableStringBuilder getFormattedLanguage(Context context, String language) {
        return getFormattedString(context, R.string.language, language);
    }

    public static SpannableStringBuilder getFormattedCallingCode(Context context, String callingCode) {
        return getFormattedString(context, R.string.calling_code, callingCode);
    }

    public static SpannableStringBuilder getFormattedTopLevelDomain(Context context, String topLevelDomain) {
        return getFormattedString(context, R.string.top_level_domain, topLevelDomain);
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
