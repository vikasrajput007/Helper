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
 * Created by payal on 2/9/2018.
 */

public class History_View_Adapter extends RecyclerView.Adapter<History_View_Adapter.ViewHolder>
{
    String [] name;
    Context contextt;
    public History_View_Adapter(Context context2 ,String[] name2)
    {
        contextt = context2;
        name  = name2;
    }


    @Override
    public History_View_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(contextt).inflate(R.layout.vendro_history_card_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(History_View_Adapter.ViewHolder holder, int position)
    {

        holder.vendorname.setText(name[position]);
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView vendorname;
        public ViewHolder(View itemView)
        {
            super(itemView);
            vendorname = (TextView)itemView.findViewById(R.id.vendor_name);
            cardView = (CardView)itemView.findViewById(R.id.cardview_history);
            cardView.setCardElevation(5.0f);

        }
    }
}
