package com.fsdm.wisd.stockmanagment.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fsdm.wisd.stockmanagment.adapter.Adapter;
import com.fsdm.wisd.stockmanagment.R;
import com.fsdm.wisd.stockmanagment.model.Category;
import com.fsdm.wisd.stockmanagment.model.DatabaseHelper;
import com.fsdm.wisd.stockmanagment.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView textCartItemCount;
    Spinner mSpinner;
    DatabaseHelper mydb;
    //for spinner
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
        //change title of this activity
        getSupportActionBar().setTitle(getResources().getString(R.string.productList));
        mydb = new DatabaseHelper(getBaseContext());
        mProducts=new ArrayList<>();

        handleCursor();
        mArrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,categories);
        mSpinner=findViewById(R.id.spinner);

        mSpinner.setAdapter(mArrayAdapter);

        mSpinner.setOnItemSelectedListener(this);
        //list
        mListView=findViewById(R.id.list);
//fill product in our table
        //0 for first category
      //  products(1);

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

    /**
     *
     * @param categoryId will be -1 if we want to get All product else id of category
     */
    public void products(int categoryId){

            mCursorCategory=mydb.getProductsByCategory(categoryId);

        while(mCursorCategory.moveToNext()){
            mProducts.add(
                    new Product(
                    mCursorCategory.getInt(0),
                            mCursorCategory.getString(1),
                    mCursorCategory.getString(2),
                    mCursorCategory.getInt(4),
                    mCursorCategory.getInt(0),
                    mCursorCategory.getDouble(3)
                    )
            );

        }

      //  mAdapter.notifyDataSetChanged();

        Toast.makeText(this, "our table "+mProducts.size(), Toast.LENGTH_SHORT).show();



    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        //lit all products that belong to this category

        //remove all old product
        mProducts.clear();
        //look for the new product
        products(categories.get(i).getId());
        //notify the adpter to make change on our list
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    //for meu items

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        //menu.removeGroup(0);
        menu.getItem(0).setVisible(false);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.command)
        {
            startActivity(new Intent(this,CommandsActivity.class));
            return true;
        }
        if(item.getItemId()==R.id.panel){

            startActivity(new Intent(this,PanelActivity.class));
            return true;

        }
        if(item.getItemId()==R.id.add){

            startActivity(new Intent(this,AddProductActivity.class));
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}