package com.csc498g.bliss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

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

        Gem currentGem = gemsList.get(position);

        // Specific
        if(currentGem instanceof TextGem) {

            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.text_gem_item,parent,false);

            TextView content = (TextView) listItem.findViewById(R.id.tweetContent);
            content.setText(((TextGem) currentGem).getContent());
        }

        if(currentGem instanceof ImageGem) {

            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.image_gem_item,parent,false);

            ImageView img_src = (ImageView) listItem.findViewById(R.id.tweetContent);
            //img_src.setImageDrawable(Link.GetImage("https://images.app.goo.gl/2CTPS2Ts2GovDEv9A"));

        }

        if(currentGem instanceof PollGem) {

            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.poll_gem_item,parent,false);

            //TextView prompt = (TextView) listItem.findViewById(R.id.)

            ProgressBar bar1 = (ProgressBar) listItem.findViewById(R.id.bar1bg);
            bar1.setProgress(((PollGem) currentGem).getOption1perc());

            TextView bar1num = (TextView) listItem.findViewById(R.id.bar1perc);
            bar1num.setText(((PollGem) currentGem).getOption1perc() + "%");

            TextView bar1choice = (TextView) listItem.findViewById(R.id.bar1choice);
            bar1choice.setText(((PollGem) currentGem).getOption1());

            ProgressBar bar2 = (ProgressBar) listItem.findViewById(R.id.bar2bg);
            bar2.setProgress(((PollGem) currentGem).getOption2perc());

            TextView bar2num = (TextView) listItem.findViewById(R.id.bar2perc);
            bar2num.setText(((PollGem) currentGem).getOption2perc() + "%");

            TextView bar2choice = (TextView) listItem.findViewById(R.id.bar2choice);
            bar2choice.setText(((PollGem) currentGem).getOption2());

            ProgressBar bar3 = (ProgressBar) listItem.findViewById(R.id.bar3bg);
            bar3.setProgress(((PollGem) currentGem).getOption3perc());

            TextView bar3num = (TextView) listItem.findViewById(R.id.bar3perc);
            bar3num.setText(((PollGem) currentGem).getOption3perc() + "%");

            TextView bar3choice = (TextView) listItem.findViewById(R.id.bar3choice);
            bar3choice.setText(((PollGem) currentGem).getOption1());

            ProgressBar bar4 = (ProgressBar) listItem.findViewById(R.id.bar4bg);
            bar4.setProgress(((PollGem) currentGem).getOption4perc());

            TextView bar4num = (TextView) listItem.findViewById(R.id.bar4perc);
            bar4num.setText(((PollGem) currentGem).getOption4perc() + "%");

            TextView bar4choice = (TextView) listItem.findViewById(R.id.bar4choice);
            bar4choice.setText(((PollGem) currentGem).getOption4());

        }

        // General for all gems
        TextView username = (TextView) listItem.findViewById(R.id.userNameText);
        username.setText(((Gem)currentGem).getOwner());

        TextView diamonds = (TextView) listItem.findViewById(R.id.diamondsNum);
        diamonds.setText(((Gem) currentGem).getDiamonds() + "");

        TextView remines = (TextView) listItem.findViewById(R.id.retweetsNum);
        remines.setText(((Gem) currentGem).getRemines() + "");

        return listItem;
    }
}
