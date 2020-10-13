package com.buckriderstudio.pocketdweller.behavior.actions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.Task;
import com.buckriderstudio.pocketdweller.components.TimeUnitComponent;
import com.buckriderstudio.pocketdweller.utility.Mappers;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class WaitAction extends ActionTask {

    public WaitAction() {
        baseTime = 100;
    }

    @Override
    public Status execute() {
        //Mappers.Time.get(getObject()).actingTime.plus(baseTime, ChronoUnit.MILLIS);
        addTimeToEntity();
        return Status.SUCCEEDED;

    }

    @Override
    protected Task<Entity> copyTo(Task<Entity> task) {
        return new WaitAction();
    }
}
