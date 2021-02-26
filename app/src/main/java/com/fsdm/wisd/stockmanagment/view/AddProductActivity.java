package com.fsdm.wisd.stockmanagment.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fsdm.wisd.stockmanagment.R;
import com.fsdm.wisd.stockmanagment.model.Category;
import com.fsdm.wisd.stockmanagment.model.DatabaseHelper;
import com.fsdm.wisd.stockmanagment.model.Product;
import com.fsdm.wisd.stockmanagment.util.DbBitmapUtility;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private static final int PICK_IMAGE = 100;
    private TextInputEditText mTitle,mDesc,mInStock,mPrix;
    private Button mAddProduct,mChooseImage;
    private Spinner mSpinner;
    private ImageView mImage;
    private Cursor mCursorCategory;
    private List<Category> categories;
    private ArrayList<Product> mProducts;
    private DatabaseHelper mydb;
    private ArrayAdapter<Category> mArrayAdapter;

    private Uri mImageFilePath;
    private Bitmap mBitmap;
    private ByteArrayOutputStream outputStream;
    private byte[] mByte;

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
        mSpinner= findViewById(R.id.ProductCat);
        mAddProduct=findViewById(R.id.addProduct);
        mChooseImage=findViewById(R.id.productImageChooser);
        mImage=findViewById(R.id.image);

        mAddProduct.setOnClickListener(this);
        mChooseImage.setOnClickListener(this);


        mydb = new DatabaseHelper(getBaseContext());
        handleCursor();
        Log.d("add",categories.size()+"");
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
         if (mydb.insertIntoProduct(title,desc,Integer.parseInt(price),Integer.parseInt(quantity), mByte, categories.get(catIndex).getId())!=-1)
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

    private void getImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_IMAGE :
               mImageFilePath=  data.getData();
                Log.d("add",mImageFilePath.getPath());
                try {
                  mBitmap=  MediaStore.Images.Media.getBitmap(getContentResolver(),mImageFilePath);
                  mImage.setImageBitmap(mBitmap);
                  mChooseImage.setText("Changer Image");
               mByte=   DbBitmapUtility.getBytes(mBitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==mAddProduct.getId())
        {
            getProduct();
            return;
        }
        if(view.getId()==mChooseImage.getId())
        {
            getImage();
            return;
        }

    }
}