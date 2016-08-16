package com.annanya.notes.classes;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import com.annanya.notes.R;


public class AllNotesViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView title1;
        TextView id1;
        Button edit;
        Button delete;

        AllNotesViewHolder(View itemView)
        {
        super(itemView);
                cv=(CardView)itemView.findViewById(R.id.cardView);
        title1=(TextView)itemView.findViewById(R.id.textView);
        id1=(TextView)itemView.findViewById(R.id.textView2);
        edit=(Button) itemView.findViewById(R.id.button);
        delete=(Button)itemView.findViewById(R.id.button2);


        }
        }