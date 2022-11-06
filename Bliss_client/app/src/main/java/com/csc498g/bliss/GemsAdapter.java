package com.csc498g.bliss;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Locale;
import java.util.Optional;


public class GemsAdapter extends ArrayAdapter<Gem> {

    // The adapter forking all the feeds in the App

    private final Context context; // The context of the Parent feed
    private final List<Gem> gems_list; // The list that this feed will populate
    private final boolean is_in_comment; // Whether this feed is inside a Comment
    private final ListView parent_list; // The parent Element of this feed

    public GemsAdapter(@NonNull Context context, @NonNull List<Gem> gems_list, boolean is_in_comment, ListView parent_list) {

        super(context, 0, gems_list);
        this.context = context;
        this.gems_list = gems_list;
        this.is_in_comment = is_in_comment;
        this.parent_list = parent_list;

    }


    public void add(Gem gem) {

        // Adds a gem to the list
        gems_list.add(gem);
    }

    public void insert(Gem gem, int i) {

        // Inserts a gem in the list at index i
        gems_list.add(i, gem);
    }

    public void flush() {

        // Empties the list
        gems_list.clear();
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

        final View listItem;

        Gem currentGem = gems_list.get(position);

        if (currentGem != null) {


            if (currentGem instanceof TextGem) {

                // Specific for Text Gems

                // Inflates the layout
                listItem = LayoutInflater.from(context).inflate(R.layout.text_gem_item, parent, false);

                // Casts the Gem for easier use
                TextGem currentTextGem = (TextGem) currentGem;

                // Initializes the elements
                TextView content = listItem.findViewById(R.id.gemTextContent);

                // Populates the Text
                content.setText(currentTextGem.getContent());

            } else if (currentGem instanceof ImageGem) {

                // Specific for Image Gems (WIP)

                // Inflates the layout
                listItem = LayoutInflater.from(context).inflate(R.layout.image_gem_item, parent, false);

            } else if (currentGem instanceof PollGem) {

                // Specific for Poll gems

                // Inflates the layout
                listItem = LayoutInflater.from(context).inflate(R.layout.poll_gem_item, parent, false);

                // Casts the gem for easier use
                final PollGem currentPollGem = (PollGem) currentGem;

                // Initializes the elements
                TextView prompt = listItem.findViewById(R.id.promptText);
                TextView voters = listItem.findViewById(R.id.votersText);

                ProgressBar bar1 = listItem.findViewById(R.id.bar1bg);
                TextView bar1num = listItem.findViewById(R.id.bar1perc);
                TextView bar1choice = listItem.findViewById(R.id.bar1choice);
                ImageView bar1check = listItem.findViewById(R.id.bar1check);
                ConstraintLayout bar1section = listItem.findViewById(R.id.bar1);
                ProgressBar bar1abs = listItem.findViewById(R.id.bar1abs);

                ProgressBar bar2 = listItem.findViewById(R.id.bar2bg);
                TextView bar2num = listItem.findViewById(R.id.bar2perc);
                TextView bar2choice = listItem.findViewById(R.id.bar2choice);
                ImageView bar2check = listItem.findViewById(R.id.bar2check);
                ConstraintLayout bar2section = listItem.findViewById(R.id.bar2);
                ProgressBar bar2abs = listItem.findViewById(R.id.bar2abs);

                ProgressBar bar3 = listItem.findViewById(R.id.bar3bg);
                TextView bar3num = listItem.findViewById(R.id.bar3perc);
                TextView bar3choice = listItem.findViewById(R.id.bar3choice);
                ImageView bar3check = listItem.findViewById(R.id.bar3check);
                ConstraintLayout bar3section = listItem.findViewById(R.id.bar3);
                ProgressBar bar3abs = listItem.findViewById(R.id.bar3abs);

                ProgressBar bar4 = listItem.findViewById(R.id.bar4bg);
                TextView bar4num = listItem.findViewById(R.id.bar4perc);
                TextView bar4choice = listItem.findViewById(R.id.bar4choice);
                ImageView bar4check = listItem.findViewById(R.id.bar4check);
                ConstraintLayout bar4section = listItem.findViewById(R.id.bar4);
                ProgressBar bar4abs = listItem.findViewById(R.id.bar4abs);

                // Populates the elements
                bar1.setProgress(100 - currentPollGem.percentage1());
                bar2.setProgress(100 - currentPollGem.percentage2());
                bar3.setProgress(100 - currentPollGem.percentage3());
                bar4.setProgress(100 - currentPollGem.percentage4());

                bar1num.setText(String.format(Locale.US, "%d%%", currentPollGem.percentage1()));
                bar2num.setText(String.format(Locale.US, "%d%%", currentPollGem.percentage2()));
                bar3num.setText(String.format(Locale.US, "%d%%", currentPollGem.percentage3()));
                bar4num.setText(String.format(Locale.US, "%d%%", currentPollGem.percentage4()));

                bar1section.setOnClickListener(v -> pollVoteChecker(1, currentPollGem, bar1check));
                bar2section.setOnClickListener(v -> pollVoteChecker(2, currentPollGem, bar2check));
                bar3section.setOnClickListener(v -> pollVoteChecker(3, currentPollGem, bar3check));
                bar4section.setOnClickListener(v -> pollVoteChecker(4, currentPollGem, bar4check));

                bar1check.setVisibility(currentPollGem.getIs_voted() == 1 ? View.VISIBLE : View.INVISIBLE);
                bar2check.setVisibility(currentPollGem.getIs_voted() == 2 ? View.VISIBLE : View.INVISIBLE);
                bar3check.setVisibility(currentPollGem.getIs_voted() == 3 ? View.VISIBLE : View.INVISIBLE);
                bar4check.setVisibility(currentPollGem.getIs_voted() == 4 ? View.VISIBLE : View.INVISIBLE);

                bar1choice.setText(currentPollGem.getOption1());
                bar2choice.setText(currentPollGem.getOption2());
                bar3choice.setText(currentPollGem.getOption3());
                bar4choice.setText(currentPollGem.getOption4());

                int index = currentPollGem.getHighestVoter();
                bar1abs.setProgressDrawable(index == 1 ? ContextCompat.getDrawable(context, R.drawable.poll_selected_bar_bg) : ContextCompat.getDrawable(context, R.drawable.poll_unselected_bar_bg));
                bar2abs.setProgressDrawable(index == 2 ? ContextCompat.getDrawable(context, R.drawable.poll_selected_bar_bg) : ContextCompat.getDrawable(context, R.drawable.poll_unselected_bar_bg));
                bar3abs.setProgressDrawable(index == 3 ? ContextCompat.getDrawable(context, R.drawable.poll_selected_bar_bg) : ContextCompat.getDrawable(context, R.drawable.poll_unselected_bar_bg));
                bar4abs.setProgressDrawable(index == 4 ? ContextCompat.getDrawable(context, R.drawable.poll_selected_bar_bg) : ContextCompat.getDrawable(context, R.drawable.poll_unselected_bar_bg));

                prompt.setText(currentPollGem.getPrompt());
                voters.setText(String.format(Locale.US, "%d voters", currentPollGem.getTotalVoters()));

            } else {

                throw new UnsupportedOperationException("Unknown Gem");

            }

            // General Section

            ImageView pic = listItem.findViewById(R.id.userProfile); // Profile Picture of the Gem
            ImageView bin = listItem.findViewById(R.id.deleteButton); // Bin to delete Gems
            TextView username = listItem.findViewById(R.id.userNameText); // Username of the Miner
            TextView date = listItem.findViewById(R.id.gemDateText); // Mine date of the Gem
            TextView comments = listItem.findViewById(R.id.commentsNum); // Comments number (Approx)
            ImageView comments_button = listItem.findViewById(R.id.commentsLabel); // Commenting button
            TextView diamonds = listItem.findViewById(R.id.diamondsNum); // Diamonds number (Approx)
            ImageView diamonds_button = listItem.findViewById(R.id.diamondsLabel); // Diamonding button
            TextView remines = listItem.findViewById(R.id.reminesNum); // Remining button (WIP)

            // Defines the visibility of the bin depending on the Miner
            bin.setVisibility(currentGem.getOwner_id() == Helper.getOwnerId(context) ? View.VISIBLE : View.INVISIBLE);

            // Adds deletion functionality to the bin
            bin.setOnClickListener(v -> Link.deleteGem(context, currentGem.getGem_id(), parent_list));

            // Sets the username according to the owner of the Gem (If found)
            username.setText(Optional.ofNullable(Temp.TEMP_USERS.get(currentGem.getOwner_id())).map(User::getUsername).orElse("INVALID"));

            // Adds a click functionality to browse the profile of the miner
            username.setOnClickListener(v -> context.startActivity(new Intent(context, ProfileActivity.class).putExtra(Constants.Users.USER_ID, currentGem.getOwner_id())));
            pic.setOnClickListener(v -> context.startActivity(new Intent(context, ProfileActivity.class).putExtra(Constants.Users.USER_ID, currentGem.getOwner_id())));

            // Sets the mine date of the Gem
            date.setText(Helper.formatRemainingDate(currentGem.getMine_date()));

            // Sets the Comments number of the gem (Approx)
            comments.setText(String.valueOf(currentGem.getComments()));

            // Handles commenting
            comments_button.setOnClickListener(v -> comment(context, currentGem));

            // Sets the Diamonds number of the gem (Approx)
            diamonds.setText(String.valueOf(currentGem.getDiamonds()));

            // Sets the Diamond button depending on the Like state
            diamonds_button.setImageDrawable(currentGem.isIs_liked() ? ContextCompat.getDrawable(context, R.drawable.selected_diamonds_icon) : ContextCompat.getDrawable(context, R.drawable.diamonds_icon));

            // Handles diamonding
            diamonds_button.setOnClickListener(v -> diamond(context, currentGem, diamonds, diamonds_button));

            // Sets the Remines number of the gem (WIP)
            remines.setText(String.valueOf(currentGem.getRemines()));

            return listItem;

        } else {

            return convertView;

        }
    }


    public void pollVoteChecker(int option, PollGem pollGem, ImageView check) {

        if (pollGem.getIs_voted() == 0) {

            Link.answerPoll(context, pollGem.getGem_id(), Helper.getOwnerId(context), option, parent_list, pollGem, check);

        }
    }

    public void comment(Context context, Gem currentGem) {

        if (is_in_comment) {

            context.startActivity(new Intent(context, CommentingActivity.class).putExtra(Constants.Gems.ROOT, currentGem.getGem_id()));


        } else {

            Temp.TEMP_COMMENTS.put(currentGem.getGem_id(), currentGem);
            context.startActivity(new Intent(context, CommentActivity.class).putExtra(Constants.Gems.GEM_ID, currentGem.getGem_id()));
        }

    }

    public void diamond(Context context, Gem currentGem, TextView diamonds, ImageView diamonds_button) {

        if (!currentGem.isIs_liked()) {

            Link.diamondsGem(context, currentGem.getGem_id(), Helper.getOwnerId(context), diamonds, diamonds_button);

        } else {

            Link.undiamondsGem(context, currentGem.getGem_id(), Helper.getOwnerId(context), diamonds, diamonds_button);

        }

    }


}
