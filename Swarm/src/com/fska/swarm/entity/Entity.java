package com.fska.swarm.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.fska.swarm.common.Common;

public abstract class Entity implements Serializable {
	private Vector2 position;
	private float width, height;

	public Entity() {
		this.position = new Vector2();
		this.width = Common.TILE_WIDTH;
		this.height = Common.TILE_HEIGHT;
	}

	public Entity(Vector2 position) {
		this.position = position;
	}
	
	public Entity(Vector2 position, int tileWidth, int tileHeight){
		this.position = position;
		this.width = tileWidth;
		this.height = tileHeight;
	}
	
	public Vector2 getCenter(){
		return new Vector2(
				this.position.x + (width / 2),
				this.position.y + (height / 2));
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Vector2 getPosition() {
		return this.position;
	}

	protected void setPosition(Vector2 position) {
		this.position = position;
	}

	public abstract void draw(Batch batch);

	public abstract void update();

	// TODO: Setup for serial data streams
	@Override
	public void write(Json json) {
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
	}

}
