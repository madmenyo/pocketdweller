package com.buckriderstudio.pocketdweller.behavior;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.buckriderstudio.pocketdweller.components.BehaviorComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.utility.Mappers;
import com.buckriderstudio.pocketdweller.world.World;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class SeesHostileCondition extends LeafTask<Entity>
{

	@Override
	public Status execute()
	{
		// Get the object
		Entity entity = getObject();
		// get the world
		BehaviorComponent behaviorComponent = Mappers.Behavior.get(entity);
		World world = behaviorComponent.world;
		// getFov
		//FovComponent fovComponent = Mappers.Fov.get(entity);

		// go trough list of entities and see if there are hostiles
		for (Entity e : world.getSentientEntities()){
			TransformComponent transformComponent = Mappers.Transform.get(e);
			/*
			if (fovComponent.fovMap[transformComponent.tilePosition.x][transformComponent.tilePosition.y] == 0){
				return Status.SUCCEEDED;
			}

			 */
		}
		return Status.FAILED;
	}

	@Override
	protected Task<Entity> copyTo(Task<Entity> task)
	{
		return new SeesHostileCondition();
	}
}
