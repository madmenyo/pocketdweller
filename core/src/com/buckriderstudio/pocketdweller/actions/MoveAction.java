package com.buckriderstudio.pocketdweller.actions;

import com.badlogic.ashley.core.Entity;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.world.World;

import squidpony.squidmath.Coord;

public class MoveAction extends Action {

    private Coord coord;

    public MoveAction(int time, Coord coord) {
        super(time);
        this.coord = coord;

    }

    @Override
    public void perform(Entity entity) {
        TransformComponent transformComponent = entity.getComponent(TransformComponent.class);

        if (transformComponent == null) throw new NullPointerException("Cannot perform move action on Entity without TransformComponent");

        transformComponent.tilePosition = coord;
        transformComponent.worldPosition.set(coord.x * World.TILE_SIZE, coord.y * World.TILE_SIZE, 0);
    }
}
