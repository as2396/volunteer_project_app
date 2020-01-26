package com.example.volunteer.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.volunteer.R;
import com.example.volunteer.model.MemberInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private String usergender;
    private boolean validate = false;
    private FirebaseAuth mAuth;
    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.validateButton).setOnClickListener(onClickListener);
        findViewById(R.id.registerButton).setOnClickListener(onClickListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.validateButton:
                    idCheck();
                    break;
                case R.id.registerButton:
                    signUP();
                    break;


            }
        }
    };

    private void signUP() {
        final String id = ((EditText)findViewById(R.id.idText)).getText().toString();
        final String password = ((EditText)findViewById(R.id.passwordText)).getText().toString();
        final String email = ((EditText)findViewById(R.id.emailText)).getText().toString();
        final String address = ((EditText)findViewById(R.id.addressText)).getText().toString();
        final String day = ((EditText)findViewById(R.id.birthdayText)).getText().toString();
        final String number = ((EditText)findViewById(R.id.numberText)).getText().toString();

        RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        int genderGroupID = genderGroup.getCheckedRadioButtonId();
        usergender = ((RadioButton)findViewById(genderGroupID)).getText().toString();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                RadioButton genderButton = (RadioButton)findViewById(i);
                usergender = genderButton.getText().toString();
            }
        });

        if(id.length() > 0 && password.length() >0 && email.length()>0 && address.length()>0 && day.length() >0 && number.length()>0){

            mAuth.createUserWithEmailAndPassword(id, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                                MemberInfo memberInfo = new MemberInfo(user1.getUid(),id,password,email,address,day,number,usergender);
                                if(user != null){
                                    db.collection("users").document(user.getUid()).set(memberInfo)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    startToast("회원 가입에 성공하였습니다.");
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                }
                                            });
                                }
                            } else {
                                startToast("회원가입에 실패하였습니다. ");

                            }
                        }
                    });
        }else{
            startToast("회원정보 입력하세요.");
        }

    }
    public  void idCheck(){
        final EditText id = ((EditText)findViewById(R.id.idText));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean checked = true;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(id.length()>0 && checkEmail(id.getText().toString())){
                                    if(id.getText().toString().equals((String)document.getData().get("id"))){
                                        startToast("중복된 아이디입니다.");
                                        checked = false;
                                    }
                                }
                                else {
                                    startToast("이메일 형식이 올바르지 않습니다.");
                                }

                            }
                            if (checked) {
                                startToast("사용가능한 아이디입니다.");
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }




    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }



    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
