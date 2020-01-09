package com.example.volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class PutUpActivity extends AppCompatActivity {
    private Spinner airSpinner;
    private Spinner yearsSpinner;
    private Spinner monthsSpinner;
    private Spinner daysSpinner;
    private static final String TAG = "PutUpActivity";
    private  FirebaseUser user;

    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> years = new ArrayList<>();
    ArrayList<String> months = new ArrayList<>();
    ArrayList<String> days = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> array2Adapter;
    ArrayAdapter<String> array3Adapter;
    ArrayAdapter<String> array4Adapter;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_up);

        Toolbar tb = (Toolbar) findViewById(R.id.app_toolbar) ;
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        findViewById(R.id.putUpButton).setOnClickListener(onClickListener);

        String[] states = {"앨라배마", "알래스카", "애리조나", "아칸소", "캘리포니아", "콜로라도", "코네티컷",
                "델라웨어", "플로리다", "조지아", "하와이", "아이다호", "일리노이", "인디애나", "아이오와", "캔자스", "켄터키",
                "루이지애나", "메인", "메릴랜드", "미시간", "미네소타", "미시시피", "미주리", "몬태나", "네브래스카", "네바다",
                "뉴햄프셔", "뉴저지", "뉴멕시코", "뉴욕", "노스캐롤라이나", "노스다코타", "오하이오", "오클라호마", "오리건",
                "펜실베이니아", "로드아일랜드", "사이스캐롤라이나", "사우스다코타", "테네시", "텍사스", "유타", "버몬트",
                "버지니아", "워싱턴", "웨스트버지니아", "위스콘신", "와이오밍"};
        items.add(0,"공항이름");
        years.add(0,"년");
        months.add(0,"월");
        days.add(0,"일");

        for(int i = 0; i < 49; i++) {
            items.add(states[i]);
        }

        for(int i = 2020; i < 2040 ; i++){
            String a = Integer.toString(i);
            years.add(a);
        }

        for(int i = 1 ; i < 13; i++){
            String a = Integer.toString(i);
            months.add(a);
        }

        for(int i = 1 ; i < 32; i++){
            String a = Integer.toString(i);
            days.add(a);
        }

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_item,items);

        array2Adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_item,years);

        array3Adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_item,months);

        array4Adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_item,days);

        airSpinner = (Spinner)findViewById(R.id.airSpinner);
        yearsSpinner = (Spinner)findViewById(R.id.yearSpinner);
        monthsSpinner = (Spinner)findViewById(R.id.monthSpinner);
        daysSpinner = (Spinner)findViewById(R.id.daySpinner);
        airSpinner.setAdapter(arrayAdapter);
        yearsSpinner.setAdapter(array2Adapter);
        monthsSpinner.setAdapter(array3Adapter);
        daysSpinner.setAdapter(array4Adapter);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.putUpButton:
                    putUp();
                    break;
            }
        }
    };


    private void putUp() {
        final String name = ((EditText) findViewById(R.id.username)).getText().toString();
        final String memo = ((EditText) findViewById(R.id.memo)).getText().toString();
        String airport = (String)airSpinner.getSelectedItem();
        String year = (String)yearsSpinner.getSelectedItem();
        String month = (String)monthsSpinner.getSelectedItem();
        String day = (String)daysSpinner.getSelectedItem();


        if (name.length() > 0) {
            user = FirebaseAuth.getInstance().getCurrentUser();
            PutInfo putInfo = new PutInfo(user.getUid(), name, memo, airport, year, month, day);
            uploader(putInfo);
        } else {
            startToast("내용을 모두 입력해주세요.");
        }
    }

    private void uploader(PutInfo putInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("puts").add(putInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        startToast("성공적으로 등록하였습니다");
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startToast("등록에 실패하였습니다.");
                    }
                });
    }
    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
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


}