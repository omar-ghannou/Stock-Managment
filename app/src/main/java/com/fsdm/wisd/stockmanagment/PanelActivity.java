package com.fsdm.wisd.stockmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class PanelActivity extends AppCompatActivity {

    Button buy;
    Button clearall;
    ListView products;
    DatabaseHelper mydb;

    ImageView plusImage;
    ImageView minusImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);

        Initialize();
    }

    void Initialize(){

        mydb = new DatabaseHelper(this);

       // mydb.insertIntoProduct("3asir","3asir bard",10,50,1);
       // mydb.insertIntoProduct("monada","monada wsf",11,30,2);
       // mydb.insertIntoProduct("tvs","tlavs ma7rou9in",100,20,3);
        mydb.insertIntoPanel(1);
        mydb.insertIntoPanel(2);
        mydb.insertIntoPanel(3);

        buy = findViewById(R.id.buy);
        clearall = findViewById(R.id.clearall);
        products = findViewById(R.id.pproducts);


        Cursor data =  mydb.getProductsFromPanel();
        //data.moveToFirst();
        CustomCursorAdapter adapter = new CustomCursorAdapter(
                getBaseContext(),
                //R.layout.product_design_in_panel,
                data,
                //new String[] { DatabaseHelper.Product_Title_Col, DatabaseHelper.Product_Desc_Col,DatabaseHelper.Product_Price_Col,DatabaseHelper.Panel_Product_Quantity_Col },
                //new int[] { R.id.ProductTitle, R.id.ProductDesc,R.id.ProductPrice,R.id.panelQuantity },0
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );



        products.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }
}