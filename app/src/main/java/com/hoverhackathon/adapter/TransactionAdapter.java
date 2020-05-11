package com.hoverhackathon.adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.hoverhackathon.R;
import com.hoverhackathon.model.TransactionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> implements Filterable {

    private List<TransactionModel> transactionModel;
    private List<TransactionModel> filteredTransactionModel;
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView date;
        TextView status;
        ImageView name_icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.date = itemView.findViewById(R.id.date);
            this.status = itemView.findViewById(R.id.status);
            this.name_icon = itemView.findViewById(R.id.name_icon);
        }
    }

    public TransactionAdapter(List<TransactionModel> data, Context context) {
        this.transactionModel = data;
        this.filteredTransactionModel = data;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TransactionModel model = filteredTransactionModel.get(listPosition);
        final TextView name = holder.name;
        TextView date = holder.date;
        TextView status = holder.status;
        ImageView name_icon = holder.name_icon;

        name.setText(filteredTransactionModel.get(listPosition).getName());
        date.setText(filteredTransactionModel.get(listPosition).getDate());
        status.setText(filteredTransactionModel.get(listPosition).getStatus());
//        if(status.getText().toString().equalsIgnoreCase("Pending")){
//            status.setTextColor();
//        }

        /*image icon*/
        /*generate random color*/
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color1 = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(60)  // width in px
                .height(60) // height in px
                .endConfig()
                .buildRound(model.getName().substring(0,1), color1);

        name_icon.setImageDrawable(drawable);

    }

    @Override
    public int getItemCount() {
        return filteredTransactionModel.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredTransactionModel = transactionModel;
                } else {
                    List<TransactionModel> filteredList = new ArrayList<>();
                    for (TransactionModel child : transactionModel) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name
                        if (child.getName().toLowerCase().contains(charString.toLowerCase()) || child.getStatus().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(child);
                        }
                    }

                    filteredTransactionModel = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredTransactionModel;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredTransactionModel = (ArrayList<TransactionModel>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }
}
