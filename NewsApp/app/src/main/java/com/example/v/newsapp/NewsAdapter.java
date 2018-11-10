package com.example.v.newsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder> {

     Context mContext;
    ArrayList<NewsItem> mNewsItems;

    public NewsAdapter(Context mContext, ArrayList<NewsItem> mNewsItems) {
        this.mContext = mContext;
        this.mNewsItems = mNewsItems;
    }


    @Override
    public NewsAdapter.NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_item, parent,shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsItemViewHolder newsHolder, int i) {

        newsHolder.bind(i);

    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }


    public class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView description;
        TextView date;

        public NewsItemViewHolder( View itemView) {
            super(itemView);


            title =  itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            date =  itemView.findViewById(R.id.date);
        }

        void bind(final int listIndex){
            title.setText("Title: " + mNewsItems.get(listIndex).getTitle());
            description.setText("Description: " + mNewsItems.get(listIndex).getDescription());
            date.setText("Date: " + mNewsItems.get(listIndex).getPublishedAt());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            String url = mNewsItems.get(getAdapterPosition()).getUrl();
            Intent intent = new Intent(mContext,WebActivity.class);
            intent.putExtra("url", url);
            mContext.startActivity(intent);

        }
    }
}
