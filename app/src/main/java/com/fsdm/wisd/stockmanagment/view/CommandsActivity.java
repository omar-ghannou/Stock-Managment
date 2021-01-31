package com.fsdm.wisd.stockmanagment.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fsdm.wisd.stockmanagment.R;
import com.fsdm.wisd.stockmanagment.model.DatabaseHelper;

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

        commandsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cmd = ((TextView)view).getText().toString();
                String idString =cmd.substring(cmd.indexOf(':')+2,cmd.indexOf("--")-1);
                int i = Integer.parseInt(idString);
                Intent intent = new Intent(getBaseContext(),ProductByCommandActivity.class);
                intent.putExtra("ID",i);
                startActivity(intent);

            }
        });

        adapter.notifyDataSetChanged();
    }

    void populateCommandList(){

        Cursor c = mydb.getAllDataFromTable(DatabaseHelper.Command_Table);
        while(c.moveToNext()){
            String s = "ID : " + c.getInt(c.getColumnIndex(DatabaseHelper.Command_Id_Col)) + " -- on " + c.getString(c.getColumnIndex(DatabaseHelper.Command_Date_Col));
            commands.add(s);
        }

    }



}