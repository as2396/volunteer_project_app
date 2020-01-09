package com.example.volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class RequestUpActivity extends AppCompatActivity {
    private ImageView petView;
    private Spinner airSpinner;
    private String petPath;
    ArrayList<String> items = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_up);

        petView = findViewById(R.id.pet);
        petView.setOnClickListener(onClickListener);

        findViewById(R.id.reUpButton).setOnClickListener(onClickListener);




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

        airSpinner = (Spinner)findViewById(R.id.airSpinner);
        airSpinner.setAdapter(arrayAdapter);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.reUpButton:
                    reUp();
                    break;
                case R.id.pet:
                    Intent intent = new Intent(RequestUpActivity.this, CameraActivity.class);
                    startActivityForResult(intent,0);
                    break;
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode){
            case 0 : {
                if (resultCode == Activity.RESULT_OK){
                    petPath = data.getStringExtra("petPath");
                    Log.e("로그 : ","petPath"+petPath);
                    Bitmap bmp = BitmapFactory.decodeFile(petPath);
                    int nh = (int)(bmp.getHeight()*(512.0 / bmp.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(bmp,512,nh,true);
                    petView.setImageBitmap(scaled);
                }
                break;
            }
        }
    }

    private void reUp() {
        final String name = ((EditText) findViewById(R.id.username)).getText().toString();
        final String memo = ((EditText) findViewById(R.id.memo)).getText().toString();
        final String pet = ((EditText) findViewById(R.id.memo)).getText().toString();
        final String airport = (String)airSpinner.getSelectedItem();



        if (name.length() > 0) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final StorageReference mountainImagesRef = storageRef.child("users/"+user.getUid()+"/pet.jpg");
            try{
                InputStream stream = new FileInputStream(new File(petPath));
                UploadTask uploadTask = mountainImagesRef.putStream(stream);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        return mountainImagesRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            Log.e("성공","성공"+downloadUri);
                            ReInfo reInfo = new ReInfo(user.getUid(), name, memo, airport,downloadUri.toString());
                            uploader(reInfo);
                        } else {

                            Log.e("로그","실패");
                        }
                    }
                });
            }catch (FileNotFoundException e){
                Log.e("로그","에러 : "+e.toString());
            }

        } else {
            startToast("내용을 모두 입력해주세요.");
        }
    }

    private void uploader(ReInfo reInfo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("requets").add(reInfo)
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
}
