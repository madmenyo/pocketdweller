package com.buckriderstudio.pocketdweller.behavior;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.decorator.Include;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibrary;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.buckriderstudio.pocketdweller.entities.Mob;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class Behaviors
{
	private BehaviorTreeLibraryManager libraryManager;
	public BehaviorTreeLibraryManager getLibraryManager()
	{
		return libraryManager;
	}

	private BehaviorTreeLibrary library;

	public Behaviors()
	{
		libraryManager = BehaviorTreeLibraryManager.getInstance();
		library = new BehaviorTreeLibrary(BehaviorTreeParser.DEBUG_HIGH);

		registeringWanderingMobBehavior();
		libraryManager.setLibrary(library);

	}

	public BehaviorTree<Entity> getWanderingBehavior(Entity entity){
		return libraryManager.createBehaviorTree("entity", entity);
	}

	private void registeringWanderingMobBehavior()
	{
		Include<Entity> include = new Include<>();
		include.lazy = true;
		include.subtree = "entity.actual";
		BehaviorTree<Entity> includeBehavior = new BehaviorTree<>(include);
		library.registerArchetypeTree("entity", includeBehavior);

		BehaviorTree<Entity> actualBehavior = new BehaviorTree<>(Mob.wanderingMobBehavior());
		library.registerArchetypeTree("entity.actual", actualBehavior);
	}
}
