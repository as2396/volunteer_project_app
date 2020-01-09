package com.example.volunteer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PutMoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_more);


        Intent intent = getIntent();

        TextView name = (TextView) findViewById(R.id.put_moreName);
        TextView airport = (TextView) findViewById(R.id.put_moreAirport);
        TextView memo = (TextView) findViewById(R.id.put_moreMemo);


        name.setText(intent.getStringExtra("name"));
        airport.setText(intent.getStringExtra("airport"));
        memo.setText(intent.getStringExtra("memo"));
    }
}
