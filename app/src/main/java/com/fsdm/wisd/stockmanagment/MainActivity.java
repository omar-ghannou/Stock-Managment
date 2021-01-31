package com.fsdm.wisd.stockmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner mSpinner;
    DatabaseHelper mydb;
    private ArrayAdapter<Category> mArrayAdapter;
    //list
    private ListView mListView;
    private ArrayList<Product> mProducts;
    private Adapter mAdapter;
    private Cursor mCursorCategory;
    private List<Category> categories;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //change title of this acitivity
        getSupportActionBar().setTitle(getResources().getString(R.string.productList));
        mydb = new DatabaseHelper(getBaseContext());

        mydb.insertIntoCategory("Mobile Téléphones");
        mydb.insertIntoCategory("Mode Homme");
        mydb.insertIntoCategory("Électroniques");
        mydb.insertIntoCategory("Jouets et enfants");
        mydb.insertIntoCategory("Sacs et chaussures");
        mydb.insertIntoCategory("Bijoux et montres");

        //fill our categories
        handleCursor();



        
        



        mArrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,categories);
        mSpinner=findViewById(R.id.spinner);
        mSpinner.setAdapter(mArrayAdapter);

        mSpinner.setOnItemSelectedListener(this);
        //list
        mListView=findViewById(R.id.list);
        mProducts=new ArrayList<Product>();

        mProducts.add(new Product(1,"Clavier","a smart keyboard with backlight",200,1,200.2));
        mProducts.add(new Product(2,"battery","a smart keyboard with backlight",200,2,200));
        mProducts.add(new Product(3,"souris","a smart keyboard with backlight",200,3,400));
        mAdapter=new Adapter(this,R.layout.item_product,mProducts);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("id",mProducts.get(i).getProductId());
                startActivity(intent);

            }
        });

    }

    /**
     * to trait data commign from database.category
     */
    public void handleCursor(){
        mCursorCategory=mydb.getCategories();
        categories=new ArrayList<>();

        while(mCursorCategory.moveToNext()){

            categories.add(new Category(mCursorCategory.getInt(0),mCursorCategory.getString(1)));

        }
        mCursorCategory.close();




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