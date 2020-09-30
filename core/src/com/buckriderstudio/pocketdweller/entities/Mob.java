package com.buckriderstudio.pocketdweller.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.ai.btree.branch.Selector;
import com.badlogic.gdx.ai.btree.branch.Sequence;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.buckriderstudio.pocketdweller.behavior.Behaviors;
import com.buckriderstudio.pocketdweller.behavior.WanderTask;
import com.buckriderstudio.pocketdweller.components.ActionComponent;
import com.buckriderstudio.pocketdweller.components.BehaviorComponent;
import com.buckriderstudio.pocketdweller.components.InfoComponent;
import com.buckriderstudio.pocketdweller.components.TextureComponent;
import com.buckriderstudio.pocketdweller.components.TimeUnitComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.world.World;

import squidpony.squidmath.AStarSearch;
import squidpony.squidmath.Coord;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class Mob extends Entity
{

	public Mob(String name, Coord spawnTile, World world, TextureRegion region, Engine engine, Behaviors behaviors)
	{
		TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
		transformComponent.tilePosition = spawnTile;
		transformComponent.worldPosition.set(spawnTile.x * World.TILE_SIZE, spawnTile.y * World.TILE_SIZE, 0);
		add(transformComponent);

		TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
		textureComponent.region = region;
		add(textureComponent);

		add(engine.createComponent(TimeUnitComponent.class));
		add(engine.createComponent(ActionComponent.class));

		InfoComponent infoComponent = engine.createComponent(InfoComponent.class);
		infoComponent.name = name;
		add(infoComponent);

		BehaviorComponent behaviorComponent = engine.createComponent(BehaviorComponent.class);
		behaviorComponent.behaviorTree = behaviors.getWanderingBehavior(this);
		behaviorComponent.world = world;
		behaviorComponent.aStarSearch = new AStarSearch(world.getCharMap(), AStarSearch.SearchType.EUCLIDEAN);
		add(behaviorComponent);
	}

	public static Task<Entity> wanderingMobBehavior(){

		Selector<Entity> selector = new Selector<>();

		Sequence<Entity> sequence = new Sequence<>();
		selector.addChild(sequence);

		WanderTask wanderTask = new WanderTask();
		sequence.addChild(wanderTask);


		return selector;
	}
}
