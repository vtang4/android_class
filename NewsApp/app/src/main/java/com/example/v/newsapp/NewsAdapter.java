package com.example.v.newsapp;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder>{

 //   private final LayoutInflater mInflater;
    private List<NewsItem> newsItems;
 private NewsItemViewModel viewModel;
    private itemClickListener mItemClickListener;

    public NewsAdapter(itemClickListener itemClickListener, NewsItemViewModel newsItemViewModel) {
        this.viewModel = newsItemViewModel;
     //   this.mInflater = LayoutInflater.from(mContext);
        this.mItemClickListener = itemClickListener;
    }

    public interface itemClickListener{
        void onItemClick(int i);
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater mInflater= LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.news_items_display, parent,false);


        return new NewsItemViewHolder(view);

    }


    @Override
    public void onBindViewHolder(NewsItemViewHolder newsHolder, int i) {

       newsHolder.bind(i);

    }

    void setNewsItems(List<NewsItem> items){
        newsItems = items;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(newsItems != null)
            return newsItems.size();
        else return 0;
    }


     class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

      private final  TextView title;
      private final  TextView description;
      private final  TextView date;
      private final ImageView imageView;

        public NewsItemViewHolder( View itemView) {
            super(itemView);


            title =  itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            date =  itemView.findViewById(R.id.date);
            imageView = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(this);

        }

        void bind(final int listIndex){

            Uri image = Uri.parse(newsItems.get(listIndex).getUrlToImage());

            if(image != null)
            {
                Picasso.get().load(image).into(imageView);
            }

            title.setText( newsItems.get(listIndex).getTitle());
            description.setText(newsItems.get(listIndex).getPublishedAt() + " . "
                    + newsItems.get(listIndex).getDescription());

          //  date.setText("Date: " + newsItems.get(listIndex).getPublishedAt());
        }

        @Override
        public void onClick(View v) {

//            String url = newsItems.get(getAdapterPosition()).getUrl();
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            startActivity(intent);
//
            int i = getAdapterPosition();
            mItemClickListener.onItemClick(i);


        }
    }
}
