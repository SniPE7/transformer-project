package com.ibm.ncs.util;

public class Stopwatch {
    //private final long start;
	private  long start;

    public Stopwatch() {
        start = System.currentTimeMillis();
    } 

    // return time (in seconds) since this object was created
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    } 
    
    public void restart(){
    	start = System.currentTimeMillis();
    }
}
