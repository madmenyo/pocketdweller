package com.buckriderstudio.pocketdweller.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.buckriderstudio.pocketdweller.components.TimeUnitComponent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.temporal.JulianFields;
import java.time.temporal.TemporalField;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class TimeSystem extends EntitySystem implements EntityListener
{
	private LocalDateTime dateTime;



	private ImmutableArray<Entity> entities;

	private ComponentMapper<TimeUnitComponent> timeMapper = ComponentMapper.getFor(TimeUnitComponent.class);

	private Comparator<Entity> timeComparator = Comparator.comparingInt(e -> {
		TimeUnitComponent time = timeMapper.get(e);
		return time == null ? 0 : time.timeUnit;
	});

	private PriorityQueue<Entity> queue = new PriorityQueue<>(timeComparator);

	private Entity currentEntity;

	public TimeSystem()
	{
		dateTime = LocalDateTime.of(2021, Month.APRIL, 1, 8, 0, 0);

		//LocalDate.of(2021, Month.APRIL, 1);


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
		System.out.println("Entity added");
		System.out.println("time: " + timeMapper.get(entity).timeUnit);
		queue.add(entity);
	}

	@Override
	public void entityRemoved(Entity entity)
	{
		queue.remove(entity);
	}
}
