package com.buckriderstudio.pocketdweller.world;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.buckriderstudio.pocketdweller.Controller;
import com.buckriderstudio.pocketdweller.components.TextureComponent;
import com.buckriderstudio.pocketdweller.components.TimeUnitComponent;
import com.buckriderstudio.pocketdweller.components.TransfromComponent;
import com.buckriderstudio.pocketdweller.systems.RenderSystem;
import com.buckriderstudio.pocketdweller.systems.TimeSystem;

import squidpony.squidmath.Coord;

/**
 * Wrapper class responsible for displaying every aspect of the game world including the
 * world, entities and gui.
 */
public class WorldScreen extends ScreenAdapter {

    private PooledEngine pooledEngine;
    private SpriteBatch spriteBatch;
    private WorldView worldView;
    private Controller controller;

    private World world;

    private TextureAtlas atlas;

    public WorldScreen() {
        pooledEngine = new PooledEngine();
        spriteBatch = new SpriteBatch();
        worldView = new WorldView();
		controller = new Controller(worldView.getViewport().getCamera());

        world = new World(128, 128);

        atlas = new TextureAtlas("tilesets/dungeon.atlas");
    }


    @Override
    public void show() {
        pooledEngine.addSystem(new RenderSystem(world, spriteBatch, worldView));
        TimeSystem timeSystem = new TimeSystem();
        pooledEngine.addEntityListener(Family.all(TimeUnitComponent.class).get(), timeSystem);
        pooledEngine.addSystem(timeSystem);


        Entity e = pooledEngine.createEntity();
		TimeUnitComponent timeUnitComponent = pooledEngine.createComponent(TimeUnitComponent.class);
		e.add(timeUnitComponent);
		pooledEngine.addEntity(e);



        for (int i = 0; i < 200; i++)
		{
			pooledEngine.addEntity(dummyEntity());
		}

        Gdx.input.setInputProcessor(controller.getInputMultiplexer());
    }

	/**
	 * Method just for tesing
	 * @return the dummy entity
	 */
	private Entity dummyEntity(){
        Entity e = pooledEngine.createEntity();
        TextureComponent textureComponent = pooledEngine.createComponent(TextureComponent.class);
        textureComponent.region = atlas.findRegion("slime_idle_anim_f0");
        e.add(textureComponent);

        TransfromComponent transfromComponent = pooledEngine.createComponent(TransfromComponent.class);
		Coord coord = Coord.get(-1, -1);
        while (world.blocksMovement(coord.x, coord.y))
		{
			coord = Coord.get((int) (MathUtils.random() * world.getWidth()), (int) (MathUtils.random() * world.getHeight()));
		}
		transfromComponent.tilePosition = coord;
		transfromComponent.worldPosition.set(coord.x * World.TILE_SIZE, coord.y * World.TILE_SIZE, 0);
        e.add(transfromComponent);

        return e;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(worldView.getViewport().getCamera().combined);
        pooledEngine.update(delta);

    }

    @Override
    public void resize(int width, int height) {
        worldView.getViewport().update(width, height);
    }

}
