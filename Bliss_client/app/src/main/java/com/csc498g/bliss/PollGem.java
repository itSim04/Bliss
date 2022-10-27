package com.csc498g.bliss;

public class PollGem extends Gem {

    private String prompt;

    String option1;
    int option1perc;
    String option2;
    int option2perc;
    String option3;
    int option3perc;
    String option4;
    int option4perc;


    public PollGem(int gem_id, String mine_date, String edit_date, int owner, String prompt, int diamonds, int remines, String option1, int option1perc, String option2, int option2perc, String option3, int option3perc, String option4, int option4perc) {
        super(gem_id, mine_date, edit_date, owner, diamonds, remines);
        this.prompt = prompt;
        this.option1 = option1;
        this.option1perc = option1perc;
        this.option2 = option2;
        this.option2perc = option2perc;
        this.option3 = option3;
        this.option3perc = option3perc;
        this.option4 = option4;
        this.option4perc = option4perc;

    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public int getOption1perc() {
        return option1perc;
    }

    public void setOption1perc(int option1perc) {
        this.option1perc = option1perc;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public int getOption2perc() {
        return option2perc;
    }

    public void setOption2perc(int option2perc) {
        this.option2perc = option2perc;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getOption3perc() {
        return option3perc;
    }

    public void setOption3perc(int option3perc) {
        this.option3perc = option3perc;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getOption4perc() {
        return option4perc;
    }

    public void setOption4perc(int option4perc) {
        this.option4perc = option4perc;
    }
}
