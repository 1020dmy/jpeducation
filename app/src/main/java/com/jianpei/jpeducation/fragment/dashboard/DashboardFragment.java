package com.jianpei.jpeducation.fragment.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.utils.L;
import com.jianpei.jpeducation.viewmodel.MainModel;

public class DashboardFragment extends Fragment {


    private MainModel mainModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        L.e("DashboardFragment:onCreateView");
        mainModel = ViewModelProviders.of(getActivity()).get(MainModel.class);

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        mainModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                L.e("onChanged:" + s);
                textView.setText(s);
            }
        });
        return root;
    }
}
