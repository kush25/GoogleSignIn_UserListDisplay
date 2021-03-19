package com.hcl.googlesigninapp_miniapp.userdetails;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.hcl.googlesigninapp_miniapp.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context ctx;
    private ArrayList<User> mUserList;
    //private int[] images;


    private OnItemClickListener mlistener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }



    public UserAdapter(Context ctx, ArrayList<User> mUserList) {
        this.ctx = ctx;
        this.mUserList = mUserList;

    }

//    public UserAdapter(Context ctx, ArrayList<User> mUserList, int[] images) {
//        this.ctx = ctx;
//        this.mUserList = mUserList;
//        this.images = images;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.user_list,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User currentList =mUserList.get(position);

        String name = currentList.getName();

        ImageView images = holder.images;
        Glide.with(images).load("https://robohash.org/kush" + String.valueOf(position)).into(images);


        //holder.img_android.setImageResource(images[position]);
        holder.txtname.setText(name);



    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtname;

        public ImageView images;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtname = itemView.findViewById(R.id.text_name);

            images = itemView.findViewById(R.id.image_user);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mlistener !=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mlistener.onItemClick(position);
                        }
                    }
                }
            });


        }
    }
}
