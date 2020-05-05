package com.hoverhackathon.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hoverhackathon.Message;
import com.hoverhackathon.R;
import com.hoverhackathon.model.PromotionalMessagesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.emperor.animatedcheckbox.AnimatedCheckBox;

public class PromotionalMessagesAdapter extends RecyclerView.Adapter<PromotionalMessagesAdapter.MyViewHolder>
        implements Filterable {
    private Context context;
    private List<PromotionalMessagesModel> messagesModelList;
    private List<PromotionalMessagesModel> messagesModelFilteredList;
    private PromotionalMessagesAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView color, messageNumber;
        AnimatedCheckBox check;

        public MyViewHolder(View view) {
            super(view);
            color = view.findViewById(R.id.color);
            messageNumber = view.findViewById(R.id.messageNumber);
            check = view.findViewById(R.id.check);

        }
    }


    public PromotionalMessagesAdapter(Context context, List<PromotionalMessagesModel> messagesModelList, PromotionalMessagesAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.messagesModelList = messagesModelList;
        this.messagesModelFilteredList = messagesModelList;
        this.listener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messages_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PromotionalMessagesModel message = messagesModelFilteredList.get(position);
        /*generate random color*/
        Random r = new Random();
        int red=r.nextInt(255 - 0 + 1)+0;
        int green=r.nextInt(255 - 0 + 1)+0;
        int blue=r.nextInt(255 - 0 + 1)+0;

        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.RECTANGLE);
        draw.setColor(Color.rgb(red,green,blue));

        holder.color.setBackground(draw);
        holder.messageNumber.setText(message.getMessageNumber());

        holder.check.setChecked(!holder.check.isChecked());
        if (holder.check.isChecked()) {
            listener.onMessageModelSelected(message);
        } else {
            listener.onMessageModelUnSelected(message);
        }


    }

    @Override
    public int getItemCount() {
        return messagesModelFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    messagesModelFilteredList = messagesModelList;
                } else {
                    List<PromotionalMessagesModel> filteredList = new ArrayList<>();
                    for (PromotionalMessagesModel row : messagesModelList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getMessageNumber().toLowerCase().contains(charString.toLowerCase())) {

                            filteredList.add(row);
                        }
                    }

                    messagesModelFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = messagesModelFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                messagesModelFilteredList = (ArrayList<PromotionalMessagesModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface PromotionalMessagesAdapterListener {
        void onMessageModelSelected(PromotionalMessagesModel message);
        void onMessageModelUnSelected(PromotionalMessagesModel message);
    }

    public void setArrayList(List<PromotionalMessagesModel> messageList) {
        this.messagesModelList = messageList;
        notifyDataSetChanged();
    }
}