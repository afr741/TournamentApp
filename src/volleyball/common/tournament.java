package volleyball.common;
import java.io.Serializable;
/*
 * Comp 3716 A3 
 * 
 * Written on Nov 7 2016
 * by @RyanLey
 */
import java.util.GregorianCalendar;
public class tournament implements Serializable {
	String name;
	
	location Location;
	GregorianCalendar deadline;
	
	GregorianCalendar startDate;
	GregorianCalendar endDate;
	
	String startDateS;
	String endDateS;
	public team[] teams;
	
	public boolean hasView = false;
	team[] extraTeams;
	int minTeams;
	int maxTeams;
	int teamCount;
	
	int numDivisions;

	String tournamentRules;
	//false for single Elimination
	//true for divisions
	boolean style;
	int maxDropped;
	int tournamentLength;
	int numMatches;
	schedule Schedule;
	public match[][] Bracket;
	public bracket[] divisions;
	
	bracket bracket;
	
	int times;
	
	public tournament(String n, GregorianCalendar s, GregorianCalendar e){
		name= n;
		startDate = s;
		endDate = e;
		teamCount = 0;
		tournamentLength = endDate.compareTo(startDate);
	}
	public tournament(String n, String s, String e){
		name= n;
		startDateS = s;
		endDateS = e;
		teamCount = 0;
		//tournamentLength = endDate.compareTo(startDate);
	}
	public int getMinTeams() {
		return minTeams;
	}
	public void setMinTeams(int minTeams) {
		this.minTeams = minTeams;
	}
	public int getMaxTeams() {
		return maxTeams;
	}
	public void setMaxTeams(int maxTeams) {
		this.maxTeams = maxTeams;
		try{
			if(teams==null){
				teams = new team[maxTeams];
			}
		}
		catch(Exception e2){
			try{
				if(teams[0]==null){
					teams = new team[maxTeams];
				}
			}
			catch(Exception e3){
				team[] t = new team[maxTeams];
				for(int i=0;i<teams.length;i++){
					if(teams[i]!=null){
						t[i]=teams[i];
					}
					else{
						t[i]=null;
					}
				}
				teams=t;
			}
		}
	}
	public String getName() {
		return name;
	}
	
	public void setStyle(int i){
		if(i==0){
			style= false;
		}
		else{
			style=true;
		}
	}
	public void setDivisions(bracket[] d){
		divisions = d;
	}
	public bracket[] getDivisions(){
		return divisions;
	}
	public void setActualBracket(bracket b){
		bracket = b;
	}
	public bracket getActualBracket(){
		return bracket;
	}
	public void setView(boolean t){
		hasView = t;
	}
	
	public boolean getStyle(){
		return style;
	}
	public void setSchedule(schedule s){
		Schedule =s;
	}
	public void setNumMatches(int n){
		numMatches = n;
	}
	public match[][] getBracket() {
		return Bracket;
	}
	public void setBracket(match[][] bracket) {
		Bracket = bracket;
	}
	public int getTeamCount() {
		teamCount=0;
		for(int i=0;i<teams.length;i++){
			if(teams[i]!=null){
				teamCount++;
			}
		}
		return teamCount;
	}
	public void setTeamCount(int teamCount) {
		this.teamCount = teamCount;
	}
	public void removeTeam(int index){
		for (int i = index;i<teams.length;i++){
			if (teams[i] !=null){
				teams[i] = teams[i+1];
			}
		}
	}
	public void setLocation(String s){
		Location = new location(s);
	}
	public location getlocation(){
		return Location;
	}
	public GregorianCalendar getDeadline() {
		return deadline;
	}
	public void setDeadline(GregorianCalendar deadline) {
		this.deadline = deadline;
	}
	public void setName(String name) {
		this.name = name;
	}
	public GregorianCalendar getStartDate() {
		return startDate;
	}
	public void setStartDate(GregorianCalendar startDate) {
		this.startDate = startDate;
	}
	public GregorianCalendar getEndDate() {
		return endDate;
	}
	public void setEndDate(GregorianCalendar endDate) {
		this.endDate = endDate;
	}
	public team[] getTeams() {
		return teams;
	}
	public void setTeams(team[] teams) {
		this.teams = teams;
	}
	
	public int getNumDivisions() {
		return numDivisions;
	}
	public void setNumDivisions(int numDivisions) {
		this.numDivisions = numDivisions;
	}

	
	//implementation of the remaining variables in the future
}