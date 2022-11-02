package com.csc498g.bliss;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Locale;
import java.util.Optional;


public class GemsAdapter extends ArrayAdapter<Gem> {

    private final Context mContext;
    private final List<Gem> gemsList;

    public GemsAdapter(@NonNull Context context, @NonNull List<Gem> list) {
        super(context, 0, list);
        mContext = context;
        gemsList = list;
    }

    public void add(Gem gem) {
        gemsList.add(gem);
    }

    public void flush() {
        gemsList.clear();
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

            TextView content = (TextView) listItem.findViewById(R.id.gemContent);
            content.setText(((TextGem) currentGem).getContent());
        }

        if(currentGem instanceof ImageGem) {

            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.image_gem_item,parent,false);

            /*ImageView img_src = (ImageView) listItem.findViewById(R.id.gemContent);
            img_src.setImageDrawable(Link.GetImage("https://images.app.goo.gl/2CTPS2Ts2GovDEv9A"));*/

        }

        if(currentGem instanceof PollGem) {

            if(listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.poll_gem_item,parent,false);

            final PollGem currentPollGem = (PollGem) currentGem;
            //TextView prompt = (TextView) listItem.findViewById(R.id.)

            ProgressBar bar1 = (ProgressBar) listItem.findViewById(R.id.bar1bg);
            bar1.setProgress(100 - currentPollGem.getOption1percentage());

            TextView bar1num = (TextView) listItem.findViewById(R.id.bar1perc);
            bar1num.setText(String.format(Locale.US, "%d%%", currentPollGem.getOption1percentage()));

            TextView bar1choice = (TextView) listItem.findViewById(R.id.bar1choice);
            bar1choice.setText(currentPollGem.getOption1());

            ProgressBar bar2 = (ProgressBar) listItem.findViewById(R.id.bar2bg);
            bar2.setProgress(100 - currentPollGem.getOption2percentage());

            TextView bar2num = (TextView) listItem.findViewById(R.id.bar2perc);
            bar2num.setText(String.format(Locale.US, "%d%%", currentPollGem.getOption2percentage()));

            TextView bar2choice = (TextView) listItem.findViewById(R.id.bar2choice);
            bar2choice.setText(currentPollGem.getOption2());

            ProgressBar bar3 = (ProgressBar) listItem.findViewById(R.id.bar3bg);
            bar3.setProgress(100 - currentPollGem.getOption3percentage());

            TextView bar3num = (TextView) listItem.findViewById(R.id.bar3perc);
            bar3num.setText(String.format(Locale.US, "%d%%", currentPollGem.getOption3percentage()));

            TextView bar3choice = (TextView) listItem.findViewById(R.id.bar3choice);
            bar3choice.setText(currentPollGem.getOption3());

            ProgressBar bar4 = (ProgressBar) listItem.findViewById(R.id.bar4bg);
            bar4.setProgress(100 - currentPollGem.getOption4percentage());

            TextView bar4num = (TextView) listItem.findViewById(R.id.bar4perc);
            bar4num.setText(String.format(Locale.US, "%d%%", currentPollGem.getOption4percentage()));

            TextView bar4choice = (TextView) listItem.findViewById(R.id.bar4choice);
            bar4choice.setText(currentPollGem.getOption4());

            int index = currentPollGem.getHighestVoter();
            switch (index) {

                case  1:

                    ((ProgressBar) listItem.findViewById(R.id.bar1abs)).setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.poll_selected_bar_bg));
                    break;

                case  2:

                    ((ProgressBar) listItem.findViewById(R.id.bar2abs)).setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.poll_selected_bar_bg));
                    break;

                case  3:

                    ((ProgressBar) listItem.findViewById(R.id.bar3abs)).setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.poll_selected_bar_bg));
                    break;

                case  4:

                    ((ProgressBar) listItem.findViewById(R.id.bar4abs)).setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.poll_selected_bar_bg));
                    break;


            }


            ((TextView) listItem.findViewById(R.id.promptText)).setText(currentPollGem.getPrompt());
            ((TextView) listItem.findViewById(R.id.votersText)).setText(String.format(Locale.US, "%d voters", currentPollGem.getTotalVoters()));

            Log.i("Poll", currentPollGem.getHighestVoter() + " " + currentPollGem.getOption1percentage() + " " + currentPollGem.getOption2percentage() + " " + currentPollGem.getOption3percentage() + " " + currentPollGem.getOption4percentage() + " " + currentPollGem.getTotalVoters());

        }

        // General for all gems
        assert listItem != null;
        TextView username = (TextView) listItem.findViewById(R.id.userNameText);
        username.setText(Optional.ofNullable(Temp.TEMP_USERS.get(currentGem.getOwner_id())).map(User::getUsername).orElse("INVALID"));

        TextView date = (TextView) listItem.findViewById(R.id.gemDateText);
        date.setText(Helper.formatRemainingDate(currentGem.getMine_date()));


        TextView diamonds = (TextView) listItem.findViewById(R.id.diamondsNum);
        diamonds.setText(String.valueOf(currentGem.getDiamonds()));

        ImageView diamonds_button = listItem.findViewById(R.id.diamondsLabel);
        if(currentGem.isLiked()) {

            diamonds_button.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.selected_diamonds_icon));

        } else {

            diamonds_button.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.diamonds_icon));

        }
            diamonds_button.setOnClickListener(v -> {

                if(!currentGem.isLiked()) {

                    Link.diamondsGem(mContext, currentGem.getGem_id(), PreferenceManager.getDefaultSharedPreferences(mContext).getInt(Constants.Users.USER_ID, -1), diamonds);
                    diamonds_button.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.selected_diamonds_icon));

                } else {

                    Link.undiamondsGem(mContext, currentGem.getGem_id(), PreferenceManager.getDefaultSharedPreferences(mContext).getInt(Constants.Users.USER_ID, -1), diamonds);
                    diamonds_button.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.diamonds_icon));

                }

            });


        TextView remines = (TextView) listItem.findViewById(R.id.reminesNum);
        remines.setText(String.valueOf(currentGem.getRemines()));

        return listItem;
    }
}
