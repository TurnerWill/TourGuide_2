package com.turnerwill.android.tourguide_2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private Context mContext;
    private List<CardData> mCardList;
    private Boolean map = false;

    // a constructor for activities that will have Intent towards DetailActivity.class
    MyAdapter(Context mContext, List<CardData> mCardList) {
        this.mContext = mContext;
        this.mCardList = mCardList;
    }

    // constructor for activities that will have Intent towards DetailActivityMap.class
    MyAdapter(Context mContext, List<CardData> mCardList, Boolean map) {
        this.mContext = mContext;
        this.mCardList = mCardList;
        this.map = map;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item, parent, false);
        return new CardViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {
        holder.mImage.setImageResource(mCardList.get(position).getCardImage());
        holder.mTitle.setText(mCardList.get(position).getCardName());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (map == true) // intent moves to DetailActivityMap
                {
                Intent mIntent = new Intent(mContext, DetailActivityMap.class);
                mIntent.putExtra("Title", mCardList.get(holder.getAdapterPosition()).getCardName());
                mIntent.putExtra("Description", mCardList.get(holder.getAdapterPosition()).getCardDescription());
                mIntent.putExtra("Image", mCardList.get(holder.getAdapterPosition()).getCardImage());
                mIntent.putExtra("Phone_number", mCardList.get(holder.getAdapterPosition()).getPhoneNumber());
                mContext.startActivity(mIntent);
                }
                else {          // intent moves to DetailActivity
                    Intent mIntent = new Intent(mContext, DetailActivity.class);
                    mIntent.putExtra("Title", mCardList.get(holder.getAdapterPosition()).getCardName());
                    mIntent.putExtra("Description", mCardList.get(holder.getAdapterPosition()).getCardDescription());
                    mIntent.putExtra("Image", mCardList.get(holder.getAdapterPosition()).getCardImage());
                    mIntent.putExtra("Phone_number", mCardList.get(holder.getAdapterPosition()).getPhoneNumber());
                    mContext.startActivity(mIntent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }
}

class CardViewHolder extends RecyclerView.ViewHolder {

    ImageView mImage;
    TextView mTitle;
    CardView mCardView;

    CardViewHolder(View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.ivImage);
        mTitle = itemView.findViewById(R.id.tvTitle);
        mCardView = itemView.findViewById(R.id.cardview);
    }
}

