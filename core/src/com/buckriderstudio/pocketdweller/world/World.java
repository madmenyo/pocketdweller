package com.buckriderstudio.pocketdweller.world;

import java.util.HashMap;

import squidpony.squidgrid.mapping.DungeonGenerator;
import squidpony.squidgrid.mapping.DungeonUtility;

public class World {
    public static final int TILE_SIZE = 32;

    private int width, height;
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    private char[][] charMap;
    public char[][] getCharMap() {
        return charMap;
    }

    private int[][] textureVariationMap;

    private double[][] costMap;
    public double[][] getCostMap() {
        return costMap;
    }

    public World(int width, int height) {
        this.width = width;
        this.height = height;

        generateMap();
    }

    private void generateMap() {
        DungeonGenerator dungeonGenerator = new DungeonGenerator(width, height);
        charMap = dungeonGenerator.generate();
        costMap = DungeonUtility.generateCostMap(charMap, new HashMap<Character, Double>(), 1);
    }

    public boolean blocksMovement(int x, int y){
        if (!withinBounds(x, y)) return false;
        return (charMap[x][y] == '#');
    }

    public boolean withinBounds(int x, int y){
        return (x >= 0 && x < width && y >= 0 && y < height);
    }
}
