package com.belousov.aleksey.alexey_belousov;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class cat_details_2 extends AppCompatActivity {
    final Context context = this;
    public static final String EXTRA_HEAD = "idHead";
    public static final String EXTRA_DESCRIPT = "idDesc";
    public static final String EXTRA_IMAGE = "idImage";
    public String emailCatch;
    ImageButton share_button_email;
    TextView headTextDescript;
    TextView secondTextDescript;
    TextView fullDescription;
    ImageView photoCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_details_2);

        headTextDescript = findViewById(R.id.headOfCats);
        secondTextDescript = findViewById(R.id.secondOfCats);
        fullDescription = findViewById(R.id.fullDescriptOfCat);
        share_button_email = findViewById(R.id.shareCatEmail);
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

        share_button_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        .setPositiveButton("OK",
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
                            Toast.makeText(cat_details_2.this, "Отправка письма на " + emailCatch, Toast.LENGTH_LONG).show();
                            sendEmail(emailCatch);
                            wantToCloseDialog = true;
                        } else {
                            Toast.makeText(cat_details_2.this, "Некорректно введен email получателя!", Toast.LENGTH_LONG).show();
                        }
                        if (wantToCloseDialog)
                            alertDialog.dismiss();
                    }
                });
            }
        });
    }

    public void sendEmail(String email) {
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
        cat_details_2.this.startActivity(Intent.createChooser(sendEmailIntent,
                "Отправка письма..."));
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
