package com.mycompany.concurrencyassignment;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.Random;

/**
 *
 * @author andre
 */



public class EntryPoint extends Thread {
    
    Road road;
    String id;
    
    int currentCar;
    int carsRemaining;
    Clock timer;
    int carsCreated = 0;
    
    public int carsGoingToIndustrialCarPark = 0, carsGoingToUniversity = 0, carsGoingToShopping = 0, carsGoingToStation = 0;
    
    int simulationTime = 360000;
    
    public EntryPoint(String id, Road r, int carsToMake, Clock timer){
        this.id = id;
        road = r;
        currentCar = 0;
        carsRemaining = carsToMake;
        this.timer = timer;
        
        setName("Entry Point - " + id);
    }
    
    public void produce(){
        
    	
    	if (road.isFull())
    	{
    		
    	}
    	else
    	{
	        int startTime = timer.getTimePassed();
	        Vehicle newCar = new Vehicle(Integer.toString(currentCar), SetDestination(), startTime);
	        road.insert(newCar);
	        currentCar++;
	        carsCreated++;
    	}
    }
    
    
    public String SetDestination()
    {

    	Random r = new Random();
    	
    	int destinationNo = r.nextInt(101);
    	
    	// Allocates destination based on desired weighting
            if (destinationNo >= 0 && destinationNo <= 10) { carsGoingToUniversity++; return "University"; }
            else if (destinationNo >= 11 && destinationNo <= 30) { carsGoingToStation++; return "Station"; }
            else if (destinationNo >= 31 && destinationNo <= 60) { carsGoingToShopping++; return "ShoppingCentre"; }
            else if (destinationNo >= 61 && destinationNo <= 100){ carsGoingToIndustrialCarPark++; return "IndustrialCarPark"; }
		 
    	return "";

    }
 
    
    
    protected void finalize() 
    {
    	
    }
    
    public void run()
    {
    	boolean running = !timer.IsRushHourOver();
    	while (running)
    	{
	        int pauseTime = this.simulationTime / this.carsRemaining;

	        for (int i = carsRemaining; i >= 0; i--)
	        {
	            produce();
	            
	            try
	            {
	                sleep(pauseTime);
	            }
	            catch (InterruptedException ex)
	            {
	            }
	            
	            running = !timer.IsRushHourOver();
	            
	            if (!running)
	            {
	            	break;
	            }
	        }
    	}
    	
    	System.out.println("Entry Point "+id+" stopped producing");
    }
}
