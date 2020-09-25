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

	/**
	 * The position in world coordinates, a higher Z means it overlaps other entities.
	 */
	public Vector3 worldPosition = new Vector3();
    public Vector2 Scale = new Vector2();
    public float rotation = 0;
}
