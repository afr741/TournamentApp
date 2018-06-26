package volleyball.common;
/*
 * Comp 3716 A3 
 * 
 * Written on Nov 7 2016
 * by @RyanLey
 */

import java.io.Serializable;

public class schedule implements Serializable {
		bracket Bracket;
		int length;
		match[][][] sched;
		Boolean Reformat = false;
		int maxDropped;
	
		public schedule(tournament t, bracket b){
			Bracket = b;
			length = t.tournamentLength;
			maxDropped = t.maxDropped;
			sched = new  match[length][t.times][t.numMatches];
		}
		public void createSchedule(){
			//using matches created by the bracket, assign them times.
		}
		
		public void teamDrop(team t ){
			//Team t drops out of tournament
		}
	}