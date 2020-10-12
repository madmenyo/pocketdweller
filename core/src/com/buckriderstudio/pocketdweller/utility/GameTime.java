package com.buckriderstudio.pocketdweller.utility;

public class GameTime implements Comparable<GameTime>{
    int milli;
    int seconds;
    int minutes;
    int hours;
    int days;

    public GameTime() {
        days = 1;


    }

    public void addMilli(int milli){
        int s = (this.milli + milli) / 1000;
        this.milli = (this.milli + milli) % 1000;
        if (s > 0) addSeconds(s);
    }

    public void addSeconds(int seconds) {
        int m = (this.seconds + seconds) / 60;
        this.seconds = (this.seconds + seconds) % 60;
        if (m > 0) addMinutes(m);
    }

    public void addMinutes(int minutes) {
        int h = (this.minutes + minutes) / 60;
        this.minutes = (this.minutes + minutes) % 60;
        if (h > 0) addHours(h);
    }

    public void addHours(int hours) {
        int d = (this.hours + hours) / 60;
        this.hours = (this.hours + hours) % 60;
        if (d > 0) addDays(d);
    }

    public void addDays(int d) {
        days += d;
    }

    @Override
    public String toString() {
        return "GameTime{Day [" + days + "] " + hours + ":" + minutes + ":" + seconds + "'" + milli;
    }

    public long totalMiliseconds(){
        return days * 24 + hours * 60 + minutes * 60 + seconds * 1000 + milli;
    }

    public boolean isEarlier(GameTime time){
        if (totalMiliseconds() < time.totalMiliseconds()) return true;
        return false;
    }

    @Override
    public int compareTo(GameTime other) {
        if (days < other.days) return -1;
        if (days > other.days) return 1;

        if (hours < other.hours) return -1;
        if (hours > other.hours) return 1;

        if (minutes < other.minutes) return -1;
        if (minutes > other.minutes) return 1;

        if (seconds < other.seconds) return -1;
        if (seconds > other.seconds) return 1;

        if (milli < other.milli) return -1;
        if (milli > other.milli) return 1;

        return 0;
    }
}
