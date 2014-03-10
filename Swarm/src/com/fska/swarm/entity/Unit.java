package com.fska.swarm.entity;

import com.badlogic.gdx.math.Vector2;

public abstract class Unit extends Entity {

	protected float maxHealth;
	protected float currentHealth;
	protected float speed;
	
	public Unit(Vector2 position, float maxHealth, float speed){
		super(position);
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.speed = speed;
	}
	
	protected void move(float delta, Vector2 destination){
		Vector2 currentPosition = super.getPosition();
		float movementSpeed = speed * delta;
		if(currentPosition.x < destination.x){
			currentPosition.x += movementSpeed;
		} else
			currentPosition.x -= movementSpeed;
			
		if(currentPosition.y < destination.y){
			currentPosition.y += movementSpeed;
		} else
			currentPosition.y -= movementSpeed;
		
		super.setPosition(currentPosition);
	}
}
