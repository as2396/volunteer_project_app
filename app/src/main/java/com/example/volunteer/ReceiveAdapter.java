package com.example.volunteer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.volunteer.model.ReceiveMsg;
import com.example.volunteer.model.SendMsg;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ReceiveAdapter extends BaseAdapter {

    private Context context;
    private List<ReceiveMsg> ReceiveList;
    private FirebaseFirestore firebaseFirestore;


    public ReceiveAdapter(Context context, List<ReceiveMsg> receiveList) {
        this.context = context;
        this.ReceiveList = receiveList;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public int getCount() {
        return ReceiveList.size();
    }

    @Override
    public Object getItem(int i) {
        return ReceiveList.get(i);
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


        email.setText(ReceiveList.get(position).getUserId());
        time.setText(ReceiveList.get(position).getDateTime());
        content.setText(ReceiveList.get(position).getMsg());

        return v;
    }

}
