package com.countryinfo.knowyournation.countrylist;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.countryinfo.knowyournation.R;
import com.countryinfo.knowyournation.adapter.CountryListAdapter;
import com.countryinfo.knowyournation.utils.CountryUtils;
import com.countryinfo.knowyournation.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountryListFragment extends Fragment {


    private ListView countryListView;
    private CountryListAdapter mCountryListAdapter;
    private List<String> mCountryList = new ArrayList<>();
    private ProgressBar loadingBar;
    private CountrySelectListener listener;

    public CountryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (CountrySelectListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country_list, container, false);
        init(view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingBar.setVisibility(View.VISIBLE);
        fetchCountryList();
    }

    private void fetchCountryList() {

        if(CountryUtils.isDatabaseEmpty(getContext())) {
            new CountryAsyncTask().execute();
        }else {
            mCountryList = CountryUtils.fetchCountryNames(getContext());
            mCountryListAdapter.updateList(mCountryList);
            loadingBar.setVisibility(GONE);
            listener.onCountrySelected(mCountryList.get(0));
        }

    }

    private void init(View view) {
        countryListView = (ListView) view.findViewById(R.id.country_list);
        mCountryListAdapter = new CountryListAdapter(getContext(), mCountryList);
        countryListView.setAdapter(mCountryListAdapter);
        countryListView.setOnItemClickListener(countrySelectListener);

        loadingBar = (ProgressBar) view.findViewById(R.id.loading_bar);
    }

    public class CountryAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            URL url = NetworkUtils.buildUrl();
            Log.d("tag", url.toString());
            try {
                String jsonString = NetworkUtils.fetchCountryList(getContext(), url);
                CountryUtils.insertIntoDatabase(getContext(), jsonString);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mCountryList = CountryUtils.fetchCountryNames(getContext());
            mCountryListAdapter.updateList(mCountryList);
            loadingBar.setVisibility(GONE);
            listener.onCountrySelected(mCountryList.get(0));
        }
    }

    AdapterView.OnItemClickListener countrySelectListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String countryName = (String) parent.getItemAtPosition(position);
            listener.onCountrySelected(countryName);
        }
    };

    public interface CountrySelectListener {
        void onCountrySelected(String name);
    }
}
