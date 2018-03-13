package com.secretdevelopernews.yournewsapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ambar on 16/2/18.
 */

public class ExpandableRecyclerAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    // child data in format of header title, child title
    private HashMap<String, String> _listDataChild;

    private  Context context;
    public ExpandableRecyclerAdapter( Context context,Activity a, ArrayList<HashMap<String, String>> d
                                      ,HashMap<String, String> listChildData) {
        activity = a;
        data=d;
        this._listDataChild = listChildData;
        this.context=context;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this.data.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int position, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ListNewsViewHolder holder = null;
        if (convertView == null) {
            holder = new ListNewsViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.list_item, parent, false);
           // holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);
         //   holder.author = (TextView) convertView.findViewById(R.id.author);
         //   holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.sdetails = (TextView) convertView.findViewById(R.id.lblListItem);
         //   holder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        } else {
            holder = (ListNewsViewHolder) convertView.getTag();
        }
       /* holder.galleryImage.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);*/
        holder.sdetails.setId(position);
      //  holder.time.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try{
           /* holder.author.setText(song.get(MainActivity.KEY_AUTHOR));
            holder.title.setText(song.get(MainActivity.KEY_TITLE));
            holder.time.setText(song.get(MainActivity.KEY_PUBLISHEDAT));*/
            if (song.get(MainActivity.KEY_DESCRIPTION)=="null"){
                holder.sdetails.setText("");
            }else{
                holder.sdetails.setText(song.get(MainActivity.KEY_DESCRIPTION));}

        }catch(Exception e) {}
        return convertView;
    }



    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int position,final boolean isExpanded,
                             View convertView, final ViewGroup parent) {
        ListNewsViewHolder holder = null;
        if (convertView == null) {
            holder = new ListNewsViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.recycler_viewlayout, parent, false);
            holder.galleryImage = (ImageView) convertView.findViewById(R.id.galleryImage);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.title = (TextView) convertView.findViewById(R.id.title);
           // holder.sdetails = (TextView) convertView.findViewById(R.id.sdetails);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.arrowButton= (ImageButton) convertView.findViewById(R.id.button2) ;
            convertView.setTag(holder);
        } else {
            holder = (ListNewsViewHolder) convertView.getTag();
        }
        holder.galleryImage.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);
      //  holder.sdetails.setId(position);
        holder.time.setId(position);

        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);

        try{
            if (song.get(MainActivity.KEY_AUTHOR)=="null"){
                holder.author.setText("");
            }else{
            holder.author.setText(song.get(MainActivity.KEY_AUTHOR));}
            holder.title.setText(song.get(MainActivity.KEY_TITLE));
            holder.time.setText(song.get(MainActivity.KEY_PUBLISHEDAT));
           // if (song.get(MainActivity.KEY_DESCRIPTION)=="null"){
         //       holder.sdetails.setText("");
          //  }else{
          //  holder.sdetails.setText(song.get(MainActivity.KEY_DESCRIPTION));}

            if(song.get(MainActivity.KEY_URLTOIMAGE).toString().length() < 5)
            {
                holder.galleryImage.setVisibility(View.GONE);
            }else{
                Picasso.with(activity)
                        .load(song.get(MainActivity.KEY_URLTOIMAGE).toString())
                        .resize(300, 200)
                        .into(holder.galleryImage);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        //indicator on click
        int imageResourceId = isExpanded ? android.R.drawable.arrow_up_float : android.R.drawable.arrow_down_float;
        holder.arrowButton.setImageResource(imageResourceId);

        holder.arrowButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(isExpanded) ((ExpandableListView) parent).collapseGroup(position);
                else ((ExpandableListView) parent).expandGroup(position, true);

            }
        });


        return convertView;
    }



    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}