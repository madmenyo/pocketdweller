package com.buckriderstudio.pocketdweller.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.buckriderstudio.pocketdweller.systems.TimeSystem;

import java.time.ZonedDateTime;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class TimeUnitComponent implements Component, Pool.Poolable
{
	public ZonedDateTime actingTime = TimeSystem.CURRENT_TIME;

	@Override
	public void reset()
	{
		actingTime = TimeSystem.CURRENT_TIME;
	}
}
