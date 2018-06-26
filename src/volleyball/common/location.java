package volleyball.common;

import java.io.Serializable;

/*
 * Comp 3716 A3 
 * 
 * Written on Nov 7 2016
 * by @RyanLey
 */
public class location implements Serializable{
	int numCourts;
	String name;
	
	public location(String n, int c){
		name = n;
		numCourts = c;
	}
	//Default Court option
	public location(String n){
		name = n;
		numCourts =1;
	}
	public String getName() {
		return name;
	}
}
