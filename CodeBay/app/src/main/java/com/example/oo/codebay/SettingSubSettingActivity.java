package com.example.oo.codebay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingSubSettingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_sub_settings);
        Spinner spLang = (Spinner) findViewById(R.id.spLang);
        Spinner spFonts = (Spinner) findViewById(R.id.spFonts);
        Spinner spTheme = (Spinner) findViewById(R.id.spTheme);
        spFonts.setOnItemSelectedListener(this);
        spTheme.setOnItemSelectedListener(this);
        spLang.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
