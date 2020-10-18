package com.buckriderstudio.pocketdweller;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.utils.StreamUtils;
import com.buckriderstudio.pocketdweller.components.InfoComponent;
import com.buckriderstudio.pocketdweller.utility.Assets;
import com.buckriderstudio.pocketdweller.utility.GameTime;
import com.buckriderstudio.pocketdweller.world.WorldScreen;
import com.kotcrab.vis.ui.VisUI;

import java.io.Reader;

import squidpony.FakeLanguageGen;
import squidpony.WeightedLetterNamegen;

public class PocketDweller extends Game {

	
	@Override
	public void create () {

		//behaviorTest();
		//stringGenTest();


		Assets assets = new Assets();
		assets.loadAll();
		assets.getAssetManager().finishLoading();

		GameTime gameTime = new GameTime();
		gameTime.addSeconds(3600);
		System.out.println(gameTime.toString());
		gameTime.addMilli(237248327);
		System.out.println(gameTime.toString());



		VisUI.load(assets.getAssetManager().get(Assets.SKIN_NEUTRALIZER));

		setScreen(new WorldScreen(assets.getAssetManager()));


	}

	private void stringGenTest()
	{
		WeightedLetterNamegen nameGen = new WeightedLetterNamegen(WeightedLetterNamegen.COMMON_USA_LAST_NAMES);
		for (int i = 0; i < 10; i++){
			System.out.println(nameGen.generate());
		}

		FakeLanguageGen language = new FakeLanguageGen();
		System.out.println(language.sentence(40, 50));
	}

	private void behaviorTest()
	{
		Entity entity = new Entity();
		InfoComponent infoComponent = new InfoComponent();
		infoComponent.name = "player";
		entity.add(infoComponent);

		BehaviorTree<Entity> tree = null;

		Reader reader = null;
		try {
			reader = Gdx.files.internal("behavior/test_behavior.tree").reader();
			BehaviorTreeParser<Entity> parser = new BehaviorTreeParser<Entity>(BehaviorTreeParser.DEBUG_HIGH);
			tree = parser.parse(reader, entity);
			System.out.println("Finished parsing tree...");
		} finally {
			StreamUtils.closeQuietly(reader);
		}

		tree.step();
	}
}
