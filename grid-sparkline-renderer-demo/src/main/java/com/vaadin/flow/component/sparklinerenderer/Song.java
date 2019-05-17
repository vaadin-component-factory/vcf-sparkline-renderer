package com.vaadin.flow.component.sparklinerenderer;

public class Song {

    private String name;
    private String artist;
    private Measurements dailyListeners;

    private int targetDailyListeners = 400;
    private int minimumTargetListeners = 100;


    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Measurements getDailyListeners() {
        return dailyListeners;
    }

    public void setDailyListeners(Measurements dailyListeners) {
        this.dailyListeners = dailyListeners;
    }

    public int getTargetDailyListeners() {
        return targetDailyListeners;
    }

    public void setTargetDailyListeners(int targetDailyListeners) {
        this.targetDailyListeners = targetDailyListeners;
    }

    public int getMinimumTargetListeners() {
        return minimumTargetListeners;
    }

    public void setMinimumTargetListeners(int minimumTargetListeners) {
        this.minimumTargetListeners = minimumTargetListeners;
    }

    @Override
    public String toString() {
        return "Song{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
