package com.abhay.sportsdemoapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class Adapter extends RecyclerView.Adapter<Adapter.VHolder>  {


    private List<ListItem> mListItem;
    private Context context;

    public Adapter(List<ListItem> mListItem, Context context) {
        this.mListItem = mListItem;
        this.context = context;
    }

    @NonNull
    @Override
    public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.newsitem,viewGroup,false);
        return new VHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull VHolder vHolder, int i) {
        final ListItem listItem= mListItem.get(i);

        vHolder.title.setText(listItem.getTitle());

        vHolder.description.setText(listItem.getDesc());

        Picasso.with(context).load(listItem.getImageUrl()).into(vHolder.imageView);

        vHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context ,Webview.class);
                intent.putExtra("web",listItem.getUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public class VHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;
        TextView description;






        public VHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imageView);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);




        }
    }
}
