package com.buckriderstudio.pocketdweller.behavior.actions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.Task;
import com.buckriderstudio.pocketdweller.components.BehaviorComponent;
import com.buckriderstudio.pocketdweller.components.MoveComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.utility.Mappers;
import com.buckriderstudio.pocketdweller.world.World;

import squidpony.squidmath.Coord;

public class MoveToPositionAction extends ActionTask {

    public MoveToPositionAction() {
        baseTime = 700;
    }

    @Override
    public Status execute() {
        Entity entity = getObject();
        BehaviorComponent behaviorComponent = Mappers.Behavior.get(entity);
        // If there is no path return failed TODO: reset to default tree?
        if (behaviorComponent.path == null || behaviorComponent.path.isEmpty()) return Status.FAILED;

        TransformComponent transformComponent = Mappers.Transform.get(entity);

        // Get stats (or action uses stats to calculate stuff like speed)
        Coord coord = behaviorComponent.path.remove(0);
        //System.out.println("Moving");
        MoveComponent moveComponent = new MoveComponent();

        moveComponent.from.set(transformComponent.worldPosition.x, transformComponent.worldPosition.y);
        moveComponent.too.set(coord.x * World.TILE_SIZE, coord.y * World.TILE_SIZE);
        transformComponent.tilePosition = coord;

        entity.add(moveComponent);

        // TODO: If diagonal move add time (Squidlib AStar does not support diagonals with higher cost.

        //Mappers.Time.get(entity).actingTime = Mappers.Time.get(entity).actingTime.plus(7000, ChronoUnit.MILLIS);
        addTimeToEntity();
        //action.action = new MoveAction(behavior.path.remove(0));

        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Entity> copyTo(Task<Entity> task) {
        return new MoveToPositionAction();
    }
}
