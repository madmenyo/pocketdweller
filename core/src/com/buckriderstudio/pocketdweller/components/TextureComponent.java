package com.buckriderstudio.pocketdweller.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

/**
 * Just holds a texture region to draw
 */
public class TextureComponent implements Component, Pool.Poolable
{
    public TextureRegion region;

	@Override
	public void reset()
	{
		region = null;
	}
}
