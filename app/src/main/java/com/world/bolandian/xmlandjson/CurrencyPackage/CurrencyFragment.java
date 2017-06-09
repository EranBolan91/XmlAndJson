package com.world.bolandian.xmlandjson.CurrencyPackage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.world.bolandian.xmlandjson.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrencyFragment extends Fragment implements CurrencyDataSource.OnCurrencyArrivedListener {
        private TextView name,currencyCode,rate,change;
        private EditText etCountrySearch;
        private Button btnSearch;

    public CurrencyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CurrencyDataSource.getCurrencies(this);
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_currency, container, false);
        name = (TextView) v.findViewById(R.id.tvName);
        currencyCode = (TextView) v.findViewById(R.id.tvCurrencyCode);
        rate = (TextView) v.findViewById(R.id.tvRate);
        change = (TextView) v.findViewById(R.id.tvChange);
        etCountrySearch = (EditText)v.findViewById(R.id.etCountrySearch);
        btnSearch = (Button)v.findViewById(R.id.btnSearch);


        return v;
    }


    @Override
    public void onCurrencyArrived(final List<CurrencyDataSource.Currency> data,final Exception e) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(e == null) {
                    btnSearch.setVisibility(View.VISIBLE);
                    btnSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String search = etCountrySearch.getText().toString();
                            for (int i = 0; i < data.size(); i++) {
                                if(search.equals(data.get(i).getCountry())){
                                    name.setText(data.get(i).getName());
                                    currencyCode.setText(data.get(i).getCurrencyCode());
                                    rate.setText(String.valueOf(data.get(i).getRate()));
                                    change.setText(String.valueOf(data.get(i).getChange()));
                                }
                            }
                        }
                    });
                }else{
                    Toast.makeText(getContext(),e + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
