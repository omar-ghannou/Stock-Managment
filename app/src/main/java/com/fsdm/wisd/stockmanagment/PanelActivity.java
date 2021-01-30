package com.fsdm.wisd.stockmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PanelActivity extends AppCompatActivity {

    Button buy;
    Button clearall;
    ListView products;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        mydb = new DatabaseHelper(this);
        //mydb.insertIntoProduct("3asir","3asir bard",10,50,1);
        //mydb.insertIntoProduct("monada","monada wsf",11,30,2);
        //mydb.insertIntoProduct("tvs","tlavs ma7rou9in",100,20,3);
        //mydb.insertIntoPanel(1);
        //mydb.insertIntoPanel(2);
        //mydb.insertIntoPanel(3);
        Initialize();
    }

    void Initialize(){

        buy = findViewById(R.id.buy);
        clearall = findViewById(R.id.clearall);
        products = findViewById(R.id.pproducts);
        Cursor data =  mydb.getProductsFromPanel();
        data.moveToFirst();
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.product_design_in_panel,
                data,
                new String[] { DatabaseHelper.Product_Title_Col, DatabaseHelper.Product_Desc_Col,DatabaseHelper.Product_Price_Col,DatabaseHelper.Panel_Product_Quantity_Col },
                new int[] { R.id.ProductTitle, R.id.ProductDesc,R.id.ProductPrice,R.id.panelQuantity }
        );
        Toast.makeText(getBaseContext(), " data size is : " + data.getCount(),Toast.LENGTH_LONG).show();
        //products.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }
}