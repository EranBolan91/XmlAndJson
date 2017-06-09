package com.world.bolandian.xmlandjson;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class MtvFragment extends Fragment implements MtvDataSource.OnMtvNewsArrivedListener {
    private RecyclerView listMtvNews;
    ArrayList<MtvDataSource.Mtv> mtvNewsArrayList = new ArrayList<>();
    // TODO: Customize parameters
    private int mColumnCount = 1;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MtvFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_mtv_list, container, false);
        listMtvNews = (RecyclerView) view.findViewById(R.id.list);
        MtvDataSource.getMtvNews(this);


        return view;
    }


    @Override
    public void onMtvNewsArrived(final ArrayList<MtvDataSource.Mtv> data,final Exception e) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(e == null) {
                    mtvNewsArrayList = data;
                    listMtvNews.setAdapter(new MymtvRecyclerViewAdapter(getContext(), mtvNewsArrayList));
                    listMtvNews.setLayoutManager(new LinearLayoutManager(getContext()));
                }else{
                    Toast.makeText(getContext(), e + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}






