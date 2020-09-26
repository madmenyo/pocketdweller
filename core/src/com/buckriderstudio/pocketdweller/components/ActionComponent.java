package com.buckriderstudio.pocketdweller.components;

import com.badlogic.ashley.core.Component;
import com.buckriderstudio.pocketdweller.actions.Action;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class ActionComponent implements Component
{
	//public String action = "Some action";
	public Action action;
	public int timeInMiliSeconds;
}
