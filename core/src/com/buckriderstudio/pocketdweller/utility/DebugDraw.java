package com.buckriderstudio.pocketdweller.utility;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.buckriderstudio.pocketdweller.world.World;
import com.buckriderstudio.pocketdweller.world.WorldView;

public class DebugDraw {
    private WorldView worldView;
    private SpriteBatch spriteBatch = new SpriteBatch();

    private Texture texture;

    private Color color = Color.RED;

    public DebugDraw(WorldView worldView) {
        this.worldView = worldView;
        texture = new Texture("white.png");

        color.a = .5f;
    }

    public void drawMap(double[][] map){

        int startX = (int)(worldView.leftWorld() / World.TILE_SIZE);
        int endX = (int)(worldView.rightWorld() / World.TILE_SIZE);
        int startY = (int)(worldView.topWorld() / World.TILE_SIZE);
        int endY = (int)(worldView.bottomWorld() / World.TILE_SIZE);

        spriteBatch.setProjectionMatrix(worldView.getViewport().getCamera().combined);

        spriteBatch.begin();
        spriteBatch.setColor(color);
        for (int y = startY; y >= endY; y--) {
            for (int x = startX; x <= endX; x++) {
                if (x < 0 || x >= map[0].length || y < 0 || y > map.length)
                spriteBatch.draw(texture, x * World.TILE_SIZE, y * World.TILE_SIZE, World.TILE_SIZE, World.TILE_SIZE);
            }
        }
        spriteBatch.end();
    }
}
