package com.buckriderstudio.pocketdweller.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.buckriderstudio.pocketdweller.components.TextureComponent;
import com.buckriderstudio.pocketdweller.components.TransfromComponent;

public class RenderSystem extends IteratingSystem {

    private ComponentMapper<TransfromComponent> transformMapper;
    private ComponentMapper<TextureComponent> textureMapper;

    public RenderSystem() {
        super(Family.all(TransfromComponent.class, TextureComponent.class).get());

        transformMapper = ComponentMapper.getFor(TransfromComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        System.out.println("Processing entity");
    }

    @Override
    public void update(float deltaTime) {
        System.out.println("Updating system, " + getEntities().size());
    }
}
