package com.example.apple.parkingsaver.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 2017/5/31.
 */

public class Item {
    private String ps_ID;
//    private String timeSectionFrom0;
//    private String timeSectionFrom6;
//    private String timeSectionFrom8;
//    private String timeSectionFrom10;
//    private String timeSectionFrom12;
//    private String timeSectionFrom14;
//    private String timeSectionFrom16;
//    private String timeSectionFrom18;
//    private String timeSectionFrom21;

    //==============change to map Map,key is time section, value is reservation message===//
    private TimeSectionReservationMessage timeSectionFrom0;
    private TimeSectionReservationMessage timeSectionFrom6;
    private TimeSectionReservationMessage timeSectionFrom8;
    private TimeSectionReservationMessage timeSectionFrom10;
    private TimeSectionReservationMessage timeSectionFrom12;
    private TimeSectionReservationMessage timeSectionFrom14;
    private TimeSectionReservationMessage timeSectionFrom16;
    private TimeSectionReservationMessage timeSectionFrom18;
    private TimeSectionReservationMessage timeSectionFrom21;



//    private static final String[] timeSection=
//            {"0:00 - 6:00", "6:00 - 8:00", "8:00 - 10:00",
//            "10:00 - 12:00", "12:00 - 14:00", "14:00 - 16:00",
//            "16:00 - 18:00", "18:00 - 21:00", "21:00 - 24:00"};



    public String getPs_ID() {
        return ps_ID;
    }

    public void setPs_ID(String ps_ID) {
        this.ps_ID = ps_ID;
    }

    public TimeSectionReservationMessage getTimeSectionFrom0() {
        return timeSectionFrom0;
    }

    public void setTimeSectionFrom0(TimeSectionReservationMessage timeSectionFrom0) {
        this.timeSectionFrom0 = timeSectionFrom0;
    }

    public TimeSectionReservationMessage getTimeSectionFrom6() {
        return timeSectionFrom6;
    }

    public void setTimeSectionFrom6(TimeSectionReservationMessage timeSectionFrom6) {
        this.timeSectionFrom6 = timeSectionFrom6;
    }

    public TimeSectionReservationMessage getTimeSectionFrom8() {
        return timeSectionFrom8;
    }

    public void setTimeSectionFrom8(TimeSectionReservationMessage timeSectionFrom8) {
        this.timeSectionFrom8 = timeSectionFrom8;
    }

    public TimeSectionReservationMessage getTimeSectionFrom10() {
        return timeSectionFrom10;
    }

    public void setTimeSectionFrom10(TimeSectionReservationMessage timeSectionFrom10) {
        this.timeSectionFrom10 = timeSectionFrom10;
    }

    public TimeSectionReservationMessage getTimeSectionFrom12() {
        return timeSectionFrom12;
    }

    public void setTimeSectionFrom12(TimeSectionReservationMessage timeSectionFrom12) {
        this.timeSectionFrom12 = timeSectionFrom12;
    }

    public TimeSectionReservationMessage getTimeSectionFrom14() {
        return timeSectionFrom14;
    }

    public void setTimeSectionFrom14(TimeSectionReservationMessage timeSectionFrom14) {
        this.timeSectionFrom14 = timeSectionFrom14;
    }

    public TimeSectionReservationMessage getTimeSectionFrom16() {
        return timeSectionFrom16;
    }

    public void setTimeSectionFrom16(TimeSectionReservationMessage timeSectionFrom16) {
        this.timeSectionFrom16 = timeSectionFrom16;
    }

    public TimeSectionReservationMessage getTimeSectionFrom18() {
        return timeSectionFrom18;
    }

    public void setTimeSectionFrom18(TimeSectionReservationMessage timeSectionFrom18) {
        this.timeSectionFrom18 = timeSectionFrom18;
    }

    public TimeSectionReservationMessage getTimeSectionFrom21() {
        return timeSectionFrom21;
    }

    public void setTimeSectionFrom21(TimeSectionReservationMessage timeSectionFrom21) {
        this.timeSectionFrom21 = timeSectionFrom21;
    }
}
