package com.buckriderstudio.pocketdweller.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.buckriderstudio.pocketdweller.world.World;

import java.util.List;

import squidpony.squidmath.AStarSearch;
import squidpony.squidmath.Coord;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class BehaviorComponent implements Component
{
    public BehaviorTree<Entity> behaviorTree;
    public World world;
    public List<Coord> path;
    public AStarSearch aStarSearch;
    public Entity target;
}
