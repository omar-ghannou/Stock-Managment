package com.fsdm.wisd.stockmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CommandsActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    ListView commandsList;
    ArrayList<String> commands;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commands);
        Initialize();
    }

    private void Initialize(){
        mydb = new DatabaseHelper(getBaseContext());
        commandsList = findViewById(R.id.CommandList);
        commands = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,commands);
        commandsList.setAdapter(adapter);

        populateCommandList();

        adapter.notifyDataSetChanged();
    }

    void populateCommandList(){

        Cursor c = mydb.getAllDataFromTable(DatabaseHelper.Command_Table);
        while(c.moveToNext()){
            String s = "ID : " + c.getInt(c.getColumnIndex(DatabaseHelper.Command_Id_Col)) + " -- Date : " + c.getString(c.getColumnIndex(DatabaseHelper.Command_Date_Col));
            commands.add(s);
        }

    }

}