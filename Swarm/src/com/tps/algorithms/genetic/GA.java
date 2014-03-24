package com.tps.algorithms.genetic;

/**
 * This class is derived from an online tutorial of an implementation
 * of a genetic algorithm for the Traveling Salesman problem
 * 
 * The source can be found here:
 * 
 * http://www.theprojectspot.com/tutorial-post/applying-a-genetic-algorithm-to-the-travelling-salesman-problem/5
 * @author fskallos
 *
 */
public class GA {

	/* GA Parameters  */
	private static final double mutationRate = 0.015;
	private static final int tournamentSize = 5;
	private static final boolean elitism = true;
	
	/**
	 * Evolves a population over one generation
	 * @param pop
	 * @return
	 */
	public static Population evolvePopulation(Population pop){
		Population newPopulation = new Population(pop.populationSize(), false);
		
		//Keep our best individual if elitism is enabled
		int elitismOffset=0;
		if(elitism){
			newPopulation.saveTour(0, pop.getFittest());
			elitismOffset = 1;
		}
		
		//Crossover population
		//Loop over the new population's size and create individuals from
		// the current population
		for(int i = elitismOffset; i < newPopulation.populationSize(); i++){
			//Select parents
			Tour parent1 = tournamentSelection(pop);
			Tour parent2 = tournamentSelection(pop);
			//Crossover Parents
			Tour child = crossover(parent1, parent2);
			newPopulation.saveTour(i, child);
		}
		
		//Mutate the new population a bit to add some new genetic material
		for(int i = elitismOffset; i< newPopulation.populationSize(); i++){
			mutate(newPopulation.getTour(i));
		}
		return newPopulation;
	}
	
	public static Tour crossover(Tour parent1, Tour parent2){
		//Create a new child tour;
		Tour child = new Tour();
		
		//Get start & end sub tour positions for parent1's tour
		int startPos = (int)(Math.random() * parent1.tourSize());
		int endPos = (int)(Math.random() * parent1.tourSize());
		
		// Loop and add the sub tour parent1 to our child
		for(int i = 0; i < child.tourSize(); i++){
			//If our start position is less than end
			if(startPos < endPos && i > startPos && i < endPos){
				child.setCity(i , parent1.getCity(i));
			} else if(startPos > endPos){
				if(!(i < startPos && i > endPos)){
					child.setCity(i, parent1.getCity(i));
				}
			}
		}
		
        // Loop through parent2's city tour
        for (int i = 0; i < parent2.tourSize(); i++) {
            // If child doesn't have the city add it
            if (!child.containsCity(parent2.getCity(i))) {
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child.tourSize(); ii++) {
                    // Spare position found, add city
                    if (child.getCity(ii) == null) {
                        child.setCity(ii, parent2.getCity(i));
                        break;
                    }
                }
            }
        }
        return child;
	}
	
	/**
	 * Mutate a tour using swap mutation
	 * @param tour
	 */
	private static void mutate(Tour tour){
		for(int tourPos1 = 0; tourPos1 < tour.tourSize(); tourPos1++){
			//Apply mutation rate
			if(Math.random() < mutationRate){
				//get a second random position in the tour
				int tourPos2 = (int)(Math.random() * tour.tourSize());
				//Get the cities at the target positions 
				City city1 = tour.getCity(tourPos1);
				City city2 = tour.getCity(tourPos2);
				
				//Swap them
				tour.setCity(tourPos2, city1);
				tour.setCity(tourPos1, city2);
			}
		}
	}
	
	/**
	 * Selects candidate tour for crossover
	 * @param pop
	 * @return
	 */
	private static Tour tournamentSelection(Population pop){
		//Create a tournament population
		Population tournament = new Population(tournamentSize, false);
		
		//For each place in the tournament, get a random candidate tour and 
		// add it
		for(int i = 0; i < tournamentSize; i++){
			int randomId = (int)(Math.random() * pop.populationSize());
			tournament.saveTour(i, pop.getTour(randomId));
		}
		
		//Get the fittest tour
		Tour fittest = tournament.getFittest();
		return fittest;
	}
}
