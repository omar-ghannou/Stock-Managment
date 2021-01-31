package com.fsdm.wisd.stockmanagment;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context _context;
    public static SQLiteDatabase mydb;
    public static final String dbname = "stockManagement.db";

    public static final String Product_Table = "product";
    public static final String Product_Id_Col = "product_id";
    public static final String Product_Title_Col = "product_title";
    public static final String Product_Desc_Col = "product_description";
    public static final String Product_Price_Col = "product_price";
    public static final String Product_Quantity_Col = "product_quantity";
    public static final String Product_Category_Ref_Col = "product_category_ref";

    public static final String Category_Table = "category";
    public static final String Category_Id_Col = "category_id";
    public static final String Category_Name_Col = "category_name";

    public static final String Panel_Table = "panel";
    public static final String Panel_Product_Id_Col = "panel_product_id";
    public static final String Panel_Product_Quantity_Col = "panel_product_quantity";

    public static final String Command_Table = "command";
    public static final String Command_Id_Col = "command_id";
    public static final String Command_Date_Col = "command_date";

    public static final String Post_Panel_Table = "post_panel"; //used to store the already purchased products to be referenced in command table
    public static final String Post_Panel_Product_Id_Col = "post_panel_productId";
    public static final String Post_Panel_Product_Quantity_Col = "post_panel_product_quantity";
    public static final String Post_Panel_Command_Ref_Col = "post_panel_command_ref";

    public DatabaseHelper(@Nullable Context context) {
        super(context, dbname, null, 1);
        _context = context;
        mydb =  this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table IF NOT EXISTS "+ Product_Table +"(" +Product_Id_Col + " INTEGER primary key AUTOINCREMENT , "+ Product_Title_Col +" TEXT NOT NULL, " + Product_Desc_Col +" TEXT," +
                 Product_Price_Col + " INTEGER NOT NULL, " + Product_Quantity_Col + " INTEGER NOT NULL, " + Product_Category_Ref_Col + " INTEGER );");

        db.execSQL("create table IF NOT EXISTS "+ Category_Table +"(" +Category_Id_Col + " INTEGER primary key AUTOINCREMENT, "+ Category_Name_Col +" TEXT NOT NULL);");

        db.execSQL("create table IF NOT EXISTS "+ Panel_Table +"(" + Panel_Product_Id_Col + " INTEGER primary key , "+ Panel_Product_Quantity_Col +" INTEGER NOT NULL);");

        db.execSQL("create table IF NOT EXISTS "+ Post_Panel_Table +"(" + Post_Panel_Product_Id_Col + " INTEGER primary key , "+ Post_Panel_Product_Quantity_Col +" INTEGER NOT NULL, " + Post_Panel_Command_Ref_Col +" INTEGER NOT NULL);");

        db.execSQL("create table IF NOT EXISTS "+ Command_Table +"(" + Command_Id_Col + " INTEGER primary key AUTOINCREMENT , "+ Command_Date_Col +" TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertIntoProduct(String Product_Title,String Product_Description, int price, int Available_Quantity, int Category){
        mydb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Product_Title_Col,Product_Title);
        values.put(Product_Desc_Col,Product_Description);
        values.put(Product_Price_Col, price);
        values.put(Product_Quantity_Col,Available_Quantity);
        values.put(Product_Category_Ref_Col,Category);
        mydb.insert(Product_Table,null,values);
    }

    public Cursor getCategories(int Category_Id){
        mydb = getReadableDatabase();
        return mydb.rawQuery("select *  from " + Product_Table + " where " + Product_Category_Ref_Col + " = " + Category_Id,null);
    }

    public void deleteFromProduct(String name){
        mydb = getWritableDatabase();
        mydb.delete(Product_Table,"" + Product_Title_Col + " = ?",new String[]{name});

    }

    public void insertIntoCategory(String Category_Name){
        mydb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Category_Name_Col,Category_Name);
        mydb.insert(Category_Table,null,values);
    }

    public void deleteFromCategory(String name){
        mydb = getWritableDatabase();
        mydb.delete(Category_Table,"" + Category_Name_Col + " = ?",new String[]{name});
    }

    public Cursor getCategories(){
        mydb = getReadableDatabase();
        return mydb.rawQuery("select *  from " + Category_Table,null);
    }

    public void insertIntoPanel(int Product_Id){
        mydb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Panel_Product_Id_Col,Product_Id);
        values.put(Panel_Product_Quantity_Col,1);
        mydb.insert(Panel_Table,null,values);
    }

    public void incrementQuantityInPanel(int Product_Id,int New_Value){
        mydb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Panel_Product_Quantity_Col,New_Value);
        mydb.update(Panel_Table,values,"" + Panel_Product_Id_Col + " = ?", new String[]{Integer.toString(Product_Id)});
    }

    public Cursor getProductsFromPanel(){
        mydb = getReadableDatabase();
        return mydb.rawQuery("select p." + Product_Id_Col + " as _id ,p.*, pp." +Panel_Product_Quantity_Col + " from "+ Product_Table + " p, "+ Panel_Table +" pp where p."+ Product_Id_Col +" = pp." + Panel_Product_Id_Col+ " AND " + Product_Id_Col + " IN ( Select " +Panel_Product_Id_Col+ " from " +Panel_Table+ ")" ,null);
    }

    public Cursor getProductQuantityFromPanel(int Product_Id){
        mydb = getReadableDatabase();
        return mydb.rawQuery("select * from "+ Panel_Table +" where " + Panel_Product_Id_Col  + " = ? ",new String[]{Integer.toString(Product_Id)});
    }

    public void deleteFromPanel(int id){
        mydb = getWritableDatabase();
        mydb.delete(Panel_Table,"" + Panel_Product_Id_Col + " = ?",new String[]{Integer.toString(id)});
    }
    public void ClearPanel(){
        mydb = getWritableDatabase();
        mydb.delete(Panel_Table,null,null);
    }

    public void insertIntoPostPanel(int Product_Id, int Product_Quantity, long Command_Id){
        mydb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Post_Panel_Product_Id_Col,Product_Id);
        values.put(Post_Panel_Product_Quantity_Col,Product_Quantity);
        values.put(Post_Panel_Command_Ref_Col,Command_Id);
        mydb.insert(Post_Panel_Table,null,values);
    }

    public Cursor getProductsFromPostPanel(){
        mydb = getReadableDatabase();
        return mydb.rawQuery("select * from "+ Post_Panel_Table,null);
    }

    public long AddCommand(String date){
        mydb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Command_Date_Col,date);
        return mydb.insert(Command_Table,null,values);
    }

    public void decrementProductQuantity(String Title,int Quantity) {
        mydb = getWritableDatabase();
        mydb.rawQuery("UPDATE "+ Product_Table +" SET "+ Product_Quantity_Col +" = " + Product_Quantity_Col + " - " + Quantity + " WHERE " +Product_Title_Col+ " = " +Title+ " ;" ,null);
    }

    public Cursor getAllDataFromTable(String Table){
        mydb = getReadableDatabase();
        return mydb.rawQuery("select * from "+ Table ,null);
    }

    public void buy(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MMM.dd G 'at' HH:mm:ss z");
        long cmdId = AddCommand(dateFormat.format(date));
        if(cmdId != -1){
            CopyDataToPostPanel(cmdId);
        }

    }

    public void CopyDataToPostPanel(long commandId){
        Cursor toSaveData = getProductsFromPanel();
        Toast.makeText(_context," size is : " + toSaveData.getCount(),Toast.LENGTH_LONG).show();
        toSaveData.moveToFirst();
        insertIntoPostPanel(toSaveData.getInt(toSaveData.getColumnIndex(DatabaseHelper.Panel_Product_Id_Col)),
                toSaveData.getInt(toSaveData.getColumnIndex(DatabaseHelper.Panel_Product_Quantity_Col)),
                (int)commandId
        );
        while(toSaveData.moveToNext()){
            //insertIntoPostPanel(toSaveData.getInt(toSaveData.getColumnIndex(DatabaseHelper.Panel_Product_Id_Col)),
            //        toSaveData.getInt(toSaveData.getColumnIndex(DatabaseHelper.Panel_Product_Quantity_Col)),
            //        (int)commandId
            //        );
        }
    }


}
