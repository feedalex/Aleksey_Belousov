package com.belousov.aleksey.alexey_belousov.models;

import android.content.Context;
import android.content.res.Resources;

import com.belousov.aleksey.alexey_belousov.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CatNewsList {
    private static CatNewsList ourInstance = new CatNewsList();
    private List<CatNews> catNewsList;

    private CatNewsList() {
        initlist();
    }

    public static CatNewsList getInsance() {
        if (ourInstance == null) {
            ourInstance = new CatNewsList();
            return ourInstance;
        } else {
            return ourInstance;
        }
    }

    private void initlist(){
        Date date = new Date();
        date.getTime();
        catNewsList = new ArrayList<>();
        for (int i = 0; i < 15; i++){
            String title = "Заголовок новости №" + i;
            Date postDate = date;
            String article = "Смешные и не очень новости про кошек.\n" +
                    "Краткое описание новости будет занимать несколько строк, но мы ограничимся тремя, остальное вынесем в активность с детализацией.";
            String imageUrl = "https://chpic.su/_data/stickers/FunnyCats64/FunnyCats64_004.png";
            int likeCounts = new Random().nextInt(100);
            boolean isLike = false;
            boolean isFavorite = false;
            catNewsList.add(new CatNews(title, postDate, article, imageUrl, likeCounts, isLike, isFavorite));
        }
    }

    public List<CatNews> getCatNewsList(){
        return catNewsList;
    }
}
