package com.buckriderstudio.pocketdweller.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

import squidpony.squidmath.Coord;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class FovComponent implements Component, Pool.Poolable
{
	//If still on the same
	public Coord previous = Coord.get(-1, -1);
	public double[][] fovMap;

	@Override
	public void reset()
	{
		previous = Coord.get(-1, -1);
	}
}
