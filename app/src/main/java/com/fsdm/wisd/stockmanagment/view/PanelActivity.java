package com.fsdm.wisd.stockmanagment.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.fsdm.wisd.stockmanagment.adapter.CustomCursorAdapter;
import com.fsdm.wisd.stockmanagment.R;
import com.fsdm.wisd.stockmanagment.model.DatabaseHelper;

public class PanelActivity extends AppCompatActivity {

    Button buy;
    Button clearall;
    ListView products;
    DatabaseHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        Initialize();
    }

    void Initialize(){

        mydb = new DatabaseHelper(this);
        
        buy = findViewById(R.id.buy);
        clearall = findViewById(R.id.clearall);
        products = findViewById(R.id.pproducts);


        Cursor data =  mydb.getProductsFromPanel();

        if(data.getCount()==0){

            buy.setEnabled(false);
            clearall.setEnabled(false);


        }

        CustomCursorAdapter adapter = new CustomCursorAdapter(
                getBaseContext(),
                data,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );



        products.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.buy();
                mydb.ClearPanel();
                adapter.changeCursor(mydb.getProductsFromPanel());
                adapter.notifyDataSetChanged();
                Intent intent = new Intent(getBaseContext(),CommandsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mydb.ClearPanel();
                adapter.changeCursor(mydb.getProductsFromPanel());
                adapter.notifyDataSetChanged();
                buy.setEnabled(false);
                clearall.setEnabled(false);
            }
        });
    }
}