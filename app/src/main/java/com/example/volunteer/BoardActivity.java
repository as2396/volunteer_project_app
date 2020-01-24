package com.example.volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class BoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Toolbar tb = (Toolbar) findViewById(R.id.app_board_toolbar) ;
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button infoButton = (Button) findViewById(R.id.infoButton);
        final Button info2Button = (Button) findViewById(R.id.info2Button);
        final LinearLayout notice = (LinearLayout)findViewById(R.id.notice);

        notice.setVisibility(View.GONE);
        infoButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        info2Button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, new InfoFragment()).commit();

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                infoButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                info2Button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new InfoFragment()).commit();

            }
        });

        info2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                infoButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                info2Button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new Info2Fragment());
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
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
            default :
                return super.onOptionsItemSelected(item) ;
        }
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

    private void Gomessage() {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }
}
