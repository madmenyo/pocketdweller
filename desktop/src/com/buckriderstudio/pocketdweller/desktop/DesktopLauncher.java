package com.buckriderstudio.pocketdweller.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.buckriderstudio.pocketdweller.PocketDweller;

public class DesktopLauncher {
	public static void main (String[] arg) {
		//packImages();

		PackCreatures();
		PackGui();
		PackObjects();
		PackTiles();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.forceExit = false;
		new LwjglApplication(new PocketDweller(), config);
	}

	private static void packImages() {
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.fast = true;
		settings.combineSubdirectories = true;
		settings.flattenPaths = true;

		String input = "../../images/";
		String output = "tilesets";
		String filename = "dungeon.atlas";

		TexturePacker.process(settings, input, output, filename);
	}

	private static void PackTiles(){
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.fast = true;
		settings.combineSubdirectories = true;
		settings.flattenPaths = true;

		String input = "../../images/tiles";
		String output = "sheets";
		String filename = "tilesheet.atlas";

		TexturePacker.process(settings, input, output, filename);
	}

	private static void PackCreatures(){
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.fast = true;
		settings.combineSubdirectories = true;
		settings.flattenPaths = true;

		String input = "../../images/creatures";
		String output = "sheets";
		String filename = "creatures.atlas";

		TexturePacker.process(settings, input, output, filename);
	}
	private static void PackObjects(){
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.fast = true;
		settings.combineSubdirectories = true;
		settings.flattenPaths = true;

		String input = "../../images/objects";
		String output = "sheets";
		String filename = "objects.atlas";

		TexturePacker.process(settings, input, output, filename);
	}

	private static void PackGui(){
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.fast = true;
		settings.combineSubdirectories = true;
		settings.flattenPaths = true;

		String input = "../../images/gui";
		String output = "gui";
		String filename = "gui.atlas";

		TexturePacker.process(settings, input, output, filename);
	}
}
