package com.buckriderstudio.pocketdweller.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * PocketDweller [2020]
 *
 * A helper class for some viewport calculations.
 *
 * By Menno Gouw
 */

public class WorldView
{
	private Viewport viewport;

	public Viewport getViewport()
	{
		return viewport;
	}

	public WorldView()
	{
		viewport = new ScreenViewport();
	}

	/**
	 * @return world position at the top of the viewport
	 */
	public float topWorld(){
		return viewport.getCamera().position.y + viewport.getWorldHeight() / 2;
	}

	/**
	 * @return world position at the bottom of the viewport
	 */
	public float bottomWorld(){
		return viewport.getCamera().position.y - viewport.getWorldHeight() / 2;
	}

	/**
	 * @return world position at the left of the viewport
	 */
	public float leftWorld(){
		return viewport.getCamera().position.x - viewport.getWorldWidth() / 2;
	}

	/**
	 * @return world position at the right of the viewport
	 */
	public float rightWorld(){
		return viewport.getCamera().position.x + viewport.getWorldWidth() / 2;
	}
}
