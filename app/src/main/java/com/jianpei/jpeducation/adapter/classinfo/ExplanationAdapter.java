package com.jianpei.jpeducation.adapter.classinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.jianpei.jpeducation.R;
import com.jianpei.jpeducation.utils.L;

import java.util.List;

public class ExplanationAdapter extends RecyclerView.Adapter<ExplanationAdapter.MyHolder> {


    private List<String> imageUrls;

    private Context context;

    public ExplanationAdapter(List<String> imageUrls, Context context) {
        this.imageUrls = imageUrls;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explanation, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Glide.with(context).load(imageUrls.get(position)).placeholder(R.drawable.placeholder_icon).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        L.e("========imageurls:"+imageUrls.size());
        return imageUrls != null ? imageUrls.size() : 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
