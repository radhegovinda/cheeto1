package com.example.android.cheeto;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private List<Article> articles;
    private Context mContext;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.mContext = context;
        this.articles = articles;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView section, headline, date, author;
        private ImageView imageItem;

        public ViewHolder(View articleView) {
            super(articleView);

            section = articleView.findViewById(R.id.section);
            headline = articleView.findViewById(R.id.headline);
            date = articleView.findViewById(R.id.date);
            author = articleView.findViewById(R.id.author);
            imageItem = articleView.findViewById(R.id.imageItem);
        }

        public void bind(final Article articleList) {
            section.setText(articleList.getCategory());
            headline.setText(articleList.getHeadline());
            String rawDate = articleList.getWebPublicationDate();
            String formattedDate = formatDate(rawDate);
            author.setText(articleList.getAuthor());

            date.setText(formattedDate);

            String imageItemUrl = articleList.getThumbnail();
            Glide.with(mContext).load(imageItemUrl).into(imageItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.onArticleClick(mContext, articleList.getWebUrl());
                }
            });
        }
    }

    public void clearArticles() {
        articles.clear();
        notifyDataSetChanged();
    }

    public void addData(List<Article> data) {
        articles.addAll(data);
        notifyDataSetChanged();  // Necessary to refresh view
    }

    private String formatDate(String dateObject) {

        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.UK);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.UK);

        Date date = null;
        try {
            date = inputFormat.parse(dateObject);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(date);
    }
}
