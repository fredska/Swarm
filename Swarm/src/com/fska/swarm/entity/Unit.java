package com.fska.swarm.entity;

import com.badlogic.gdx.math.Vector2;

public abstract class Unit extends Entity {
	protected enum Status {
		MOVE, WAIT, SLEEP, WORKING
		}
	
	protected enum Action {
		HARVEST, HAUL, BUILD
	}
	protected float maxHealth;
	protected float currentHealth;
	protected float speed;
	protected Status status;
	protected Action action;
	
	
	public Unit(Vector2 position, float maxHealth, float speed){
		super(position);
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.speed = speed;
	}
	
	protected void move(float delta, Vector2 destination){
		if(destination == null) return;
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
