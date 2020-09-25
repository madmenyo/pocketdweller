package com.buckriderstudio.pocketdweller.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class WorldView
{
	private Viewport viewport;

	public Viewport getViewport()
	{
		return viewport;
	}

	private Vector3 center;

	public WorldView()
	{
		viewport = new ScreenViewport();
	}

	public float topWorld(){
		return viewport.getCamera().position.y + viewport.getWorldHeight() / 2;
	}

	public float bottomWorld(){
		return viewport.getCamera().position.y - viewport.getWorldHeight() / 2;
	}

	public float leftWorld(){
		return viewport.getCamera().position.x - viewport.getWorldWidth() / 2;
	}

	public float rightWorld(){
		return viewport.getCamera().position.x + viewport.getWorldWidth() / 2;
	}
}
