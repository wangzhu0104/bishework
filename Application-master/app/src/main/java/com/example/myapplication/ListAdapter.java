package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHoler>  {
    List<Msg> list;

    public ListAdapter(List<Msg> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.tvMsg.setText(list.get(position).getContent());
        holder.tvTime.setText(list.get(position).getTime());
        holder.tvName.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        TextView tvTime;
        TextView tvMsg;
        TextView tvName;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.chat_user_name);
            tvTime = itemView.findViewById(R.id.chat_time);
            tvMsg = itemView.findViewById(R.id.chat_textview);
        }
    }
}
