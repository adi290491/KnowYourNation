package com.countryinfo.knowyournation.countrydetails;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.countryinfo.knowyournation.R;
import com.countryinfo.knowyournation.RegionalBlocCustomView;
import com.countryinfo.knowyournation.data.CountryContract;
import com.countryinfo.knowyournation.pojo.Currency;
import com.countryinfo.knowyournation.pojo.RegionalBloc;
import com.countryinfo.knowyournation.utils.CountryDataUtils;
import com.countryinfo.knowyournation.utils.CountryUtils;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountryDetailFragment extends Fragment {


    TextView tvName, tvCapital, tvArea, tvRegion, tvSubRegion, tvCoord, tvBorders, tvPopulation, tvTimezones;
    TextView tvNativeName, tvCurrency, tvRegionalBloc, tvLanguage, tvCallingCode, tvTopLevelDomain, tvTranslations, tvDemonym;
    TextView tvCurrencyCode, tvCurrencyName, tvCurrencySymbol;
    private ImageView flagImageView;
    private LinearLayout languageContainer, regionalBlocContainer;

    public CountryDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country_detail, container, false);
        init(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void init(View view) {
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvCapital = (TextView) view.findViewById(R.id.tv_capital_value);
        tvArea = (TextView) view.findViewById(R.id.tv_area_value);
        tvRegion = (TextView) view.findViewById(R.id.tv_region_value);
        tvSubRegion = (TextView) view.findViewById(R.id.tv_subregion_value);
        tvCoord = (TextView) view.findViewById(R.id.tv_coord_value);
        tvBorders = (TextView) view.findViewById(R.id.tv_borders_value);
        tvPopulation = (TextView) view.findViewById(R.id.tv_population_value);
        tvTimezones = (TextView) view.findViewById(R.id.tv_timezones_value);
        tvNativeName = (TextView) view.findViewById(R.id.tv_native_name_value);
        initCurrency(view);
        flagImageView = (ImageView) view.findViewById(R.id.iv_flag);

        tvCallingCode = (TextView) view.findViewById(R.id.tv_calling_code_value);
        tvTopLevelDomain = (TextView) view.findViewById(R.id.tv_top_level_domain_value);

        tvDemonym = (TextView) view.findViewById(R.id.tv_demonym_value);
        languageContainer = (LinearLayout) view.findViewById(R.id.language_container);
        regionalBlocContainer = (LinearLayout) view.findViewById(R.id.regional_bloc_container);
    }

    private void initCurrency(View view) {
        tvCurrencyCode = (TextView) view.findViewById(R.id.tv_currency_code_value);
        tvCurrencyName = (TextView) view.findViewById(R.id.tv_currency_name_value);
        tvCurrencySymbol = (TextView) view.findViewById(R.id.tv_currency_symbol_value);
    }

    private void fetchCountryDetails(String countryName) {
        Cursor cursor = CountryUtils.getCountryDetailsByName(getContext(), countryName);
        populateData(cursor);
    }

    private void populateData(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.NAME));
        Log.d("tag", name);
        tvName.setText(name);

        String flagUrl = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.FLAG));
        Log.d("tag", flagUrl);
        CountryDataUtils.loadCountryFlag(getContext(), flagUrl, flagImageView, name);

        String capital = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.CAPITAL));
        Log.d("tag", capital);
        tvCapital.setText(!capital.equalsIgnoreCase("") ? capital : "N/A");

        String area = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.AREA));
        Log.d("tag", area);
        tvArea.setText(!area.equalsIgnoreCase("") ? area : "N/A");

        String region = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.REGION));
        Log.d("tag", region);
        tvRegion.setText(!region.equalsIgnoreCase("") ? region : "N/A");

        String subRegion = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.SUB_REGION));
        Log.d("tag", subRegion);
        tvSubRegion.setText(!subRegion.equalsIgnoreCase("") ? subRegion : "N/A");

        String coord = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.LAT_LNG));
        Log.d("tag", coord);
        tvCoord.setText(!coord.equalsIgnoreCase("") ? coord : "N/A");

        String border = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.BORDERS));
        Log.d("tag", border);
        tvBorders.setText(!border.equalsIgnoreCase("") ? border : "N/A");

        String population = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.POPULATION));
        Log.d("tag", population);
        tvPopulation.setText(!population.equalsIgnoreCase("") ? population : "N/A");

        String timezones = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.TIMEZONES));
        Log.d("tag", timezones);
        tvTimezones.setText(!timezones.equalsIgnoreCase("") ? timezones : "N/A");

        String nativeName = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.NATIVE_NAME));
        Log.d("tag", nativeName);
        tvNativeName.setText(!nativeName.equalsIgnoreCase("") ? nativeName : "N/A");

        String currency = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.CURRENCIES));
        Log.d("tag", currency);
        Currency curr = CountryDataUtils.getFormattedCurrency(getContext(), currency);
        tvCurrencyCode.setText(curr.getCode());
        tvCurrencyName.setText(curr.getName());
        tvCurrencySymbol.setText(curr.getSymbol());

        String regionalBloc = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.REGIONAL_BLOCS));
        Log.d("tag", regionalBloc);
        List<RegionalBloc> regionalBlocs = CountryDataUtils.getFormattedRegionalBloc(getContext(), regionalBloc);
        setRegionalBlocs(regionalBlocs);

        String language = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.LANGUAGES));
        Log.d("tag", language);
        List<String> languageList = CountryDataUtils.getFormattedLanguage(getContext(), language);
        populateLanguage(languageList);

        String callingCode = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.CALLING_CODE));
        Log.d("tag", callingCode);
        List<String> callingCodeList = CountryDataUtils.getFormattedCallingCode(getContext(), callingCode);
        tvCallingCode.setText(callingCodeList.get(0));

        String topLevelDomain = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.TOP_LEVEL_DOMAIN));
        Log.d("tag", topLevelDomain);
        List<String> topLevelDomainList = CountryDataUtils.getFormattedTopLevelDomain(getContext(), topLevelDomain);
        //tvTopLevelDomain.setText(topLevelDomainList.get(0));

        String demonym = cursor.getString(cursor.getColumnIndex(CountryContract.CountryEntry.DEMONYM));
        Log.d("tag", demonym);
        tvDemonym.setText(!demonym.equalsIgnoreCase("") ? demonym : "N/A");
    }

    private void setRegionalBlocs(List<RegionalBloc> regionalBlocs) {
        RegionalBlocCustomView view = new RegionalBlocCustomView(getContext());
        if(regionalBlocs == null){
            RegionalBloc bloc = new RegionalBloc("N/A", "N/A");
            view.setData(bloc);
        }else {
            for (RegionalBloc rb : regionalBlocs) {
                view.setData(rb);
            }
        }
        regionalBlocContainer.addView(view);
    }

    private void populateLanguage(List<String> languageList) {
        for(String language : languageList){
            languageContainer.addView(getLanguagePopulatedView(language));
            Log.d("language", language);
        }
    }

    private TextView getLanguagePopulatedView(String language){
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5,5 );
        textView.setLayoutParams(layoutParams);
        textView.setText(language);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        return textView;
    }

    public void showCountryInfo(String name) {
        clearContainers();
        fetchCountryDetails(name);
    }

    private void clearContainers() {
        regionalBlocContainer.removeAllViews();
        languageContainer.removeAllViews();
    }
}
