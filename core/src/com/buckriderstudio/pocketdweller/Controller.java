package com.buckriderstudio.pocketdweller;

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

	private GestureDetector.GestureAdapter gestureAdapter = new GestureDetector.GestureAdapter(){
		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY)
		{
			camera.translate(-deltaX, deltaY, 0);
			camera.update();
			return true;
		}
	};

	public Controller(Camera camera)
	{
		this.camera = camera;
		inputMultiplexer =new InputMultiplexer(new GestureDetector(gestureAdapter));
	}
}
