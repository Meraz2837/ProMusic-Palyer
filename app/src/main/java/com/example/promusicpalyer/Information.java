package com.example.promusicpalyer;

public class Information {
    private String name, feedback_star, yesnofeedback;

    public Information() {
    }

    public Information(String name, String feedback_star, String yesnofeedback) {
        this.name = name;
        this.feedback_star = feedback_star;
        this.yesnofeedback = yesnofeedback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedback_star() {
        return feedback_star;
    }

    public void setFeedback_star(String feedback_star) {
        this.feedback_star = feedback_star;
    }

    public String getYesnofeedback() {
        return yesnofeedback;
    }

    public void setYesnofeedback(String yesnofeedback) {
        this.yesnofeedback = yesnofeedback;
    }
}
