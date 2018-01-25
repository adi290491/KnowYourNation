package com.example.android.countryinfo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    private ListView countryListView;
    private CountryListAdapter mCountryListAdapter;
    private List<String> mCountryList = new ArrayList<>();

    ProgressBar loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loadingBar.setVisibility(View.VISIBLE);
        fetchCountryList();
    }

    private void fetchCountryList() {

        if(CountryUtils.isDatabaseEmpty(this)) {
            new CountryAsyncTask().execute();
        }else {
            mCountryList = CountryUtils.fetchCountryNames(this);
            mCountryListAdapter.updateList(mCountryList);
            loadingBar.setVisibility(GONE);
        }

    }

    private void init() {
        countryListView = (ListView) findViewById(R.id.country_list);
        mCountryListAdapter = new CountryListAdapter(this, mCountryList);
        countryListView.setAdapter(mCountryListAdapter);
        countryListView.setOnItemClickListener(countrySelectListener);

        loadingBar = (ProgressBar) findViewById(R.id.loading_bar);


    }

    public class CountryAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            URL url = NetworkUtils.buildUrl();
            Log.d("tag", url.toString());
            try {
                String jsonString = NetworkUtils.fetchCountryList(MainActivity.this, url);
                CountryUtils.insertIntoDatabase(MainActivity.this, jsonString);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mCountryList = CountryUtils.fetchCountryNames(MainActivity.this);
            mCountryListAdapter.updateList(mCountryList);
            loadingBar.setVisibility(GONE);
        }
    }

    AdapterView.OnItemClickListener countrySelectListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String countryName = (String) parent.getItemAtPosition(position);
            Log.d("tag", "Country Selected: " + countryName);
            Intent intent = new Intent(MainActivity.this, CountryDetailActivity.class);
            intent.putExtra("country_name", countryName);
            startActivity(intent);
        }
    };
}
