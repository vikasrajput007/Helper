package com.example.helper.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.helper.R;

/**
 * Created by Payal on 2/23/2018.
 */

public class Payment_Card_Adapter extends RecyclerView.Adapter<Payment_Card_Adapter.ViewHolder>
{
    String[] amount;
    Context context;

    public Payment_Card_Adapter(Context context , String[] amt)
    {
    this.amount = amt;
    this.context = context;
    }
    @Override
    public Payment_Card_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.vendor_payment_card_layout,parent,false);
        ViewHolder  viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Payment_Card_Adapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return amount.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView textView;
        public ViewHolder(View itemView)
        {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView_payment);
            textView = itemView.findViewById(R.id.text_amount);
            cardView.setCardElevation(5.0f);
        }
    }
}
