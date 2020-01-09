package com.example.volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private BackPressHandler backPressHandler;
    boolean Check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressHandler = new BackPressHandler(this);

        findViewById(R.id.boardButton).setOnClickListener(onClickListener);
        findViewById(R.id.putButton).setOnClickListener(onClickListener);
        findViewById(R.id.requestButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.boardButton:
                    Intent mainIntent = new Intent(MainActivity.this, BoardActivity.class);
                    MainActivity.this.startActivity(mainIntent);
                    break;
                case R.id.putButton:
                    Intent main2Intent = new Intent(MainActivity.this, PutActivity.class);
                    MainActivity.this.startActivity(main2Intent);
                    break;
                case R.id.requestButton:
                    Intent main3Intent = new Intent(MainActivity.this, RequestActivity.class);
                    MainActivity.this.startActivity(main3Intent);
                    break;

            }
        }
    };

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressHandler.onBackPressed(Check);
    }
}
