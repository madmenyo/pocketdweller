package com.buckriderstudio.pocketdweller.behavior;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.MathUtils;
import com.buckriderstudio.pocketdweller.actions.MoveAction;
import com.buckriderstudio.pocketdweller.components.ActionComponent;
import com.buckriderstudio.pocketdweller.components.BehaviorComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.utility.Mappers;

import squidpony.squidmath.Coord;


public class WanderTask extends LeafTask<Entity>
{

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
		action.action = new MoveAction(behavior.path.remove(0));

		return Status.RUNNING;
	}

	@Override
	protected Task copyTo(Task task)
	{
		return new WanderTask();
	}
}
