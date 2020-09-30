package com.buckriderstudio.pocketdweller.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

/**
 * PocketDweller [2020]
 * Moves a entity from V2 to V2 over time with interpolation
 * By Menno Gouw
 */

public class MoveComponent implements Component, Pool.Poolable
{
	public Vector2 from = new Vector2();
	public Vector2 too = new Vector2();
	public float animationTime = .9f;
	public float currentTime = 0;
	public Interpolation interpolation = Interpolation.exp5;

	@Override
	public void reset()
	{
		animationTime = .2f;
		currentTime = 0;
		interpolation = Interpolation.exp5;
		from = new Vector2();
		too = new Vector2();
	}
}
