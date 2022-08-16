package com.mycompany.concurrencyassignment;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andre
 */

//enums to store location of car park
enum Location
{
    Industrial, ShoppingCentre, Station, University
}

public final class CarPark extends Thread
{
    Vehicle[] parkedCars;
    Road entryRoad;
    Clock clock;
    Location ID;
    
    int Limit;
    int numSpacesOccupied;
    int totalJourneyTime;
    int nextIn;
    boolean running;
    
    public CarPark (int limit, Road road, int locationID, Clock clock)
    {
    	parkedCars = new Vehicle[limit];
    	nextIn = 0;
        entryRoad = road;
        SetDestination(locationID);
        Limit = limit;
        this.clock = clock;
        this.running = true;   
        this.totalJourneyTime = 0;
        setName("Car Park - " + locationID);
    }
    
    //Method to set the destination ID of the car park based on the integer parameter passed in
    public void SetDestination(int dest)
    {
        switch (dest)
        {
            case 0:
                this.ID = Location.Industrial;
                break;
            case 1:
                this.ID = Location.ShoppingCentre;
                break;
            case 2:
            	this.ID = Location.Station;
                break;
            case 3:
                this.ID = Location.University;
                break;
        }
    }
    

    public void EnterCarPark()
    {
    	if (!entryRoad.isEmpty()) //Ensure that entry buffer is not empty before attempting to take vehicle
    	{
    		if (this.numSpacesOccupied < Limit) // Ensure that the car park has eneough space for the vehicle
	    	{
		        Vehicle nextCar = entryRoad.extract(); // Extract car from entry buffer
		        this.numSpacesOccupied++; // Increase number of occupied spaces by one to account for the new vehicle
		        int finishTime = clock.getTimePassed(); // Finish time is set to the time currently written by the clock object
		        nextCar.SetFinishTime(finishTime);
		        int journeyTime = finishTime - nextCar.GetStartTime(); // Calculation which gets the current cars total journey time from entry point to car park
		        
		        parkedCars[nextIn] = nextCar;
		        nextIn++; 
		        
		        totalJourneyTime += journeyTime;
		        try { sleep(1200); } catch (InterruptedException ex) { }
	    	}
    	}
       
    }
    
    public void PrintNumSpacesAvailable()
    {
        System.out.println("CARPARK " + ID + ": " + (this.Limit - this.numSpacesOccupied) + " Spaces"); 
       
    }
    
    public void PrintNumParkedJourneyTime()
    {
    	
    	int averageTime = totalJourneyTime / this.numSpacesOccupied;
        System.out.println("CARPARK " + ID + ": " + (this.numSpacesOccupied) + " parked, average journey time "+ averageTime/60 +"m" + averageTime %60+"s");

    }
    
    @Override
    protected void finalize() 
    {
    	this.PrintNumParkedJourneyTime();
    }
    
    
    @Override
    public void run()
    {
    	running = !clock.IsRushHourOver(); 
        
        while (running) // While simulaton is not over
        {
            EnterCarPark();
            running = !clock.IsRushHourOver(); // Ends when clock informs thread that simulation has ended
        }  
        
        try 
        { 
            sleep(1200); 
        } 
        catch (InterruptedException ex) 
        { }
        
        this.PrintNumParkedJourneyTime(); // Print out stats
    }
}

