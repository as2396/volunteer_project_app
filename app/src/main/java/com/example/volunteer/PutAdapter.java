package com.example.volunteer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PutAdapter extends BaseAdapter {

    private Context context;
    private List<ReInfo> ReInfoList;

    public PutAdapter(Context context, List<ReInfo> reInfoList) {
        this.context = context;
        ReInfoList = reInfoList;
    }

    @Override
    public int getCount() {
        return ReInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return ReInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.put_item,null);
        TextView airName = (TextView)v.findViewById(R.id.put_airName);
        TextView voName = (TextView)v.findViewById(R.id.put_voName);

        airName.setText(ReInfoList.get(position).getAirport());
        voName.setText(ReInfoList.get(position).getName());

        v.setTag(ReInfoList.get(position).getAirport());
        return v;
    }

}
