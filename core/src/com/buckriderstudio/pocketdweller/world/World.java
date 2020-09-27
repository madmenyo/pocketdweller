package com.buckriderstudio.pocketdweller.world;

import java.util.HashMap;

import squidpony.squidgrid.mapping.DungeonGenerator;
import squidpony.squidgrid.mapping.DungeonUtility;

/**
 * This class holds all currently loaded world/map data. It is kept outside the ECS because a whole map with each tile a seperate static entity makes no sense.
 */
public class World {
    public static final int TILE_SIZE = 64;

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

    private boolean[][] discovered;
	public boolean[][] getDiscovered()
	{
		return discovered;
	}

	public World(int width, int height) {
        this.width = width;
        this.height = height;

        generateMap();
    }

    /**
     * Generates a basic map using the width and height
     */
    private void generateMap() {
        DungeonGenerator dungeonGenerator = new DungeonGenerator(width, height);
        charMap = dungeonGenerator.generate();
        costMap = DungeonUtility.generateCostMap(charMap, new HashMap<Character, Double>(), 1);

        discovered = new boolean[width][height];

    }

    /**
     * Checks if tile blocks movement
     * @param x
     * @param y
     * @return
     */
    public boolean blocksMovement(int x, int y){
        if (!withinBounds(x, y)) return true;
        return (charMap[x][y] == '#');
    }

    /**
     * Checks if coordinate is within map bounds
     * @param x
     * @param y
     * @return
     */
    public boolean withinBounds(int x, int y){
        return (x >= 0 && x < width && y >= 0 && y < height);
    }
}
