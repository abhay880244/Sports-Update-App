package com.abhay.sportsdemoapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.VHolder> {




   private List<ListItem> ListItem;
   private Context context;

    public Adapter(List<ListItem> ListItem, Context context) {
        this.ListItem = ListItem;
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
        final ListItem listItem=ListItem.get(i);

        vHolder.title.setText(listItem.getTitle());

        vHolder.description.setText(listItem.getDesc());

        Picasso.with(context).load(listItem.getImageUrl()).into(vHolder.imageView);

        vHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent webIntent =new Intent(context,webview.class);
                    webIntent.putExtra("web",listItem.getUrl());
                    webIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(webIntent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return ListItem.size();
    }

    public class VHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView description;
        ImageView imageView;
        public LinearLayout linearLayout;



        public VHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            imageView=itemView.findViewById(R.id.imageView);
            linearLayout=itemView.findViewById(R.id.linearlayout);

        }
    }
}
