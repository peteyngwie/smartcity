package com.tra.loginscreen;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DelFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView txt ;
    public DelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DelFragment newInstance(String param1, String param2) {
        DelFragment fragment = new DelFragment();
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
            // Sorry, this function is unavailable .
            Toast.makeText(getContext(), "hello , this is onCreate of del fragment ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Toast.makeText(getContext(), "hello , this is onCreate of del fragment ", Toast.LENGTH_SHORT).show();

        return inflater.inflate(R.layout.fragment_del, container, false);
    }

    /* the following codes is responsible for changing those objects on delete fragment
    *  they must be added by developers.
    *  Construction Date : 2023/06/16
    *  Author: peter
    *
    */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txt1;
        TextView txt2;
        TextView txt3;
        txt = (TextView) view.findViewById(R.id.delTxt);
        txt.setText("kkkkkkkkk");

    }
}
