package com.countryinfo.knowyournation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.countryinfo.knowyournation.countrydetails.CountryDetailFragment;
import com.countryinfo.knowyournation.countrylist.CountryListFragment;

public class MainActivity extends AppCompatActivity implements CountryListFragment.CountrySelectListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onCountrySelected(String name) {
        CountryDetailFragment detailFragment = (CountryDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
        if(detailFragment != null){
            detailFragment.showCountryInfo(name);
        }
    }
}
