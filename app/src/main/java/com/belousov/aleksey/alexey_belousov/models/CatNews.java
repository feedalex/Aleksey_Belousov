package com.belousov.aleksey.alexey_belousov.models;

import android.widget.CheckBox;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CatNews {
    private String title;
    private Date date;
    private String article;
    private String imageUrl;
    private int like;
    private boolean isLiked;
    private boolean isFavorite;

    public CatNews(String title, Date date, String article, String imageUrl, int like, boolean isLiked, boolean isFavorite) {
        this.title = title;
        this.date = date;
        this.article = article;
        this.imageUrl = imageUrl;
        this.like = like;
        this.isLiked = isLiked;
        this.isFavorite = isFavorite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate(){
        return new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date);
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public boolean setLiked(boolean liked) {
        isLiked = liked;
        return isLiked;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public boolean setFavorite(boolean favorite) {
        isFavorite = favorite;
        return isFavorite;
    }

    @Override
    public String toString() {
        return "CatNews{" +
                "title='" + title + '\'' +
                ", date=" + date +
                ", article='" + article + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", like=" + like +
                ", isLiked=" + isLiked +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
