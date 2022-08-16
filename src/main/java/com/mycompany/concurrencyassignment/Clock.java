package com.mycompany.concurrencyassignment;




/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andre
 */
public class Clock extends Thread
{
    ReadWriteLock rwLock;
    int currentMinute =0;
    int timePassed;
    boolean isRushHourOver = false;
    int simulationTimeLimit = 3600;
    boolean tenMinutes = false;
    
    public CarParkStats cpt;
    
    //Clock constructor initialises ReadWriteLock object 
    public Clock () 
    {
       rwLock = new ReadWriteLock();
       cpt = new CarParkStats();
    }
    
    protected void finalize() {
    	
    }
    //Method to get time passed in simulation. Use of lock to ensurethat only one thread can access at a time
    public int getTimePassed()
    {
        try
        {
            // Get lock 
            rwLock.requestRead();
            return this.timePassed;
        }
        finally
        {
            //Relinquish lock
            rwLock.readAccomplished();
        }  
    }
    
    //Get ten minute interval to print car park stats 
    public boolean tenMinutes()
    {
        try
        {
            //Get lock
            rwLock.requestRead();
            return this.tenMinutes;
        }
        finally
        {
            //Read has been accomplished and writer can be notified to write again
          rwLock.readAccomplished();
        }  
    }
    
    //Check if simulation is over
    public  boolean IsRushHourOver()
    {
        try
        {
          rwLock.requestRead();
            return this.isRushHourOver;
        }
        finally
        {
           rwLock.readAccomplished();
        }
    }
    
    @Override
    public void run()
    {
        isRushHourOver = false;
        while(timePassed < simulationTimeLimit) // while loop runs until the time passed exceeds the simulation time limit
        {
            //Get lock to update time passed so no thread can read whilst time is being updated
            rwLock.requestWrite();
            timePassed++;
            //Release lock when time has been updated
            rwLock.writeAccomplished();

            try
            {
                Thread.sleep(100);
            }
            catch(InterruptedException ex)
            {
                
            }
            if (timePassed %60 == 0)
            {
                System.out.println("One Minute has passed");
            }
            
            if (timePassed %600 == 0)
            {
            	currentMinute = timePassed / 60;
            	System.out.println("Time:    " + currentMinute + "m" );
                this.cpt.PromptCarParkPrintStats(); // calls the carp[ark stats class to print out the car parks stats every ten minutes
               
            }
        }
        
        isRushHourOver = true;
        System.out.println("Sim finished");
        

    }
    
    
    
}
