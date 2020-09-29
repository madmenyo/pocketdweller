package com.buckriderstudio.pocketdweller.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.buckriderstudio.pocketdweller.components.FovComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.world.World;

import squidpony.squidgrid.FOV;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class FovSystem extends IteratingSystem
{
	private World world;
	private double[][] resistanceMap;
	private FOV fov;

	private int counter = 0;
	private long totalTime = 0;
	private float averageTime = 0;

	public float getAverageTime() {
		return averageTime;
	}

	private ComponentMapper<FovComponent> fovMapper = ComponentMapper.getFor(FovComponent.class);
	private ComponentMapper<TransformComponent> tranformMapper = ComponentMapper.getFor(TransformComponent.class);

	public FovSystem(World world)
	{
		super(Family.all(FovComponent.class).get());

		this.world = world;
		resistanceMap = FOV.generateResistances(world.getCharMap());

		fov = new FOV(FOV.SHADOW);
	}


	@Override
	protected void processEntity(Entity entity, float deltaTime)
	{
		FovComponent fovComponent = fovMapper.get(entity);
		TransformComponent transformComponent = tranformMapper.get(entity);

		// If target did not move skip
		// Perhaps need to check for map change (doors, destructable/placeable objects)
		if (fovComponent.previous.equals(transformComponent.tilePosition)) return;

		long time = System.nanoTime();
		// Calculate fov
		fovComponent.fovMap = fov.calculateFOV(resistanceMap, transformComponent.tilePosition.x, transformComponent.tilePosition.y, 40);

		Gdx.app.log("FovSystem", "FOV processed: " + String.format("%.2f", (double)(System.nanoTime() - time) / 1000000f) + "ms.");
		// update fov position
		fovComponent.previous = transformComponent.tilePosition;
	}

	@Override
	public void update(float deltaTime) {
		long time = System.nanoTime();
		super.update(deltaTime);

		// If debug
		counter++;
		totalTime += (System.nanoTime() - time);
		if (counter == 100){
			// Convert nano to mili, convert to 2 decimal and average over counter
			averageTime = Math.round((totalTime / 1000000) * 100 / counter) / 100f;
			counter = 0;
			totalTime = 0;
		}
	}
}
