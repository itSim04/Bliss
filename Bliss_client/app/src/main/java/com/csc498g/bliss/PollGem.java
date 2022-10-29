package com.csc498g.bliss;

public class PollGem extends Gem {

    private String prompt;

    String option1;
    int option1percentage;
    String option2;
    int option2percentage;
    String option3;
    int option3percentage;
    String option4;
    int option4percentage;


    public PollGem(int gem_id, String mine_date, String edit_date, int owner, String prompt, int diamonds, int remines, String option1, int option1percentage, String option2, int option2percentage, String option3, int option3percentage, String option4, int option4percentage) {
        super(gem_id, mine_date, edit_date, owner, diamonds, remines);
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

    public int getOption1percentage() {
        return option1percentage;
    }

    public void setOption1percentage(int option1percentage) {
        this.option1percentage = option1percentage;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public int getOption2percentage() {
        return option2percentage;
    }

    public void setOption2percentage(int option2percentage) {
        this.option2percentage = option2percentage;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getOption3percentage() {
        return option3percentage;
    }

    public void setOption3percentage(int option3percentage) {
        this.option3percentage = option3percentage;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getOption4percentage() {
        return option4percentage;
    }

    public void setOption4percentage(int option4percentage) {
        this.option4percentage = option4percentage;
    }
}
