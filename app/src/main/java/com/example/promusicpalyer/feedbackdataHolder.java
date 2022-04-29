package com.example.promusicpalyer;

public class feedbackdataHolder {
    String name, email, address, feedback_star, yesnofeedback;

    public feedbackdataHolder(String name, String email, String address, String feedback_star, String yesnofeedback) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.feedback_star = feedback_star;
        this.yesnofeedback = yesnofeedback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
