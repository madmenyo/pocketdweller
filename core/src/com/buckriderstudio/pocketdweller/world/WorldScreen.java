package com.buckriderstudio.pocketdweller.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.buckriderstudio.pocketdweller.Controller;
import com.buckriderstudio.pocketdweller.components.FovComponent;
import com.buckriderstudio.pocketdweller.components.PlayerComponent;
import com.buckriderstudio.pocketdweller.components.TextureComponent;
import com.buckriderstudio.pocketdweller.components.TimeUnitComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.systems.FovSystem;
import com.buckriderstudio.pocketdweller.systems.MoveSystem;
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

        world = new World(128, 128);

        atlas = new TextureAtlas("tilesets/dungeon.atlas");
    }


    @Override
    public void show() {
		// Add dummy player and controller
		Entity player = dummyPlayer();
		controller = new Controller(worldView.getViewport().getCamera(), player, world);

    	// Add systems
		pooledEngine.addSystem(new FovSystem(world));
        pooledEngine.addSystem(new RenderSystem(world, spriteBatch, worldView, player));
        TimeSystem timeSystem = new TimeSystem();
        pooledEngine.addEntityListener(Family.all(TimeUnitComponent.class).get(), timeSystem);
        pooledEngine.addSystem(timeSystem);

        pooledEngine.addSystem(new MoveSystem());

        // Add player to engine
		pooledEngine.addEntity(player);


        // Add dummy entities
        for (int i = 0; i < 200; i++)
		{
			pooledEngine.addEntity(dummyEntity());
		}

        // Set input
        Gdx.input.setInputProcessor(controller.getInputMultiplexer());
    }

	/**
	 * Creates a dummy player for testing
	 * @return dummy player controlled entity
	 */
	private Entity dummyPlayer() {
        Entity player = pooledEngine.createEntity();

        TransformComponent transformComponent = pooledEngine.createComponent(TransformComponent.class);
        Coord coord = Coord.get(0, 0);
        while (world.blocksMovement(coord.x, coord.y)){
            coord = coord.add(Coord.get(1, 1));
            System.out.println(coord);
        }
        transformComponent.tilePosition = coord;
        transformComponent.worldPosition.set(coord.x * World.TILE_SIZE, coord.y * World.TILE_SIZE, 0);
        player.add(transformComponent);

        TextureComponent textureComponent = pooledEngine.createComponent(TextureComponent.class);
        textureComponent.region = atlas.findRegion("knight_idle_anim_f0");
        player.add(textureComponent);

        player.add(new PlayerComponent());
        player.add(new TimeUnitComponent());
        player.add(new FovComponent());

        return player;
    }

    private Entity mobEntity(){
		Entity e = pooledEngine.createEntity();
		TextureComponent textureComponent = pooledEngine.createComponent(TextureComponent.class);
		textureComponent.region = atlas.findRegion("slime_idle_anim_f0");
		e.add(textureComponent);

		TransformComponent transformComponent = pooledEngine.createComponent(TransformComponent.class);
		Coord coord = Coord.get(-1, -1);
		while (world.blocksMovement(coord.x, coord.y))
		{
			coord = Coord.get((int) (MathUtils.random() * world.getWidth()), (int) (MathUtils.random() * world.getHeight()));
		}
		return e;
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

        TransformComponent transformComponent = pooledEngine.createComponent(TransformComponent.class);
		Coord coord = Coord.get(-1, -1);
        while (world.blocksMovement(coord.x, coord.y))
		{
			coord = Coord.get((int) (MathUtils.random() * world.getWidth()), (int) (MathUtils.random() * world.getHeight()));
		}
		transformComponent.tilePosition = coord;
		transformComponent.worldPosition.set(coord.x * World.TILE_SIZE, coord.y * World.TILE_SIZE, 0);
        e.add(transformComponent);

        return e;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.02f, .026f, .042f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(worldView.getViewport().getCamera().combined);
        pooledEngine.update(delta);

    }

    @Override
    public void resize(int width, int height) {
        worldView.getViewport().update(width, height);
    }

}
