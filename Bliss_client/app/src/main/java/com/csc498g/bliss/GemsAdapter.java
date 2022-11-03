package com.csc498g.bliss;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
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
    private final boolean is_in_comment;

    public GemsAdapter(@NonNull Context context, @NonNull List<Gem> list, boolean is_in_comment) {
        super(context, 0, list);
        mContext = context;
        gemsList = list;
        this.is_in_comment = is_in_comment;
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

        if(currentGem != null) {

            // Specific
            if (currentGem instanceof TextGem) {

                if (listItem == null)
                    listItem = LayoutInflater.from(mContext).inflate(R.layout.text_gem_item, parent, false);

                TextView content = (TextView) listItem.findViewById(R.id.gemTextContent);
                content.setText(((TextGem) currentGem).getContent());
            }

            if (currentGem instanceof ImageGem) {

                if (listItem == null)
                    listItem = LayoutInflater.from(mContext).inflate(R.layout.image_gem_item, parent, false);

            /*ImageView img_src = (ImageView) listItem.findViewById(R.id.gemContent);
            img_src.setImageDrawable(Link.GetImage("https://images.app.goo.gl/2CTPS2Ts2GovDEv9A"));*/

            }

            if (currentGem instanceof PollGem) {

                if (listItem == null)
                    listItem = LayoutInflater.from(mContext).inflate(R.layout.poll_gem_item, parent, false);

                final PollGem currentPollGem = (PollGem) currentGem;
                //TextView prompt = (TextView) listItem.findViewById(R.id.)

                ProgressBar bar1 = (ProgressBar) listItem.findViewById(R.id.bar1bg);
                if (bar1 != null)
                    bar1.setProgress(100 - currentPollGem.getOption1percentage());

                TextView bar1num = (TextView) listItem.findViewById(R.id.bar1perc);
                if (bar1num != null)
                    bar1num.setText(String.format(Locale.US, "%d%%", currentPollGem.getOption1percentage()));

                TextView bar1choice = (TextView) listItem.findViewById(R.id.bar1choice);
                if (bar1choice != null)
                    bar1choice.setText(currentPollGem.getOption1());

                ProgressBar bar2 = (ProgressBar) listItem.findViewById(R.id.bar2bg);
                if (bar2 != null)
                    bar2.setProgress(100 - currentPollGem.getOption2percentage());

                TextView bar2num = (TextView) listItem.findViewById(R.id.bar2perc);
                if (bar2num != null)
                    bar2num.setText(String.format(Locale.US, "%d%%", currentPollGem.getOption2percentage()));

                TextView bar2choice = (TextView) listItem.findViewById(R.id.bar2choice);
                if (bar2choice != null)
                    bar2choice.setText(currentPollGem.getOption2());

                ProgressBar bar3 = (ProgressBar) listItem.findViewById(R.id.bar3bg);
                if (bar3 != null)
                    bar3.setProgress(100 - currentPollGem.getOption3percentage());

                TextView bar3num = (TextView) listItem.findViewById(R.id.bar3perc);
                if (bar3num != null)
                    bar3num.setText(String.format(Locale.US, "%d%%", currentPollGem.getOption3percentage()));

                TextView bar3choice = (TextView) listItem.findViewById(R.id.bar3choice);
                if (bar3choice != null)
                    bar3choice.setText(currentPollGem.getOption3());

                ProgressBar bar4 = (ProgressBar) listItem.findViewById(R.id.bar4bg);
                if (bar4 != null)
                    bar4.setProgress(100 - currentPollGem.getOption4percentage());

                TextView bar4num = (TextView) listItem.findViewById(R.id.bar4perc);
                if (bar4num != null)
                    bar4num.setText(String.format(Locale.US, "%d%%", currentPollGem.getOption4percentage()));

                TextView bar4choice = (TextView) listItem.findViewById(R.id.bar4choice);
                if (bar4choice != null)
                    bar4choice.setText(currentPollGem.getOption4());

                int index = currentPollGem.getHighestVoter();
                switch (index) {

                    case 1:

                        ProgressBar pb1 = ((ProgressBar) listItem.findViewById(R.id.bar1abs));
                        if (pb1 != null)
                            pb1.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.poll_selected_bar_bg));
                        break;

                    case 2:

                        ProgressBar pb2 = ((ProgressBar) listItem.findViewById(R.id.bar2abs));
                        if (pb2 != null)
                            pb2.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.poll_selected_bar_bg));
                        break;
                    case 3:

                        ProgressBar pb3 = ((ProgressBar) listItem.findViewById(R.id.bar3abs));
                        if (pb3 != null)
                            pb3.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.poll_selected_bar_bg));
                        break;

                    case 4:

                        ProgressBar pb4 = ((ProgressBar) listItem.findViewById(R.id.bar4abs));
                        if (pb4 != null)
                            pb4.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.poll_selected_bar_bg));
                        break;


                }

                TextView prompt = ((TextView) listItem.findViewById(R.id.promptText));
                if (prompt != null)
                    prompt.setText(currentPollGem.getPrompt());

                TextView voters = ((TextView) listItem.findViewById(R.id.votersText));
                if (voters != null)
                    voters.setText(String.format(Locale.US, "%d voters", currentPollGem.getTotalVoters()));

            }

            // General for all gems
            assert listItem != null;

            ImageView bin = listItem.findViewById(R.id.deleteButton);
            if (currentGem.getOwner_id() == PreferenceManager.getDefaultSharedPreferences(mContext).getInt(Constants.Users.USER_ID, -1))
                bin.setVisibility(View.VISIBLE);
            bin.setOnClickListener(v -> Link.deleteGem(mContext, currentGem.getGem_id()));

            TextView username = (TextView) listItem.findViewById(R.id.userNameText);
            username.setText(Optional.ofNullable(Temp.TEMP_USERS.get(currentGem.getOwner_id())).map(User::getUsername).orElse("INVALID"));

            TextView date = (TextView) listItem.findViewById(R.id.gemDateText);
            date.setText(Helper.formatRemainingDate(currentGem.getMine_date()));

            ImageView comments_button = listItem.findViewById(R.id.commentsLabel);
            comments_button.setOnClickListener(v -> {

                if (is_in_comment) {

                    mContext.startActivity(new Intent(mContext, CommentingActivity.class).putExtra(Constants.Gems.ROOT, currentGem.getGem_id()));


                } else {

                    Temp.TEMP_GEMS.put(currentGem.getGem_id(), currentGem);
                    mContext.startActivity(new Intent(mContext, CommentActivity.class).putExtra(Constants.Gems.GEM_ID, currentGem.getGem_id()));
                }
            });

            TextView diamonds = (TextView) listItem.findViewById(R.id.diamondsNum);
            diamonds.setText(String.valueOf(currentGem.getDiamonds()));

            ImageView diamonds_button = listItem.findViewById(R.id.diamondsLabel);
            if (currentGem.isLiked()) {

                diamonds_button.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.selected_diamonds_icon));

            } else {

                diamonds_button.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.diamonds_icon));

            }

            diamonds_button.setOnClickListener(v -> {

                if (!currentGem.isLiked()) {

                    Link.diamondsGem(mContext, currentGem.getGem_id(), PreferenceManager.getDefaultSharedPreferences(mContext).getInt(Constants.Users.USER_ID, -1), diamonds);
                    diamonds_button.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.selected_diamonds_icon));

                } else {

                    Link.undiamondsGem(mContext, currentGem.getGem_id(), PreferenceManager.getDefaultSharedPreferences(mContext).getInt(Constants.Users.USER_ID, -1), diamonds);
                    diamonds_button.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.diamonds_icon));

                }

            });

            TextView remines = (TextView) listItem.findViewById(R.id.reminesNum);
            remines.setText(String.valueOf(currentGem.getRemines()));
        }
        return listItem;
    }
}
