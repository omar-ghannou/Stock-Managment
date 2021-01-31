package com.fsdm.wisd.stockmanagment.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fsdm.wisd.stockmanagment.adapter.Adapter;
import com.fsdm.wisd.stockmanagment.R;
import com.fsdm.wisd.stockmanagment.model.Category;
import com.fsdm.wisd.stockmanagment.model.DatabaseHelper;
import com.fsdm.wisd.stockmanagment.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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


/*        mydb.insertIntoCategory("Mobile Téléphones");
        mydb.insertIntoCategory("Mode Homme");
        mydb.insertIntoCategory("Électroniques");
        mydb.insertIntoCategory("Jouets et enfants");
        mydb.insertIntoCategory("Sacs et chaussures");
        mydb.insertIntoCategory("Bijoux et montres");*/

/*        mydb.insertIntoProduct("Portable mini talkie-walkie",
                "2 pièces/ensemble Baofeng T3 BF-T3 Portable mini talkie-walkie pour" +
                        " enfants cadeau radio 0.5W Radio bidirectionnelle Interphone émetteur-récepteur BFT3",
                12,200,1);

        mydb.insertIntoProduct("BF-888S talkie-walkie station de radio Portable",
                "2 pièces/ensemble Baofeng T3 BF-T3 Portable mini talkie-walkie pour" +
                        " enfants cadeau radio 0.5W Radio bidirectionnelle Interphone émetteur-récepteur BFT3",
                15,200,1);

        mydb.insertIntoProduct("costume automne hiver",
                "Riinr marque hommes laine mélanges costume automne hiver nouvelle couleur" +
                        " unie haute qualité hommes laine costume luxueux laine mélanges costume mâ",
                40,150,2);

        mydb.insertIntoProduct(" Micro USB",
                "Prise de câble magnétique Micro USB Type C USB C prise à 8 brochesâ",
                40,150,3);

        mydb.insertIntoProduct(" Lhaya ",
                "lhaya bach yskot",
                4,120,4);

        mydb.insertIntoProduct(" sac ",
                "sac k7al fih warda bida",
                35,110,5);

        mydb.insertIntoProduct(" khatm ",
                "hadak lkhatm li chafto naima samih w3jabha",
                95,20,6);*/



        //fill our categories
        handleCursor();
        mArrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,categories);
        mSpinner=findViewById(R.id.spinner);

        mSpinner.setAdapter(mArrayAdapter);

        mSpinner.setOnItemSelectedListener(this);
        //list
        mListView=findViewById(R.id.list);
//fill product in our table
        //0 for first category
        products(0);

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




        //if(categoryId!=-1){
            mCursorCategory=mydb.getProductsByCategory(categoryId);
            Toast.makeText(this, "number of data "+mCursorCategory.getCount(), Toast.LENGTH_SHORT).show();
       // }
       // else
           // mCursorCategory=mydb.getAllDataFromTable(DatabaseHelper.Product_Table);


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
        products(i);
        //notify the adpter to make change on our list
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}