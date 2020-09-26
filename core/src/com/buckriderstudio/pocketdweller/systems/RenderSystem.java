package com.buckriderstudio.pocketdweller.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.buckriderstudio.pocketdweller.components.TextureComponent;
import com.buckriderstudio.pocketdweller.components.TransfromComponent;
import com.buckriderstudio.pocketdweller.world.World;
import com.buckriderstudio.pocketdweller.world.WorldView;

public class RenderSystem extends IteratingSystem {

    private ComponentMapper<TransfromComponent> transformMapper;
    private ComponentMapper<TextureComponent> textureMapper;

    private Array<Entity> renderQueue;

    private World world;
	private WorldView worldView;
    private SpriteBatch batch;
    private TextureAtlas atlas;


    public RenderSystem(World world, SpriteBatch batch, WorldView worldView) {
        super(Family.all(TransfromComponent.class, TextureComponent.class).get());
        this.world = world;
        this.worldView = worldView;
        this.batch = batch;

        atlas = new TextureAtlas("tilesets/dungeon.atlas");

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

    	int startX = (int)(worldView.leftWorld() / World.TILE_SIZE);
		int endX = (int)(worldView.rightWorld() / World.TILE_SIZE);
		int startY = (int)(worldView.topWorld() / World.TILE_SIZE);
		int endY = (int)(worldView.bottomWorld() / World.TILE_SIZE);

    	batch.begin();
		for (int y = startY; y >= endY; y--)
		{
			for (int x = startX; x <= endX; x++)
			{
				if (!world.withinBounds(x, y)) continue;
				if (world.getCharMap()[x][y] == '#')
				{
					batch.draw(atlas.findRegion("wall"), x * World.TILE_SIZE, y * World.TILE_SIZE,
							World.TILE_SIZE, World.TILE_SIZE);
				}
				else if (world.getCharMap()[x][y] == '.')
				{
					batch.draw(atlas.findRegion("floor"), x * World.TILE_SIZE, y * World.TILE_SIZE,
							World.TILE_SIZE, World.TILE_SIZE);
				}
			}
		}

		for (Entity entity : renderQueue){
			TextureComponent textureComponent = textureMapper.get(entity);
			TransfromComponent transfromComponent = transformMapper.get(entity);
			batch.draw(textureComponent.region, transfromComponent.worldPosition.x, transfromComponent.worldPosition.y, World.TILE_SIZE, World.TILE_SIZE);
		}

		batch.end();

    }

}
