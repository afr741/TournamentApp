package volleyball.common;

import java.io.Serializable;

import javax.swing.JOptionPane;

/*
 * Comp 3716 A3 
 * 
 * Written on Nov 7 2016
 * by @RyanLey
 * 
 */
public class team implements Serializable{
	String name;
	int id;
	public player[] players;
	boolean verified = false;
	coach Coach;
	int playerCount =0;
	
	int maxPlayers;
	
	
	public team(int i, String n){
		name = n;
		id = i;
	}
	
	public team(String n){
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPlayers(int n){
		players = new player[n];
		maxPlayers = n+1;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public player[] getPlayers() {
		return players;
	}

	public boolean isVerified() {
		return verified;
	}
	public int getPlayerCount(){
		return playerCount;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public void addPlayer(player p){
		if(playerCount<getMaxPlayers()){
			players[playerCount] = p;
			playerCount++;
		}
		else{
			JOptionPane.showMessageDialog(null, "Sorry, you have tried to register too many teams for this tournament","Too many teams",JOptionPane.INFORMATION_MESSAGE);		}
		}
	public void setCoach(String name){
		Coach = new coach(name);
	}
	public String getCoach(){
		return Coach.getName();
	}
	public void setPlayersArray(player[] p){
		players =p;
	}
	public int getMaxPlayers() {
		return maxPlayers;
	}

}

