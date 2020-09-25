package com.buckriderstudio.pocketdweller.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import com.buckriderstudio.pocketdweller.components.TextureComponent;
import com.buckriderstudio.pocketdweller.components.TransfromComponent;

public class RenderSystem extends IteratingSystem {

    private ComponentMapper<TransfromComponent> transformMapper;
    private ComponentMapper<TextureComponent> textureMapper;

    private Array<Entity> renderQueue;

    public RenderSystem() {
        super(Family.all(TransfromComponent.class, TextureComponent.class).get());

        transformMapper = ComponentMapper.getFor(TransfromComponent.class);
        textureMapper = ComponentMapper.getFor(TextureComponent.class);

        renderQueue = new Array<>();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
		renderQueue.add(entity);
    }

    @Override
    public void update(float deltaTime) {
    	renderQueue.clear();
    	super.update(deltaTime);
        System.out.println("Updating system, " + getEntities().size() + ", " + renderQueue.size);

    }
}
