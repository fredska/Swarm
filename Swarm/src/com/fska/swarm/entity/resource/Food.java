package com.fska.swarm.entity.resource;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.entity.Resource;

public class Food extends Resource {

	public Food(Vector2 position) {
		super(position, 1, 2);
	}

	@Override
	public void draw(Batch batch) {
	}

	@Override
	public void update() {
	}

}
