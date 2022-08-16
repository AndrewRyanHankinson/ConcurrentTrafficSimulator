package com.mycompany.concurrencyassignment;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.*;
/**
 *
 * @author andre
 */
public class ReadWriteLock {
    private int activeReaders;     
    private int waitingReaders;    
    private int activeWriters;     

    private final LinkedList<Object> writerLocks = new LinkedList<>(); 


    public ReadWriteLock() 
    {
        System.out.println("read write lock created");
    }

    public synchronized void requestRead()
    {
        if( activeWriters==0 && writerLocks.isEmpty() )
            ++activeReaders;
        else
        {  
           ++waitingReaders;
           try{ wait(); }catch(InterruptedException e){}
        }
    }

    public synchronized boolean requestImmediateRead()
      {
          if( activeWriters==0 && writerLocks.isEmpty() )
          {   
              ++activeReaders;
              return true;
          }
          return false;
      }

      public synchronized void readAccomplished()
        {   
          if( --activeReaders == 0 )
                notifyWriters();
        }

        public void requestWrite()
          {
             
             Object lock = new Object();
              synchronized( lock )
              {   synchronized( this )
                  {   boolean okay_to_write = writerLocks.isEmpty()
                                              && activeReaders==0
                                              && activeWriters==0;
                      if( okay_to_write )
                      {   
                          ++activeWriters;
                          return; // the "return" jumps over the "wait" call
                      }
                      writerLocks.addLast( lock );
                  }
                  try{ lock.wait(); } catch(InterruptedException e){}
              }
          }

          synchronized public boolean requestImmediateWrite()
            {
                if( writerLocks.isEmpty()  && activeReaders==0
                                            && activeWriters==0 )
                {   
                    ++activeWriters;
                    return true;
                }
                return false;
            }

            public synchronized void writeAccomplished()
              {
                
                  --activeWriters;
                  if( waitingReaders > 0 )   // priority to waiting readers
                      notifyReaders();
                  else
                      notifyWriters();
              }

             synchronized private void notifyReaders()
              { 
                  activeReaders += waitingReaders;
                  waitingReaders = 0;
                  notifyAll();
              }

              private void notifyWriters()       
                {                                   
                    if( !writerLocks.isEmpty() )
                    {
                        Object oldest = writerLocks.removeFirst();
                        ++activeWriters;
                        synchronized( oldest ){ oldest.notify(); }
                    }
                }
}
