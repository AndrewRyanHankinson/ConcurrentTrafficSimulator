package com.mycompany.concurrencyassignment;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andre
 */
class Road 
{
    private final Vehicle [] CarsOnRoad;
    private int nextCarIn=0;
    private int nextCarOut=0;
    private int available;
    private final String ID;
    private int carsInQueue;
    int roadSize;
    
    //Road constructor
    public Road(int size, String id)
    {
        this.ID = id;
        CarsOnRoad = new Vehicle[size];
        available = 0;
        this.carsInQueue = 0;
        this.roadSize = size;
    }

    //Synchronised method used to insert vehicle into buffer. Method is synchronised to ensure concurrency and to ensure that pnly one thread can acccess the critical region at one time
    public synchronized void insert(Vehicle c)
    {
        
        CarsOnRoad[nextCarIn] = c;
        available = available + 1;
        carsInQueue++;
        nextCarIn++;
        
        if(nextCarIn == CarsOnRoad.length)
        {
            nextCarIn = 0;
        }
                
        notifyAll(); //wake up all threads waiting to insert
    }

    
    //Synchronised method used to extract vehicle from buffer. Method is synchronised to ensure concurrency and to ensure that pnly one thread can acccess the critical region at one time
    public synchronized Vehicle extract()
    {
        Vehicle carToExtract;
        while (available == 0) //If nothing is available, will wait 
        {
            	  
            try { wait(); } 
            catch (InterruptedException ex) { }
		
        }
        carToExtract = CarsOnRoad[nextCarOut]; //Extract car from buffer road
        
        available--; // decrement cars available to account for the car being exracted 
        carsInQueue--;
        
        nextCarOut++; // increment so next car gets extracted
        
        if (nextCarOut==CarsOnRoad.length) // if the car being extracted is the last one in the buffer, reset to 0 
        {
            nextCarOut=0;
        }
        notifyAll(); // notify all threads waiting to extract
        return carToExtract;
    }
    
    public String GetVehicleDestination()
    {
        return CarsOnRoad[nextCarOut].GetDestination();
    }
    
    
    public int GetCarsInQueue()
    {
    
        return carsInQueue;
    }
    
    public String GetRoadID()
    {
    	return this.ID;
    }
    
    boolean isFull()
    {
    	   	
    	return carsInQueue == roadSize;
    }
    
    boolean isEmpty()
    {
    	
    	return available == 0;
    }
}