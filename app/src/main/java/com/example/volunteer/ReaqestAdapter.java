package com.example.volunteer;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ReaqestAdapter extends BaseAdapter {

    private Context context;
    private List<PutInfo> PutInfoList;

    public ReaqestAdapter(Context context, List<PutInfo> putInfoList) {
        this.context = context;
        PutInfoList = putInfoList;
    }

    @Override
    public int getCount() {
        return PutInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return PutInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.request_item,null);
        TextView airName = (TextView)v.findViewById(R.id.airName);
        TextView date = (TextView)v.findViewById(R.id.date);
        TextView voName = (TextView)v.findViewById(R.id.voName);

        airName.setText(PutInfoList.get(position).getAirport());
        String year = PutInfoList.get(position).getYear();
        String month = PutInfoList.get(position).getMonth();
        String day = PutInfoList.get(position).getDay();
        String result = year + "-" + month + "-" + day;
        date.setText(result);
        voName.setText(PutInfoList.get(position).getName());


        v.setTag(PutInfoList.get(position).getAirport());
        return v;
    }

}
