package com.jianpei.jpeducation.fragment.mine;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.presenter.MainPresenter;

public class MineFragment extends Fragment {


    private MainPresenter mainPresenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mainPresenter =
                ViewModelProviders.of(getActivity()).get(MainPresenter.class);
        View root = inflater.inflate(R.layout.mine_fragment, container, false);
        final TextView textView = root.findViewById(R.id.textView);

        mainPresenter.getLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

}
