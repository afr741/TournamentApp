package volleyball.common;

import java.util.GregorianCalendar;


public class bracketTester {
	
	public static void main(String[] args){
			tournament[] t =new tournament[19];
			GregorianCalendar s = new GregorianCalendar();
			s.set(2016, 10, 25);
			GregorianCalendar e = new GregorianCalendar();
			s.set(2016, 10, 27);
			t[0] = new tournament("Viking Home Tournament",s,e);
			t[0].setMaxTeams(20);
			t[0].setMinTeams(1);
			t[0].setStyle(1);
			t[0].setLocation("Bridgewater");
			
			team Team = new team("The Panthers");
			Team.setCoach("Fred");
			Team.setPlayers(5);
			Team.setVerified(true);
			player p = new player("Ryan","D",10,22);
			Team.addPlayer(p);
			player d = new player("Pelley","S",12,23);
			Team.addPlayer(d);
			registration.addTeam(Team, t[0]);
			//N2nd Team
			//==========================
			team Team1 = new team("The Spiders");
			Team1.setCoach("Frank");
			Team1.setPlayers(5);
			Team1.setVerified(true);
			player p1 = new player("Nick","S",10,22);
			Team1.addPlayer(p1);
			player d1 = new player("Smith","J",12,23);
			Team1.addPlayer(d1);
			//System.out.println(t[0].getTeamCount());
			registration.addTeam(Team1, t[0]);
			
			//3rd team
			team Team2 = new team("The otters");
			Team2.setCoach("Frank");
			Team2.setPlayers(5);
			Team2.setVerified(true);
			player p2 = new player("Nick","S",10,22);
			Team2.addPlayer(p2);
			player d2 = new player("Smith","J",12,23);
			Team2.addPlayer(d2);
			//System.out.println(t[0].getTeamCount());
			registration.addTeam(Team2, t[0]);
			//4th team
			team Team3 = new team("The oxen");
			Team3.setCoach("Frank");
			Team3.setPlayers(5);
			Team3.setVerified(true);
			player p3 = new player("Nick","S",10,22);
			Team3.addPlayer(p3);
			player d3 = new player("Smith","J",12,23);
			Team3.addPlayer(d3);
			//System.out.println(t[0].getTeamCount());
			registration.addTeam(Team3, t[0]);
			
			//4th Team
			
			team Team4 = new team("The Cheetahs");
			Team4.setCoach("Frank");
			Team4.setPlayers(5);
			Team4.setVerified(true);
			player p4 = new player("Nick","S",10,22);
			Team4.addPlayer(p4);
			player d4 = new player("Smith","J",12,23);
			Team4.addPlayer(d4);
			//System.out.println(t[0].getTeamCount());
			registration.addTeam(Team4, t[0]);
			
			
			//TEAM 5
			
			team Team5 = new team("The Pythons");
			Team5.setCoach("Frank");
			Team5.setPlayers(5);
			Team5.setVerified(true);
			player p5 = new player("Nick","S",10,22);
			Team5.addPlayer(p5);
			player d5 = new player("Smith","J",12,23);
			Team5.addPlayer(d5);
			//System.out.println(t[0].getTeamCount());
			registration.addTeam(Team5, t[0]);
			
			//TEAM 6
			team Team6 = new team("The Cougars");
			Team6.setCoach("Frank");
			Team6.setPlayers(5);
			Team6.setVerified(true);
			player p6 = new player("Nick","S",10,22);
			Team6.addPlayer(p6);
			player d6 = new player("Smith","J",12,23);
			Team6.addPlayer(d6);
			//System.out.println(t[0].getTeamCount());
			registration.addTeam(Team6, t[0]);
			
			//team7
			team Team7 = new team("The Cats");
			Team7.setCoach("Frank");
			Team7.setPlayers(5);
			Team7.setVerified(true);
			player p7 = new player("Nick","S",10,22);
			Team7.addPlayer(p7);
			player d7 = new player("Smith","J",12,23);
			Team7.addPlayer(d7);
			//System.out.println(t[0].getTeamCount());
			registration.addTeam(Team7, t[0]);

			
			//team 8
			team Team8 = new team("The Dogs");
			Team8.setCoach("Frank");
			Team8.setPlayers(5);
			Team8.setVerified(true);
			player p8 = new player("Nick","S",10,22);
			Team.addPlayer(p8);
			player d8 = new player("Smith","J",12,23);
			Team8.addPlayer(d8);
			//System.out.println(t[0].getTeamCount());
			registration.addTeam(Team8, t[0]);
			
			/*
			team Team9 = new team("The Dogs");
			Team9.setCoach("Frank");
			Team9.setPlayers(5);
			Team9.setVerified(true);
			player p9 = new player("Nick","S",10,22);
			Team.addPlayer(p9);
			player d9 = new player("Smith","J",12,23);
			Team8.addPlayer(d9);
			//System.out.println(t[0].getTeamCount());
			registration.addTeam(Team9, t[0]);
			*/
			
			//Testting for correct array assignment
			//================================================
			
			/*
			System.out.println(t[0].getTeamCount());
			for (int i =0;i<t[0].teams.length;i++){
				try{
					System.out.println(i+" "+t[0].teams[i].getName());
				}
				catch(Exception e1){
					System.out.println(i+" Null, no name");
				}
			}
			*/
			
			//bracket b = new bracket(t[0].getTeams());
			//System.out.println("TEam L B$:"+t[0].getTeams().length);
			divisionsBackup b = new divisionsBackup(t[0].getTeams(),2);
			// b.progMatch();
			 //match[][] matches = b.getDivBracket(1).getBracket();
			
			//Code to Simulate updating scores from gui
			//===============================================
			/*
			matches[0][0].setScore(25, 22, 1);
			matches[0][0].setScore(25, 22, 1);
			matches[0][0].setScore(25, 22, 1);

			matches[0][1].setScore(25, 22, 1);
			matches[0][1].setScore(25, 22, 1);
			matches[0][1].setScore(25, 22, 1);
			
			matches[0][1].setScore(25, 22, 1);
			matches[0][1].setScore(25, 22, 1);
			matches[0][1].setScore(25, 22, 1);
			
			matches[0][2].setScore(25, 22, 1);
			matches[0][2].setScore(25, 22, 1);
			matches[0][2].setScore(25, 22, 1);
			
			matches[0][3].setScore(25, 22, 1);
			matches[0][3].setScore(25, 22, 1);
			matches[0][3].setScore(25, 22, 1);
			
			
			matches[0][4].setScore(25, 22, 1);
			matches[0][4].setScore(25, 22, 1);
			matches[0][4].setScore(25, 22, 1);
			
			*/
			//====================================================
			
			 
			 /*
			b.getDivBracket(1).progMatch();
			matches = b.getDivBracket(1).getBracket();

			for(int i=0;i<b.getDivBracket(1).getBracket().length;i++){
				for(int j =0;j<matches[i].length;j++){
					System.out.println("Column "+i);
					System.out.println("Match "+j);
					try{
						System.out.println("Team A: "+matches[i][j].getTeam1().getName());
					}
					catch(Exception e2){
						System.out.println("Team B: Not set yet");
					}
					try{
						System.out.println("Team A: "+matches[i][j].getTeam2().getName());
					}
					catch(Exception e2){
						System.out.println("Team B: Not set yet");
					}
		
					System.out.println("");


				}
			}
			*/
			System.out.println("Bracket Created, it worked!!!");
			
			//showSingleEli frame = new showSingleEli(b);
            //frame.setVisible(true);
			
	}
}
