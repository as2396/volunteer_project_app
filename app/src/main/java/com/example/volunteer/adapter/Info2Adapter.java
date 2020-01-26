package com.example.volunteer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.volunteer.R;
import com.example.volunteer.model.Info2;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Info2Adapter extends BaseAdapter {

    private Context context;
    private List<Info2> info2List;
    private FirebaseFirestore firebaseFirestore;


    public Info2Adapter(Context context, List<Info2> info2List) {
        this.context = context;
        this.info2List = info2List;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public int getCount() {
        return info2List.size();
    }

    @Override
    public Object getItem(int i) {
        return info2List.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.info2_item,null);
        TextView title = (TextView)v.findViewById(R.id.info2_title);

        title.setText(info2List.get(position).getTitle());


        return v;
    }

}
