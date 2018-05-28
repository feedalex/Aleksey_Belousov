package com.belousov.aleksey.alexey_belousov;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.belousov.aleksey.alexey_belousov.models.CatNews;
import com.belousov.aleksey.alexey_belousov.models.CatNewsList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class CatNewsDetails extends AppCompatActivity {
    final Context context = this;
    protected String emailCatch;
    protected String catName;
    protected String catAgeNum;
    protected String catWeightNum;
    ListView catsListView;
    TextView headTextDescript;
    TextView secondTextDescript;
    TextView fullDescription;
    TextView likeTextView;
    TextView favoriteTextView;
    ImageView photoCat;
    SimpleAdapter catAdapter;
    ArrayList<HashMap<String, String>> cats = new ArrayList<>();
    HashMap<String, String> catMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_details);

        int index = getIntent().getIntExtra("index", 0);
        CatNews catNews = CatNewsList.getInsance().getCatNewsList().get(index);

        catAdapter = new SimpleAdapter(this, cats, android.R.layout.simple_list_item_2,
                new String[]{"Name", "AgeWeight"},
                new int[]{android.R.id.text1, android.R.id.text2});
        catsListView = findViewById(R.id.catList);
        photoCat = findViewById(R.id.photoOfCats);
        Picasso.get().load(catNews.getImageUrl()).into(photoCat);
        headTextDescript = findViewById(R.id.headOfCats);
        headTextDescript.setText(catNews.getTitle());
        secondTextDescript = findViewById(R.id.secondOfCats);
        secondTextDescript.setText(catNews.getArticle());
        likeTextView = findViewById(R.id.likedText);
        if (catNews.isLiked()){
            likeTextView.setVisibility(View.VISIBLE);
        } else {
            likeTextView.setVisibility(View.INVISIBLE);
        }
        favoriteTextView = findViewById(R.id.favoriteText);
        if (catNews.isFavorite()){
            favoriteTextView.setVisibility(View.VISIBLE);
        } else {
            favoriteTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_descriptoon, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_menu = item.getItemId();
        switch (id_menu) {
            case R.id.action_add_cat:
                addNewCat();
                return true;
            case R.id.action_share:
                shareIt();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //вводим данные нового питомца
    protected void addNewCat() {
        orientationLock();
        LayoutInflater addCat = LayoutInflater.from(context);
        View addCatView = addCat.inflate(R.layout.add_cat_dialog, null);
        final EditText catNameInput = addCatView.findViewById(R.id.cat_name_edit_text);
        final Spinner catAgeInput = addCatView.findViewById(R.id.cat_age_spinner);
        final Spinner catWeightInput = addCatView.findViewById(R.id.cat_weight_spinner);
        AlertDialog.Builder addCatDialogBuilder = new AlertDialog.Builder(context);
        addCatDialogBuilder.setView(addCatView);
        addCatDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Добавить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                orientationUnlock();
                            }
                        });
        final AlertDialog alertDialog = addCatDialogBuilder.create();
        alertDialog.show();
        //проверка имени кота
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;
                catName = catNameInput.getText().toString();
                catAgeNum = catAgeInput.getSelectedItem().toString();
                catWeightNum = catWeightInput.getSelectedItem().toString();
                if (!(TextUtils.isEmpty(catName) || catName.length() < 3)) {
                    Toast.makeText(CatNewsDetails.this, "У вас новый птомец: " + catName, Toast.LENGTH_LONG).show();
                    addCat(catName, catAgeNum, catWeightNum);
                    wantToCloseDialog = true;
                    orientationUnlock();
                } else {
                    Toast.makeText(CatNewsDetails.this, "Некорректно введено имя котика!", Toast.LENGTH_LONG).show();
                }
                if (wantToCloseDialog)
                    alertDialog.dismiss();
            }
        });
    }

    //делимся статьей
    protected void shareIt() {
        orientationLock();
        //создание и привязка xml к диалоговому окну
        LayoutInflater sendEmail = LayoutInflater.from(context);
        View emailSendView = sendEmail.inflate(R.layout.email_enter_dialog, null);
        final EditText emailTextInput = emailSendView.findViewById(R.id.emailEditText);
        //создание диалога
        AlertDialog.Builder emailDialogBuilder = new AlertDialog.Builder(context);
        //указание на привязку к xml образцу
        emailDialogBuilder.setView(emailSendView);
        //конструктор алерта
        emailDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Отправить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                orientationUnlock();
                            }
                        });
        final AlertDialog alertDialog = emailDialogBuilder.create();
        alertDialog.show();
        //изменяем обработчик кнопки ОК, чтобы при неверном вводе email диалог не закрывался
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;
                //передаем строке значение из поля ввода email
                emailCatch = emailTextInput.getText().toString();
                //проверяем валидность вводимого Email
                if (isValidEmail(emailCatch)) {
                    Toast.makeText(CatNewsDetails.this, "Отправка письма на " + emailCatch, Toast.LENGTH_LONG).show();
                    sendEmail(emailCatch);
                    wantToCloseDialog = true;
                    orientationUnlock();
                } else {
                    Toast.makeText(CatNewsDetails.this, "Некорректно введен email получателя!", Toast.LENGTH_LONG).show();
                }
                if (wantToCloseDialog)
                    alertDialog.dismiss();
            }
        });
    }

    //как раз тут реализовывается неявный интент
    protected void sendEmail(String email) {
        //отправка по email
        Intent sendEmailIntent = new Intent(android.content.Intent.ACTION_SEND);
        sendEmailIntent.setType("plain/text");
        //кому
        sendEmailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
        //тема
        sendEmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                headTextDescript.getText().toString());
        //текст сообщения
        sendEmailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                secondTextDescript.getText().toString());
        CatNewsDetails.this.startActivity(Intent.createChooser(sendEmailIntent,
                "Отправка письма..."));
    }

    //добавление нового питомца
    protected void addCat(String name, String age, String weight) {
        catMap = new HashMap<>();
        catMap.put("Name", name);
        catMap.put("AgeWeight", "Возраст: " + age + ", " + "вес: " + weight);
        cats.add(catMap);
        catsListView.setAdapter(catAdapter);
    }

    //метод проверки email на валидность
    protected final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    //программная блокировка ориентации экрана
    public void orientationLock() {
        int currentOrientation = this.getResources().getConfiguration().orientation;
        this.setRequestedOrientation(currentOrientation);
    }

    //разблокировка
    public void orientationUnlock() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    //запись в файл
    protected void writeCatsToFile(String name, String age, String weight) {

    }

}
