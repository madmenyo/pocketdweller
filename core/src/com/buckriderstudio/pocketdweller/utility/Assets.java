package com.buckriderstudio.pocketdweller.utility;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class Assets
{
	private final AssetManager assetManager = new AssetManager();

	public static AssetDescriptor<TextureAtlas> TILES = new AssetDescriptor<TextureAtlas>("sheets/tilesheet.atlas", TextureAtlas.class);
	public static AssetDescriptor<TextureAtlas> CREATURES = new AssetDescriptor<TextureAtlas>("sheets/creatures.atlas", TextureAtlas.class);
	public static AssetDescriptor<TextureAtlas> OBJECTS = new AssetDescriptor<TextureAtlas>("sheets/objects.atlas", TextureAtlas.class);
	public static AssetDescriptor<TextureAtlas> GUI_ELEMENTS = new AssetDescriptor<TextureAtlas>("sheets/gui.atlas", TextureAtlas.class);

	public void LoadAll(){
		assetManager.load(TILES);
		assetManager.load(CREATURES);
		assetManager.load(OBJECTS);
		assetManager.load(GUI_ELEMENTS);
	}

}
