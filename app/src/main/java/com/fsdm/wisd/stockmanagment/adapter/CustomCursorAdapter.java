package com.fsdm.wisd.stockmanagment.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fsdm.wisd.stockmanagment.R;
import com.fsdm.wisd.stockmanagment.model.DatabaseHelper;
import com.fsdm.wisd.stockmanagment.util.DbBitmapUtility;

public class CustomCursorAdapter extends CursorAdapter {

    Context _context;
    Cursor _cursor;

    DatabaseHelper mydb;

    LayoutInflater inflater;

    public CustomCursorAdapter(Context context, Cursor c, int flags){
        super(context,c,flags);
        inflater = LayoutInflater.from(context);
        _context = context;
        _cursor =c;
        mydb = new DatabaseHelper(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor){
        int row_id = cursor.getInt(cursor.getColumnIndex("_id"));
        ((TextView)view.findViewById(R.id.panelQuantity)).setText(Integer.toString(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Panel_Product_Quantity_Col))));
        ((TextView)view.findViewById(R.id.ProductTitle)).setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Product_Title_Col)));
        ((TextView)view.findViewById(R.id.ProductDesc)).setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.Product_Desc_Col)));
        ((TextView)view.findViewById(R.id.ProductPrice)).setText(Integer.toString(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Product_Price_Col))));
        ((ImageView)view.findViewById(R.id.productImage)).
                setImageBitmap(DbBitmapUtility.getImage(cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.Product_Image))));


        ((ImageView)view.findViewById(R.id.incquantity)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int q = Integer.parseInt(((TextView)view.findViewById(R.id.panelQuantity)).getText().toString());
                if((q+1) <= cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Product_Quantity_Col))) {
                    ((TextView) view.findViewById(R.id.panelQuantity)).setText(Integer.toString(q + 1));
                    mydb.incrementQuantityInPanel(row_id, q + 1);
                    onContentChanged();
                }
            }
        });

        ((ImageView)view.findViewById(R.id.decquantity)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int q = Integer.parseInt(((TextView)view.findViewById(R.id.panelQuantity)).getText().toString());
                if((q-1) > 0) {
                    ((TextView) view.findViewById(R.id.panelQuantity)).setText(Integer.toString(q - 1));
                    mydb.incrementQuantityInPanel(row_id, q - 1);
                    onContentChanged();
                }
                else if((q-1) == 0){
                    mydb.deleteFromPanel(row_id);
                    changeCursor(mydb.getProductsFromPanel());
                    onContentChanged();
                }
            }
        });


        ((Button)view.findViewById(R.id.removeProduct)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mydb.deleteFromPanel(row_id);
                changeCursor(mydb.getProductsFromPanel());
                onContentChanged();
            }
        });

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.product_design_in_panel, parent, false);
        bindView(v,context,cursor);
        return v;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }


}
