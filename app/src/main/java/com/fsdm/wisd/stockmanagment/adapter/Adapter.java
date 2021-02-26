package com.fsdm.wisd.stockmanagment.adapter;
/*
 **    *** Stock Managment ***
 **   Created by EL KHARROUBI HASSAN
 **   At Saturday January 2021 16H 00MIN
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fsdm.wisd.stockmanagment.R;
import com.fsdm.wisd.stockmanagment.model.Product;

import java.util.List;

public class Adapter extends ArrayAdapter<Product>  {
    private int mRessource;
    private Context mContext;
    private List<Product> mProducts;
    //views
    private TextView title;
    private TextView desc;
    private TextView price;
    private TextView inStock;



    public Adapter(@NonNull Context context, int resource, List<Product> products) {
        super(context, resource, products);
        mRessource=resource;
        mProducts=products;
        mContext=context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(mRessource,parent,false);
          }
             title=convertView.findViewById(R.id.productTitle);
            // desc=convertView.findViewById(R.id.productDescription);
             price=convertView.findViewById(R.id.productPrice);
             //inStock=convertView.findViewById(R.id.inStock);




            title.setText(mProducts.get(position).getTitle());
            desc.setText(mProducts.get(position).getDescription());
            price.setText(mProducts.get(position).getPrice()+"$");
            inStock.setText("int stock ("+mProducts.get(position).getInStock()+")");

            //ToKtOk@8282



        return convertView;
    }


}
