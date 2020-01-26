package com.example.volunteer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.volunteer.R;
import com.example.volunteer.fragment.ReceiveMessageFragment;
import com.example.volunteer.fragment.SendMessageFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar tb = (Toolbar) findViewById(R.id.app_message_toolbar) ;
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button SendButton = (Button)findViewById(R.id.SendMessage_Button);
        final Button ReceiveButton = (Button)findViewById(R.id.ReceiveMessage_Button);
        final LinearLayout MessageLayout = (LinearLayout)findViewById(R.id.MessageLayout);

        MessageLayout.setVisibility(View.GONE);
        SendButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        ReceiveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Messagefragment, new SendMessageFragment()).commit();

        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageLayout.setVisibility(View.GONE);
                SendButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                ReceiveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Messagefragment, new SendMessageFragment()).commit();

            }
        });

        ReceiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageLayout.setVisibility(View.GONE);
                SendButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ReceiveButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.Messagefragment, new ReceiveMessageFragment());
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
            case android.R.id.home:
                finish();
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