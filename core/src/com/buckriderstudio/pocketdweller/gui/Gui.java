package com.buckriderstudio.pocketdweller.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.buckriderstudio.pocketdweller.systems.TimeSystem;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;

import java.time.format.DateTimeFormatter;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class Gui extends Stage
{
	private TimeSystem timeSystem;
	private AssetManager assetManager;

	private VisTable mainTable;
	private VisTable rightTable;
	private VisTable topTable;

	private VisLabel date;
	private VisLabel time;

	private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm:ss");
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");

	public Gui(TimeSystem timeSystem, AssetManager assetManager)
	{
		this.timeSystem = timeSystem;
		this.assetManager = assetManager;


		setViewport(new ScreenViewport());

		setupGui();

	}

	private void setupGui()
	{
		mainTable = new VisTable();
		mainTable.setFillParent(true);
		mainTable.right();
		mainTable.top();

		topTable = new VisTable();
		topTable.setTouchable(Touchable.enabled);
		topTable.left();
		topTable.setBackground("background");
		mainTable.add(topTable).growX().top();

		rightTable = new VisTable();
		rightTable.setTouchable(Touchable.enabled);
		rightTable.left();
		rightTable.setBackground("background");
		mainTable.add(rightTable).width(200).growY();

		topTable.add(createTimeTable());

		addActor(mainTable);

	}

	private VisTable createTimeTable()
	{
		VisTable timeTable = new VisTable();
		//timeTable.debugAll();
		timeTable.left();
		//timeTable.add("Date: ");
		timeTable.add(new Image(VisUI.getSkin(), "calendar")).size(24);
		date = new VisLabel("");
		timeTable.add(date).left();
		timeTable.row();
		//timeTable.add("Time: ");
		timeTable.add(new Image(VisUI.getSkin(), "stopwatch")).size(24);
		time = new VisLabel("");
		timeTable.add(time).left();
		return timeTable;
	}

	@Override
	public void act(float delta)
	{
		super.act(delta);
		time.setText(TimeSystem.CURRENT_TIME.format(timeFormatter));
		date.setText(TimeSystem.CURRENT_TIME.format(dateFormatter));
	}

	public void resize(int width, int height, boolean center){
		getViewport().update(width, height, center);
	}


}
