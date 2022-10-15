package com.example.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private ArrayList<User> listData;
    private LayoutInflater layoutInflater;

    static class ViewHolder {
        TextView textViewName, textViewEmail, textViewPhone;
    }

    public CustomListAdapter(Context aContext, ArrayList<User> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final int pos = position;
        if (convertView == null) {
            if(listData.get(position).getActive() == 1){
                convertView = layoutInflater.inflate(R.layout.item_list, null);
            }
            else{
                convertView = layoutInflater.inflate(R.layout.item_disabled_list, null);
            }

            holder = new ViewHolder();
            holder.textViewName = (TextView) convertView.findViewById(R.id.textViewName);
            holder.textViewEmail = (TextView) convertView.findViewById(R.id.textViewEmail);
            holder.textViewPhone = (TextView) convertView.findViewById(R.id.textViewPhone);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textViewName.setText(listData.get(position).getName());
        holder.textViewEmail.setText(listData.get(position).getEmail());
        holder.textViewPhone.setText(listData.get(position).getPhone());

        return convertView;
    }
}
