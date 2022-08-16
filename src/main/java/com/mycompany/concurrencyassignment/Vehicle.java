package com.mycompany.concurrencyassignment;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andre
 */


public class Vehicle {
    
    String destination;
    String ID;
    
    int startTime;
    int finishTime;
    
    public Vehicle (String id, String destination, int starttime) 
    {
        this.destination = destination;
        ID = id;
        startTime = starttime;
    }
    
    //Deconstructor
    @Override
    protected void finalize() 
    {
    
    }
    // Method to return vehicle ID
    public String GetVehicleID()
    {
        return this.ID;
    }
    //Method to return vehicle destination
    public String GetDestination() {
        return this.destination;
    }
    //Method to return vehicle start time
    public int GetStartTime()
    {
    	return this.startTime;
    }
    //Method to set vehicle finish time
    public void SetFinishTime(int finishTime)
    {
    	this.finishTime = finishTime;
    }
}
