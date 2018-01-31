package com.example.android.countryinfo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by aditya.sawant on 30-01-2018.
 */

public class RegionalBlocCustomView extends LinearLayout{

    private TextView tvAcronym, tvName;
    public RegionalBlocCustomView(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.regional_bloc_item, this);
        init();
    }

    public RegionalBlocCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.regional_bloc_item, this);
        init();
    }

    public RegionalBlocCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.regional_bloc_item, this);
        init();
    }

    private void init(){
        tvAcronym = findViewById(R.id.tv_acronym_value);
        tvName = findViewById(R.id.tv_bloc_name_value);
    }

    public void setData(RegionalBloc regionalBloc){
        tvAcronym.setText(regionalBloc.getAcronym());
        tvName.setText(regionalBloc.getName());
    }
}
