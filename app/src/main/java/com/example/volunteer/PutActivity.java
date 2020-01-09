package com.example.volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PutActivity extends AppCompatActivity {
    ArrayList<String> items = new ArrayList<>();
    private Spinner put_seAirSpinner;
    ArrayAdapter<String> arrayAdapter;
    private String airName;

    private ListView put_reListView;
    private PutAdapter adapter;
    private List<ReInfo> ReInfoList;
    private static final String TAG = "RequestActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put);

        Toolbar tb = (Toolbar) findViewById(R.id.app_put_toolbar) ;
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.put_searchButton).setOnClickListener(onClickListener);
        findViewById(R.id.putupButton).setOnClickListener(onClickListener);

        put_reListView = (ListView) findViewById(R.id.put_reListView);
        ReInfoList = new ArrayList<ReInfo>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("requets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ReInfoList.add(new ReInfo(
                                        document.getData().get("publisher").toString(),
                                        document.getData().get("name").toString(),
                                        document.getData().get("memo").toString(),
                                        document.getData().get("airport").toString(),
                                        document.getData().get("pet").toString()));

                                adapter = new PutAdapter(getApplicationContext(),ReInfoList);
                                put_reListView.setAdapter(adapter);

                                put_reListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(getApplicationContext(), PutMoreActivity.class);
                                        intent.putExtra("name",ReInfoList.get(position).getName());
                                        intent.putExtra("memo",ReInfoList.get(position).getMemo());
                                        intent.putExtra("airport",ReInfoList.get(position).getAirport());
                                        intent.putExtra("year",ReInfoList.get(position).getPet());
                                        startActivity(intent);
                                    }
                                });
                            }
                        } else {

                        }
                    }
                });

        String[] states = {"앨라배마", "알래스카", "애리조나", "아칸소", "캘리포니아", "콜로라도", "코네티컷",
                "델라웨어", "플로리다", "조지아", "하와이", "아이다호", "일리노이", "인디애나", "아이오와", "캔자스", "켄터키",
                "루이지애나", "메인", "메릴랜드", "미시간", "미네소타", "미시시피", "미주리", "몬태나", "네브래스카", "네바다",
                "뉴햄프셔", "뉴저지", "뉴멕시코", "뉴욕", "노스캐롤라이나", "노스다코타", "오하이오", "오클라호마", "오리건",
                "펜실베이니아", "로드아일랜드", "사이스캐롤라이나", "사우스다코타", "테네시", "텍사스", "유타", "버몬트",
                "버지니아", "워싱턴", "웨스트버지니아", "위스콘신", "와이오밍"};
        items.add(0,"공항이름");

        for(int i = 0; i < 49; i++) {
            items.add(states[i]);
        }

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_item,items);

        put_seAirSpinner = (Spinner)findViewById(R.id.put_seAirSpinner);
        put_seAirSpinner.setAdapter(arrayAdapter);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.putupButton:
                    Intent putIntent = new Intent(PutActivity.this, PutUpActivity.class);
                    PutActivity.this.startActivity(putIntent);
                    break;
                case R.id.put_searchButton:
                    searchUp();
                    break;
            }
        }
    };

    private void searchUp(){
        airName = (String)put_seAirSpinner.getSelectedItem();

        put_reListView = (ListView) findViewById(R.id.put_reListView);
        ReInfoList = new ArrayList<ReInfo>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference putsRef = db.collection("puts");
        Query query = putsRef.whereEqualTo("airport",airName);
        db.collection("requets")
                .whereEqualTo("airport",airName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ReInfoList.add(new ReInfo(
                                        document.getData().get("publisher").toString(),
                                        document.getData().get("name").toString(),
                                        document.getData().get("memo").toString(),
                                        document.getData().get("airport").toString(),
                                        document.getData().get("pet").toString()));

                            }
                            if(ReInfoList.isEmpty()){
                                startToast("검색 결과가 존재하지 않습니다. ");
                            }else{
                                adapter = new PutAdapter(getApplicationContext(),ReInfoList);
                                put_reListView.setAdapter(adapter);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
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