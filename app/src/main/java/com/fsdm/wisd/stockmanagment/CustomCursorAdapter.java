package com.fsdm.wisd.stockmanagment;

import android.app.LoaderManager;
import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
                if((q-1) >= 0) {
                    ((TextView) view.findViewById(R.id.panelQuantity)).setText(Integer.toString(q - 1));
                    mydb.incrementQuantityInPanel(row_id, q - 1);
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

   //public CustomCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
   //    super(context, layout, c, from, to, flags);
   //    _context = context;
   //    _layout = layout;
   //    cursor = c;
   //    mydb = new DatabaseHelper(context);
   //    ids = new ArrayList<Integer>();
   //    availableQuantities = new ArrayList<Integer>();
   //    inc = new ArrayList<ImageView>();
   //    dec = new ArrayList<ImageView>();
   //    quantity = new ArrayList<EditText>();
   //    deleteItem = new ArrayList<Button>();
   //    positions = new ArrayList<Integer>();
   //}



   //@Override
   //public View getView(int position, View convertView, ViewGroup parent) {
   //    View v= super.getView(position, convertView, parent);
   //    // = LayoutInflater.from(_context).inflate(_layout,parent);
   //    //if(position < cursor.getCount()) {
   //        //cursor.move(1);
   //        positions.add(position);
   //        Toast.makeText(_context, "" + positions.get(position), Toast.LENGTH_LONG).show();
   //        ids.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Product_Id_Col)));
   //        availableQuantities.add(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Product_Quantity_Col)));

   //        inc.add(v.findViewById(R.id.incquantity));
   //        dec.add(v.findViewById(R.id.decquantity));
   //        quantity.add(v.findViewById(R.id.panelQuantity));
   //        deleteItem.add(v.findViewById(R.id.removeProduct));

   //        inc.get(positions.get(position)).setOnClickListener(new View.OnClickListener() {
   //            @Override
   //            public void onClick(View v) {
   //                int q = Integer.parseInt(quantity.get(positions.get(position)).getText().toString());
   //                Toast.makeText(_context, "d : " + q + " a : " + availableQuantities.get(positions.get(position)), Toast.LENGTH_LONG).show();
   //                if (q < availableQuantities.get(positions.get(position))) {
   //                    quantity.get(positions.get(position)).setText(Integer.toString(q + 1));
   //                    mydb.incrementQuantityInPanel(ids.get(positions.get(position)), q + 1);
   //                    notifyDataSetChanged();
   //                }
   //            }
   //        });
   //        //
   //        //dec.setOnClickListener(new View.OnClickListener() {
   //        //    @Override
   //        //    public void onClick(View v) {
   //        //        int q = Integer.parseInt(quantity.getText().toString());
   //        //        if(q > 0) {
   //        //            quantity.setText(Integer.toString(q-1));
   //        //            mydb.incrementQuantityInPanel(id,q-1);
   //        //        }
   //        //    }
   //        //});
   //        //
   //        //deleteItem.setOnClickListener(new View.OnClickListener() {
   //        //    @Override
   //        //    public void onClick(View v) {
   //        //        mydb.deleteFromPanel(id);
   //        //    }
   //        //});
   //        //
   //        //

   //    return v;
   //}




