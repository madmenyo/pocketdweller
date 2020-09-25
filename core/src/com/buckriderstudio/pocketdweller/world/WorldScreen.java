package com.buckriderstudio.pocketdweller.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.buckriderstudio.pocketdweller.components.TextureComponent;
import com.buckriderstudio.pocketdweller.components.TransfromComponent;
import com.buckriderstudio.pocketdweller.systems.RenderSystem;

public class WorldScreen extends ScreenAdapter {

    private PooledEngine pooledEngine;
    private SpriteBatch spriteBatch;
    private Viewport viewport;

    private World world;

    public WorldScreen() {
        pooledEngine = new PooledEngine();
        spriteBatch = new SpriteBatch();
        viewport = new ScreenViewport();

        world = new World(128, 128);
    }


    @Override
    public void show() {
        pooledEngine.addSystem(new RenderSystem(world, spriteBatch));

        for (int i = 0; i < 200; i++)
		{
			pooledEngine.addEntity(dummyEntity());
		}
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

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        pooledEngine.update(delta);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

}
