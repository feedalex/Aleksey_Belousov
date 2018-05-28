package com.belousov.aleksey.alexey_belousov;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.belousov.aleksey.alexey_belousov.models.CatNews;
import com.belousov.aleksey.alexey_belousov.models.CatNewsList;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class CatNewsListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CatNewsAdapter catNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_news_list_activity);
        recyclerView = findViewById(R.id.cat_news_recycler_view);
        catNewsAdapter = new CatNewsAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(catNewsAdapter);
    }

    private class CatNewsAdapter extends RecyclerView.Adapter<CatNewsViewHolder> {
        List<CatNews> catNewsList;
        Context context;

        public CatNewsAdapter(Context context) {
            this.catNewsList = CatNewsList.getInsance().getCatNewsList();
            this.context = context;
        }

        @NonNull
        @Override
        public CatNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.cat_news_card_view, parent, false);
            return new CatNewsViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final CatNewsViewHolder holder, int position) {
            final CatNews catNews = catNewsList.get(position);
            holder.titleTextView.setText(catNews.getTitle());
            holder.articleTextView.setText(catNews.getArticle());
            holder.postDateTextView.setText(catNews.getFormattedDate());
            Picasso.get().load(catNews.getImageUrl()).into(holder.listItemImageView);
            holder.listItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CatNewsDetails.class);
                    intent.putExtra("index", holder.getAdapterPosition());
                    startActivity(intent);
                }
            });
            holder.listItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupMenu(v, holder.getAdapterPosition());
                    return false;
                }
            });
            holder.popupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(v, holder.getAdapterPosition());
                }
            });
            holder.likeCheckBox.setText(String.valueOf(catNews.getLike()));
            holder.likeCheckBox.setChecked(catNews.isLiked());
            holder.favoriteCheckBox.setChecked(catNews.isFavorite());
            holder.likeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (holder.likeCheckBox.isChecked()) {
                        int i = Integer.parseInt(String.valueOf(holder.likeCheckBox.getText()));
                        i++;
                        holder.likeCheckBox.setText(Integer.toString(i));
                        holder.likeCheckBox.setChecked(catNews.setLiked(true));
                    } else {
                        int i = Integer.parseInt(String.valueOf(holder.likeCheckBox.getText()));
                        i--;
                        holder.likeCheckBox.setText(Integer.toString(i));
                        holder.likeCheckBox.setChecked(catNews.setLiked(false));
                    }
                }
            });
            holder.favoriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (holder.favoriteCheckBox.isChecked()) {
                        holder.favoriteCheckBox.setChecked(catNews.setFavorite(true));
                    } else {
                        holder.favoriteCheckBox.setChecked(catNews.setFavorite(false));
                    }
                }
            });
        }

        public void addItem(int index) {
            int newPos = index + 1;
            String title = "Новая новость!!!";
            Date postDate = new Date();
            postDate.getTime();
            String article = "Эта новость была добавлена вручную! Позиция добавленной новости: " + newPos;
            String imageUrl = "https://0.gravatar.com/avatar/d2e9e4a8e24a1daf5d3985172ee47078?s=210";
            int likeCounts = 0;
            boolean isLike = false;
            boolean isFavorite = false;
            catNewsList.add(index + 1, new CatNews(title, postDate, article, imageUrl, likeCounts, isLike, isFavorite));
            notifyDataSetChanged();
        }

        public void deleteItem(int index) {
            catNewsList.remove(index);
            notifyItemRemoved(index);
        }

        @Override
        public int getItemCount() {
            return catNewsList.isEmpty() ? 0 : catNewsList.size();
        }

        public void showPopupMenu(View v, final int index) {
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.popup_delete:
                            deleteItem(index);
                            return true;
                        case R.id.popup_add:
                            addItem(index);
                            return true;
                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        }
    }

    private static class CatNewsViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView articleTextView;
        TextView postDateTextView;
        ImageView listItemImageView;
        CardView listItem;
        ImageView popupButton;
        CheckBox likeCheckBox;
        CheckBox favoriteCheckBox;
        //ImageView shareButton;

        public CatNewsViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.cat_news_title_text);
            articleTextView = itemView.findViewById(R.id.cat_news_article);
            postDateTextView = itemView.findViewById(R.id.cat_news_time);
            listItemImageView = itemView.findViewById(R.id.cat_news_head_image);
            listItem = itemView.findViewById(R.id.cat_news_card_view);
            popupButton = itemView.findViewById(R.id.cat_news_item_menu);
            likeCheckBox = itemView.findViewById(R.id.cat_news_like_check);
            favoriteCheckBox = itemView.findViewById(R.id.cat_news_favorite_check);
            //shareButton = itemView.findViewById(R.id.cat_news_share);
        }
    }


}
