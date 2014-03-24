package com.tps.algorithms.genetic;

public class Population {
	//Holds population of tours
	Tour[] tours;
	
	public Population(int populationSize, boolean initialize){
		tours = new Tour[populationSize];
		//If we need to initialize a population of tours, DO IT!
		if(initialize){
			for(int i = 0; i < populationSize(); i++){
				Tour newTour = new Tour();
				newTour.generateIndividual();
				saveTour(i, newTour);
			}
		}
	}
	
	public Tour getTour(int tourPosition){
		return tours[tourPosition];
	}
	
	public void saveTour(int tourPosition, Tour tour){
		this.tours[tourPosition] = tour;
	}
	
	//Get the best tour in the population
	public Tour getFittest(){
		Tour fittest = getTour(0);
		//Loop through individuals to find fittest
		for(int i = 1; i < populationSize(); i++){
			if(fittest.getFitness() <= getTour(i).getFitness()){
				fittest = getTour(i);
			}
		}
		return fittest;
	}
	
	public int populationSize(){
		return tours.length;
	}
}
