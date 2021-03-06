package com.example.volunteer.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.volunteer.R;
import com.example.volunteer.R_Customdialog;
import com.example.volunteer.adapter.ReceiveAdapter;
import com.example.volunteer.model.ReceiveMsg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ReceiveMessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Info2Fragment.OnFragmentInteractionListener mListener;

    public ReceiveMessageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Info2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReceiveMessageFragment newInstance(String param1, String param2) {
        ReceiveMessageFragment fragment = new ReceiveMessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    private ListView receiver_listview;
    private ReceiveAdapter adapter;
    private List<ReceiveMsg> receiveMsgList;
    private String Uid;

    public void listUpdate(){
        receiver_listview = (ListView) getView().findViewById(R.id.receive_listView);
        receiveMsgList = new ArrayList<ReceiveMsg>();
        Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("message").document(Uid).collection("ReceiveMessage").orderBy("dateTime", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                receiveMsgList.add(new ReceiveMsg(
                                        document.getData().get("msg").toString(),
                                        document.getData().get("sourceUid").toString(),
                                        document.getData().get("dateTime").toString(),
                                        document.getData().get("destinationUid").toString(),
                                        document.getData().get("userId").toString(),
                                        document.getId()));

                                adapter = new ReceiveAdapter(getContext(), receiveMsgList);
                                receiver_listview.setAdapter(adapter);

                                receiver_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        R_Customdialog r_customdialog = new R_Customdialog(getActivity());

                                        r_customdialog.callFunction(position,receiveMsgList);

                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else {
                        }
                    }
                });
    }
    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        listUpdate();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.message_receive_fragment, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
