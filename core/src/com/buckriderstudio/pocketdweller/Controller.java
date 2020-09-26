package com.buckriderstudio.pocketdweller;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;

/**
 * PocketDweller [2020]
 *
 * This is the controller class that catches all input and directs it
 *
 * By Menno Gouw
 */

public class Controller
{
	private Camera camera;

	private InputMultiplexer inputMultiplexer;
	public InputMultiplexer getInputMultiplexer()
	{
		return inputMultiplexer;
	}

	private Entity player;

	private GestureDetector.GestureAdapter gestureAdapter = new GestureDetector.GestureAdapter(){
		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY)
		{
			camera.translate(-deltaX, deltaY, 0);
			camera.update();
			return true;
		}
	};

	private InputAdapter inputAdapter = new InputAdapter(){
		@Override
		public boolean keyDown(int keycode) {
			keycode == Input.Keys.W{
				player.
			}
			return false;
		}
	};

	public Controller(Camera camera, Entity player)
	{
		this.camera = camera;
		this.player = player;
		inputMultiplexer =new InputMultiplexer(new GestureDetector(gestureAdapter));
	}
}
