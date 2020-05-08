package com.hoverhackathon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import it.emperor.animatedcheckbox.AnimatedCheckBox;

public class MessageListAdapter extends BaseAdapter {
    private Context ctx;
    public List<Message> messageListArray;
    boolean[] mCheckedState;
    public MessageListAdapter(Context context, int textViewResourceId,
                              List<Message> messageListArray) {
//        super(context, textViewResourceId);
        this.messageListArray = messageListArray;
        this.ctx = context;
        this.messageListArray = messageListArray;
        mCheckedState = new boolean[messageListArray.size()];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        View convertView1 = convertView;
        if (convertView1 == null) {
            holder = new Holder();
            LayoutInflater vi = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView1 = vi.inflate(R.layout.message_list_item, null);

            holder.check = convertView1.findViewById(R.id.check);
            holder.messageTo = convertView1.findViewById(R.id.txt_msgTO);
            holder.color = convertView1.findViewById(R.id.color);

            convertView1.setTag(holder);
        } else {
            holder = (Holder) convertView1.getTag();
        }
        final Message message = getItem(position);

        holder.messageTo.setText(message.messageNumber + "  ");

        /*generate random color*/
        Random r = new Random();
        int red = r.nextInt(255 - 0 + 1) + 0;
        int green = r.nextInt(255 - 0 + 1) + 0;
        int blue = r.nextInt(255 - 0 + 1) + 0;

        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.RECTANGLE);
        draw.setColor(Color.rgb(red, green, blue));
        holder.color.setBackground(draw);

        holder.check.setOnCheckedChangeListener(null);
        holder.check.setFocusable(false);

        if (messageListArray.get(position).isChecked) {
            holder.check.setChecked(true);
        } else {
            holder.check.setChecked(false);
        }

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean b) {

                if (checkMaxLimit()) {


                    if (messageListArray.get(position).isChecked && b) {
                        holder.check.setChecked(false);
                        messageListArray.get(position).isChecked = false;

                    } else {
                        holder.check.setChecked(false);
                        messageListArray.get(position).isChecked = false;
                        Toast.makeText(ctx, "Max limit reached", Toast.LENGTH_SHORT).show();
                    }
                }
                    else {
                        if (b) {
                            messageListArray.get(position).isChecked = true;
                        } else {
                            messageListArray.get(position).isChecked = false;
                        }
                    }

                }


        });

        return convertView1;
    }

    public boolean checkMaxLimit() {
        int countermax = 0;
        for(Message item : messageListArray){
            if(item.isChecked){
                countermax++;
            }
        }
        return countermax >= 100;
    }

    @Override
    public int getCount() {
        return messageListArray.size();
    }

    @Override
    public Message getItem(int position) {
        return messageListArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setArrayList(List<Message> messageList) {
        this.messageListArray = messageList;
        notifyDataSetChanged();
    }

    private class Holder {
        public TextView messageTo, messageContent, color;
        CheckBox check;
    }

}