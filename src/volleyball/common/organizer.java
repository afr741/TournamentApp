package volleyball.common;

import java.io.Serializable;

/*
 * Comp 3716 A3 
 * 
 * Written on Nov 7 2016
 * by @RyanLey
 */
public class organizer extends account implements Serializable{
	public static String name="Organizer";
	public String email; 

	public organizer(String e){
		super(name,e);
	}
}

