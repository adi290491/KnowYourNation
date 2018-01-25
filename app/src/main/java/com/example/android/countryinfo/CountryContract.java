package com.example.android.countryinfo;

import android.provider.BaseColumns;

/**
 * Created by aditya.sawant on 23-11-2017.
 */

public final class CountryContract {

    public class CountryEntry implements BaseColumns{
        public static final String TABLE_NAME = "country_table";
        public static final String NAME = "name";
        public static final String TOP_LEVEL_DOMAIN = "top_level_domain";
        public static final String ALPHA_2_CODE = "alpha2Code";
        public static final String ALPHA_3_CODE = "alpha3Code";
        public static final String CALLING_CODE = "callingCodes";
        public static final String CAPITAL = "capital";
        public static final String ALT_SPELLINGS = "altSpellings";
        public static final String REGION = "region";
        public static final String SUB_REGION = "subregion";
        public static final String POPULATION = "population";
        public static final String LAT_LNG = "latlng";
        public static final String DEMONYM = "demonym";
        public static final String AREA = "area";
        public static final String GINI = "gini";
        public static final String TIMEZONES = "timezones";
        public static final String BORDERS = "borders";
        public static final String NATIVE_NAME = "NATIVE_NAME";
        public static final String NUMERIC_CODE = "numbericCode";
        public static final String CURRENCIES = "currencies";
        public static final String LANGUAGES = "languages";
        public static final String TRANSLATIONS = "translations";
        public static final String FLAG = "flag";
        public static final String REGIONAL_BLOCS = "regional_blocs";
        public static final String CIOC = "cioc";

    }
}
