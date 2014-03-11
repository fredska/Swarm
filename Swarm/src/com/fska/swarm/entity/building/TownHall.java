package com.fska.swarm.entity.building;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.entity.Building;

public class TownHall extends Building {

	private TextureRegion buildingTexture;
	
	public TownHall(Vector2 position) {
		super(position);
		buildingTexture = new TextureRegion(
				new Texture(Gdx.files.internal("units/TownHall_1.png")));
	}
	

	@Override
	public void draw(Batch batch) {
		batch.draw(buildingTexture, super.getPosition().x, super.getPosition().y);
	}

	@Override
	public void update() {
		
	}

}
