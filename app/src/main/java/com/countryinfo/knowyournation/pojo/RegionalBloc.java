package com.countryinfo.knowyournation.pojo;

/**
 * Created by aditya.sawant on 30-01-2018.
 */

public class RegionalBloc {
    private String acronym;
    private String name;

    public RegionalBloc() {
    }

    public RegionalBloc(String acronym, String name) {
        this.acronym = acronym;
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
