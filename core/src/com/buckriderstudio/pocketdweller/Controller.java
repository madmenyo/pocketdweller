package com.buckriderstudio.pocketdweller;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.buckriderstudio.pocketdweller.actions.MoveAction;
import com.buckriderstudio.pocketdweller.components.ActionComponent;
import com.buckriderstudio.pocketdweller.components.PlayerComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;
import com.buckriderstudio.pocketdweller.world.World;

import squidpony.squidmath.Coord;

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
	private TransformComponent transfromComponent;
	private PlayerComponent playerComponent;

	private World world;

	public Controller(Camera camera, Entity player, World world)
	{
		this.camera = camera;
		this.player = player;
		this.world = world;
		inputMultiplexer =new InputMultiplexer(new GestureDetector(gestureAdapter), inputAdapter);
		transfromComponent = player.getComponent(TransformComponent.class);
		playerComponent = player.getComponent(PlayerComponent.class);
	}


	private GestureDetector.GestureAdapter gestureAdapter = new GestureDetector.GestureAdapter(){
		@Override
		public boolean tap(float x, float y, int count, int button)
		{
			Vector3 v3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(v3);

			System.out.println("Tile: " + (int)(v3.x / World.TILE_SIZE) + ", " + (int)(v3.y / World.TILE_SIZE));
			return false;
		}

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
			switch (keycode){
				case Input.Keys.W:
					move(Coord.get(0, 1));
					break;
				case Input.Keys.S:
					move(Coord.get(0, -1));
					break;
				case Input.Keys.A:
					move(Coord.get(-1, 0));
					break;
				case Input.Keys.D:
					move(Coord.get(1, 0));
					break;
				default:
					break;
			}
			return false;
		}
	};

	private void move(Coord translation){
		if (!playerComponent.playerTurn) return;
		Coord destination = transfromComponent.tilePosition.add(translation);
		if (world.blocksMovement(destination.x, destination.y)) return;

		transfromComponent.tilePosition = destination;

		ActionComponent actionComponent = new ActionComponent();
		actionComponent.action = new MoveAction(100, destination);
		actionComponent.timeInMiliSeconds = 100;
		player.add(actionComponent);
	}
}
