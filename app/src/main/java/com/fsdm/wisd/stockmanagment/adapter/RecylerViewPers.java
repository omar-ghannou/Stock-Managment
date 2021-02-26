package com.fsdm.wisd.stockmanagment.adapter;
/*
 **    *** Stock Managment ***
 **   Created by EL KHARROUBI HASSAN
 **   At Monday February 2021 14H 08MIN
 */


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fsdm.wisd.stockmanagment.R;
import com.fsdm.wisd.stockmanagment.model.DatabaseHelper;
import com.fsdm.wisd.stockmanagment.model.Product;
import com.fsdm.wisd.stockmanagment.util.DbBitmapUtility;
import com.fsdm.wisd.stockmanagment.view.PanelActivity;

import java.util.List;

public class RecylerViewPers extends RecyclerView.Adapter<RecylerViewPers.Holder> implements View.OnClickListener {

    private Context mContext;
    private List<Product> mProductList;
    private DatabaseHelper myDb;
//button for clikListenrr
    private Button mAdd,mBuy;
    private int position;

    public RecylerViewPers(Context context, List<Product> productList)
    {
        mProductList=productList;
        mContext=context;
        myDb=new DatabaseHelper(mContext);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(mContext).inflate(R.layout.card,parent,false);
        return new Holder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        Log.d("Recyler",mProductList.get(position).toString());

        holder.title.setText(mProductList.get(position).getTitle());
        holder.price.setText(mProductList.get(position).getPrice()+"");
        holder.image.setImageBitmap(DbBitmapUtility.getImage(mProductList.get(position).getProductImage()));

        mAdd=holder.add;
        mAdd.setOnClickListener(this);

        mBuy=holder.buy;
        mBuy.setOnClickListener(this);

        this.position=position;

    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }

    @Override
    public void onClick(View view) {
        Cursor cursor= myDb.getProductQuantityFromPanel(mProductList.get(position).getProductId());
        int quantity=0;



        if(cursor.moveToFirst()){
            quantity=cursor.getInt(  cursor.getColumnIndex(DatabaseHelper.Panel_Product_Quantity_Col));

        }
        if(quantity==0)
        {
            quantity++;
            Toast.makeText(mContext, "+"+quantity, Toast.LENGTH_SHORT).show();
            myDb.insertIntoPanel(mProductList.get(position).getProductId());
        }

        else
        {
            quantity++;
            Toast.makeText(mContext, "+"+quantity, Toast.LENGTH_SHORT).show();
            myDb.incrementQuantityInPanel(mProductList.get(position).getProductId(),quantity);
        }
        //if button is clicked then go to panel
        if(mBuy.getId()==view.getId())
            mContext.startActivity(new Intent(mContext, PanelActivity.class));



    }

    public static class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView price;
        Button add,buy;
        public Holder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.imageProduct);
            title=itemView.findViewById(R.id.productTitle);
            price=itemView.findViewById(R.id.productPrice);
            add=itemView.findViewById(R.id.add);
            buy=itemView.findViewById(R.id.buy);

        }
    }
}
