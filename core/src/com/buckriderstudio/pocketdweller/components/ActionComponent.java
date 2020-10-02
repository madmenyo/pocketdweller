package com.buckriderstudio.pocketdweller.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.buckriderstudio.pocketdweller.actions.Action;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class ActionComponent implements Component, Pool.Poolable
{
	//public String action = "Some action";
	public Action action = null;

	@Override
	public void reset()
	{
		action = null;
	}
}
