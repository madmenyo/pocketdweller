package com.buckriderstudio.pocketdweller.actions;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;

public class WaitAction extends Action {

    public WaitAction(int time) {
        super(time);
    }

	@Override
	public int getTime(Entity entity)
	{
		return time;
	}

	@Override
    public void perform(Entity entity, Engine engine) {

    }
}
