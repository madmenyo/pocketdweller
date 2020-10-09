package com.buckriderstudio.pocketdweller.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.buckriderstudio.pocketdweller.Controller;
import com.buckriderstudio.pocketdweller.behavior.Behaviors;
import com.buckriderstudio.pocketdweller.components.InfoComponent;
import com.buckriderstudio.pocketdweller.entities.Mob;
import com.buckriderstudio.pocketdweller.gui.Gui;
import com.buckriderstudio.pocketdweller.utility.Assets;
import com.buckriderstudio.pocketdweller.utility.DebugDraw;
import com.buckriderstudio.pocketdweller.utility.DebugTable;
import com.buckriderstudio.pocketdweller.components.PlayerComponent;
import com.buckriderstudio.pocketdweller.components.TextureComponent;
import com.buckriderstudio.pocketdweller.components.TimeUnitComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.systems.MoveSystem;
import com.buckriderstudio.pocketdweller.systems.RenderSystem;
import com.buckriderstudio.pocketdweller.systems.TimeSystem;

import squidpony.squidgrid.SoundMap;
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

    private AssetManager assetManager;
    private TextureAtlas creatureAtlas;

    private Stage stage;
    private Gui gui;
    private DebugTable debugTable;
    private DebugDraw debugDraw;

    SoundMap soundMap;

    public WorldScreen(AssetManager assetManager) {
    	this.assetManager = assetManager;
        pooledEngine = new PooledEngine();
        spriteBatch = new SpriteBatch();
        worldView = new WorldView();

        world = new World(32, 32);


        stage = new Stage(new ExtendViewport(1280, 720));

        creatureAtlas = assetManager.get(Assets.CREATURES);
    }


    @Override
    public void show() {
		// Add dummy player and controller
		Entity player = dummyPlayer();
		controller = new Controller(worldView.getViewport().getCamera(), player, world);

    	// Add systems
        pooledEngine.addSystem(new RenderSystem(world, spriteBatch, worldView, player));
        TimeSystem timeSystem = new TimeSystem(world);
        pooledEngine.addSystem(timeSystem);

        pooledEngine.addSystem(new MoveSystem());

        // Add listeners
		pooledEngine.addEntityListener(Family.all(TimeUnitComponent.class).get(), timeSystem);
		pooledEngine.addEntityListener(Family.all(TimeUnitComponent.class).get(), world);

        // Add player to engine
		pooledEngine.addEntity(player);

		// Add some mobs
		Behaviors behaviors = new Behaviors();
        for (int i = 0; i < 50; i++)
		{
			String name = "Mob[" + i + "]";
			pooledEngine.addEntity(mobEntity(name, behaviors));
		}

		gui = new Gui(timeSystem, assetManager);
        debugTable = new DebugTable(world);
        debugDraw = new DebugDraw(worldView);



        stage.addActor(debugTable);
		stage.addActor(new DebugTable(world));

        // Set input
        Gdx.input.setInputProcessor(new InputMultiplexer(gui, stage, controller.getInputMultiplexer()));


        soundMap = new SoundMap(world.getCharMap());
        Coord coord = Coord.get(-1, -1);
        for (int i = 0; i < 5; i++){
            while (world.blocksMovement(coord.x, coord.y)){
                coord = Coord.get(MathUtils.random(world.getWidth()), MathUtils.random(world.getHeight()));
            }
            soundMap.setSound(coord, 5);
        }

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
        textureComponent.region = creatureAtlas.findRegion("knight_idle_anim_f0");
        player.add(textureComponent);

        player.add(new PlayerComponent());
        player.add(new TimeUnitComponent());

        InfoComponent infoComponent = pooledEngine.createComponent(InfoComponent.class);
        infoComponent.name = "Player";
        player.add(infoComponent);

        return player;
    }

    private Entity mobEntity(String name, Behaviors behaviors){
		Coord coord = Coord.get(-1, -1);
		while (world.blocksMovement(coord.x, coord.y))
		{
			coord = Coord.get((int) (MathUtils.random() * world.getWidth()), (int) (MathUtils.random() * world.getHeight()));
		}

		return new Mob(name, coord, world, creatureAtlas.findRegion("goblin_idle_anim_f0"), pooledEngine, behaviors);
	}


    @Override
    public void render(float delta) {
	    stage.act(delta);
	    gui.act(delta);

        Gdx.gl.glClearColor(.02f, .026f, .042f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(worldView.getViewport().getCamera().combined);
        pooledEngine.update(delta);

        stage.draw();

		gui.draw();

		//debugDraw.drawMap(soundMap.gradientMap);


    }

    @Override
    public void resize(int width, int height) {
        worldView.getViewport().update(width, height);
        gui.resize(width, height, true);
    }

}
