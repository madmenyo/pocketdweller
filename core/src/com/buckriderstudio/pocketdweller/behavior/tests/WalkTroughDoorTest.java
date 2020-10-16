package com.buckriderstudio.pocketdweller.behavior.tests;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

public class WalkTroughDoorTest extends LeafTask<Entity> {

    @Override
    public Status execute() {
        Gdx.app.log("WalkTroughDoorTest", "Walking trough door, this node succeeded.");
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Entity> copyTo(Task<Entity> task) {
        return this;
    }
}
