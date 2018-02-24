package com.example.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by CUBASTION on 23-02-2018.
 */

public class RAdapter extends RecyclerView.Adapter<RAdapter.RviewHolder> {

    public ArrayList<Weather> arrayList;
Context context;
    public RAdapter(Context context)
    {this.context=context;
    }
    public void setData(ArrayList<Weather> list)
    {
        this.arrayList=list;
        notifyDataSetChanged();
    }

    @Override
    public RviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item,parent,false);
        return new RviewHolder(view);


    }

    @Override
    public void onBindViewHolder(RviewHolder holder, int position) {

        Weather weather=arrayList.get(position);
        holder.textView1.setText("Temperature");
        holder.textView2.setText(weather.Temp);
        holder.textView3.setText("Description");
        holder.textView4.setText(weather.Desc);
        holder.textView5.setText("Day/Date");
        holder.textView6.setText(weather.Time);

        int id = context.getResources().getIdentifier(weather.Icon, "drawable", context.getPackageName());

        holder.imageView.setImageResource(id);

    }

    @Override
    public int getItemCount() {
        if(arrayList==null)
            return 0;
        else
        {
            return arrayList.size();
        }
    }

    public class RviewHolder extends RecyclerView.ViewHolder {

        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        ImageView imageView;


        public RviewHolder(View itemView) {
            super(itemView);
            textView1=(TextView)itemView.findViewById(R.id.textView);
            textView2=(TextView)itemView.findViewById(R.id.textView2);
            textView3=(TextView)itemView.findViewById(R.id.textView3);
            textView4=(TextView)itemView.findViewById(R.id.textView12);
            textView5=(TextView)itemView.findViewById(R.id.textView13);
            textView6=(TextView)itemView.findViewById(R.id.textView14);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);


        }


    }
}
