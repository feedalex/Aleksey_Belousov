package com.belousov.aleksey.alexey_belousov;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class cats_list_activity extends AppCompatActivity {

    final String HEADLINE_CAT = "head";
    final String DESCRIPTION_CAT = "desc";
    final String PHOTO_CAT = "image";

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
                String catHeadText = "Временный текст";

                /*String catDescriptText = findViewById(R.id.secondTextCard).toString();
                ImageView catPhotoId = findViewById(R.id.catsPhotoCard);*/

                Intent intent = new Intent(cats_list_activity.this,
                        cat_details_2.class);

                intent.putExtra(cat_details_2.EXTRA_MESSAGE,
                        catHeadText);

                startActivity(intent);

                Toast.makeText(cats_list_activity.this, catHeadText, Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void catsListInit() {
        String[] heads = {"Русская голубая", "Мейнкун", "Сфинкс", "Бенгальская кошка"};
        String[] descriptions = {"Красивая кошка с серой шерстью", "Поначалу они милнькие, пока не вырастают больше тебя",
                "Страшная лысая кошка. Брал крысу, оказался кот", "Я не леопард, но тоже могу сделать кусь"};
        int[] photos = {R.drawable.rus_blue_cat, R.drawable.very_big_cat, R.drawable.fuu_cat, R.drawable.bengalskaya_koshka};

        ArrayList<Map<String, Object>> catsData = new ArrayList<>(heads.length);
        Map<String, Object> catMap;

        for (int i = 0; i < heads.length; i++) {
            catMap = new HashMap<>();
            catMap.put(HEADLINE_CAT, heads[i]);
            catMap.put(DESCRIPTION_CAT, descriptions[i]);
            catMap.put(PHOTO_CAT, photos[i]);
            catsData.add(catMap);
        }

        String[] from = {HEADLINE_CAT, DESCRIPTION_CAT, PHOTO_CAT};
        int[] to = {R.id.headTextCard, R.id.secondTextCard, R.id.catsPhotoCard};

        SimpleAdapter simpleCatAdapter = new SimpleAdapter(this, catsData, R.layout.cat_card_view, from, to);

        catListView.setAdapter(simpleCatAdapter);
    }
}
