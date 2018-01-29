package com.example.android.countryinfo;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class CountryDetailActivity extends AppCompatActivity {

    TextView tvName, tvCapital, tvArea, tvRegion, tvSubRegion, tvCoord, tvBorders, tvPopulation, tvTimezones;
    TextView tvNativeName, tvCurrency, tvRegionalBloc, tvLanguage, tvCallingCode, tvTopLevelDomain, tvTranslations, tvDemonym;
    TextView tvCurrencyCode, tvCurrencyName, tvCurrencySymbol;
    private ImageView flagImageView;
    private LinearLayout languageContainer;

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
        initCurrency();
        tvRegionalBloc = (TextView) findViewById(R.id.tv_regional_bloc);
        flagImageView = (ImageView) findViewById(R.id.iv_flag);

        tvCallingCode = (TextView) findViewById(R.id.tv_calling_code);
        tvTopLevelDomain = (TextView) findViewById(R.id.tv_top_level_domain);
        tvTranslations = (TextView) findViewById(R.id.tv_translations);
        tvDemonym = (TextView) findViewById(R.id.tv_demonym);
        languageContainer = (LinearLayout) findViewById(R.id.language_container);
    }

    private void initCurrency() {
        tvCurrencyCode = (TextView) findViewById(R.id.tv_currency_code_value);
        tvCurrencyName = (TextView) findViewById(R.id.tv_currency_name_value);
        tvCurrencySymbol = (TextView) findViewById(R.id.tv_currency_symbol_value);
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
        Currency curr = CountryDataUtils.getFormattedCurrency(this, currency);
        tvCurrencyCode.setText(curr.getCode());
        tvCurrencyName.setText(curr.getName());
        tvCurrencySymbol.setText(curr.getSymbol());

        String regionalBloc = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.REGIONAL_BLOCS));
        Log.d("tag", regionalBloc);
        tvRegionalBloc.setText(CountryDataUtils.getFormattedRegionalBloc(this, regionalBloc));

        String language = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.LANGUAGES));
        Log.d("tag", language);
        List<String> languageList = CountryDataUtils.getFormattedLanguage(this, language);
        populateLanguage(languageList);

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

    private void populateLanguage(List<String> languageList) {
        for(String language : languageList){
            languageContainer.addView(getLanguagePopulatedView(language));
            Log.d("language", language);
        }
    }

    private TextView getLanguagePopulatedView(String language){
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5,5 );
        textView.setLayoutParams(layoutParams);
        textView.setText(language);
        return textView;
    }
}
