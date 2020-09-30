package com.buckriderstudio.pocketdweller.utility;

import com.badlogic.ashley.core.ComponentMapper;
import com.buckriderstudio.pocketdweller.components.ActionComponent;
import com.buckriderstudio.pocketdweller.components.BehaviorComponent;
import com.buckriderstudio.pocketdweller.components.FovComponent;
import com.buckriderstudio.pocketdweller.components.InfoComponent;
import com.buckriderstudio.pocketdweller.components.TimeUnitComponent;
import com.buckriderstudio.pocketdweller.components.TransformComponent;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class Mappers
{
	public static final ComponentMapper<TransformComponent> Transform = ComponentMapper.getFor(TransformComponent.class);
	public static final ComponentMapper<FovComponent> Fov = ComponentMapper.getFor(FovComponent.class);
	public static final ComponentMapper<TimeUnitComponent> Time = ComponentMapper.getFor(TimeUnitComponent.class);
	public static final ComponentMapper<BehaviorComponent> Behavior = ComponentMapper.getFor(BehaviorComponent.class);
	public static final ComponentMapper<ActionComponent> Action = ComponentMapper.getFor(ActionComponent.class);
	public static final ComponentMapper<InfoComponent> Info = ComponentMapper.getFor(InfoComponent.class);
}
