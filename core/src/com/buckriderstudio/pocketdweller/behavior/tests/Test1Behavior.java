package com.buckriderstudio.pocketdweller.behavior.tests;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.buckriderstudio.pocketdweller.components.InfoComponent;
import com.buckriderstudio.pocketdweller.utility.Mappers;


public class Test1Behavior extends LeafTask<Entity> {
    @Override
    public Status execute() {
        InfoComponent infoComponent = Mappers.Info.get(getObject());
        String name = "";

        if (infoComponent != null){
            name = infoComponent.name;
        }

        Gdx.app.log("Test1Behavior", name + " succeeded test1.");
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Entity> copyTo(Task<Entity> task) {
        return this;
    }
}
