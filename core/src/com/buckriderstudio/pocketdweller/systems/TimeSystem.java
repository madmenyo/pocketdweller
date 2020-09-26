package com.buckriderstudio.pocketdweller.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.buckriderstudio.pocketdweller.components.ActionComponent;
import com.buckriderstudio.pocketdweller.components.BehaviorComponent;
import com.buckriderstudio.pocketdweller.components.TimeUnitComponent;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class TimeSystem extends EntitySystem implements EntityListener
{
	public static ZonedDateTime CURRENT_TIME = ZonedDateTime.of(LocalDateTime.of(2021, Month.APRIL, 1, 8, 0, 0), ZoneId.systemDefault());
	private ImmutableArray<Entity> entities;

	private ComponentMapper<TimeUnitComponent> timeMapper = ComponentMapper.getFor(TimeUnitComponent.class);
	private ComponentMapper<ActionComponent> actionMapper = ComponentMapper.getFor(ActionComponent.class);
	private ComponentMapper<BehaviorComponent> behaviorMapper = ComponentMapper.getFor(BehaviorComponent.class);

	private Comparator<Entity> timeComparator = Comparator.comparingLong(e -> {
		TimeUnitComponent time = timeMapper.get(e);
		return time == null ? 0 : time.time.toInstant().toEpochMilli();
	});

	private PriorityQueue<Entity> queue = new PriorityQueue<>(timeComparator);

	private Entity currentEntity;

	public TimeSystem()
	{
		// Get first entity in line

		// If player controlled
		//if (queue.peek().getComponent(PlayerComponent))

	}

	@Override
	public void addedToEngine(Engine engine)
	{
		entities = engine.getEntitiesFor(Family.all(TimeUnitComponent.class).get());
	}

	@Override
	public void update(float deltaTime)
	{

	}

	@Override
	public void entityAdded(Entity entity)
	{
		//System.out.println("Entity added");
		//System.out.println("time: " + timeMapper.get(entity).timeUnit);
		queue.add(entity);
	}

	@Override
	public void entityRemoved(Entity entity)
	{
		queue.remove(entity);
	}
}
