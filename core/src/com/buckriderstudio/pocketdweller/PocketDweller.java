package com.buckriderstudio.pocketdweller;

import com.badlogic.gdx.Game;
import com.buckriderstudio.pocketdweller.utility.Assets;
import com.buckriderstudio.pocketdweller.world.WorldScreen;
import com.kotcrab.vis.ui.VisUI;

public class PocketDweller extends Game {

	
	@Override
	public void create () {

		Assets assets = new Assets();
		assets.loadAll();
		assets.getAssetManager().finishLoading();

		VisUI.load(assets.getAssetManager().get(Assets.SKIN_NEUTRALIZER));

		setScreen(new WorldScreen(assets.getAssetManager()));
	}
}
