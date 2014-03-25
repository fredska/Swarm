package com.fska.swarm.entity.resource;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.entity.Resource;

public class Water extends Resource {

	public Water(Vector2 position) {
		super(position, 3, 1f, Resource.ResourceType.Water);
	}

	@Override
	public void draw(Batch batch) {
	}

	@Override
	public void update() {
	}

}
