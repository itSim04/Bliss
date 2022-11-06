package com.csc498g.bliss;

import androidx.annotation.NonNull;

public class PollGem extends Gem {

    // Specific class for the Gem Polls

    private final String option1; // First Option
    private int option1percentage; // Amount of voters for the first option
    private final String option2; // Second option
    private int option2percentage; // Amount of voters for the second option
    private final String option3; // Third option
    private int option3percentage; // Amount of voters for the third option
    private final String option4; // Fourth option
    private int option4percentage; // Amount of voters for the fourth option
    private final String prompt; // The prompt


    public PollGem(int gem_id, String mine_date, String edit_date, int owner, String prompt, String option1, int option1percentage, String option2, int option2percentage, String option3, int option3percentage, String option4, int option4percentage, int diamonds, int remines, int comments, int root, boolean is_liked, int is_voted) {

        // Constructor
        super(gem_id, mine_date, edit_date, owner, diamonds, remines, comments, root, is_liked, is_voted);
        this.prompt = prompt;
        this.option1 = option1;
        this.option1percentage = option1percentage;
        this.option2 = option2;
        this.option2percentage = option2percentage;
        this.option3 = option3;
        this.option3percentage = option3percentage;
        this.option4 = option4;
        this.option4percentage = option4percentage;

    }

    // Accessors
    public String getPrompt() {

        return prompt;

    }

    public String getOption1() {

        return option1;

    }

    public void increment(int option) {

        // Increments the number of voters in one option

        switch (option) {

            case 1:

                option1percentage++;
                break;

            case 2:

                option2percentage++;
                break;

            case 3:

                option3percentage++;
                break;

            case 4:

                option4percentage++;
                break;
        }


    }

    public String getOption2() {

        return option2;

    }

    public String getOption3() {

        return option3;

    }

    public String getOption4() {

        return option4;

    }

    public int percentage1() {

        // Retrieves the percentage of voters on option 1
        return getTotalVoters() == 0 ? 0 : option1percentage * 100 / getTotalVoters() ;

    }

    public int percentage2() {

        // Retrieves the percentage of voters on option 2
        return getTotalVoters() == 0 ? 0 : option2percentage * 100 / getTotalVoters();
    }

    public int percentage3() {

        // Retrieves the percentage of voters on option 3
        return getTotalVoters() == 0 ? 0 : option3percentage * 100 / getTotalVoters();

    }

    public int percentage4() {

        // Retrieves the percentage of voters on option 4
        return getTotalVoters() == 0 ? 0 : option4percentage * 100 / getTotalVoters();

    }

    public int getTotalVoters() {

        // Get total number of voters
        return option1percentage + option2percentage + option3percentage + option4percentage;

    }

    public int getHighestVoter() {

        // Gets the highest weighting voter

        int index = 0, max = 0;

        if(option1percentage > max) {

            max = option1percentage;
            index = 1;

        }
        if(option2percentage > max) {

            max = option2percentage;
            index = 2;

        }
        if(option3percentage > max) {

            max = option3percentage;
            index = 3;

        }
        if(option4percentage > max) {

            index = 4;

        }
        return index;

    }

    @NonNull
    @Override
    public String toString() {
        return "PollGem{" +
                "gem_id=" + gem_id +
                ", mine_date=" + mine_date +
                ", edit_date='" + edit_date + '\'' +
                ", diamonds=" + diamonds +
                ", remines=" + remines +
                ", comments=" + comments +
                ", owner_id=" + owner_id +
                ", is_liked=" + is_liked +
                ", root=" + root +
                ", option1='" + option1 + '\'' +
                ", option1percentage=" + option1percentage +
                ", option2='" + option2 + '\'' +
                ", option2percentage=" + option2percentage +
                ", option3='" + option3 + '\'' +
                ", option3percentage=" + option3percentage +
                ", option4='" + option4 + '\'' +
                ", option4percentage=" + option4percentage +
                ", prompt='" + prompt + '\'' +
                '}';
    }

}
