package com.buckriderstudio.pocketdweller.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.btree.BehaviorTree;

/**
 * PocketDweller [2020]
 * By Menno Gouw
 */

public class BehaviorComponent implements Component
{
    public BehaviorTree<Entity> behaviorTree;
}
