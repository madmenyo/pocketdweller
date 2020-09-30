package com.buckriderstudio.pocketdweller.components;

import com.badlogic.ashley.core.Component;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class StatsComponent implements Component
{
	public int maxHealth;
	public int currentHealth;

	public int maxStamina;
	public int currentStamina;

	public int baseFocus;
	public int currentFocus;
}
