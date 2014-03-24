package com.tps.algorithms.genetic;

public class City {
	private int x, y;
	
	public City(){
		this.x = (int)(Math.random()*500);
		this.y = (int)(Math.random()*500);
	}
	
	public City(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public double distanceTo(City city){
		int xDistance = getX() - city.getX();
		int yDistance = getY() - city.getY();
		return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
	}
	
	@Override
	public String toString(){
		return getX() + "," + getY();
	}
}
