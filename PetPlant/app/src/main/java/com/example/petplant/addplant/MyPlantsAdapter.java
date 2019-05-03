package com.example.petplant.addplant;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.petplant.R;


import java.util.ArrayList;
import java.util.List;

class MyPlantsHolder extends RecyclerView.ViewHolder{

    public ImageView imageView;
    public TextView textName;
    public TextView textTitle;
    public RelativeLayout plantcard;

    public MyPlantsHolder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.thumbnail);
        textName = (TextView)itemView.findViewById(R.id.name);
        textTitle = (TextView)itemView.findViewById(R.id.title);
        plantcard =  itemView.findViewById(R.id.plantcard);
/*
        plantcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context mContext = view.getContext();
                Intent i = new Intent(mContext, AddPlantActivity.class);
                i.putExtra("myplant",  textName.getText());
                mContext.startActivity(i);
            }
        });*/

    }
}


public class MyPlantsAdapter extends RecyclerView.Adapter<MyPlantsHolder> {
    public static Context mContext;
    private List<PlantProfileCard> listPlants;

    public MyPlantsAdapter(Context context, List<PlantProfileCard> listPlants) {
        mContext = context;
        this.listPlants = listPlants;

    }

    @Override
    public MyPlantsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View bearCard = inflater.inflate(R.layout.expertcard, parent, false);
        return new MyPlantsHolder(bearCard);
    }

    @Override
    public void onBindViewHolder(MyPlantsHolder holder, int position) {
        holder.imageView.setImageResource(listPlants.get(position).getPicture());
        holder.textName.setText(listPlants.get(position).getName());
        holder.textTitle.setText(listPlants.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return listPlants.size();
    }



}

