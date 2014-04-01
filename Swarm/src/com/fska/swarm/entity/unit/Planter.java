package com.fska.swarm.entity.unit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.fska.swarm.entity.Unit;

public class Planter extends Unit {

	private Texture texture;
	private TextureRegion textureRegion;
	
	public Planter(Vector2 position){
		this(position, 100, 45);
	}
	
	public Planter(Vector2 position, float maxHealth, float speed) {
		super(position, maxHealth, speed);
		texture = new Texture(Gdx.files.internal("units/Gatherer_1.png"));
		TextureRegion[][] frames = TextureRegion.split(texture, 32, 32);
		textureRegion = frames[0][0];
		walkAnimation = new Animation(0.1f, frames[0][0], frames[0][1],
				frames[0][2]);
		walkAnimation.setPlayMode(Animation.LOOP_PINGPONG);
		status = Status.WAIT;
	}

	@Override
	public void draw(Batch batch) {
		batch.draw(textureRegion, getPosition().x, getPosition().y);
	}

	@Override
	public void update() {
	}

}
