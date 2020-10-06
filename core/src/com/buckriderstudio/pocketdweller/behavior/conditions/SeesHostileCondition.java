package com.buckriderstudio.pocketdweller.behavior.conditions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
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
		System.out.println("Running guard...");
		// Get the object
		Entity entity = getObject();
		// get the world
		BehaviorComponent behaviorComponent = Mappers.Behavior.get(entity);
		World world = behaviorComponent.world;
		// getFov
		//FovComponent fovComponent = Mappers.Fov.get(entity);
		TransformComponent thisTransform = Mappers.Transform.get(entity);


		// go trough list of entities and see if there are hostiles
		for (Entity e : world.getSentientEntities()){
			if (!isHostile(entity, e)) continue;
			TransformComponent otherTransfrom = Mappers.Transform.get(e);
			//System.out.println(thisTransform.tilePosition);
			//System.out.println(otherTransfrom.tilePosition);
			// Check if hostile is visible
			if (behaviorComponent.world.los(Mappers.Transform.get(entity).tilePosition, Mappers.Transform.get(e).tilePosition)){
				String name = Mappers.Info.get(entity).name;
				Gdx.app.log("SeeHostileCondition", name + " has been interupted by visible hostile.");
				return Status.FAILED;
			}
		}
		return Status.SUCCEEDED;
	}

	/**
	 * Determines if other entity is hostile
	 * Currently only player is hostile
	 * @param current
	 * @param other
	 * @return
	 */
	private boolean isHostile(Entity current, Entity other){
		if (Mappers.player.get(other) == null) return false;
		return true;
	}

	@Override
	protected Task<Entity> copyTo(Task<Entity> task)
	{
		return new SeesHostileCondition();
	}
}
