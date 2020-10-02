package com.buckriderstudio.pocketdweller.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public abstract class Action {
    protected int time;
	public abstract int getTime(Entity entity);

	public Action(int time) {
        this.time = time;
    }

    public abstract void perform(Entity entity, Engine engine);
}
