package com.example.volunteer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.volunteer.model.ReceiveMsg;
import com.example.volunteer.model.SendMsg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessageSending extends AppCompatActivity {

    private String DestinationUid;
    private String Uid;
    private String UserId;
    private SendMsg sendMsg;
    SimpleDateFormat format1 = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
    String destinationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_sending);

        Toolbar tb = (Toolbar) findViewById(R.id.app_message_sending_toolbar) ;
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        DestinationUid = intent.getStringExtra("DestinationUid");
        Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UserId = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        findViewById(R.id.sendingMessage).setOnClickListener(onClickListener);

        getDestinationId();

        //checkChatRoom();

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.sendingMessage:
                    uploader();
                    finish();
                    break;
            }
        }
    };

    private void getDestinationId() {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(DestinationUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            destinationId = document.getData().get("id").toString();
                            if (document.exists()) {
                            } else {
                            }
                        } else {
                        }
                    }
                });
    }

    private void uploader() {
        final String messagetext = ((EditText) findViewById(R.id.messageText)).getText().toString();
        SendMsg sendMsg = new SendMsg(messagetext, DestinationUid, UserId, Uid);
        ReceiveMsg receiveMsg = new ReceiveMsg(messagetext, Uid, UserId, DestinationUid);
        Date time = new Date();
        String time1 = format1.format(time);
        sendMsg.setDateTime(time1);
        sendMsg.setDestinationId(destinationId);
        receiveMsg.setDateTime(time1);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("message").document(Uid).collection("SendMessage").document()
                .set(sendMsg);


        db.collection("message").document(DestinationUid).collection("ReceiveMessage")
                .document()
                .set(receiveMsg);
        startToast("쪽지 보내기 성공");
    }

    private void checkUid() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("message")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                            }
                        } else {

                        }
                    }
                });
    }

   /*  private void MessageUp() {
        final String messagetext = ((EditText) findViewById(R.id.messageText)).getText().toString();
        ChatModel chatModel = new ChatModel();
        chatModel.sourceUid = Uid;
        chatModel.destinationUid = DestinationUid;



        if(chatRoomUid == null) {
            message.clear();
            context.clear();
            findViewById(R.id.sendingMessage).setEnabled(false);
            uploader(chatModel);

        } else {
            check.message.add(messagetext);
            check.context.put("message", check.message);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("chatrooms").document(chatRoomUid).set(check.context, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void avoid) {
                            startToast("전송되었습니다");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            startToast("등록에 실패하였습니다.");
                        }
                    });
        }



    }


    void checkChatRoom() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference putsRef = db.collection("chatrooms");
        Query query = putsRef.whereEqualTo("sourceUid", Uid);
        query
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ChatModel chatModel = document.toObject(ChatModel.class);
                                if(chatModel.destinationUid.equals(DestinationUid)) {
                                    chatRoomUid = document.getId();
                                    findViewById(R.id.sendingMessage).setEnabled(true);
                                }

                            }
                        } else {
                            startToast("실패");
                        }
                    }
                });
    } */

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
            case android.R.id.home:
                finish();
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