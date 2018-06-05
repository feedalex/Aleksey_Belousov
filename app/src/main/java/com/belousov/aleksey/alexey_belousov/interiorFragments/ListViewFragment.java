package com.belousov.aleksey.alexey_belousov.interiorFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.belousov.aleksey.alexey_belousov.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ListViewFragment extends Fragment {
    protected String catName;
    protected String catAgeNum;
    protected String catWeightNum;
    SimpleAdapter catAdapter;
    ArrayList<HashMap<String, String>> cats = new ArrayList<>();
    HashMap<String, String> catMap;
    ListView catsListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_view_fragment, container, false);
        catsListView = view.findViewById(R.id.catList);
        for (int i = 0; i < 5; i++) {
            catName = "Мурзик";
            catAgeNum = "1 год";
            catWeightNum = "7 кг";
            addCat(catName, catAgeNum, catWeightNum);
        }
        return view;
    }

    protected void addCat(String name, String age, String weight) {
        catMap = new HashMap<>();
        catMap.put("Name", name);
        catMap.put("AgeWeight", "Возраст: " + age + ", " + "вес: " + weight);
        cats.add(catMap);
        catsListView.setAdapter(catAdapter);
    }
}
