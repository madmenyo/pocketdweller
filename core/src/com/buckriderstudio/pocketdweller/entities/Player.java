package com.buckriderstudio.pocketdweller.entities;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.buckriderstudio.pocketdweller.components.PlayerComponent;
import com.buckriderstudio.pocketdweller.components.TextureComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.world.World;

import squidpony.squidmath.Coord;

public class Player extends Entity {
    public Player(Coord tilePosition, PooledEngine engine) {
        TransformComponent transformComponent = engine.createComponent(TransformComponent.class);
        transformComponent.tilePosition = tilePosition;
        transformComponent.worldPosition.set(tilePosition.x * World.TILE_SIZE, tilePosition.y * World.TILE_SIZE, 10);
        add(transformComponent);
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        add(new PlayerComponent());
    }
}
