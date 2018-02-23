package com.example.weatherapp;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by CUBASTION on 23-02-2018.
 */

public class RAdapter extends RecyclerView.Adapter<RAdapter.RviewHolder> {

    public ArrayList<Weather> arrayList;

    public RAdapter(ArrayList<Weather> list)
    {
        arrayList=list;
    }

    @Override
    public RviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.item,parent,true);
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
      
    }

    @Override
    public int getItemCount() {
        return 0;
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
