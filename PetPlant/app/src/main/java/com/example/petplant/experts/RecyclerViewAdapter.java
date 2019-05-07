package com.example.petplant.experts;

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

class RecyclerViewHolder extends RecyclerView.ViewHolder{

    public ImageView imageView;
    public TextView textName;
    public TextView textTitle;
    public TextView textSpecialties;
    public RelativeLayout expertcard;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView)itemView.findViewById(R.id.thumbnail);
        textName = (TextView)itemView.findViewById(R.id.name);
        textTitle = (TextView)itemView.findViewById(R.id.title);
        textSpecialties = (TextView) itemView.findViewById(R.id.specialties);
        expertcard = (RelativeLayout) itemView.findViewById(R.id.expertcard);

        expertcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context mContext = view.getContext();
                Intent i = new Intent(mContext, MessageActivity.class);
                i.putExtra("expertname",  textName.getText());
                mContext.startActivity(i);
            }
        });

    }
}

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private List<OneExpert> listExperts;

    public RecyclerViewAdapter(List<OneExpert> listBear){

        this.listExperts = listBear;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View bearCard = inflater.inflate(R.layout.expertcard,parent,false);
        return new RecyclerViewHolder(bearCard);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        //System.out.println(listExperts.get(position).getPicture());
        holder.imageView.setImageResource(listExperts.get(position).getPicture());
        holder.textName.setText(listExperts.get(position).getName());
        holder.textTitle.setText(listExperts.get(position).getTitle());
        holder.textSpecialties.setText(listExperts.get(position).getSpecialties());
    }

    @Override
    public int getItemCount() {
        return listExperts.size();
    }

    public void updateList(List<OneExpert> newList)
    {
        listExperts = new ArrayList<OneExpert>();
        listExperts.addAll(newList);
        notifyDataSetChanged();
    }
}