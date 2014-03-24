package com.tps.algorithms.genetic;

import java.util.ArrayList;

/**
 * Stores a candidate tour
 * @author fskallos
 *
 */
public class Tour {
	//Holds our tours of the cities
	private ArrayList<City> tour = new ArrayList<City>();
	
	//Cache
	private double fitness = 0;
	private int distance = 0;
	
	//Constructs a blank tour
	public Tour(){
		for(int i = 0; i < TourManager.numberOfCities(); i++){
			tour.add(null);
		}
	}
	
	public Tour(ArrayList<City> tour){
		this.tour = tour;
	}
	
	public City getCity(int tourPosition){
		return tour.get(tourPosition);
	}
	
	//Creates a random individual
	public void generateIndividual(){
		for(int cityIndex = 0; cityIndex < TourManager.numberOfCities(); cityIndex++){
			setCity(cityIndex, TourManager.getCity(cityIndex));
		}
	}
	
	public void setCity(int tourPosition, City city){
		tour.set(tourPosition, city);
		//If the tours have been altered, we need to reset the fitness & distance
		this.fitness = 0;
		this.distance = 0;
	}
	
	public double getFitness(){
		if(fitness == 0){
			fitness = 1/(double)getDistance();
		}
		return fitness;
	}
	
	public int getDistance(){
		if(distance==0){
			int tourDistance = 0;
			//Loop through our tour's cities
			for(int cityIndex = 0; cityIndex < tourSize(); cityIndex++){
				//Get the City we're traveling from 
				City fromCity = getCity(cityIndex);
				//City we're going to
				City destinationCity = null;
				//Check we're not on our tour's last city;
				//If we are, set our tour's final destination city to
				// our starting city
				if(cityIndex+1 < tourSize()){
					destinationCity = getCity(cityIndex+1);
				} else {
					destinationCity = getCity(0);
				}
				
				tourDistance += fromCity.distanceTo(destinationCity);
			}
			distance = tourDistance;
		}
		return distance;
	}
	
	public int tourSize(){
		return this.tour.size();
	}
	
	//Check if the tour contains a city
	public boolean containsCity(City city){
		return tour.contains(city);
	}
	
	@Override
	public String toString(){
		String geneString = "|";
		for(int i = 0; i < tourSize(); i++){
			geneString += getCity(i) + "|";
		}
		return geneString;
	}
}
