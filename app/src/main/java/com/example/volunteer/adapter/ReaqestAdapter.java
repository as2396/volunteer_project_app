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
import com.example.volunteer.listener.OnRequestListener;
import com.example.volunteer.model.PutInfo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ReaqestAdapter extends BaseAdapter {

    private Context context;
    private List<PutInfo> PutInfoList;
    private FirebaseFirestore firebaseFirestore;
    private String name;
    private String id;
    private OnRequestListener onRequestListener;


    public ReaqestAdapter(Context context, List<PutInfo> putInfoList) {
        this.context = context;
        PutInfoList = putInfoList;
        firebaseFirestore = FirebaseFirestore.getInstance();
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
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = View.inflate(context, R.layout.request_item,null);
        TextView airName = (TextView)view.findViewById(R.id.airName);
        TextView date = (TextView)view.findViewById(R.id.date);
        TextView voName = (TextView)view.findViewById(R.id.voName);
        ImageView menu_img = (ImageView)view.findViewById(R.id.menu);

        menu_img.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showPopup(v,position);
            }
        });

        airName.setText(PutInfoList.get(position).getAirport());
        String year = PutInfoList.get(position).getYear();
        String month = PutInfoList.get(position).getMonth();
        String day = PutInfoList.get(position).getDay();
        String result = year + "-" + month + "-" + day;
        date.setText(result);
        voName.setText(PutInfoList.get(position).getName());


        return view;
    }

    public void setOnRequestListener(OnRequestListener onRequestListener){
        this.onRequestListener = onRequestListener;
    }

    public void showPopup(View v, final int position) {
        PopupMenu popup = new PopupMenu(context,v);
        name = PutInfoList.get(position).getPublisher();
        id = PutInfoList.get(position).getId();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete_btn:
                        onRequestListener.onDelete(id,name);
                        return true;

                    case R.id.update_btn:
                        onRequestListener.onModify(id,name);
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
