package com.example.tinder.Cards;

import static androidx.core.content.ContextCompat.getDrawable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.tinder.R;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<Cards> {

    Context context;

    public arrayAdapter(Context context, int resourceId, List<Cards> items){
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Cards matchNeed = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.name);
        ImageView imageView = convertView.findViewById(R.id.image);
        TextView budget = convertView.findViewById(R.id.budget);
        ImageView mNeedImage = convertView.findViewById(R.id.needImage);
        ImageView mGiveImage = convertView.findViewById(R.id.giveImage);

        name.setText(card_item.getName());
        budget.setText(card_item.getBudget());

        //need image
        if (card_item.getNeed().equals("Netflix")) {
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.netflix));
        } else if (card_item.getNeed().equals("Hulu")) {
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.hulu));
        } else if (card_item.getNeed().equals("Vudu")) {
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.vudu));
        } else if (card_item.getNeed().equals("HBO Now")) {
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.hbo));
        } else if (card_item.getNeed().equals("Youtube Originals")) {
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.youtube));
        } else {
            mNeedImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.none));
        }

        //give Image
        if (card_item.getGive().equals("Netflix")) {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.netflix));
        } else if (card_item.getGive().equals("Amazon Prime")) {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.amazon));
        } else if (card_item.getGive().equals("Hulu")) {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.hulu));
        } else if (card_item.getGive().equals("Vudu")) {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.vudu));
        } else if (card_item.getGive().equals("HBO Now")) {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.hbo));
        } else if (card_item.getGive().equals("Youtube Originals")) {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.youtube));
        } else {
            mGiveImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.none));
        }

        switch (card_item.getProfileImageUrl()){
            case "default":
                Glide.with(convertView.getApplicationContext()).load(R.drawable.profile).into(imageView);
                break;
            default:
                Glide.clear(imageView);
                Glide.with(convertView.getApplicationContext()).load(card_item.getProfileImageUrl()).into(imageView);
                break;
        }
        return convertView;

    }
}

