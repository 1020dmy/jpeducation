package com.jianpei.jpeducation.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jianpei.alyplayer.PlayerActivity;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.viewmodel.MainModel;
import com.jianpei.umeng.ShareActivity;


public class HomeFragment extends Fragment {

    private MainModel mainModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        L.e("HomeFragment:onCreateView");

        mainModel = new ViewModelProvider(getActivity()).get(MainModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Button button = root.findViewById(R.id.btn_player);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PlayerActivity.class));
            }
        });
        Button button1 = root.findViewById(R.id.btn_share);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ShareActivity.class));
            }
        });

        mainModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                L.e("onChanged:" + s);
                button.setText(s);
            }
        });
        return root;
    }
}
