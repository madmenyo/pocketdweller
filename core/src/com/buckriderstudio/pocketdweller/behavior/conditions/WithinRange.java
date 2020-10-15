package com.buckriderstudio.pocketdweller.behavior.conditions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.buckriderstudio.pocketdweller.components.BehaviorComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.utility.Mappers;

import squidpony.squidmath.Coord;

public class WithinRange extends LeafTask<Entity> {

    @Override
    public Status execute() {
        Entity entity = getObject();
        BehaviorComponent thisBehavior = Mappers.Behavior.get(entity);
        if (thisBehavior.target == null) return Status.FAILED;

        TransformComponent thisTransform = Mappers.Transform.get(entity);
        TransformComponent targetTransform = Mappers.Transform.get(thisBehavior.target);

        // Get range of carried weapons and decide if plausable to use it
        double range = 1;

        if (thisTransform.tilePosition.distance(targetTransform.tilePosition) > 100d && thisBehavior.world.los(thisTransform.tilePosition, targetTransform.tilePosition)){
            return Status.SUCCEEDED;
        }

        return Status.FAILED;
    }

    @Override
    protected Task<Entity> copyTo(Task<Entity> task) {
        return new WithinRange();
    }
}
