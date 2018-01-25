package com.example.android.countryinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aditya.sawant on 23-11-2017.
 */

public class CountryListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mCountryList;

    public CountryListAdapter(Context context, List<String> countryList){
        mContext = context;
        mCountryList = countryList;
    }

    @Override
    public int getCount() {
        return mCountryList.size();
    }

    @Override
    public String getItem(int position) {
        return mCountryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CountryViewHolder holder = null;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.country_list_item, parent, false);
            holder = new CountryViewHolder();
            holder.countryName = convertView.findViewById(R.id.tv_country_name);
            convertView.setTag(holder);
        }else{
            holder = (CountryViewHolder) convertView.getTag();
        }

        String countryName = mCountryList.get(position);
        holder.countryName.setText(countryName);
        return convertView;
    }

    public void updateList(List<String> mCountryList) {
        this.mCountryList = mCountryList;
        notifyDataSetChanged();
    }

    class CountryViewHolder {
        TextView countryName;
    }
}
