package com.example.volunteer.adapter;

import android.content.Context;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.volunteer.R;
import com.example.volunteer.listener.OnPutListener;
import com.example.volunteer.model.ReInfo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PutAdapter extends BaseAdapter {

    private Context context;
    private List<ReInfo> ReInfoList;
    private FirebaseFirestore firebaseFirestore;
    private String name;
    private String id;
    private OnPutListener onPutListener;


    public PutAdapter(Context context, List<ReInfo> reInfoList) {
        this.context = context;
        ReInfoList = reInfoList;
        firebaseFirestore = FirebaseFirestore.getInstance();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.put_item,null);
        TextView airName = (TextView)v.findViewById(R.id.put_airName);
        TextView voName = (TextView)v.findViewById(R.id.put_voName);
        ImageView menu_img = (ImageView)v.findViewById(R.id.put_menu);

        menu_img.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showPopup(v,position);
            }
        });

        airName.setText(ReInfoList.get(position).getAirport());
        voName.setText(ReInfoList.get(position).getName());

        v.setTag(ReInfoList.get(position).getAirport());
        return v;
    }

    public void setOnPutListener(OnPutListener onPutListener){
        this.onPutListener = onPutListener;
    }

    public void showPopup(View v, final int position) {
        PopupMenu popup = new PopupMenu(context,v);
        name = ReInfoList.get(position).getPublisher();
        id = ReInfoList.get(position).getId();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_btn:
                        onPutListener.onDelete(id,name);
                        return true;

                    case R.id.update_btn:
                        onPutListener.onModify(id,name);
                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.request_menu,popup.getMenu());
        popup.show();

    }

}
