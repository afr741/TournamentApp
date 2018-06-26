package volleyball.common;

import java.io.Serializable;

import javax.swing.JOptionPane;

/*
 * Comp 3716 A3 
 * 
 * Written on Nov 7 2016
 * by @RyanLey
 */
public class score implements Serializable{
	team team1;
	team team2;
	team[] winners = new team[4];
	int setsWon1 =0;
	int setsWon2 =0;
	int bestOf = 3;
	team winner =null;
	boolean verified=false;
	
	public void updateScore(int team1Score, int team2Score, int set,team A, team B){
		team1 = A;
		team2 = B;
		verified=verify(team1Score,team2Score,set);
		if(verified==true){
			if(team1Score>team2Score){
				setsWon1++;
				winners[set-1] = team1;
			}
			else{
				setsWon2++;
				winners[set-1] = team2;
			}
			//System.out.println("Sets 1: "+setsWon1);
			//System.out.println("Sets 2: "+setsWon2);
			if(setsWon1 == 3){
				System.out.println("here");
				winner = team1;
				//System.out.println(team1.getName());

			}
			if(setsWon2 == 3){
				winner = team2;
			}
			if(winner!=null){
				//System.out.println(winner.getName());
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "Sorry, you have entered an invalid score for set "+set,"Invalid Score",JOptionPane.INFORMATION_MESSAGE);
		}
		

	}
	public team getWinner(){
		return winner;
	}
	public boolean verify(int team1Score, int team2Score, int set){
		int scoreMaxA = 25;
		int scoreMaxB = 15;
		int setMax = 5;
		System.out.println(team1Score+" "+team2Score);
		
		if(team1Score>team2Score){
			if(set == setMax){
				if(team1Score == scoreMaxB && team2Score <= (scoreMaxB-2)){
					return true;
				}
				
				if(team1Score > scoreMaxB && team2Score <= (team1Score-2)){
					return true;
				}
				
				else{
					return false;
				}
			}
			
			if(team1Score == scoreMaxA && team2Score <= (scoreMaxA-2)){
				return true;
			}
			
			if(team1Score > scoreMaxA && team2Score <= (team1Score-2)){
				return true;
			}
			
			else{
				return false;
			}
		}
		else{
			if(set == setMax){
				if(team2Score == scoreMaxB && team1Score <= (scoreMaxB-2)){
					return true;
				}
				
				if(team2Score > scoreMaxB && team1Score <= (team2Score-2)){
					return true;
				}
				
				else{
					return false;
				}
			}
			
			if(team2Score == scoreMaxA && team1Score <= (scoreMaxA-2)){
				return true;
			}
			
			if(team2Score > scoreMaxA && team1Score <= (team2Score-2)){
				return true;
			}
			
			else{
				return false;
			}
		}
	}
}
