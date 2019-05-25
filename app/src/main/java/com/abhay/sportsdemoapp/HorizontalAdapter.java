package com.abhay.sportsdemoapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HorizontalAdapter  extends RecyclerView.Adapter<HorizontalAdapter.VHolder> {








        private List<matchListItem> mListItem;
        private Context context;

        public HorizontalAdapter(List<matchListItem> mListItem, Context context) {
            this.mListItem = mListItem;
            this.context = context;
        }

        @NonNull
        @Override
        public VHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            LayoutInflater inflater=LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.item,viewGroup,false);
            return new VHolder(view);


        }

        @Override
        public void onBindViewHolder(@NonNull VHolder vHolder, int i) {
            final matchListItem match_ListItem= mListItem.get(i);

            vHolder.team1.setText(match_ListItem.getTeam1());

            vHolder.team2.setText(match_ListItem.getTeam2());
            vHolder.type_text_view.setText("Match Type :"+match_ListItem.getType());
            if(match_ListItem.getMatchStatus()=="true"){
                vHolder.matchStatus_text_view.setText("Match Started");
            }else{
                vHolder.matchStatus_text_view.setText("Match Not Started Yet");

            }




        }

        @Override
        public int getItemCount() {
            return mListItem.size();
        }

        public class VHolder extends RecyclerView.ViewHolder{

            TextView team1;
            TextView team2;
            TextView type_text_view;
            TextView matchStatus_text_view;





            public VHolder(@NonNull View itemView) {
                super(itemView);

                team1=itemView.findViewById(R.id.team1_text_view);
                team2=itemView.findViewById(R.id.team2_text_view);
                type_text_view=itemView.findViewById(R.id.matchtype_text_view);
                matchStatus_text_view=itemView.findViewById(R.id.matchstatus_text_view);


            }
        }
    }





