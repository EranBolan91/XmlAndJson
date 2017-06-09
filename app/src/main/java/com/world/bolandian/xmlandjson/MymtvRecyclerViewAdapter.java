package com.world.bolandian.xmlandjson;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.world.bolandian.xmlandjson.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the

 * TODO: Replace the implementation with code for your data type.
 */
public class MymtvRecyclerViewAdapter extends RecyclerView.Adapter<MymtvRecyclerViewAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<MtvDataSource.Mtv> mtvNewsList;

    public MymtvRecyclerViewAdapter(Context context, List<MtvDataSource.Mtv> mtvNewsList) {
        this.context = context;
        this.mtvNewsList = mtvNewsList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MtvDataSource.Mtv mtv = mtvNewsList.get(position);
        holder.title.setText(mtv.getTitle());
        holder.link.setText(mtv.getLink());
        holder.description.setText(mtv.getDescription());
        holder.date.setText(mtv.getDate());
    }

    @Override
    public int getItemCount() {
        return mtvNewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,link,description,date;

        public ViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.tvTitle);
            link = (TextView)view.findViewById(R.id.tvLink);
            description = (TextView)view.findViewById(R.id.tvDescription);
            date = (TextView)view.findViewById(R.id.tvDate);
        }
    }
}
