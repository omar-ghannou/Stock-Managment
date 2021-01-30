package com.fsdm.wisd.stockmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Spinner mSpinner;
    DatabaseHelper mydb;
    private ArrayAdapter<String> mArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DatabaseHelper(getBaseContext());
        String []category={"Choose category","Computer","Clavier","Telephone"};
        mArrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,category);
        mSpinner=findViewById(R.id.spinner);
        mSpinner.setAdapter(mArrayAdapter);

    }
}