package com.example.volunteer.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.volunteer.BackPressHandler;
import com.example.volunteer.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private BackPressHandler backPressHandler;
    boolean Check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = (Toolbar) findViewById(R.id.app_main_toolbar) ;
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.message_btn:
                Gomessage();
                return true;
            case R.id.logout_btn:
                signOut();
                startloginActivity();
                return true;
            case R.id.notice_btn:
                Intent Boardintent = new Intent(this, BoardActivity.class);
                startActivity(Boardintent);
                return true;
            case R.id.put_btn:
                Intent Putintent = new Intent(this, PutActivity.class);
                startActivity(Putintent);
                return true;
            case R.id.request_btn:
                Intent Requestintent = new Intent(this, RequestActivity.class);
                startActivity(Requestintent);
                return true;
            default :
                return super.onOptionsItemSelected(item) ;
        }
    }

    private void Gomessage() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    private void startloginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressHandler.onBackPressed(Check);
    }
}
