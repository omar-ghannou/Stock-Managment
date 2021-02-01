package com.fsdm.wisd.stockmanagment.view;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fsdm.wisd.stockmanagment.R;
import com.fsdm.wisd.stockmanagment.model.Category;
import com.fsdm.wisd.stockmanagment.model.DatabaseHelper;
import com.fsdm.wisd.stockmanagment.model.Product;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    private EditText mTitle,mDesc,mInStock,mPrix;
    private Button mAddProduct;
    private Spinner mSpinner;
    private Cursor mCursorCategory;
    private List<Category> categories;
    private ArrayList<Product> mProducts;
    private DatabaseHelper mydb;
    private ArrayAdapter<Category> mArrayAdapter;
    //to access to catego from getProduct to whisch category is selected
    private int catIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getSupportActionBar().setTitle("Ajouter Produit");

        mTitle=findViewById(R.id.ProductName);
        mDesc=findViewById(R.id.ProductDescription);
        mPrix=findViewById(R.id.Price);
        mInStock=findViewById(R.id.Quantity);
        mSpinner=findViewById(R.id.ProductCat);
        mAddProduct=findViewById(R.id.addProduct);
        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProduct();
            }
        });

        mydb = new DatabaseHelper(getBaseContext());
        handleCursor();
        mArrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,categories);


        mSpinner.setAdapter(mArrayAdapter);

        mSpinner.setOnItemSelectedListener(this);

    }

    public void handleCursor(){
        mCursorCategory=mydb.getCategories();
        categories=new ArrayList<>();

        while(mCursorCategory.moveToNext()){

            categories.add(new Category(mCursorCategory.getInt(0),mCursorCategory.getString(1)));

        }
        mCursorCategory.close();

    }

private  void getProduct()
    {
        String title=mTitle.getText().toString();
        String desc=mDesc.getText().toString();
        String quantity=mInStock.getText().toString();
        String price=mPrix.getText().toString();
        String category=mTitle.getText().toString();

        if(title.isEmpty() || desc.isEmpty()|| quantity.isEmpty()||price.isEmpty()||category.isEmpty())
        {
            Toast.makeText(this, "Veuillez remplir toutes les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("Add","heloooooo"+categories.get(catIndex).getId()+" "+categories.get(catIndex).getName());
         if (mydb.insertIntoProduct(title,desc,Integer.parseInt(price),Integer.parseInt(quantity),categories.get(catIndex).getId())!=-1)
         {
             Toast.makeText(this, "le produit est bien ajoute", Toast.LENGTH_SHORT).show();

         }



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        catIndex=i;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}