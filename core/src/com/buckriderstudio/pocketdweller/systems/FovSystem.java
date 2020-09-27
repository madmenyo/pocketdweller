package com.buckriderstudio.pocketdweller.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
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

		// Calculate fov
		fovComponent.fovMap = fov.calculateFOV(resistanceMap, transformComponent.tilePosition.x, transformComponent.tilePosition.y, 10);
		// update fov position
		fovComponent.previous = transformComponent.tilePosition;

	}
}
