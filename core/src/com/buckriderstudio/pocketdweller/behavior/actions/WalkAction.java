package com.buckriderstudio.pocketdweller.behavior.actions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.Task;

public class WalkAction extends ActionTask {

    public WalkAction() {
        // Time to move in cardinal direction
        baseTime = 700;
    }

    @Override
    public Status execute() {
        return null;
    }

    @Override
    protected Task<Entity> copyTo(Task<Entity> task) {
        return null;
    }
}
