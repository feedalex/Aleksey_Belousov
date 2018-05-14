package com.belousov.aleksey.alexey_belousov;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.MessageFormat;
import java.util.ArrayList;

public class cat_info_details extends AppCompatActivity {

    Button addButton;
    EditText catName;
    Spinner ageSpinner;
    SeekBar weightSeekBar;
    ListView catsList;
    TextView weightText;
    String selectName;
    String selectAge;
    String selectWeight;

    ArrayList<String> cats = new ArrayList<>();
    ArrayAdapter<String> catAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cats_info_details);

        addButton = findViewById(R.id.cat_add_button);
        catName = findViewById(R.id.cat_name_edit_text);
        ageSpinner = findViewById(R.id.cat_age_spinner);
        weightSeekBar = findViewById(R.id.cat_weight_seekbar);
        catsList = findViewById(R.id.cats_listview);
        weightText = findViewById(R.id.weight_text);

        catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cats);

        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightText.setText(MessageFormat.format(getString(R.string.weight), progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectName = catName.getText().toString();
                selectAge = ageSpinner.getSelectedItem().toString();
                selectWeight = weightText.getText().toString();
                cats.add(selectName + ", " + selectAge + ", " + selectWeight);
                catsList.setAdapter(catAdapter);
            }
        });


    }
}
