package com.example.volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class RequestUpActivity extends AppCompatActivity {
    private ImageView petView;
    private Spinner airSpinner;
    private String petPath;
    private ImageView pp_menu;
    ArrayList<String> items = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_up);

        Toolbar tb = (Toolbar) findViewById(R.id.app_request_up_toolbar) ;
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pp_menu = (ImageView)findViewById(R.id.p_menu);

        pp_menu.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        petView = findViewById(R.id.pet);


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

    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.picture_btn:
                        Intent intent = new Intent(RequestUpActivity.this, CameraActivity.class);
                        startActivityForResult(intent,0);
                        return true;

                    case R.id.gallery_btn:
                        if (ContextCompat.checkSelfPermission(RequestUpActivity.this,  //앱 권한 여부 체크(갤러리)
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(RequestUpActivity.this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                            if (ActivityCompat.shouldShowRequestPermissionRationale(RequestUpActivity.this,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            } else {
                                startToast("권한을 허용해 주세요.");
                            }
                        }else{
                            Intent intent2 = new Intent(RequestUpActivity.this, GalleryActivity.class);
                            startActivityForResult(intent2,0);
                            return true;
                        }
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.p_menu, popup.getMenu());
        popup.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent2 = new Intent(RequestUpActivity.this, GalleryActivity.class);
                    startActivity(intent2);
                } else {
                    startToast("권한을 허용해 주세요.");
                }
            }
        }
    }



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.reUpButton:
                    reUp();
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
                    //Log.e("로그 : ","petPath"+petPath);
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
            Random rnd = new Random();
            int k = rnd.nextInt();
            String number = Integer.toString(k);

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            final StorageReference mountainImagesRef = storageRef.child("users/"+user.getUid()+"/pet"+number+".jpg");
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
                            reInfo.setDateTime(FieldValue.serverTimestamp());
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
        String id = getIntent().getStringExtra("id");
        if(id == null){
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

        }else{
            db.collection("requets").document(id).set(reInfo)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startToast("게시글을 수정하였습니다");
                            Intent intent = new Intent(RequestUpActivity.this, PutActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            startToast("게시글 수정에 실패하였습니다.");
                        }
                    });
        }
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
}
