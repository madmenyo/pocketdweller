package com.buckriderstudio.pocketdweller.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.buckriderstudio.pocketdweller.components.FovComponent;
import com.buckriderstudio.pocketdweller.components.TextureComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.world.World;
import com.buckriderstudio.pocketdweller.world.WorldView;

public class RenderSystem extends IteratingSystem {

    private ComponentMapper<TransformComponent> transformMapper;
    private ComponentMapper<TextureComponent> textureMapper;

    private Array<Entity> renderQueue;

    private World world;
	private WorldView worldView;
    private SpriteBatch batch;
    private TextureAtlas atlas;

    private Entity player;
    private FovComponent fovComponent;

    public RenderSystem(World world, SpriteBatch batch, WorldView worldView, Entity player) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get());
        this.world = world;
        this.worldView = worldView;
        this.batch = batch;
		this.player = player;
		fovComponent = player.getComponent(FovComponent.class);

		atlas = new TextureAtlas("tilesets/dungeon.atlas");

        transformMapper = ComponentMapper.getFor(TransformComponent.class);
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
				// Check if position is within bounds and visible by player
				if (!world.withinBounds(x, y)) continue;
				if (fovComponent.fovMap == null){
					throw new NullPointerException("FovComponent of player cannot be null in RenderSystem");
				}

				// If tile currently not visible
				if (fovComponent.fovMap[x][y] == 0){
					// If it is not discovered draw nothing and continue
					if (!world.getDiscovered()[x][y]) continue;
					// else set blue hue fog of war view
					batch.setColor(.15f, 0.2f, .3f,1);
				}
				else
				{
					// If it is visible set discovery to true
					world.getDiscovered()[x][y] = true;
					// and set color based on lightmap from fov
					batch.setColor((float) fovComponent.fovMap[x][y], (float) fovComponent.fovMap[x][y], (float) fovComponent.fovMap[x][y], 1);
				}

				// Draw based on type
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

		// Draw entities
		for (Entity entity : renderQueue){
			TransformComponent transformComponent = transformMapper.get(entity);
			// continue loop if entity position is not visible
			if (fovComponent.fovMap[transformComponent.tilePosition.x][transformComponent.tilePosition.y] == 0) continue;

			TextureComponent textureComponent = textureMapper.get(entity);

			// Set color based on light map at entity position
			batch.setColor(
					(float)fovComponent.fovMap[transformComponent.tilePosition.x][transformComponent.tilePosition.y],
					(float)fovComponent.fovMap[transformComponent.tilePosition.x][transformComponent.tilePosition.y],
					(float)fovComponent.fovMap[transformComponent.tilePosition.x][transformComponent.tilePosition.y],
					1);
			// Draw entity
			batch.draw(textureComponent.region, transformComponent.worldPosition.x, transformComponent.worldPosition.y, World.TILE_SIZE, World.TILE_SIZE);
		}

		batch.end();

    }

}
