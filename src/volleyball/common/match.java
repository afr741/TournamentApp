package volleyball.common;

import java.io.Serializable;

/*
 * Comp 3716 A3 
 * 
 * Written on Nov 7 2016
 * by @RyanLey
 */
public class match implements Serializable{
	location loc;
	team team1;
	team team2;
	referee ref;
	team winner;
	team loser;
	score matchScore =new score();
	int  id;
	

	public match(){
		
	}
	public match(team t1, team t2){
		team1 = t1;
		team2 = t2;
		}
	public match(team t1, team t2, location l, referee r){
		team1 = t1;
		team2 = t2;
		loc = l;
		ref = r;
	}

	public location getLoc() {
		return loc;
	}

	public team getTeam1() {
		return team1;
	}

	public team getTeam2() {
		return team2;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public referee getRef() {
		return ref;
	}

	public void setLoc(location loc) {
		this.loc = loc;
	}

	public void setTeam1(team team1) {
		this.team1 = team1;
	}

	public void setTeam2(team team2) {
		this.team2 = team2;
	}

	public void setRef(referee ref) {
		this.ref = ref;
	}
	public void bi(team A){
		winner = A;
	}
	public void setWinner(){
		winner = matchScore.getWinner();
	}
	public team getWinner(){
		return matchScore.getWinner();
	}
	public void setScore(int a, int b, int c){
		matchScore.updateScore(a, b, c, team1, team2);
		try{
			winner = matchScore.getWinner();
			System.out.println(winner.getName());
		}
		catch(Exception e){
			winner= null;
		}
	}
	
}
