package com.fsdm.wisd.stockmanagment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.view.View.VISIBLE;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textCartItemCount;
    int mCartItemCount = 0;

    //detect if user enter quantity by keyboard or by clicking on button
    private boolean clicked=true;

    Button mAddToPanel,mRemoveFromPanel;
    EditText mQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //change title of this acitivity
        getSupportActionBar().setTitle(getResources().getString(R.string.detailActivity));

        //todo hanlde the intent that is comming from MainActivity

        mAddToPanel=findViewById(R.id.addPanel);
       mRemoveFromPanel=findViewById(R.id.remove);
       mRemoveFromPanel.setVisibility(View.GONE);

       //get ref to quantity ecdit text
        mQuantity=findViewById(R.id.quantityNumber);
        mQuantity.setSelection(0);
        mQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if(id== EditorInfo.IME_ACTION_DONE)
                {
                    clicked=false;
                    onClick(mAddToPanel);
                }
                return false;
            }
        });



       mAddToPanel.setOnClickListener(this);
       mRemoveFromPanel.setOnClickListener(this);
    }


    //! ref https://stackoverflow.com/questions/43194243/notification-badge-on-action-item-android
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_cart: {
                startActivity(new Intent(this,PanelActivity.class));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount,100)));
                if (textCartItemCount.getVisibility() != VISIBLE) {
                    textCartItemCount.setVisibility(VISIBLE);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==mAddToPanel.getId())
        {

            int quantity=0;
            try {
                quantity=Integer.parseInt(mQuantity.getText().toString());
                if(clicked)
                quantity++;

            }catch (Exception e){

            }

            mCartItemCount=quantity;


            //todo update our panel to do

            mQuantity.setText(String.valueOf(mCartItemCount));
            mRemoveFromPanel.setVisibility(VISIBLE);
            setupBadge();
            return;

        }

        if(view.getId()==mRemoveFromPanel.getId())
        {
            int quantity=0;
            try {
                quantity=Integer.parseInt(mQuantity.getText().toString());
                quantity--;

            }catch (Exception ignored){


            }

            if(quantity==0){
                Log.d("Details","quantity="+quantity);
                mRemoveFromPanel.setVisibility(View.GONE);
            }


            mCartItemCount=quantity;
            //todo update our panel table

            mQuantity.setText(String.valueOf(mCartItemCount));

            setupBadge();

        }

    }
}