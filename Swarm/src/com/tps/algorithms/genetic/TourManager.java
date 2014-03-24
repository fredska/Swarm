package com.tps.algorithms.genetic;

import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.DSTORE;

/**
 * Holds the cities of a tour
 * @author fskallos
 *
 */
public class TourManager {
	
	private static ArrayList<City> destinationCities = new ArrayList<City>();
	
	public static void addCity(City city){
		destinationCities.add(city);
	}
	
	public static City getCity(int index){
		return destinationCities.get(index);
	}
	
	public static int numberOfCities(){
		return destinationCities.size();
	}
}
