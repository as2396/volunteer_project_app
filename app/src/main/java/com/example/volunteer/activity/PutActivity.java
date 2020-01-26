package com.example.volunteer.activity;

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

import com.example.volunteer.adapter.PutAdapter;
import com.example.volunteer.R;
import com.example.volunteer.listener.OnPutListener;
import com.example.volunteer.model.ReInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser user;
    private String cname;


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

        ListUpdate();

        arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.spinner_item,items);

        put_seAirSpinner = (Spinner)findViewById(R.id.put_seAirSpinner);
        put_seAirSpinner.setAdapter(arrayAdapter);
    }

    private void ListUpdate(){
        put_reListView = (ListView) findViewById(R.id.put_reListView);
        ReInfoList = new ArrayList<ReInfo>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("request").orderBy("dateTime", Query.Direction.DESCENDING)
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
                                        document.getData().get("pet").toString(),
                                        document.getId()));

                                adapter = new PutAdapter(getApplicationContext(),ReInfoList);
                                adapter.setOnPutListener(onPutListener);
                                put_reListView.setAdapter(adapter);

                                put_reListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(getApplicationContext(), PutMoreActivity.class);
                                        intent.putExtra("name",ReInfoList.get(position).getName());
                                        intent.putExtra("memo",ReInfoList.get(position).getMemo());
                                        intent.putExtra("airport",ReInfoList.get(position).getAirport());
                                        intent.putExtra("pet",ReInfoList.get(position).getPet());
                                        intent.putExtra("DestinationUid",ReInfoList.get(position).getPublisher());
                                        startActivity(intent);
                                    }
                                });
                            }
                        } else {

                        }
                    }
                });
    }

    OnPutListener onPutListener = new OnPutListener() {
        @Override
        public void onDelete(String id,String name) {
            Log.e("삭제","삭제"+id+name);
            user = FirebaseAuth.getInstance().getCurrentUser();
            cname = user.getUid();
            firebaseFirestore = FirebaseFirestore.getInstance();
            if(cname.equals(name)) {
                firebaseFirestore.collection("request").document(id)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("게시글을 삭제하였습니다. ");
                                ListUpdate();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startToast("게시물을 삭제할 수 없습니다.");
                            }
                        });
            }else{
                startToast("다른 사용자의 게시물은 삭제할 수 없습니다.");
            }
        }

        @Override
        public void onModify(String id,String name) {
            user = FirebaseAuth.getInstance().getCurrentUser();
            cname = user.getUid();
            if(cname.equals(name)){
                myStartActivity(RequestUpActivity.class,id);
            }else {
                startToast("다른 사용자의 게시물은 수정할 수 없습니다.");
            }
        }
    };

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
        db.collection("request")
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
                                        document.getData().get("pet").toString(),
                                        document.getId()));

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

    private void myStartActivity(Class c, String id){
        Intent intent = new Intent(this,c);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}