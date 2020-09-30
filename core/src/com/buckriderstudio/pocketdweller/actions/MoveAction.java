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

    public MoveAction(int time, Coord coord) {
        super(time);
        this.coord = coord;

    }

    @Override
    public void perform(Entity entity, Engine engine) {
        TransformComponent transformComponent = entity.getComponent(TransformComponent.class);

        if (transformComponent == null) throw new NullPointerException("Cannot perform move action on Entity without TransformComponent");

        //transformComponent.tilePosition = coord;
        //transformComponent.worldPosition.set(coord.x * World.TILE_SIZE, coord.y * World.TILE_SIZE, 0);

		MoveComponent moveComponent = engine.createComponent(MoveComponent.class);
		moveComponent.from.set(transformComponent.worldPosition.x, transformComponent.worldPosition.y);
		moveComponent.too.set(coord.x * World.TILE_SIZE, coord.y * World.TILE_SIZE);

		TransformComponent transformComponent1 = Mappers.Transform.get(entity);
		transformComponent.tilePosition = coord;
		entity.add(moveComponent);
    }
}
