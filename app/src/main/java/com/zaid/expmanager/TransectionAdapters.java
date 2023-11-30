package com.zaid.expmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransectionAdapters extends RecyclerView.Adapter<TransectionAdapters.MyviewHolder> {

    Context context;
    ArrayList<TransectionModel> transectionModelArrayList;

    public TransectionAdapters(Context context, ArrayList<TransectionModel> transectionModelArrayList) {
        this.context = context;
        this.transectionModelArrayList = transectionModelArrayList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item_recycler,parent,false);
        return new MyviewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
    TransectionModel model=transectionModelArrayList.get(position);

    try {
        if((model.getType().charAt(0))=='i' || (model.getType().charAt(0)=='I')){
            holder.priority.setBackgroundResource(R.drawable.baseline_arrow_circle_down_24);
        }
        else{
            holder.priority.setBackgroundResource(R.drawable.baseline_arrow_circle_up_24);
        }
    }
    catch (NullPointerException e){
        holder.priority.setBackgroundResource(R.drawable.edittax_backgraound);
    }
    holder.amount.setText(model.getAmount());
    holder.note.setText(model.getNote());
    holder.date.setText(model.getDate());


    }

    @Override
    public int getItemCount() {
        return transectionModelArrayList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{

        TextView note,amount,date;
        View priority;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            note=itemView.findViewById(R.id.note1);
            amount=itemView.findViewById(R.id.amount1);
            date=itemView.findViewById(R.id.date1);
            priority=itemView.findViewById(R.id.periority1);
        }
    }
}
