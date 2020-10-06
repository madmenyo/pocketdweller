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
import com.buckriderstudio.pocketdweller.components.PlayerComponent;
import com.buckriderstudio.pocketdweller.components.TimeUnitComponent;
import com.buckriderstudio.pocketdweller.utility.Mappers;
import com.buckriderstudio.pocketdweller.world.World;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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

	private World world;


	public TimeSystem(World world)
	{

		this.world = world;
	}

	@Override
	public void addedToEngine(Engine engine)
	{
		entities = engine.getEntitiesFor(Family.all(TimeUnitComponent.class).get());
	}

	@Override
	public void update(float deltaTime)
	{

		// If player controlled
		if (queue.peek().getComponent(PlayerComponent.class) != null){

			processPlayerTurn();
		} else { // Must be other
			while (queue.peek().getComponent(PlayerComponent.class) == null)
				processOther();
		}
	}

	private void processOther() {
		// Poll the next entity, this entity should act now
		Entity entity = queue.poll();

		ActionComponent action = Mappers.Action.get(entity);
		// If no action let AI pick action
		while (action.action == null){

			//Gdx.app.log("TimeSystem", Mappers.Info.get(entity).name + " : taking behavior step");
			BehaviorComponent behavior = Mappers.Behavior.get(entity);
			behavior.behaviorTree.step();
		}


		// If has action perform it
		// Currently always true since above loop
		// Perform
		action.action.perform(entity, getEngine());
		// add time
		TimeUnitComponent time = Mappers.Time.get(entity);
		time.time = time.time.plus(action.action.getTime(entity), ChronoUnit.MILLIS);
		//Gdx.app.log("TimeSystem", Mappers.Info.get(entity).name + " : action completed. Time added " + action.timeInMiliSeconds);

		// reset action
		action.action = null;
		// reinsert in queue
		queue.add(entity);
	}

	private void processPlayerTurn()
	{
		ActionComponent actionComponent = actionMapper.get(queue.peek());
		TimeUnitComponent timeUnitComponent = timeMapper.get(queue.peek());
		PlayerComponent playerComponent = queue.peek().getComponent(PlayerComponent.class);
		CURRENT_TIME = timeUnitComponent.time;

		// If player has action perform it
		if (queue.peek().getComponent(ActionComponent.class) != null)
		{
			world.resetFovTime();
			// Pull out of queue
			Entity player = queue.poll();
			//perform
			//System.out.println("Performing action: " + actionComponent.action);
			actionComponent.action.perform(player, getEngine());

			timeUnitComponent.time = timeUnitComponent.time.plus(actionComponent.action.getTime(player), ChronoUnit.MILLIS);
			player.remove(ActionComponent.class);

			// push into queue
			queue.add(player);
			playerComponent.playerTurn = false;

			//Gdx.app.log("TimeSystem","Player acted " + actionComponent.timeInMiliSeconds + "ms. Next action at: " + timeUnitComponent.time);
		}
		else
		{
			//System.out.println("Player need to decide what to do");
			playerComponent.playerTurn = true;
			return;
		}
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
