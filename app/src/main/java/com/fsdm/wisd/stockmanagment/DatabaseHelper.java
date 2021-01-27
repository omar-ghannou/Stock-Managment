package com.fsdm.wisd.stockmanagment;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    static SQLiteDatabase mydb;
    static  String dbname = "StockManagement";

    static String Product_Table = "product";
    static String Product_Id_Col = "product_id";
    static String Product_Title_Col = "product_title";
    static String Product_Desc_Col = "product_description";
    static String Product_Price_Col = "product_Price";
    static String Product_Quantity_Col = "product_quantity";
    static String Product_Category_Ref_Col = "product_category_ref";

    static String Category_Table = "category";
    static String Category_Id_Col = "category_id";
    static String Category_Name_Col = "category_name";

    static String Panel_Table = "panel";
    static String Panel_Product_Id_Col = "panel_product_id";
    static String Panel_Product_Quantity_Col = "panel_product_quantity";

    static String Command_Table = "command";
    static String Command_Id_Col = "command_id";
    static String Command_Date_Col = "command_date";

    static String Post_Panel_Table = "post_panel"; //used to store the already purchased products to be referenced in command table
    static String Post_Panel_Product_Id_Col = "post_panel_product_id";
    static String Post_Panel_Product_Quantity_Col = "post_panel_product_quantity";
    static String Post_Panel_Command_Ref_Col = "post_panel_command_ref";

    public DatabaseHelper(@Nullable Context context, int version) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table IF NOT EXISTS "+ Product_Table +"(" +Product_Id_Col + " NUMBER primary key AUTOINCREMENT , "+ Product_Title_Col +" TEXT NOT NULL, " + Product_Desc_Col +" TEXT," +
                 Product_Price_Col + " NUMBER NOT NULL, " + Product_Quantity_Col + " NUMBER NOT NULL, " + Product_Category_Ref_Col + "NUMBER );");

        db.execSQL("create table IF NOT EXISTS "+ Category_Table +"(" +Category_Id_Col + " NUMBER primary key AUTOINCREMENT , "+ Category_Name_Col +" TEXT NOT NULL);");

        db.execSQL("create table IF NOT EXISTS "+ Panel_Table +"(" + Panel_Product_Id_Col + " NUMBER primary key AUTOINCREMENT , "+ Panel_Product_Quantity_Col +" NUMBER NOT NULL);");

        db.execSQL("create table IF NOT EXISTS "+ Post_Panel_Table +"(" + Post_Panel_Product_Id_Col + " NUMBER primary key AUTOINCREMENT , "+ Post_Panel_Product_Quantity_Col +" NUMBER NOT NULL, " + Post_Panel_Command_Ref_Col +" NUMBER NOT NULL);");

        db.execSQL("create table IF NOT EXISTS "+ Command_Table +"(" + Command_Id_Col + " NUMBER primary key AUTOINCREMENT , "+ Command_Date_Col +" TEXT);");

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

    public void insertIntoPanel(String Product_Name){
        mydb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Panel_Product_Id_Col,Product_Name);
        values.put(Panel_Product_Quantity_Col,1);
        mydb.insert(Panel_Table,null,values);
    }

    public void incrementQuantityInPanel(int Product_Id,int New_Value){
        mydb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Panel_Product_Quantity_Col,New_Value);
        mydb.update(Panel_Table,values,"" + Panel_Product_Id_Col + " = ?", new String[]{Integer.toString(Product_Id)});
    }

    public Cursor getProductQuantityFromPanel(int Product_Id){
        mydb = getReadableDatabase();
        return mydb.rawQuery("select * from "+ Panel_Table +" where " + Panel_Product_Id_Col  + " = ? ",new String[]{Integer.toString(Product_Id)});
    }

    public void deleteFromPanel(String name){
        mydb = getWritableDatabase();
        mydb.delete(Category_Table,"" + Category_Name_Col + " = ?",new String[]{name});
    }

    public Cursor getAllDataFromTable(String Table){
        mydb = getReadableDatabase();
        return mydb.rawQuery("select * from "+ Table ,null);
    }







    //public ArrayList<Product> ShowAllInTable(String Table){
    //    ArrayList<Product> products = new ArrayList<Product>();
    //    Cursor data = getAllInTable(Table);
    //    while(data.moveToNext()){
    //        products.add(new Product(Integer.toString(data.getInt(data.getColumnIndex("id"))),
    //                data.getString(data.getColumnIndex("name")),data.getString(data.getColumnIndex("info"))));
    //    }
    //    return products;
    //}
}
