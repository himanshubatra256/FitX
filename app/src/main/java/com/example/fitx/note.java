package com.example.fitx;

public class note {
    private String email;
    private String steps;
    public note(){
        //needed
    }

    public note(String email, String steps) {
        this.email = email;
        this.steps = steps;
    }

    public String getEmail() {
        return email;
    }

    public String getSteps() {
        return steps;
    }
}
