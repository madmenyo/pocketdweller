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
import com.buckriderstudio.pocketdweller.utility.GameTime;
import com.buckriderstudio.pocketdweller.utility.Mappers;
import com.buckriderstudio.pocketdweller.world.World;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class TimeSystem extends EntitySystem implements EntityListener
{
	public static GameTime CURRENT_TIME = new GameTime();
	private ImmutableArray<Entity> entities;

	private ComponentMapper<TimeUnitComponent> timeMapper = ComponentMapper.getFor(TimeUnitComponent.class);
	private ComponentMapper<ActionComponent> actionMapper = ComponentMapper.getFor(ActionComponent.class);
	private ComponentMapper<BehaviorComponent> behaviorMapper = ComponentMapper.getFor(BehaviorComponent.class);

	/*
	private Comparator<Entity> timeComparator = Comparator.comparingLong(e -> {
		TimeUnitComponent time = timeMapper.get(e);
		return time == null ? 0 : time.actingTime.toInstant().toEpochMilli();
	});*/

	private Comparator<Entity> timeComparator = (o1, o2) -> Mappers.Time.get(o1).actingTime.compareTo(Mappers.Time.get(o2).actingTime);

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
		//Pick on time and reinsert when time is added
		handleNext();

		// Pick on custom action
		// If player controlled
		if (queue.peek().getComponent(PlayerComponent.class) != null){

			processPlayerTurn();
		} else { // Must be other
			while (queue.peek().getComponent(PlayerComponent.class) == null)
				processOther();
		}
	}

	private void handleNext() {
		if (queue.peek().getComponent(PlayerComponent.class) != null){
			// TODO if player has behavior keep tickin it until acting time changed. This way player can do longer behavioral tasks

			// as long as the players acting time is equal the player can act and rest must wait
			if (CURRENT_TIME.equals(Mappers.Time.get(queue.peek()).actingTime)) return;
			// Player must have acted, reinsert into tree
			queue.add(queue.poll());
		} else { // Must be other
			while (queue.peek().getComponent(PlayerComponent.class) == null) {
				// Get NPC
				Entity npc = queue.poll();
				// Set its acting time to current time, since it is his time to act
				CURRENT_TIME.setTime(Mappers.Time.get(npc).actingTime);
				// Get BT to tick it as long as a action is performed
				BehaviorComponent bc = Mappers.Behavior.get(npc);
				while (CURRENT_TIME.equals(Mappers.Time.get(npc).actingTime)){
					System.out.println(CURRENT_TIME.getTimeString() + " : " + Mappers.Time.get(npc).actingTime.getTimeString());

					//System.out.println("Stepping behavior: \n" + CURRENT_TIME.format(DateTimeFormatter.ISO_TIME) + "\n" + Mappers.Time.get(npc).actingTime.format(DateTimeFormatter.ISO_TIME));
					bc.behaviorTree.step();
				}
				// NPC must have acted, reinsert it to the queue
				queue.add(npc);
			}
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
		time.actingTime.addMilli(action.action.getTime(entity)); //time.actingTime.plus(action.action.getTime(entity), ChronoUnit.MILLIS);
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
		CURRENT_TIME = timeUnitComponent.actingTime;

		// If player has action perform it
		if (queue.peek().getComponent(ActionComponent.class) != null)
		{
			world.resetFovTime();
			// Pull out of queue
			Entity player = queue.poll();
			//perform
			//System.out.println("Performing action: " + actionComponent.action);
			actionComponent.action.perform(player, getEngine());

			//timeUnitComponent.actingTime = timeUnitComponent.actingTime.plus(actionComponent.action.getTime(player), ChronoUnit.MILLIS);
			timeUnitComponent.actingTime.addMilli(actionComponent.action.getTime(player));
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
