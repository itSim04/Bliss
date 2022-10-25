package com.csc498g.bliss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;


public class GemsAdapter extends ArrayAdapter<Gem> {

    private Context mContext;
    private List<Gem> gemsList;

    public GemsAdapter(@NonNull Context context, List<Gem> list) {
        super(context, 0, list);
        mContext = context;
        gemsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.text_gem_item,parent,false);

        Gem currentGem = gemsList.get(position);


        // General for all gems
        TextView username = (TextView) listItem.findViewById(R.id.userNameText);
        username.setText(((Gem)currentGem).getOwner());

        TextView diamonds = (TextView) listItem.findViewById(R.id.diamondsNum);
        diamonds.setText(((Gem) currentGem).getDiamonds() + "");

        TextView remines = (TextView) listItem.findViewById(R.id.retweetsNum);
        remines.setText(((Gem) currentGem).getRemines() + "");

        // Specific
        if(currentGem instanceof TextGem) {
            TextView content = (TextView) listItem.findViewById(R.id.tweetContent);
            content.setText(((TextGem) currentGem).getContent());
        }



        return listItem;
    }
}
