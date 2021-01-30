package com.fsdm.wisd.stockmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner mSpinner;
    DatabaseHelper mydb;
    private ArrayAdapter<String> mArrayAdapter;
    //list
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DatabaseHelper(getBaseContext());
        String []category={"Choose category","Computer","Clavier","Telephone"};
        mArrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,category);
        mSpinner=findViewById(R.id.spinner);
        mSpinner.setAdapter(mArrayAdapter);
        mSpinner.setOnItemSelectedListener(this);
        //list
        mListView=findViewById(R.id.list);





    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        //lsit all products that belong to this category
        //adapterView.getItemAtPosition(i)
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}