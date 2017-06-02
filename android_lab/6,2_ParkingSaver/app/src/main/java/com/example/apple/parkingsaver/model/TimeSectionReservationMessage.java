package com.example.apple.parkingsaver.model;

/**
 * Created by apple on 2017/6/1.
 */

public class TimeSectionReservationMessage {

    private String timeSection;
    private String ifReservation;

    public TimeSectionReservationMessage(String timeSection, String ifReservation) {
        this.timeSection = timeSection;
        this.ifReservation = ifReservation;
    }

    public String getTimeSection() {
        return timeSection;
    }

    public void setTimeSection(String timeSection) {
        this.timeSection = timeSection;
    }

    public String isIfReservation() {
        return ifReservation;
    }

    public void setIfReservation(String ifReservation) {
        this.ifReservation = ifReservation;
    }
}
