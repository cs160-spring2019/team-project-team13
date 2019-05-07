package com.example.petplant.addplant;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.petplant.R;


import java.util.ArrayList;
import java.util.List;

class SearchPlantHolder extends RecyclerView.ViewHolder{

    public ImageView imageView;
    public TextView textName;
    public TextView textTitle;
    public RelativeLayout pc;
    public View line;

    public SearchPlantHolder(View itemView) {
        super(itemView);
        pc = itemView.findViewById(R.id.plantcard);
        imageView = itemView.findViewById(R.id.thumbnail);
        textName = itemView.findViewById(R.id.name);
        textTitle = itemView.findViewById(R.id.title);

        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context mContext = view.getContext();
                Intent i = new Intent(mContext, PlantInfoActivity.class);
                i.putExtra("id",  R.id.thumbnail);
                mContext.startActivity(i);
            }
        });

    }
}


public class SearchPlantAdapter extends RecyclerView.Adapter<SearchPlantHolder> {
    public static Context mContext;
    private List<PlantProfileCard> listPlants;
    public SearchPlantAdapter(Context context, List<PlantProfileCard> listPlants) {
        mContext = context;
        this.listPlants = listPlants;

    }

    @Override
    public SearchPlantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View bearCard = inflater.inflate(R.layout.plantcard, parent, false);
        return new SearchPlantHolder(bearCard);
    }

    @Override
    public void onBindViewHolder(SearchPlantHolder holder, int position) {
        holder.imageView.setImageResource(listPlants.get(position).getPicture());
        holder.textName.setText(listPlants.get(position).getName());
        holder.textTitle.setText(listPlants.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return listPlants.size();
    }


    public void updateList(List<PlantProfileCard> newList)
    {
        listPlants = new ArrayList<PlantProfileCard>();
        listPlants.addAll(newList);
        notifyDataSetChanged();
    }

}

