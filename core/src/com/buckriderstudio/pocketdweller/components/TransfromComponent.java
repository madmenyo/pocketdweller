package com.buckriderstudio.pocketdweller.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import squidpony.squidmath.Coord;

/**
 * This component represents the position on in the world.
 * Without this component the entity does not exists directly in the world but perhaps in inventory.
 */
public class TransfromComponent implements Component {

    public Coord tilePosition = Coord.get(0, 0);

    public Vector2 worldPosition = new Vector2();
    public Vector2 Scale = new Vector2();
    public float rotation = 0;
}
