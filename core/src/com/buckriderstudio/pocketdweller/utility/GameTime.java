package com.buckriderstudio.pocketdweller.utility;

public class GameTime implements Comparable<GameTime>{
    private int milli;
    private int seconds;
    private int minutes;
    private int hours;
    private int days;

    public int getMilli() {
        return milli;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getDays() {
        return days;
    }

    public GameTime() {
        days = 1;
    }

    public GameTime(int milli, int seconds, int minutes, int hours, int days) {
        addMilli(milli);
        addSeconds(seconds);
        addMinutes(minutes);
        addHours(hours);
        addDays(days);
    }

    public GameTime(GameTime gameTime){
        gameTime.milli = milli;
        gameTime.seconds = seconds;
        gameTime.minutes = minutes;
        gameTime.hours = hours;
        gameTime.days = days;
    }

    public void setTime(GameTime gameTime) {
        gameTime.milli = milli;
        gameTime.seconds = seconds;
        gameTime.minutes = minutes;
        gameTime.hours = hours;
        gameTime.days = days;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameTime gameTime = (GameTime) o;

        if (milli != gameTime.milli) return false;
        if (seconds != gameTime.seconds) return false;
        if (minutes != gameTime.minutes) return false;
        if (hours != gameTime.hours) return false;
        return days == gameTime.days;
    }

    @Override
    public int hashCode() {
        int result = milli;
        result = 31 * result + seconds;
        result = 31 * result + minutes;
        result = 31 * result + hours;
        result = 31 * result + days;
        return result;
    }

    public String getTimeString() {
        return String.format("%02d:%02d:%02d:", hours, minutes, seconds);
    }

    public GameTime cpy() {
        return new GameTime(this);
    }
}
