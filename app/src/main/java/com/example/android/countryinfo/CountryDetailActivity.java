package com.example.android.countryinfo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class CountryDetailActivity extends AppCompatActivity {

    TextView tvName, tvCapital, tvArea, tvRegion, tvSubRegion, tvCoord, tvBorders, tvPopulation, tvTimezones;
    TextView tvNativeName, tvCurrency, tvRegionalBloc, tvLanguage, tvCallingCode, tvTopLevelDomain, tvTranslations, tvDemonym;
    private ImageView flagImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);
        init();
        String countryName = getIntent().getStringExtra("country_name");
        Log.d("tag", "Country name: " + countryName);

        fetchCountryDetails(countryName);
    }

    private void init() {
        tvName = (TextView) findViewById(R.id.tv_name);
        tvCapital = (TextView) findViewById(R.id.tv_capital);
        tvArea = (TextView) findViewById(R.id.tv_area);
        tvRegion = (TextView) findViewById(R.id.tv_region);
        tvSubRegion = (TextView) findViewById(R.id.tv_subregion);
        tvCoord = (TextView) findViewById(R.id.tv_coord);
        tvBorders = (TextView) findViewById(R.id.tv_borders);
        tvPopulation = (TextView) findViewById(R.id.tv_population);
        tvTimezones = (TextView) findViewById(R.id.tv_timezones);
        tvNativeName = (TextView) findViewById(R.id.tv_native_name);
        tvCurrency = (TextView) findViewById(R.id.tv_currency);
        tvRegionalBloc = (TextView) findViewById(R.id.tv_regional_bloc);
        flagImageView = (ImageView) findViewById(R.id.iv_flag);
        tvLanguage = (TextView) findViewById(R.id.tv_language);
        tvCallingCode = (TextView) findViewById(R.id.tv_calling_code);
        tvTopLevelDomain = (TextView) findViewById(R.id.tv_top_level_domain);
        tvTranslations = (TextView) findViewById(R.id.tv_translations);
        tvDemonym = (TextView) findViewById(R.id.tv_demonym);
    }

    private void fetchCountryDetails(String countryName) {
        Cursor cursor = CountryUtils.getCountryDetailsByName(this, countryName);
        populateData(cursor);
    }

    private void populateData(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.NAME));
        Log.d("tag", name);
        tvName.setText(name);

        String flagUrl = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.FLAG));
        Log.d("tag", flagUrl);
        CountryDataUtils.loadCountryFlag(this, flagUrl, flagImageView);

        String capital = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.CAPITAL));
        Log.d("tag", capital);
        tvCapital.setText(CountryDataUtils.getFormattedCapital(this, capital));

        String area = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.AREA));
        Log.d("tag", area);
        tvArea.setText(CountryDataUtils.getFormattedArea(this, area));

        String region = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.REGION));
        Log.d("tag", region);
        tvRegion.setText(CountryDataUtils.getFormattedRegion(this, region));

        String subRegion = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.SUB_REGION));
        Log.d("tag", subRegion);
        tvSubRegion.setText(CountryDataUtils.getFormattedSubRegion(this, subRegion));

        String coord = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.LAT_LNG));
        Log.d("tag", coord);
        tvCoord.setText(CountryDataUtils.getCoordinateString(this, coord));

        String border = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.BORDERS));
        Log.d("tag", border);
        tvBorders.setText(CountryDataUtils.getFormattedBorder(this, border));

        String population = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.POPULATION));
        Log.d("tag", population);
        tvPopulation.setText(CountryDataUtils.getFormattedPopulation(this, population));

        String timezones = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.TIMEZONES));
        Log.d("tag", timezones);
        tvTimezones.setText(CountryDataUtils.getFormattedTimezones(this, timezones));

        String nativeName = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.NATIVE_NAME));
        Log.d("tag", nativeName);
        tvNativeName.setText(CountryDataUtils.getFormattedNativeName(this, nativeName));

        String currency = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.CURRENCIES));
        Log.d("tag", currency);
        tvCurrency.setText(CountryDataUtils.getFormattedCurrency(this, currency));

        String regionalBloc = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.REGIONAL_BLOCS));
        Log.d("tag", regionalBloc);
        tvRegionalBloc.setText(CountryDataUtils.getFormattedRegionalBloc(this, regionalBloc));

        String language = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.LANGUAGES));
        Log.d("tag", language);
        tvLanguage.setText(CountryDataUtils.getFormattedLanguage(this, language));

        String callingCode = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.CALLING_CODE));
        Log.d("tag", callingCode);
        tvCallingCode.setText(CountryDataUtils.getFormattedCallingCode(this, callingCode));

        String topLevelDomain = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.TOP_LEVEL_DOMAIN));
        Log.d("tag", topLevelDomain);
        tvTopLevelDomain.setText(CountryDataUtils.getFormattedTopLevelDomain(this, topLevelDomain));

        String translations = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.TRANSLATIONS));
        Log.d("tag", translations);
        tvTranslations.setText(CountryDataUtils.getFormattedTranslations(this, translations));

        String demonym = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.DEMONYM));
        Log.d("tag", demonym);
        tvDemonym.setText(CountryDataUtils.getFormattedDemonym(this, demonym));

    }

}
