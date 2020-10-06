package com.buckriderstudio.pocketdweller.world;

import squidpony.squidgrid.SoundMap;
import squidpony.squidmath.Coord;

public class SoundTest {

    private SoundMap soundMap;

    public double[][] getSoundMap() {
        return soundMap.gradientMap;
    }

    public SoundTest(char[][] charMap) {
        soundMap = new SoundMap(charMap);
    }

    public void setSound(Coord coord, int level){
        soundMap.setSound(coord, level);
    }

}
