package com.example.alexa.quizzapp.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.alexa.quizzapp.Interface.ItemClickListener;
import com.example.alexa.quizzapp.R;

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txt_name, txt_score;

    private ItemClickListener itemClickListener;

    public RankingViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_name = (TextView) itemView.findViewById(R.id.text_name);
        txt_score = (TextView) itemView.findViewById(R.id.txt_score);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
