package com.mystorege;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddViewHolder extends RecyclerView.ViewHolder{
    Button btnEdit;

    View mView;
    public AddViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }

    void setTitle(String title){
        TextView textViewTitle = (TextView)mView.findViewById(R.id.textViewTitle);
        textViewTitle.setText(title);
    }

    @SuppressLint("SetTextI18n")
    void setDimensions(int dimensions){
        TextView textViewDimensions =  (TextView)mView.findViewById(R.id.textViewDimensions);
        textViewDimensions.setText(""+dimensions);
    }

    void setFeatures(String features){
        TextView textViewFeatures =  (TextView)mView.findViewById(R.id.textViewFeatures);
        textViewFeatures.setText(features);
    }

    @SuppressLint("SetTextI18n")
    void setRental(int rental){
        TextView textViewRental =  (TextView)mView.findViewById(R.id.textViewPrice);
        textViewRental.setText(""+rental);
    }

    public void setNotes(String notes){
        TextView textViewNotes =  (TextView)mView.findViewById(R.id.textViewNotes);
        textViewNotes.setText(notes);
    }
    public void setReporter(String reporter){
        TextView textViewReporter =  (TextView)mView.findViewById(R.id.textViewReporter);
        textViewReporter.setText(reporter);
    }


    //        Preparing the required details to fetch
    public void setDetails(Context ctx, String storeType, double dimensions, String storeFeatures, int monthlyRental, String notes, String reportName, String id){
        TextView textViewTitle = (TextView)mView.findViewById(R.id.textViewTitle);
        textViewTitle.setText(storeType);

        TextView textViewDimensions =  (TextView)mView.findViewById(R.id.textViewDimensions);
        textViewDimensions.setText(""+dimensions);

        TextView textViewFeatures =  (TextView)mView.findViewById(R.id.textViewFeatures);
        textViewFeatures.setText(storeFeatures);

        TextView textViewRental =  (TextView)mView.findViewById(R.id.textViewPrice);
        textViewRental.setText(""+monthlyRental);

        TextView textViewNotes =  (TextView)mView.findViewById(R.id.textViewNotes);
        textViewNotes.setText(notes);

        TextView textViewReporter =  (TextView)mView.findViewById(R.id.textViewReporter);
        textViewReporter.setText(reportName);

        TextView textViewId =  (TextView)mView.findViewById(R.id.textViewId);
        textViewId.setText(id);
    }



}