package com.example.volunteer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RequestMoreActivity extends AppCompatActivity {

    private String DestinationUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reqest_more);

        Toolbar tb = (Toolbar) findViewById(R.id.app_request_more_toolbar) ;
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        TextView name = (TextView) findViewById(R.id.moreName);
        TextView airport = (TextView) findViewById(R.id.moreAirport);
        TextView date = (TextView) findViewById(R.id.moreDate);
        TextView memo = (TextView) findViewById(R.id.moreMemo);
        findViewById(R.id.sendButton).setOnClickListener(onClickListener);

        name.setText(intent.getStringExtra("name"));
        airport.setText(intent.getStringExtra("airport"));
        memo.setText(intent.getStringExtra("memo"));
        String year = intent.getStringExtra("year");
        String month = intent.getStringExtra("month");
        String day = intent.getStringExtra("day");
        String result = year + "-" + month + "-" + day;
        date.setText(result);
        DestinationUid = intent.getStringExtra("DestinationUid");

        ImageView p_img = (ImageView)findViewById(R.id.p_menu);


    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.sendButton:
                    Intent intent = new Intent(RequestMoreActivity.this, MessageSending.class);
                    intent.putExtra("DestinationUid", DestinationUid);
                    startActivity(intent);
                    break;
            }
        }
    };

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
