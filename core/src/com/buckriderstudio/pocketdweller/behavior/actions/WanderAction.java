package com.buckriderstudio.pocketdweller.behavior.actions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.MathUtils;
import com.buckriderstudio.pocketdweller.components.ActionComponent;
import com.buckriderstudio.pocketdweller.components.BehaviorComponent;
import com.buckriderstudio.pocketdweller.components.MoveComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.utility.Mappers;
import com.buckriderstudio.pocketdweller.world.World;

import squidpony.squidmath.Coord;


public class WanderAction extends ActionTask
{

	public WanderAction() {
		baseTime = 100;
	}

	@Override
	public Status execute()
	{
		Entity entity = getObject();
		//System.out.println("Wander task. Guard: " + checkGuard(control));
		//if (!checkGuard(control)) return Status.FAILED;


		BehaviorComponent behavior = Mappers.Behavior.get(entity);
		TransformComponent transform = Mappers.Transform.get(entity);
		ActionComponent action = Mappers.Action.get(entity);

		// If path is empty find position to wonder too
		if (behavior.path == null || behavior.path.isEmpty())
		{
			// Find suitable coord to wander too
			Coord coord = Coord.get(-1, -1);
			while (behavior.world.blocksMovement(coord.x, coord.y))
			{
				coord = Coord.get(transform.tilePosition.x + MathUtils.random(-10, 11), transform.tilePosition.y + MathUtils.random(-10, 11));
			}
			// Find path
			behavior.path = behavior.aStarSearch.path(transform.tilePosition, coord);
			System.out.println("New path set: " + behavior.path.size());
		}
		// Get stats (or action uses stats to calculate stuff like speed)
		Coord coord = behavior.path.remove(0);
		//System.out.println("Moving");
		MoveComponent moveComponent = new MoveComponent();

		moveComponent.from.set(transform.worldPosition.x, transform.worldPosition.y);
		moveComponent.too.set(coord.x * World.TILE_SIZE, coord.y * World.TILE_SIZE);
		transform.tilePosition = coord;

		entity.add(moveComponent);

		//Mappers.Time.get(entity).actingTime = Mappers.Time.get(entity).actingTime.plus(7000, ChronoUnit.MILLIS);
		addTimeToEntity();
		//action.action = new MoveAction(behavior.path.remove(0));

		return Status.SUCCEEDED;
	}

	@Override
	protected Task copyTo(Task task)
	{
		return new WanderAction();
	}
}
