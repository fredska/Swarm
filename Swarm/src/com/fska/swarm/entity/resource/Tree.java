package com.fska.swarm.entity.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.entity.Resource;

public class Tree extends Resource {

	private TextureRegion treeTexture;
	public Tree(Vector2 position) {
		super(position, 1, 4f,Resource.ResourceType.Wood);
		treeTexture = new TextureRegion(new Texture(Gdx.files.internal("units/Tree_1.png")));
		
	}

	@Override
	public void draw(Batch batch) {
		batch.draw(treeTexture, getPosition().x, getPosition().y);
	}

	@Override
	public void update() {
		
	}

}
