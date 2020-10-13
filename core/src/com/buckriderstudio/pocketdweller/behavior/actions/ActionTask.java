package com.buckriderstudio.pocketdweller.behavior.actions;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.buckriderstudio.pocketdweller.components.TimeUnitComponent;
import com.buckriderstudio.pocketdweller.utility.Mappers;

import java.time.temporal.ChronoUnit;


public abstract class ActionTask extends LeafTask<Entity> {
    protected int baseTime;

    protected void addTimeToEntity(){
        //System.out.println("adding time: " + baseTime);
        TimeUnitComponent tu = Mappers.Time.get(getObject());
        tu.actingTime.addMilli(baseTime);
    }


}
