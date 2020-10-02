package com.buckriderstudio.pocketdweller.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.buckriderstudio.pocketdweller.components.MoveComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.utility.Mappers;
import com.buckriderstudio.pocketdweller.world.World;

import squidpony.squidmath.Coord;

public class MoveAction extends Action {

    private Coord coord;

    public MoveAction(Coord coord) {
        super(700);
        this.coord = coord;
    }

	@Override
	public int getTime(Entity entity)
	{
		// Get entity speed
		// Get entity injury or focus
		// Get other stuff that influences move time

		return time;
	}

	@Override
    public void perform(Entity entity, Engine engine) {
        TransformComponent transformComponent = entity.getComponent(TransformComponent.class);

        if (transformComponent == null) throw new NullPointerException("Cannot perform move action on Entity without TransformComponent");

		MoveComponent moveComponent = engine.createComponent(MoveComponent.class);
		moveComponent.from.set(transformComponent.worldPosition.x, transformComponent.worldPosition.y);
		moveComponent.too.set(coord.x * World.TILE_SIZE, coord.y * World.TILE_SIZE);

		transformComponent.tilePosition = coord;
        //System.out.println("Coord moved to: " + coord);
		entity.add(moveComponent);
    }
}
