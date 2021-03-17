package com.hcl.googlesigninapp_miniapp.userdetails;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hcl.googlesigninapp_miniapp.R;

public class UserDetailsAdapter extends RecyclerView.Adapter<UserDetailsAdapter.ViewHolder> {

    private Context ctx;
    private int[] images;

    public UserDetailsAdapter(Context ctx, int[] images) {
        this.ctx = ctx;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(ctx).inflate(R.layout.user_detail_display,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.user_img.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {

        return images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView user_img;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            user_img = itemView.findViewById(R.id.user_image);

        }
    }
}
