package com.buckriderstudio.pocketdweller.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.buckriderstudio.pocketdweller.components.TextureComponent;
import com.buckriderstudio.pocketdweller.components.TransfromComponent;
import com.buckriderstudio.pocketdweller.systems.RenderSystem;

public class WorldScreen extends ScreenAdapter {

    private PooledEngine pooledEngine;
    private Engine engine;
    private Viewport viewport;

    public WorldScreen() {
        pooledEngine = new PooledEngine();
        engine = new Engine();
        viewport = new ScreenViewport();
    }


    @Override
    public void show() {
        pooledEngine.addSystem(new RenderSystem());
        engine.addSystem(new RenderSystem());

        pooledEngine.addEntity(dummyEntity());
        pooledEngine.addEntity(dummyEntity());
        pooledEngine.addEntity(dummyEntity());

        engine.addEntity(dummyEntity());
        engine.addEntity(dummyEntity());
        engine.addEntity(dummyEntity());

    }

    private Entity dummyEntity(){
        Entity e = pooledEngine.createEntity();
        TextureComponent textureComponent = pooledEngine.createComponent(TextureComponent.class);
        e.add(textureComponent);

        TransfromComponent transfromComponent = pooledEngine.createComponent(TransfromComponent.class);
        e.add(transfromComponent);

        return e;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
