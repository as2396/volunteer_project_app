package com.example.volunteer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.volunteer.model.SendMsg;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Customdialog extends Dialog {

    private Context context;
    private FirebaseFirestore db;
    private String Uid;
    private String id;

    public Customdialog(Context context){
        super(context);
        this.context = context;
    }

    public void callFunction(final int position, final List<SendMsg> sendMsgList){
        final Dialog dlg = new Dialog(context);

        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dlg.setContentView(R.layout.custom_dialog);

        dlg.show();

        final TextView DestinationUid = (TextView)dlg.findViewById(R.id.DestinationUid);
        final TextView delButton = (TextView)dlg.findViewById(R.id.del_btn);
        final TextView sendButton = (TextView)dlg.findViewById(R.id.send_btn);
        final TextView cancelButton = (TextView)dlg.findViewById(R.id.cancel_btn);
        DestinationUid.setText(sendMsgList.get(position).getDestinationId());

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"취소 했습니다.",Toast.LENGTH_SHORT).show();

                dlg.dismiss();
            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseFirestore.getInstance();
                Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                id = sendMsgList.get(position).getUid();


                db.collection("message").document(Uid).collection("SendMessage").document(id)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context,"삭제 했습니다.",Toast.LENGTH_SHORT).show();
                                dlg.dismiss();

                            }
                        });


            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MessageSending.class);
                intent.putExtra("DestinationUid", sendMsgList.get(position).getDestinationUid());
                getContext().startActivity(intent);
                dlg.dismiss();
            }
        });


    }





}
