package com.belousov.aleksey.alexey_belousov;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.belousov.aleksey.alexey_belousov.interfaces.OnSelectItemListener;
import com.belousov.aleksey.alexey_belousov.interiorFragments.ImageViewFragment;
import com.belousov.aleksey.alexey_belousov.interiorFragments.ListViewFragment;
import com.belousov.aleksey.alexey_belousov.interiorFragments.TextFragment;
import com.belousov.aleksey.alexey_belousov.models.CatNews;
import com.belousov.aleksey.alexey_belousov.models.CatNewsList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class CatNewsDetailsFragment extends Fragment {
    final Context context = getActivity();
    public static final String INDEX = "INDEX";
    public static final String IMAGE = "IMAGE";
    public static final String TEXT = "TEXT";
    public boolean showFragment;
    ImageViewFragment imgFragment = null;
    TextFragment textFragment = null;
    protected String emailCatch;
    protected String catName;
    protected String catAgeNum;
    protected String catWeightNum;
    ListView catsListView;
    TextView headTextDescript;
    TextView secondTextDescript;
    TextView likeTextView;
    TextView favoriteTextView;
    ImageView photoCat;
    SimpleAdapter catAdapter;
    ArrayList<HashMap<String, String>> cats = new ArrayList<>();
    HashMap<String, String> catMap;
    OnSelectItemListener callbackActivity;
    int listIndex;
    FrameLayout frameLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbackActivity = (OnSelectItemListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listIndex = getArguments().getInt(INDEX);
    }

    public static CatNewsDetailsFragment newInstance(int workoutIndex) {
        CatNewsDetailsFragment fragment = new CatNewsDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(INDEX, workoutIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.cat_details_fragment, container, false);
        initUI(view);
        initTextFragment();
        //initImageFragment();
        return view;
    }

    public void initUI(View v) {
        frameLayout = v.findViewById(R.id.iteriorContainer);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showFragment) {
                    initTextFragment();
                } else {
                    initImageFragment();
                }
            }
        });
        CatNews catNews = CatNewsList.getInsance().getCatNewsList().get(listIndex);
        catAdapter = new SimpleAdapter(getActivity(), cats, android.R.layout.simple_list_item_2,
                new String[]{"Name", "AgeWeight"},
                new int[]{android.R.id.text1, android.R.id.text2});
        catsListView = v.findViewById(R.id.catList);
        photoCat = v.findViewById(R.id.photoOfCats);
        Picasso.get().load(catNews.getImageUrl()).into(photoCat);
        headTextDescript = v.findViewById(R.id.headOfCats);
        headTextDescript.setText(catNews.getTitle());
        secondTextDescript = v.findViewById(R.id.secondOfCats);
        secondTextDescript.setText(catNews.getArticle());
        likeTextView = v.findViewById(R.id.likedText);
        if (catNews.isLiked()) {
            likeTextView.setVisibility(View.VISIBLE);
        } else {
            likeTextView.setVisibility(View.INVISIBLE);
        }
        favoriteTextView = v.findViewById(R.id.favoriteText);
        if (catNews.isFavorite()) {
            favoriteTextView.setVisibility(View.VISIBLE);
        } else {
            favoriteTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void initTextFragment() {
        FragmentManager fm = getChildFragmentManager();
        textFragment = new TextFragment();
        fm.beginTransaction()
                .setCustomAnimations(
                        R.animator.flip_right_enter,
                        R.animator.flip_right_exit,
                        R.animator.flip_left_enter,
                        R.animator.flip_left_exit)
                .replace(R.id.iteriorContainer, textFragment, TEXT)
                .commit();
        showFragment = false;
    }

    private void initImageFragment() {
        FragmentManager fm = getChildFragmentManager();
        imgFragment = new ImageViewFragment();
        fm.beginTransaction()
                .setCustomAnimations(
                        R.animator.flip_right_enter,
                        R.animator.flip_right_exit,
                        R.animator.flip_left_enter,
                        R.animator.flip_left_exit)
                .replace(R.id.iteriorContainer, imgFragment, IMAGE)
                .commit();
        showFragment = true;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_descriptoon, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_cat:
                //((MainActivity) Objects.requireNonNull(getActivity())).listFragment();
                //listFragment();
                return true;
            case R.id.action_share:
                shareIt();
                return true;
        }
        return super.onContextItemSelected(item);
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
                    Toast.makeText(getActivity(), "У вас новый птомец: " + catName, Toast.LENGTH_LONG).show();
                    addCat(catName, catAgeNum, catWeightNum);
                    wantToCloseDialog = true;
                    orientationUnlock();
                } else {
                    Toast.makeText(getActivity(), "Некорректно введено имя котика!", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(), "Отправка письма на " + emailCatch, Toast.LENGTH_LONG).show();
                    sendEmail(emailCatch);
                    wantToCloseDialog = true;
                    orientationUnlock();
                } else {
                    Toast.makeText(getActivity(), "Некорректно введен email получателя!", Toast.LENGTH_LONG).show();
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
        CatNewsDetailsFragment.this.startActivity(Intent.createChooser(sendEmailIntent,
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
        getActivity().setRequestedOrientation(currentOrientation);
    }

    //разблокировка
    public void orientationUnlock() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}
