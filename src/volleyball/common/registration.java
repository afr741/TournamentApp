package volleyball.common;
import java.io.Serializable;
import java.util.GregorianCalendar;
/*
 * Comp 3716 A3 
 * 
 * Written on Nov 7 2016
 * by @RyanLey
 */
public class registration implements Serializable{
		team receiveTeam;
		Boolean deadlinePass;
		tournament Tournament;
					
		public boolean notifyDrop(){
			GregorianCalendar currentDate = new GregorianCalendar();
			if (currentDate.before(Tournament.deadline)==false){
				return true;
			}
			else{
				return false;
			}
		}
		public static tournament addTeam(team t, tournament tour){
			boolean verify = verifyTeam(t);
			if (verify ==true){
				tour.teams[tour.getTeamCount()] =t;

			}
			return tour;
		}
		public static boolean verifyTeam(team t){
			//does team t meet tournament qualifications?
				t.verified = true;
				return true;
				/*
				t.verified = false;
				return false;*/
		}
		
	}