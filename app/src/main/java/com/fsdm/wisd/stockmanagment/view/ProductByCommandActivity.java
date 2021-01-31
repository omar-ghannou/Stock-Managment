package com.fsdm.wisd.stockmanagment.view;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fsdm.wisd.stockmanagment.R;
import com.fsdm.wisd.stockmanagment.model.DatabaseHelper;

import java.util.ArrayList;

public class ProductByCommandActivity extends AppCompatActivity {

    ListView productsInCmd;
    DatabaseHelper mydb;
    ArrayList<String> productsInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_command);
        Initialize();
    }

    private void Initialize(){
        mydb = new DatabaseHelper(getBaseContext());
        productsInCmd = findViewById(R.id.CmdProducts);
        productsInfo = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,productsInfo);
        productsInCmd.setAdapter(adapter);

        populateProductList();

        adapter.notifyDataSetChanged();
    }

    void populateProductList(){
        Cursor c = mydb.getProductsFromPostPanel(getIntent().getIntExtra("ID",-1));
        if(c == null) return;
        while(c.moveToNext()){
            String title = mydb.getProductTitleFromProduct(c.getInt(c.getColumnIndex(DatabaseHelper.Post_Panel_Product_Id_Col)));
            int quantity = c.getInt(c.getColumnIndex(DatabaseHelper.Post_Panel_Product_Quantity_Col));
            String s = "Product : " + title + " -- quantity : " + Integer.toString(quantity);
            productsInfo.add(s);
        }

    }
}
