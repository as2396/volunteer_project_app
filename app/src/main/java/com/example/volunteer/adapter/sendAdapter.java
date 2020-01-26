package com.example.volunteer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.volunteer.R;
import com.example.volunteer.model.SendMsg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class sendAdapter extends BaseAdapter {

    private Context context;
    private List<SendMsg> SendList;
    private FirebaseFirestore firebaseFirestore;
    private String Id;


    public sendAdapter(Context context, List<SendMsg> sendList) {
        this.context = context;
        this.SendList = sendList;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public sendAdapter(List<SendMsg> sendList) {
        SendList = sendList;
    }

    @Override
    public int getCount() {
        return SendList.size();
    }

    @Override
    public Object getItem(int i) {
        return SendList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_message,null);
        TextView email = (TextView)v.findViewById(R.id.email);
        TextView time = (TextView)v.findViewById(R.id.time);
        TextView content = (TextView)v.findViewById(R.id.content);

        email.setText(SendList.get(position).getDestinationId());
        time.setText(SendList.get(position).getDateTime());
        content.setText(SendList.get(position).getMsg());

        return v;
    }

}
