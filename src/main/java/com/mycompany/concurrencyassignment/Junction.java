package com.mycompany.concurrencyassignment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Vector;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andre
 */

public class Junction extends Thread
{
    Road ExitRoad;
    Road ExitRoad2;
    Road EntryRoad;
    Clock Clock;
    
    char id;
    int currentTime;
    boolean lightsRed;
    int carsThroughJunction;
    int currentEntryRoad;
    int lightConfiguration;
    
    String fileName;
    
    Vector<Road> entryRoads;
    Vector<Road> exitRoads;
    
    public Junction (char ID, Clock c, Vector<Road> entry, Vector<Road> exit, int lightTime)
    {
    	this.id = ID;
    	this.Clock = c;
    	this.currentTime = 0;
    	this.carsThroughJunction = 0;
    	this.lightsRed = false;
    	this.currentEntryRoad = 0;
    	this.lightConfiguration = lightTime;
  
    	entryRoads = entry;
    	exitRoads = exit;
    	
    	setName("Junction - " + id);
        
        
    	fileName = "Junction " + id + ".txt";
    		
    }

    
    public void AddEntryRoad(Road r)
    {
    	entryRoads.add(r);
    }

    public char GetJunctionID()
    {
        return this.id;
    }
    
    public void CrossJunction()
    {
    	// Resets the entry road to the first one in the vector, acts as a circular queue 
    	if (currentEntryRoad == this.entryRoads.size())
    	{
    		this.currentEntryRoad = 0;
    	}
    	
    	// 
    	if (this.entryRoads.elementAt(currentEntryRoad).isEmpty() == false)
    	{
    		// Check where car is going by checking destination
	        String nextCarDest = this.entryRoads.elementAt(currentEntryRoad).GetVehicleDestination();


	        if (isExitFull(nextCarDest) == false) // Check if exit buffer is full before extracting
	        {
	        	Junction.this.CrossJunction(this.entryRoads.elementAt(currentEntryRoad).extract()); // Extract car from entry buffer 
	        	carsThroughJunction++;
	        	
	        	try
		        {
		            sleep(501);
		        }
		        catch (InterruptedException ex)
		        {
		        }
	        }	             
    	}  

    }
       
    void CrossJunction(Vehicle vehicle)
    {	
    	switch (this.id)
    	{
    	
    		case 'A':
    			
    			if ("IndustrialCarPark".equals(vehicle.GetDestination()))
    			{
    				this.exitRoads.elementAt(0).insert(vehicle);
    				break;
    				
    			}
    			else
    				this.exitRoads.elementAt(1).insert(vehicle);
    				break;

    		case 'B':
    			if ("IndustrialCarPark".equals(vehicle.GetDestination()))
    			{
    				this.exitRoads.elementAt(0).insert(vehicle);
    				break;
    			}
    			if ("ShoppingCentre".equals(vehicle.GetDestination()) || "Station".equals(vehicle.GetDestination()) || "University".equals(vehicle.GetDestination()))
    			{
    				this.exitRoads.elementAt(1).insert(vehicle);
    				break;
    				
    			}
    		case 'C':
    			if ("IndustrialCarPark".equals(vehicle.GetDestination()))
    			{
    				this.exitRoads.elementAt(0).insert(vehicle);
    				break;
    			}
    			if ("ShoppingCentre".equals(vehicle.GetDestination()))
    			{
    				this.exitRoads.elementAt(1).insert(vehicle);
    				break;
    				
    			}
    			if("Station".equals(vehicle.GetDestination()) || "University".equals(vehicle.GetDestination()))
    			{
    				this.exitRoads.elementAt(2).insert(vehicle);
    				break;
    			}
    		case 'D':
    			if ("Station".equals(vehicle.GetDestination()))
    			{
    				this.exitRoads.elementAt(0).insert(vehicle);
    				break;
    			}
    			if ("University".equals(vehicle.GetDestination()))
    			{
    				this.exitRoads.elementAt(1).insert(vehicle);
    				break;
    				
    			}
    			break;
    	}
    }   
    
    
    boolean isExitFull(String destination)
    {
    	boolean isExitFull = false;
    	switch (this.id)
    	{
    	
    		case 'A':
    			
    			if ("IndustrialCarPark".equals(destination))
    			{
    				isExitFull = this.exitRoads.elementAt(0).isFull();
    				break;
    				
    			}
    			else
    				isExitFull = this.exitRoads.elementAt(1).isFull();
    				break;

    		case 'B':
    			if ("IndustrialCarPark".equals(destination))
    			{
    				isExitFull = this.exitRoads.elementAt(0).isFull();
    				break;
    			}
    			if ("ShoppingCentre".equals(destination) || "Station".equals(destination) || "University".equals(destination))
    			{
    				isExitFull = this.exitRoads.elementAt(1).isFull();
    				break;
    				
    			}
    		case 'C':
    			if ("IndustrialCarPark".equals(destination))
    			{
    				isExitFull = this.exitRoads.elementAt(0).isFull();
    				break;
    			}
    			if ("ShoppingCentre".equals(destination))
    			{
    				isExitFull = this.exitRoads.elementAt(1).isFull();
    				break;
    				
    			}
    			if("Station".equals(destination) || "University".equals(destination))
    			{
    				isExitFull = this.exitRoads.elementAt(2).isFull();
    				break;
    			}
    		case 'D':
    			if ("Station".equals(destination))
    			{
    				isExitFull = this.exitRoads.elementAt(0).isFull();
    				break;
    			}
    			if ("University".equals(destination))
    			{
    				isExitFull = this.exitRoads.elementAt(1).isFull();
    				break;
    				
    			}
    	}
    	
    	return isExitFull;
    } 
    
    public int GetLightConfiguration()
    {
    	return this.lightConfiguration;
    }
    
    
    
    synchronized public void ChangeLights()
    {
        //Junction D only has one entry road so assume the light is always green and allow cars through to exits
    	if (this.id != 'D')
    	{
                //Circular queue which changes the exit road that cars are pllaced on after crossing junction
	    	this.currentEntryRoad++;
	    	if (currentEntryRoad > this.entryRoads.size() - 1)
	    	{
	    		this.currentEntryRoad = 0;
	    	}
    	}
    }
            
    private void PrintJunctionStats(int seconds, int minutes)
    {
        if (carsThroughJunction == 0 && !this.entryRoads.elementAt(currentEntryRoad).isEmpty())
                    {
                        System.out.println("Time: " + minutes +"m" + seconds + "s - Junction " + this.GetJunctionID() + ": " + carsThroughJunction + " cars crossed coming from " + this.entryRoads.elementAt(currentEntryRoad).GetRoadID() + ", " + this.entryRoads.elementAt(currentEntryRoad).GetCarsInQueue() + " in the queue. Gridlock has occured.");
                        
                    }
                    else
                    {
                        System.out.println("Time: " + minutes +"m" + seconds + "s - Junction " + this.GetJunctionID() + ": " + carsThroughJunction + " cars crossed coming from " + this.entryRoads.elementAt(currentEntryRoad).GetRoadID() + ", " + this.entryRoads.elementAt(currentEntryRoad).GetCarsInQueue() + " in the queue.");
                        
                    }

    }
    
    private void WriteStatsToFile(int seconds, int minutes ) 
    {
            try {
                    FileWriter fstream = new FileWriter(fileName, true);
                    try (BufferedWriter outputWriter = new BufferedWriter(fstream)) {
                        if (carsThroughJunction == 0 && !this.entryRoads.elementAt(currentEntryRoad).isEmpty())
                        {
                            outputWriter.write("Time: " + minutes +"m" + seconds + "s - Junction " + this.GetJunctionID() + ": " + carsThroughJunction + " cars crossed coming from " + this.entryRoads.elementAt(currentEntryRoad).GetRoadID() + ", " + this.entryRoads.elementAt(currentEntryRoad).GetCarsInQueue() + " in the queue. Gridlock has occured.");
                            outputWriter.newLine();
                        }
                        else
                        {
                            outputWriter.write("Time: " + minutes +"m" + seconds + "s - Junction " + this.GetJunctionID() + ": " + carsThroughJunction + " cars crossed coming from " + this.entryRoads.elementAt(currentEntryRoad).GetRoadID() + ", " + this.entryRoads.elementAt(currentEntryRoad).GetCarsInQueue() + " in the queue.");
                            outputWriter.newLine();
                        }
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block

            } catch (IOException e) {
                    // TODO Auto-generated catch block

            }
    }
    
    @Override
    public void run()
    {
        
        boolean running = !Clock.IsRushHourOver();
        this.currentTime = Clock.getTimePassed();
        int timePassed = 0;

        while (running)
        {
            timePassed = Clock.getTimePassed();
            running = !Clock.IsRushHourOver();
            
            if (timePassed - currentTime >= this.lightConfiguration)
            {
                currentTime = timePassed;
                this.ChangeLights();
                int seconds = timePassed % 60;
                int minutes = timePassed / 60;
                
                PrintJunctionStats(seconds, minutes);
                WriteStatsToFile(seconds, minutes);
                this.carsThroughJunction = 0;
                
            }
            
            // Checks the road before trying to place a cat onto it
        	if (currentEntryRoad > this.entryRoads.size() - 1)
        	{
        		this.currentEntryRoad = 0;
        	}
            
            CrossJunction();
        }
    }

}