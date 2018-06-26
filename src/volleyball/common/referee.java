package volleyball.common;

import java.io.Serializable;

/*
 * Comp 3716 A3 
 * 
 * Written on Nov 7 2016
 * by @RyanLey
 */
public class referee extends account implements Serializable{
	public static String name="Referee";
	public String email; 

	public referee(String e){
		super(name,e);
	}
}

