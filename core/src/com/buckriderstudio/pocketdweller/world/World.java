package com.buckriderstudio.pocketdweller.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.math.MathUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import squidpony.squidai.DijkstraMap;
import squidpony.squidgrid.FOV;
import squidpony.squidgrid.LOS;
import squidpony.squidgrid.Measurement;
import squidpony.squidgrid.Radius;
import squidpony.squidgrid.mapping.DungeonGenerator;
import squidpony.squidgrid.mapping.DungeonUtility;
import squidpony.squidmath.AStarSearch;
import squidpony.squidmath.Coord;

/**
 * This class holds all currently loaded world/map data. It is kept outside the ECS because a whole map with each tile a seperate static entity makes no sense.
 */
public class World implements EntityListener
{
    public static final int TILE_SIZE = 16;

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

	private Set<Entity> sentientEntities;
	public Set<Entity> getSentientEntities()
	{
		return sentientEntities;
	}

	private LOS los;

	private FOV fieldOfView;
	private long fovTime = 0;
    public float getFovTime() {
        return fovTime;
    }
    public void resetFovTime() {
        fovTime = 0;
    }

    private double[][] resistanceMap;

	private DijkstraMap dijkstraMap;

	public World(int width, int height) {
        this.width = width;
        this.height = height;

        //generateDungeon();
        generateLandscape(15);


		updateMapChange();

		sentientEntities = new HashSet<>();
    }

	/**
	 * Should be run whenever map changes to update path finding, fov, etc
	 */
	private void updateMapChange()
	{
	    los = new LOS(LOS.BRESENHAM);

		fieldOfView = new FOV(FOV.SHADOW);
		resistanceMap = FOV.generateResistances(charMap);

		dijkstraMap = new DijkstraMap(charMap, Measurement.EUCLIDEAN);
	}

	private void generateLandscape(int treePercentage) {
	    charMap = new char[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (MathUtils.random(100) < treePercentage){
                    charMap[x][y] = '#';
                }else {
                    charMap[x][y] = '.';
                }
            }
        }

        costMap = DungeonUtility.generateCostMap(charMap, new HashMap<>(), 1);

        discovered = new boolean[width][height];
    }

    /**
     * Generates a basic map using the width and height
     */
    private void generateDungeon() {
        DungeonGenerator dungeonGenerator = new DungeonGenerator(width, height);
        charMap = dungeonGenerator.generate();
        costMap = DungeonUtility.generateCostMap(charMap, new HashMap<Character, Double>(), 1);

        discovered = new boolean[width][height];

    }

    /*
    public List<Coord> findPath(Coord from, Coord too){
		return aStarSearch.path(from, too);
	}*/

	/**
	 * Generates the light map for the current position and radius
	 * @param coord
	 * @param radius
	 * @return
	 */
	public double[][] getLightMap(Coord coord, double radius){
	    return fieldOfView.calculateFOV(resistanceMap, coord.x, coord.y, radius, Radius.CIRCLE);
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

	@Override
	public void entityAdded(Entity entity)
	{
		sentientEntities.add(entity);
	}

	@Override
	public void entityRemoved(Entity entity)
	{
		sentientEntities.remove(entity);
	}

    public boolean los(Coord from, Coord too) {
        return los.isReachable(charMap, from.x, from.y, too.x, too.y);
    }
}
