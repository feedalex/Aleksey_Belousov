package com.belousov.aleksey.alexey_belousov;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class cat_details extends AppCompatActivity {
    final Context context = this;
    public static final String EXTRA_HEAD = "idHead";
    public static final String EXTRA_DESCRIPT = "idDesc";
    public static final String EXTRA_IMAGE = "idImage";
    public String emailCatch;
    public String catName;
    public String catAgeNum;
    public String catWeightNum;
    ListView catsListView;
    TextView headTextDescript;
    TextView secondTextDescript;
    TextView fullDescription;
    ImageView photoCat;
    CheckBox lookedCkeckBox;
    SimpleAdapter catAdapter;
    ArrayList<HashMap<String, String>> cats = new ArrayList<>();
    HashMap<String, String> catMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_details);
        catAdapter = new SimpleAdapter(this, cats, android.R.layout.simple_list_item_2,
                new String[]{"Name", "AgeWeight"},
                new int[]{android.R.id.text1, android.R.id.text2});
        catsListView = findViewById(R.id.catList);
        fullDescription = findViewById(R.id.fullDescriptOfCat);//в последствии удалится из этой строки, так как будет получаться из другой активити
        lookedCkeckBox = findViewById(R.id.lookedCardCheck);
        //принимаем данные с предыдущей активности, расфасовываем их по соответствующим полям
        Intent intent = getIntent();
        if (intent != null) {
            String headMessageText = intent.getStringExtra(EXTRA_HEAD);
            String descMessageText = intent.getStringExtra(EXTRA_DESCRIPT);
            int imageMessageId = intent.getIntExtra(EXTRA_IMAGE, 0);
            headTextDescript = findViewById(R.id.headOfCats);
            secondTextDescript = findViewById(R.id.secondOfCats);
            photoCat = findViewById(R.id.photoOfCats);
            headTextDescript.setText(headMessageText);
            secondTextDescript.setText(descMessageText);
            photoCat.setImageResource(imageMessageId);
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
                Toast.makeText(cat_details.this, "Тест", Toast.LENGTH_SHORT).show();
                shareIt();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void addNewCat() {
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
                    Toast.makeText(cat_details.this, "У вас новый птомец: " + catName, Toast.LENGTH_LONG).show();
                    addCat(catName, catAgeNum, catWeightNum);
                    wantToCloseDialog = true;
                } else {
                    Toast.makeText(cat_details.this, "Некорректно введено имя котика!", Toast.LENGTH_LONG).show();
                }
                if (wantToCloseDialog)
                    alertDialog.dismiss();
            }
        });
    }


    //делимся статьей
    protected void shareIt() {
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
                    Toast.makeText(cat_details.this, "Отправка письма на " + emailCatch, Toast.LENGTH_LONG).show();
                    sendEmail(emailCatch);
                    wantToCloseDialog = true;
                } else {
                    Toast.makeText(cat_details.this, "Некорректно введен email получателя!", Toast.LENGTH_LONG).show();
                }
                if (wantToCloseDialog)
                    alertDialog.dismiss();
            }
        });
    }

    protected void addCat(String name, String age, String weight) {
        catMap = new HashMap<>();
        catMap.put("Name", name);
        catMap.put("AgeWeight", "Возраст: " + age + ", " + "вес: " + weight);
        cats.add(catMap);
        catsListView.setAdapter(catAdapter);
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
        cat_details.this.startActivity(Intent.createChooser(sendEmailIntent,
                "Отправка письма..."));
    }

    //метод проверки email на валидность
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
