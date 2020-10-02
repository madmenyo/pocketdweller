package com.buckriderstudio.pocketdweller.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.buckriderstudio.pocketdweller.systems.TimeSystem;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

class Gui extends Stage
{
	private TimeSystem timeSystem;

	//

	public Gui(TimeSystem timeSystem, AssetManager assetManager)
	{
		this.timeSystem = timeSystem;
		setViewport(new ScreenViewport());
	}

}
