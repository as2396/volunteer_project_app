package com.example.volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class PutMoreActivity extends AppCompatActivity {
    private ImageView pet;
    private String DestinationUid;
    private String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_more);

        Toolbar tb = (Toolbar) findViewById(R.id.app_put_more_toolbar) ;
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();

        TextView name = (TextView) findViewById(R.id.put_moreName);
        TextView airport = (TextView) findViewById(R.id.put_moreAirport);
        TextView memo = (TextView) findViewById(R.id.put_moreMemo);
        findViewById(R.id.sendButton).setOnClickListener(onClickListener);

        pet = (ImageView) findViewById(R.id.pet_picture);

        name.setText(intent.getStringExtra("name"));
        airport.setText(intent.getStringExtra("airport"));
        memo.setText(intent.getStringExtra("memo"));;
        String petPath = intent.getStringExtra("pet");
        DestinationUid = intent.getStringExtra("DestinationUid");
        Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Glide.with(this).load(petPath).override(1000).thumbnail(0.1f).into(pet);

    }


    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.sendButton:
                    Intent intent = new Intent(PutMoreActivity.this, MessageSending.class);
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
