package com.example.android.countryinfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by aditya.sawant on 23-11-2017.
 */

public final class NetworkUtils {

    private static final String COUNTRY_LIST_URL = "https://restcountries.eu/rest/v2/all";
    public static URL buildUrl(){
        URL url = null;
        try {
            url = new URL(COUNTRY_LIST_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String fetchCountryList(Context context, URL url) throws IOException {
        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readJsonData(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readJsonData(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        String line = "";
        while((line = bufferedReader.readLine()) != null){
            output.append(line);
        }
        return output.toString();
    }

    public static Bitmap fetchCountryFlag(Context context, URL flagUrl) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) flagUrl.openConnection();
        InputStream is = connection.getInputStream();

        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }
}
