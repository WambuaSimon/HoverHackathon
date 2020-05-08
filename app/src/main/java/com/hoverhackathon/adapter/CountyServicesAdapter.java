package com.hoverhackathon.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hoverhackathon.R;
import com.hoverhackathon.model.CountyModel;

import java.util.ArrayList;
import java.util.List;

public class CountyServicesAdapter extends RecyclerView.Adapter<CountyServicesAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<CountyModel> countyList;
    private List<CountyModel> countyListFiltered;
    private CountyServicesAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView countyName;
        ImageView countyLogo;
        CardView county;

        public MyViewHolder(View view) {
            super(view);
            countyName = view.findViewById(R.id.countyName);
            countyLogo = view.findViewById(R.id.countyLogo);
            county = view.findViewById(R.id.county);

            county.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected patient in callback
                    listener.onCountyModelSelected(countyListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public CountyServicesAdapter(Context context, List<CountyModel> countyList, CountyServicesAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.countyList = countyList;
        this.countyListFiltered = countyList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.county_services_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CountyModel county = countyListFiltered.get(position);

        String countyNameValue =county.getCountyName();
        holder.countyName.setText(county.getCountyName());

       if(countyNameValue.equalsIgnoreCase("Kiambu County")){
           holder.countyLogo.setImageResource(R.drawable.kiambu);
       }else if(countyNameValue.equalsIgnoreCase("Nyeri County")){
           holder.countyLogo.setImageResource(R.drawable.nyeri);
       }
       else if(countyNameValue.equalsIgnoreCase("Mombasa County")){
           holder.countyLogo.setImageResource(R.drawable.msa);
       }

       else if(countyNameValue.equalsIgnoreCase("Nakuru County")){
           holder.countyLogo.setImageResource(R.drawable.nakuru);
       }
       else if(countyNameValue.equalsIgnoreCase("Kakamega County")){
           holder.countyLogo.setImageResource(R.drawable.kakamega);
       }
       else if(countyNameValue.equalsIgnoreCase("Nairobi County")){
           holder.countyLogo.setImageResource(R.drawable.nrb);
       }

    }

    @Override
    public int getItemCount() {
        return countyListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    countyListFiltered = countyList;
                } else {
                    List<CountyModel> filteredList = new ArrayList<>();
                    for (CountyModel row : countyList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCountyName().toLowerCase().contains(charString.toLowerCase())) {

                            filteredList.add(row);
                        }
                    }

                    countyListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = countyListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                countyListFiltered = (ArrayList<CountyModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface CountyServicesAdapterListener {
        void onCountyModelSelected(CountyModel county);
    }
}