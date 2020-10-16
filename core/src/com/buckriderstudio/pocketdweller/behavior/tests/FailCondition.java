package com.buckriderstudio.pocketdweller.behavior.tests;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.buckriderstudio.pocketdweller.components.InfoComponent;
import com.buckriderstudio.pocketdweller.utility.Mappers;

public class FailCondition extends LeafTask<Entity> {

    @Override
    public Status execute() {
        InfoComponent infoComponent = Mappers.Info.get(getObject());
        String name = "";

        if (infoComponent != null){
            name = infoComponent.name;
        }

        Gdx.app.log("FailCondition", name + " failed condition.");

        return Status.FAILED;
    }

    @Override
    protected Task copyTo(Task task) {
        return this;
    }
}
