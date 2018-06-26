package volleyball.common;

import java.io.Serializable;

/*
 * Comp 3716 A3 
 * 
 * Written on Nov 7 2016
 * by @RyanLey
 */
public class coach extends account implements Serializable{
	public static String name;
	public String email; 
	public team[] teams;
	public int teamCount;

	public coach(String name){
		super(name);
		teams = new team[19];
		teamCount = 0;
	}
	public team[] getTeams(){
		return this.teams;
	}
	public void addTeam(team t){
		teams[teamCount] =t;
		teamCount++;
	}
}
