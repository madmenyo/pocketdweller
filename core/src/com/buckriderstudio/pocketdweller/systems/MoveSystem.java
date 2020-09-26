package com.buckriderstudio.pocketdweller.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.buckriderstudio.pocketdweller.components.MoveComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;

/**
 * PocketDweller [2020]
 * This system is responsible for the move animations. This is independant of the time as it
 * simulates the turn. A thrown item might
 * By Menno Gouw
 */

public class MoveSystem extends IteratingSystem
{
	private ComponentMapper<TransformComponent> transformMapper;
	private ComponentMapper<MoveComponent> moveMapper;

	private Vector2 v2 = new Vector2();

	public MoveSystem()
	{
		super(Family.all(TransformComponent.class, MoveComponent.class).get());

		transformMapper = ComponentMapper.getFor(TransformComponent.class);
		moveMapper = ComponentMapper.getFor(MoveComponent.class);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime)
	{
		// Get needed components
		TransformComponent tc = transformMapper.get(entity);
		MoveComponent mc = moveMapper.get(entity);

		// Increase the current time of the move animation
		mc.currentTime += deltaTime;
		// Get the alpha time and clamp it within 0 and 1
		float a = MathUtils.clamp(mc.currentTime / mc.animationTime, 0, 1);
		// Calculate current position using the alpha and interpolation
		v2.set(mc.from).interpolate(mc.too, a, mc.interpolation);
		// Set the transform
		tc.worldPosition.set(v2, 0);

		// If this is finished remove the move component
		if (a == 1) entity.remove(MoveComponent.class);


	}
}
