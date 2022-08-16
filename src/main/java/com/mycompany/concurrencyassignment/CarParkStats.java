/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.concurrencyassignment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andre
 */
public class CarParkStats 
{
    private List<CarPark> CarParks = new ArrayList<CarPark>();
   
   
   public void addCarPark(CarPark cp){
      CarParks.add(cp);		
   }
   
   public void PromptCarParkPrintStats(){
      for (CarPark cp : CarParks) {
         cp.PrintNumSpacesAvailable();
      }
   }  
    
    
    
    
}
