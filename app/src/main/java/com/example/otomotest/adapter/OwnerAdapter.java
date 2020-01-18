package com.example.otomotest.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.otomotest.R;
import com.example.otomotest.model.Owner;

import java.util.ArrayList;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.CardViewHolder> {

    private ArrayList<Owner> mData = new ArrayList<>();
    int index = -1;

    public void setData(ArrayList<Owner> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, final int position) {
        holder.bind(mData.get(position));
        boolean expanded = mData.get(position).isExpanded();


        holder.itemView.setOnClickListener(v -> {

            mData.get(position).setExpanded(!expanded);
            notifyItemChanged(position);
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.get(position).setExpanded(!expanded);
                notifyItemChanged(position);
                index = position;
                notifyDataSetChanged();
            }
        });

        if(index==position){
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#d1c9c9"));

        }else {
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView imgOwner;
        ImageView imgItem;
        TextView title;
        TextView name;
        TextView message, messageAlt;
        private View subItem;
        RelativeLayout relativeLayout;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOwner = itemView.findViewById(R.id.img_owner_photo);
            title = itemView.findViewById(R.id.tv_owner_nameTitle);
            name = itemView.findViewById(R.id.tv_owner_name);
            message = itemView.findViewById(R.id.tv_message);
            messageAlt = itemView.findViewById(R.id.tv_message_alt);
            imgItem = itemView.findViewById(R.id.img_photo_collapse);
            subItem = itemView.findViewById(R.id.item_collapse);
            relativeLayout = itemView.findViewById(R.id.relatifLayout);
        }

        void bind(Owner ownerItems) {

            boolean expanded = ownerItems.isExpanded();

            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
            message.setVisibility(expanded ? View.GONE : View.VISIBLE);



            Glide.with(itemView.getContext())
                    .load(ownerItems.getPhoto())
                    .apply(new RequestOptions().override(350, 550).circleCropTransform())
                    .into(imgOwner);

            String nameTitle = Character.toString(ownerItems.getTitle().charAt(0)).toUpperCase()+ownerItems.getTitle().substring(1);
            title.setText(nameTitle);
            if(title.getText().equals("Mr")){
                title.setTextColor(Color.parseColor("#0061b0"));
            }else{
                title.setTextColor(Color.parseColor("#ea35b0"));
            }

            String firstName = ownerItems.getName();
            String lastName = ownerItems.getLastName();
            name.setText(firstName+" "+lastName);
            message.setText(ownerItems.getMessage());
            messageAlt.setText(ownerItems.getMessage());

            Glide.with(itemView.getContext())
                    .load(ownerItems.getItemPhoto())
                    .apply(new RequestOptions().override(550, 550))
                    .into(imgItem);
        }
    }
}
