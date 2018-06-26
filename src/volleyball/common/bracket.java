package volleyball.common;

import java.io.Serializable;

/*
 * Comp 3716 A3 
 * 
 * Written on Nov 7 2016
 * by @RyanLey 
 * by @DonaldRyan
 * 
 * 
 * 
 */
public class bracket implements Serializable{
		team[] teams;
		location[] locations;
		tournamentStyle style;
		int[] rdmArray;
		int Count = 0;
		int Nteams = 0;
		int hold =0;
		int layers;
		int[] perLayer ;
		match[][] bracketGen;
		public bracket(){
			
		}
		
		
		public bracket(team[] t){
			teams =t;
			try{
				while ( teams[Nteams] != null){
					if(teams[Nteams].getName() != null){
						Nteams++;
						System.out.println("working");
						
					}
				
			}
			}
			catch(Exception e){
				
			}
			
				
			
			
			rdmArray = new int[Nteams];
			for(int i=0; i < Nteams;i++){
				rdmArray[i] = i;
			}
			rdmArray = Randomizer.RandomizeArray(rdmArray);
			
			
			hold = Nteams;
			
			while( hold != 1){

				if(hold%2 !=  0){
					hold+=1;
				}

				hold = hold/2;

				Count += 1; 
			}
			//issues are in bracket array generation below 
			layers=Count;
			
			bracketGen= new match[Count][];
			hold =Nteams;
			for(int i =0;i<Count;i++){
				if(hold%2 != 0){
					hold+=1;
				}
				hold = hold/2;
				
				bracketGen[i] = new match[hold];
				
			}
			
			
			initTeam();
			
		}
		
		public void initTeam(){
			
			int curMatch = 0;
			//System.out.println(Nteams);
			for(int i=0;i<Nteams ;i= i+2){
				int rdmH = rdmArray[i];
				team teamA = teams[i];
				if(teams[i+1] == null){
					//System.out.println("bi gen");
					bracketGen[0][curMatch] = new match (teamA, teamA);
					bracketGen[0][curMatch].bi(teamA);
				}
				else{
				team teamB = teams[i+1];
				
				bracketGen[0][curMatch] = new match( teamA , teamB);
				}	
				//System.out.println("inititialized " + bracketGen[0][curMatch].team1.name +" and the " + bracketGen[0][curMatch].team2.name);
				curMatch++;
				
			}
			
		}
		//this can be changed so that all winners are needed on the previous layer before they progress 
		public void progMatch(){
			//team winners[] ;
			int hop=0;
			int j=0;
			int matchPos =0;
			for(int i=0;i < layers;i++){
				System.out.println("sup");
				matchPos = 0;
				for( j=0; j< bracketGen[i].length ; j= j+2){
					System.out.println("HEY there are " +bracketGen[i].length + " matches");
					
					if(bracketGen[i][j] != null && bracketGen[i][j].winner != null ){
						//&& bracketGen[i][j+1] != null && bracketGen[i][j+1].winner != null
						System.out.println("executed");
						if( j+1 == bracketGen[i].length){
							System.out.println("by set");
							bracketGen[i+1][matchPos] = new match( bracketGen[i][j].winner ,bracketGen[i][j].winner);
							bracketGen[i+1][matchPos].bi(bracketGen[i][j].winner);
							matchPos++;
						}
						else if(bracketGen[i][j+1] != null && bracketGen[i][j+1].winner != null) {
							System.out.println("no by");
						bracketGen[i+1][matchPos] = new match( bracketGen[i][j].winner ,bracketGen[i][j+1].winner);
						matchPos ++;
						}
						else{
							System.out.println("no winner here");
							matchPos++;
							
						}
						//bracketGen[i+1][matchPos].setTeam1(bracketGen[i][j].winner);
						//bracketGen[i+1][matchPos].setTeam2(bracketGen[i][j+1].winner);
						
					}
					if (bracketGen[i][j] != null && bracketGen[i][j].winner == null && bracketGen[i][j+1] != null && bracketGen[i][j+1].winner == null){
						hop++;
					}
					if(hop == 1){
						hop=0;
						matchPos++;
					}
					
					
				}
			}
		}
		
		public match[][] getBracket(){
			return bracketGen;
		}
		public team bracketWinner(){
			int po = layers-1;
			team b = bracketGen[po][0].winner;
			return b;
			
		}
		
		public void store(team t){
			
		}
		
		public void delete(team t){
			
		}
		
		public team retrieve(team t){
			return t;
		}
		public int getLayers(){
			return layers;
		}
		public int matchesPerLayer(int a){
			int math=0;
			
				math = math + bracketGen[a].length;
			
			return math;
		}
		
		public int countTeams(){
			return teams.length;
		}
		//method to take teams[], locations[], and style to create all the matches for the tournament
		public void createBracket(){
			
		}
		// should be incorperated into progression.
		public void generateBy(int teamDrop, bracket b){
			//generate by
		}
		
		public boolean CheckReformat(int maxDropped, int teamDrop){
			return true;
		}
		
		public int getMatchTot(){
			int lcount=0;
			for(int i=0; i < layers;i++){
				lcount = lcount + bracketGen[i].length;
			}
			return lcount;
		}
		
		/*public void progMatch(){
			//team winners[] ;
			int j=0;
			int matchPos =0;
			for(int i=0;i < layers;i++){
				System.out.println("sup");
				for( j=0; bracketGen[i][j] != null && bracketGen[i][j+1] != null ; j= j+2){
					System.out.println("HEY");
					if(bracketGen[i][j] != null && bracketGen[i][j].winner != null && bracketGen[i][j+1] != null && bracketGen[i][j+1].winner != null){
						
						System.out.println("executed");
						bracketGen[i+1][matchPos] = new match( bracketGen[i][j].winner ,bracketGen[i][j+1].winner);
						//bracketGen[i+1][matchPos].setTeam1(bracketGen[i][j].winner);
						//bracketGen[i+1][matchPos].setTeam2(bracketGen[i][j+1].winner);
					}
				}
			}
		}
		*/
		
	
	}