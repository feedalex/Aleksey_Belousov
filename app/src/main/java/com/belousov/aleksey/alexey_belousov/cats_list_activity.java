package com.belousov.aleksey.alexey_belousov;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class cats_list_activity extends AppCompatActivity {
    final String HEADLINE_CAT = "head";
    final String DESCRIPTION_CAT = "desc";
    final String PHOTO_CAT = "image";
    final String CHECKBOX = "ckeck";
    String[] heads = {"Русская голубая", "Мейнкун", "Сфинкс", "Бенгальская кошка"};
    String[] descriptions = {"Красивая кошка с серой шерстью", "Поначалу они милнькие, пока не вырастают больше тебя",
            "Страшная лысая кошка. Брал крысу, оказался кот", "Я не леопард, но тоже могу сделать кусь"};
    int[] photos = {R.drawable.rus_blue_cat, R.drawable.very_big_cat, R.drawable.fuu_cat, R.drawable.bengalskaya_koshka};

    ListView catListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cats_list_activity);
        catListView = findViewById(R.id.list_view_cat_main);
        catsListInit();
        catListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View catListView, int position, long id) {
                String catHeadText = heads[position];
                String catDescriptText = descriptions[position];
                int catPhotoId = photos[position];
                //создаем интент, передаем: заголовок, описание, id изображения
                Intent intent = new Intent(cats_list_activity.this,
                        cat_details.class);
                intent.putExtra(cat_details.EXTRA_HEAD, catHeadText);
                intent.putExtra(cat_details.EXTRA_DESCRIPT, catDescriptText);
                intent.putExtra(cat_details.EXTRA_IMAGE, catPhotoId);
                startActivityForResult(intent, 1);
            }
        });
    }


    //заполнение listView
    protected void catsListInit() {
        ArrayList<Map<String, Object>> catsData = new ArrayList<>(heads.length);
        Map<String, Object> catMap;
        for (int i = 0; i < heads.length; i++) {
            catMap = new HashMap<>();
            catMap.put(HEADLINE_CAT, heads[i]);
            catMap.put(DESCRIPTION_CAT, descriptions[i]);
            catMap.put(PHOTO_CAT, photos[i]);
            catsData.add(catMap);
        }
        String[] from = {HEADLINE_CAT, DESCRIPTION_CAT, PHOTO_CAT,};
        int[] to = {R.id.cat_news_title_text, R.id.cat_news_article, R.id.cat_news_head_image};
        SimpleAdapter simpleCatAdapter = new SimpleAdapter(this, catsData, R.layout.cat_news_card_view, from, to);
        catListView.setAdapter(simpleCatAdapter);
    }
}
