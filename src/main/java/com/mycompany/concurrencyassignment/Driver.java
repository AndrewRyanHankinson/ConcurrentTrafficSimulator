package com.mycompany.concurrencyassignment;

import java.io.BufferedReader;
import java.util.Vector;


import java.io.FileReader;
import java.io.IOException;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author andre
 */
public class Driver {
    public Driver()
    {
    }
    
    public static void main(String[] args) throws IOException
    {
    	//File path containing the scenarios
    	String filename = "C:\\Users\\Andrew's laptop\\OneDrive - UCLan\\Desktop\\ConcurrencyAssignment\\src\\Scenario5.txt";
        
        //Initialise cars from entrypoints
        int carsFromNorth = 0;
        int carsFromEast = 0;
        int carsFromSouth = 0;
        
        
        //Initiliase traffic light durartions
        int junctionALightConfig = 0;
        int junctionBLightConfig = 0;
        int junctionCLightConfig = 0;
        int junctionDLightConfig = 0;
        
        
        //Read file in from configuration file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
               String[] arrSplit = line.split(" ");
               
               if (arrSplit[0].equals("North"))
               {
            	   carsFromNorth = Integer.parseInt(arrSplit[1]);
               }
               if (arrSplit[0].equals("East"))
               {
            	   carsFromEast = Integer.parseInt(arrSplit[1]);
               }
               if (arrSplit[0].equals("South"))
               {
            	   carsFromSouth = Integer.parseInt(arrSplit[1]);
               }
               if (arrSplit[0].equals("A"))
               {
            	   junctionALightConfig = Integer.parseInt(arrSplit[1]);
               }
               if (arrSplit[0].equals("B"))
               {
            	   junctionBLightConfig = Integer.parseInt(arrSplit[1]);
               }
               if (arrSplit[0].equals("C"))
               {
            	   junctionCLightConfig = Integer.parseInt(arrSplit[1]);
               }
               if (arrSplit[0].equals("D"))
               {
            	   junctionDLightConfig = Integer.parseInt(arrSplit[1]);
               }
              
               
               
            }
        }
        catch(Exception ex)
        {
        	
        }
    
    		
    	
        
        //Road buffers
        Road SouthToJunctionA = new Road(60, "SouthToJunctionA");
        Road JunctionBtoJunctionA = new Road(7, "JunctionBtoJunctionA");
        
        
        Road JunctionAtoIndustrial = new Road(15, "JunctionAtoIndustrial");
        Road JunctionAtoJunctionB = new Road(7, "JunctionAtoJunctionB");
        
       
        Road EastToJunctionB = new Road(30, "EastToJunctionB");
        Road JunctionCtoJunctionB = new Road(10, "JunctionCtoJunctionB");

        
        Road JunctionBtoJunctionC = new Road(10, "JunctionBtoJunctionC");
        Road NorthToJunctionC = new Road(50, "NorthToJunctionC");
        
        
        Road JunctionCtoShoppingCentre = new Road(10, "JunctionCtoShoppingCentre");
        Road JunctionCtoJunctionD = new Road(10, "JunctionCtoJunctionD");
        
        Road JunctionDtoStation = new Road(15, "JunctionDtoStation");
        Road JunctionDtoUniversity = new Road(15, "JunctionDtoUniversity");
      
        
        //Roads entering each junction
        Vector<Road> enteringJunctionA = new Vector<>();
        enteringJunctionA.add(SouthToJunctionA);
        enteringJunctionA.add(JunctionBtoJunctionA);
        
        Vector<Road> enteringJunctionB = new Vector<>();
        enteringJunctionB.add(JunctionAtoJunctionB);
        enteringJunctionB.add(EastToJunctionB);
        enteringJunctionB.add(JunctionCtoJunctionB);
        
        Vector<Road> enteringJunctionC = new Vector<>();
        enteringJunctionC.add(JunctionBtoJunctionC);
        enteringJunctionC.add(NorthToJunctionC);
        
        Vector<Road> enteringJunctionD = new Vector<>();
        enteringJunctionD.add(JunctionCtoJunctionD);
                
        
        
        
        //Exits
        Vector<Road> exitingJunctionA = new Vector<>();
        exitingJunctionA.add(JunctionAtoIndustrial);
        exitingJunctionA.add(JunctionAtoJunctionB);
        
        Vector<Road> exitingJunctionB = new Vector<>();
        exitingJunctionB.add(JunctionBtoJunctionA);
        exitingJunctionB.add(JunctionBtoJunctionC);
        
        Vector<Road> exitingJunctionC = new Vector<>();
        exitingJunctionC.add(JunctionCtoJunctionB);
        exitingJunctionC.add(JunctionCtoShoppingCentre);
        exitingJunctionC.add(JunctionCtoJunctionD);

        Vector<Road> exitingJunctionD = new Vector<>();
        exitingJunctionD.add(JunctionDtoStation);
        exitingJunctionD.add(JunctionDtoUniversity);
    
        
        //Threaded clock object used to change lights and ends simulation
        Clock clock = new Clock();
        
        
        
         //Car entrypoints
        EntryPoint entryPointSouth = new EntryPoint("South", SouthToJunctionA, carsFromSouth, clock);
        EntryPoint entryPointEast = new EntryPoint("East", EastToJunctionB, carsFromEast, clock);
        EntryPoint entryPointNorth = new EntryPoint("North", NorthToJunctionC, carsFromNorth, clock);

        //Junctions
        Junction A = new Junction('A', clock, enteringJunctionA, exitingJunctionA, junctionALightConfig);
        Junction B = new Junction('B', clock, enteringJunctionB, exitingJunctionB, junctionBLightConfig);
        Junction C = new Junction('C', clock, enteringJunctionC, exitingJunctionC, junctionCLightConfig);
        Junction D = new Junction('D', clock, enteringJunctionD, exitingJunctionD, junctionDLightConfig);
        
        //Car parks
        CarPark industrialPark = new CarPark(1000, JunctionAtoIndustrial, 0, clock);
        CarPark shoppingCentre = new CarPark(400, JunctionCtoShoppingCentre, 1, clock);
        CarPark station = new CarPark(150, JunctionDtoStation, 2, clock);
        CarPark university = new CarPark(100, JunctionDtoUniversity, 3, clock);
        
        //Add each car park to list to call car park stats
        clock.cpt.addCarPark(industrialPark);
        clock.cpt.addCarPark(shoppingCentre);
        clock.cpt.addCarPark(station);
        clock.cpt.addCarPark(university);
        
        //Print the name of the config file currently being used
        System.out.println("Using scenario: " + filename);
        
        
        
        // Starting threaded objects
        
        clock.start();
        entryPointSouth.start();
        entryPointEast.start();
        entryPointNorth.start();
        
        
        D.start();
        A.start();
        B.start();
        C.start();
       
        industrialPark.start();
        shoppingCentre.start();
        station.start();
        university.start();
        
       
        try {
                clock.join();

                A.join(); 
                B.join(); 
                C.join(); 
                D.join(); 

            entryPointSouth.join();
            entryPointEast.join();
            entryPointNorth.join();

            industrialPark.join();
            shoppingCentre.join();
            station.join(); 
            university.join();
            } catch (InterruptedException ex) { }
		


        int totalCarsParked = shoppingCentre.numSpacesOccupied + industrialPark.numSpacesOccupied + station.numSpacesOccupied  + university.numSpacesOccupied;
        
        int totalCarsOnRoad = SouthToJunctionA.GetCarsInQueue() + JunctionBtoJunctionA.GetCarsInQueue() + JunctionAtoIndustrial.GetCarsInQueue() + JunctionAtoJunctionB.GetCarsInQueue() + 
        		              EastToJunctionB.GetCarsInQueue() + JunctionCtoJunctionB.GetCarsInQueue() + JunctionBtoJunctionC.GetCarsInQueue() + NorthToJunctionC.GetCarsInQueue() +
        		              JunctionCtoShoppingCentre.GetCarsInQueue() + JunctionCtoJunctionD.GetCarsInQueue() + JunctionDtoStation.GetCarsInQueue() + JunctionDtoUniversity.GetCarsInQueue();
        
        int totalProduced = entryPointSouth.carsCreated + entryPointNorth.carsCreated + entryPointEast.carsCreated; 
        
        
        
        System.out.println("Total amount of cars created: " + totalProduced);
        System.out.println("Total amount of cars in the queues: " + totalCarsOnRoad);

        
        System.out.println("Total cars that entered the town : " + (totalCarsParked + totalCarsOnRoad));
        System.out.println("Total cars missing: " + (totalProduced - (totalCarsParked + totalCarsOnRoad)));
      
        
    }
}
