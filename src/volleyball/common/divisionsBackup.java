package volleyball.common;

/*
* COMP 3716 Final Project
* by @Donald Ryan
*/

public class divisionsBackup {
	
	team[] teams;
	team[][] teamsPerDiv;
	bracket[] brackets;
	team[] finalists;
	bracket finals = new bracket();
	int divs;
	int tNum;
	int leftOver;
	int per;
	team[] hold;
	int prss=0; // keeps track of position of assigning teams
	
	
	public divisionsBackup(team[] team, int div){
		teams = team;
		System.out.println("ryan");
		divs=div;
		brackets = new bracket[divs];
		
		
		
		while ( teams[tNum] != null){
			if(teams[tNum].getName() != null){
				tNum++;
				
			}
		}
		per = tNum/divs;
		leftOver = tNum/divs;
		
		teamsPerDiv = new team[div][];
		
		for(int i=0;i<divs;i++){ // figures out how many teams go in each division
			
			if( i+1 == divs){
				System.out.println("hey look at me");
				teamsPerDiv[i]= new team[per+leftOver];// sets last division to hold extra teams
			}
			else{
			System.out.println("assigned normally");
			teamsPerDiv[i]= new team[per];
			}
		}
		
		for(int i=0; i < divs;i++){//cycle for each div
			//System.out.println(teamsPerDiv[i].length);
			hold = new team[teamsPerDiv[i].length];//set the size of hold to number of
			//System.out.println(brackets.length);
			for(int j=0; j< teamsPerDiv[i].length;j++){//cycles for each team in that division
				hold[j] = teams[prss];// set teams to positions in  hold
				//System.out.println(teams[prss].name);
				prss++;//increment progress in origin team list	
			}
			
			
			brackets[i] = new bracket(hold);// create bracket with currently held team list	
		}
	}
	
	public bracket[] getAllDiv(){
		bracket[] pass = brackets;
		return pass;
	}
	
	
	public bracket getDivBracket(int a){
		bracket placeholder = brackets[a];
		return  placeholder;
	}
	
	public void createFinals(){
		finalists = new team[divs]; // finalists array size 
		for(int i=0; i<divs;i++){
			finalists[i] = brackets[i].bracketWinner();
		}
		finals = new bracket(finalists);
	}
	
	public bracket getFinals(){
		if(finals != null){
			return finals;
		}
		else{
			System.out.println("Still waiting on all Divisions to produce finalists");
			return null;
		}
	}
	
	public team finalWinner(){
		if(finals != null && finals.bracketWinner() != null){
			return finals.bracketWinner();
		}
		else{
			System.out.println("the finals must be initialized and have a winner");
			return null;
		}
	}
	
	public void progressDiv(int a){
		brackets[a].progMatch();
	}
	
	
	
	
	
	
	
}


   