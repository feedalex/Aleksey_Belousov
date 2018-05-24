package com.belousov.aleksey.alexey_belousov;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class test_activity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "saveCheckbox";
    public static final String APP_PREFERENCES_LIKE = "like";
    public static final String APP_PREFERENCES_FAVORITE = "favorite";
    String cat_news_head_text;
    String cat_news_article_text;
    Boolean cat_news_like;
    Boolean cat_news_favorite;
    TextView headTextView;
    TextView articleTextView;
    CheckBox likeCheck;
    CheckBox favoriteCheck;
    SharedPreferences checkSave;
    CardView catCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_news_card_view);

        catCardView = findViewById(R.id.car_nws_card_view);
        checkSave = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        headTextView = findViewById(R.id.cat_news_title_text);
        articleTextView = findViewById(R.id.cat_news_article);
        likeCheck = findViewById(R.id.cat_news_like_check);
        favoriteCheck = findViewById(R.id.cat_news_favorite_check);


        checkSave = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = checkSave.edit();

        if (checkSave.contains("likeChecked") && checkSave.getBoolean("likeChecked", false)) {
            likeCheck.setChecked(true);
        } else {
            likeCheck.setChecked(false);
        }

        if (checkSave.contains("favoriteChecked") && checkSave.getBoolean("favoriteChecked", false)) {
            favoriteCheck.setChecked(true);
        } else {
            favoriteCheck.setChecked(false);
        }

        likeCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (likeCheck.isChecked()) {
                    editor.putBoolean("likeChecked", true);
                    editor.apply();
                } else {
                    editor.putBoolean("likeChecked", false);
                    editor.apply();
                }
            }
        });

        favoriteCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (favoriteCheck.isChecked()) {
                    editor.putBoolean("favoriteChecked", true);
                    editor.apply();
                } else {
                    editor.putBoolean("favoriteChecked", false);
                    editor.apply();
                }
            }
        });

        catCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_news_head_text = headTextView.getText().toString();
                cat_news_article_text = articleTextView.getText().toString();
                cat_news_like = likeCheck.isChecked();
                cat_news_favorite = favoriteCheck.isChecked();
                        Intent intent = new Intent(test_activity.this,
                        cat_details.class);
                intent.putExtra(cat_details.EXTRA_HEAD, cat_news_head_text);
                intent.putExtra(cat_details.EXTRA_DESCRIPT, cat_news_article_text);
                intent.putExtra(cat_details.EXTRA_LIKE, cat_news_like);
                intent.putExtra(cat_details.EXTRA_FAVORITE, cat_news_favorite);
                startActivityForResult(intent, 1);
            }
        });


    }
}


