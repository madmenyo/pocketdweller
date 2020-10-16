package com.buckriderstudio.pocketdweller.behavior.tests;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;

public class OpenDoorTest extends LeafTask<Entity> {

    @Override
    public Status execute() {
        Gdx.app.log("OpenDoorTest", "Opening the door, this node succeeded.");
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Entity> copyTo(Task<Entity> task) {
        return this;
    }
}
