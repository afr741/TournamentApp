/*
 * Created by   on Fri Nov 25 12:12:58 NST 2016
 */

package volleyball.views;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.beans.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.toedter.calendar.*;

import sun.util.calendar.LocalGregorianCalendar.Date;
import volleyball.common.bracket;
import volleyball.common.createMock;
import volleyball.common.divisionsBackup;
import volleyball.common.match;
import volleyball.common.player;
import volleyball.common.team;
import volleyball.common.tournament;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.JTextComponent;

/**
 * @author Ryan Ley
 * 
 * Front End GUI
 * 
 * 
 * CS 3715- Final Project
 * 
 * 
 *Structure of project is as follows:
 *
 *Constrcutors
 *goHome() method to return to main homepage relevant to that user
 *All the action listeners
 *Add all the actionListeners to the components that need them
 *
 *Initialize ALL COMPONENTS INCLUDING PANELS
 *		Starts with buttons and boxes
 *		then goes to panels and the cards
 *
 *After the cards, then the variables get declared.
 *
 *Main Method is then called if need be
 *
 *THATS ALL THE OUTLINE
 *
 *
 *
 */
public class volleyballTourn extends JFrame {
	


	//			CONSTRUCTOR
	//==========================================
	
	public volleyballTourn() {
		tournaments = createMock.getMock();
		initComponents();
		addListeners();
	}
	public volleyballTourn(tournament[] t) {
		tournaments = t;
		initComponents();		
		addListeners();
	}
	
	//RETURN TO APPROPRIATE HOMEPAGE, AND CLEARS DATA
	public void goHome(){
		nameBox.setText("");
		startDateChooser.setDate(null);
		endDateChooser.setDate(null);
		locationBox.setText("");
		regStartChooser.setDate(null);
		regEndChooser.setDate(null);

		btnSingle.setSelected(false);
		btnDivisions.setSelected(false);

		//Register New Team

		nameBox3.setText("");
		numPlayersBox.setSelectedIndex(0);
		coachBox.setText("");
	
		
		if(currentUser ==0){
			CardLayout cardLayout = (CardLayout) cards.getLayout();
			cardLayout.show(cards, "card2");
		}
		if(currentUser==1){
			CardLayout cardLayout = (CardLayout) cards.getLayout();
			cardLayout.show(cards, "card3");
		}
		if(currentUser ==2){
			CardLayout cardLayout = (CardLayout) cards.getLayout();
			cardLayout.show(cards, "card4");
		}
		if(currentUser==3){
			CardLayout cardLayout = (CardLayout) cards.getLayout();
			cardLayout.show(cards, "card5");
		} playerList.removeAll();
		playerList = new JList(listModel2);
		playerListEdit.removeAll();
		listModel2.clear();
		playerListEdit = new JList(listModel2);
		teamList.removeAll();
		teamListCoach.removeAll();
		teamList = new JList(listModel);
		listModel.clear();
		teamListCoach = new JList(listModel);

		
	}

	//======== listeners========
	
	//LOGIN, GET USER CHOICE AND THEN DISPLAY APPROPRIATE PAGE
	private void btnGoActionPerformed(ActionEvent e) {
		try{
			if(userChooseBox.getSelectedIndex()==0){
				currentUser=0;
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card2");
			}
			else if(userChooseBox.getSelectedIndex()==1){
				currentUser=1;
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card3");
			}
			else if(userChooseBox.getSelectedIndex()==2){
				currentUser=2;
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card4");
			}
			else if(userChooseBox.getSelectedIndex()==3){
				currentUser=3;
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card5");
			}
		}
		catch(Exception e3){
			JOptionPane.showMessageDialog(null, "User selection not valid","Uh oh.....",JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	//			SHOW LOGIN SCREEN
	//====================================================
	
	private void logoutActionPerformed(ActionEvent e){
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, "card1");
	}
	//			RETURN TO HOME PAGE (MANAGE PANEL)
	//===================================================================
	
	private void homeActionPerformed(ActionEvent e) {
		goHome();
	}
	
	//			RETURN TO VIEW TEAMS
	//			LINK TO CREATE NEW TOURNAMENT PAGE
	//==============================================================
	
	private void createTournamentItemActionPerformed(ActionEvent e) {
		
		minTeamsBox.setSelectedIndex(0);
		maxTeamsBox.setSelectedIndex(1);
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, "card6");
	}
	//        GENERATE BRACKET (DIVISIONS OR SINGLE ELIMINATION)
	//=============================================================
	
	private void generateBracketActionPerformed(ActionEvent e) {
		try{
			if(tournaments[tournamentList.getSelectedIndex()].getStyle()==true){
				if(tournaments[tournamentList.getSelectedIndex()].getNumDivisions()==0){
					tournaments[tournamentList.getSelectedIndex()].setNumDivisions(Integer.parseInt(JOptionPane.showInputDialog("Please enter the number of division you would like:")));
				}
			}
			//RUN THROUGH THIS IF SINGLE ELEIMINATION IS SELECTED
			if(tournaments[tournamentList.getSelectedIndex()].getStyle()==false){
				bracket Bracket = new bracket(tournaments[tournamentList.getSelectedIndex()].teams);
				
				tournaments[tournamentList.getSelectedIndex()].setActualBracket(Bracket);
				match[][] matches = Bracket.getBracket();
				tournaments[tournamentList.getSelectedIndex()].setBracket(matches);
				int k =0;
				for(int i=0;i<Bracket.getBracket().length;i++){
					for(int j =0;j<matches[i].length;j++){
						try{
							tournaments[tournamentList.getSelectedIndex()].getBracket()[i][j].setId(k);
						}
						catch(Exception e3){}
						k++;
					}
				}
			}
			if(tournaments[tournamentList.getSelectedIndex()].getStyle()==true){
				divisionsBackup divisions = new divisionsBackup(tournaments[tournamentList.getSelectedIndex()].getTeams(),tournaments[tournamentList.getSelectedIndex()].getNumDivisions());
				tournaments[tournamentList.getSelectedIndex()].setDivisions(divisions.getAllDiv());
				for(int p =0;p<divisions.getAllDiv().length;p++){
					match[][]matches = divisions.getAllDiv()[p].getBracket();
					tournaments[tournamentList.getSelectedIndex()].setBracket(matches);
					 int k =0;
					for(int i=0;i<matches.length;i++){
						for(int j =0;j<matches[i].length;j++){
							try{
								tournaments[tournamentList.getSelectedIndex()].getDivisions()[p].getBracket()[i][j].setId(k);;
							}
							catch(Exception e3){}
							k++;
						}
					}
				}
			}
			JOptionPane.showMessageDialog(null, "Bracket has been successfully generated for the "+tournaments[tournamentList.getSelectedIndex()].getName());
		}
		catch(Exception e3){
			JOptionPane.showMessageDialog(null,"Cannot generate bracket, please select a tournament from the list","Tournament Not Found",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	//     VIEW TOURNAMENT (BRACKET OR DIVISIONS)
	//===============================================
	
	private void btnViewActionPerformed(ActionEvent e) {
		try{
			columnpanel.removeAll();
			columnpanel1.removeAll();
			try{
				divNumBox.removeAll();

				if(currentUser==0){
					for(int i =1;i<=tournaments[tournamentList.getSelectedIndex()].getActualBracket().getMatchTot();i++){
			        	comboBox.addItem(i);
			        }
					match[][] m = tournaments[tournamentList.getSelectedIndex()].getBracket();
				  	int x = m[0].length;
			        int y = tournaments[tournamentList.getSelectedIndex()].getActualBracket().getLayers();
			        columnpanel.setLayout(new GridLayout(1, y, 0, 1));
			        columnpanel.setBackground(Color.gray);
			        int numMatches =1;
			        JPanel[] panels = new JPanel[y];
			        for(int i=0;i<y;i++){
			        	panels[i] =new JPanel();
			        	columnpanel.add(panels[i]);
			        	panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.PAGE_AXIS));
			        }
			        int spacing =32;
			        for(int i =0;i<y;i++){
			        	for(int j=0;j<tournaments[tournamentList.getSelectedIndex()].getBracket()[i].length;j++){
			        	
			        		if(i==0&&j==0){
			        			panels[i].add(Box.createRigidArea(new Dimension(0,5)));
			        			if(tournaments[tournamentList.getSelectedIndex()].getBracket()[0].length!=3){
			            			spacing =32+46;
			            		}
			        		}
			        		else if(j==0){
			        			panels[i].add(Box.createRigidArea(new Dimension(0,spacing)));
			        			spacing=spacing+46;
			        		}
			        		else{
			        			panels[i].add(Box.createRigidArea(new Dimension(0,28)));

			        		}
			        		JLabel teamA;
			        		JLabel teamB;
			        		JLabel matchNum = new JLabel("Match #"+numMatches);
			        		Font font = matchNum.getFont();
			        		Map attributes = font.getAttributes();
			        		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			        		matchNum.setFont(font.deriveFont(attributes));
			        		panels[i].add(matchNum);

			        		try{
			        			teamA = new JLabel(tournaments[tournamentList.getSelectedIndex()].getBracket()[i][j].getTeam1().getName());
			        		}
			        		catch(Exception e3){
			        			teamA = new JLabel("No team");
			        		}
			        		panels[i].add(teamA);
			        		
			        		numMatches++;
			        		try{
			            		teamB = new JLabel(tournaments[tournamentList.getSelectedIndex()].getBracket()[i][j].getTeam2().getName());

			        		}
			        		catch(Exception e3){
			            		teamB = new JLabel("No team");
			        		}
			        		panels[i].add(teamB);
			        	}
			    
				
		        }
		    	CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card13");
			}
			if(currentUser==1){
					for(int i =1;i<=tournaments[tournamentListCoach.getSelectedIndex()].getActualBracket().getMatchTot();i++){
			        	comboBox.addItem(i);
			        }
					match[][] m = tournaments[tournamentListCoach.getSelectedIndex()].getBracket();
				  	int x = m[0].length;
			        int y = tournaments[tournamentListCoach.getSelectedIndex()].getActualBracket().getLayers();
			        columnpanel.setLayout(new GridLayout(1, y, 0, 1));
			        columnpanel.setBackground(Color.gray);
			        int numMatches =1;
			        JPanel[] panels = new JPanel[y];
			        for(int i=0;i<y;i++){
			        	panels[i] =new JPanel();
			        	columnpanel.add(panels[i]);
			        	panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.PAGE_AXIS));
			        }
			        int spacing =32;
			        for(int i =0;i<y;i++){
			        	for(int j=0;j<tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[i].length;j++){
			        	
			        		if(i==0&&j==0){
			        			panels[i].add(Box.createRigidArea(new Dimension(0,5)));
			        			if(tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[0].length!=3){
			            			spacing =32+46;
			            		}
			        		}
			        		else if(j==0){
			        			panels[i].add(Box.createRigidArea(new Dimension(0,spacing)));
			        			spacing=spacing+46;
			        		}
			        		else{
			        			panels[i].add(Box.createRigidArea(new Dimension(0,28)));

			        		}
			        		JLabel teamA;
			        		JLabel teamB;
			        		JLabel matchNum = new JLabel("Match #"+numMatches);
			        		Font font = matchNum.getFont();
			        		Map attributes = font.getAttributes();
			        		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			        		matchNum.setFont(font.deriveFont(attributes));
			        		panels[i].add(matchNum);

			        		try{
			        			teamA = new JLabel(tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[i][j].getTeam1().getName());
			        		}
			        		catch(Exception e3){
			        			teamA = new JLabel("No team");
			        		}
			        		panels[i].add(teamA);
			        		
			        		numMatches++;
			        		try{
			            		teamB = new JLabel(tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[i][j].getTeam2().getName());

			        		}
			        		catch(Exception e3){
			            		teamB = new JLabel("No team");
			        		}
			        		panels[i].add(teamB);
			        	}
				}
		    	CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card13");
			}
			if(currentUser==2){
					for(int i =1;i<=tournaments[tournamentListReferee.getSelectedIndex()].getActualBracket().getMatchTot();i++){
			        	comboBox.addItem(i);
			        }
					match[][] m = tournaments[tournamentListReferee.getSelectedIndex()].getBracket();
				  	int x = m[0].length;
			        int y = tournaments[tournamentListReferee.getSelectedIndex()].getActualBracket().getLayers();
			        columnpanel.setLayout(new GridLayout(1, y, 0, 1));
			        columnpanel.setBackground(Color.gray);
			        int numMatches =1;
			        JPanel[] panels = new JPanel[y];
			        for(int i=0;i<y;i++){
			        	panels[i] =new JPanel();
			        	columnpanel.add(panels[i]);
			        	panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.PAGE_AXIS));
			        }
			        int spacing =32;
			        for(int i =0;i<y;i++){
			        	for(int j=0;j<tournaments[tournamentListReferee.getSelectedIndex()].getBracket()[i].length;j++){
			        	
			        		if(i==0&&j==0){
			        			panels[i].add(Box.createRigidArea(new Dimension(0,5)));
			        			if(tournaments[tournamentListReferee.getSelectedIndex()].getBracket()[0].length!=3){
			            			spacing =32+46;
			            		}
			        		}
			        		else if(j==0){
			        			panels[i].add(Box.createRigidArea(new Dimension(0,spacing)));
			        			spacing=spacing+46;
			        		}
			        		else{
			        			panels[i].add(Box.createRigidArea(new Dimension(0,28)));

			        		}
			        		JLabel teamA;
			        		JLabel teamB;
			        		JLabel matchNum = new JLabel("Match #"+numMatches);
			        		Font font = matchNum.getFont();
			        		Map attributes = font.getAttributes();
			        		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			        		matchNum.setFont(font.deriveFont(attributes));
			        		panels[i].add(matchNum);

			        		try{
			        			teamA = new JLabel(tournaments[tournamentListReferee.getSelectedIndex()].getBracket()[i][j].getTeam1().getName());
			        		}
			        		catch(Exception e3){
			        			teamA = new JLabel("No team");
			        		}
			        		panels[i].add(teamA);
			        		
			        		numMatches++;
			        		try{
			            		teamB = new JLabel(tournaments[tournamentListReferee.getSelectedIndex()].getBracket()[i][j].getTeam2().getName());

			        		}
			        		catch(Exception e3){
			            		teamB = new JLabel("No team");
			        		}
			        		panels[i].add(teamB);
			        	}
			        }
		    	CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card13");
			}
			if(currentUser==3){
					for(int i =1;i<=tournaments[tournamentListSpectator.getSelectedIndex()].getActualBracket().getMatchTot();i++){
			        	comboBox.addItem(i);
			        }
					match[][] m = tournaments[tournamentListSpectator.getSelectedIndex()].getBracket();
				  	int x = m[0].length;
			        int y = tournaments[tournamentListSpectator.getSelectedIndex()].getActualBracket().getLayers();
			        columnpanel.setLayout(new GridLayout(1, y, 0, 1));
			        columnpanel.setBackground(Color.gray);
			        int numMatches =1;
			        JPanel[] panels = new JPanel[y];
			        for(int i=0;i<y;i++){
			        	panels[i] =new JPanel();
			        	columnpanel.add(panels[i]);
			        	panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.PAGE_AXIS));
			        }
			        int spacing =32;
			        for(int i =0;i<y;i++){
			        	for(int j=0;j<tournaments[tournamentListSpectator.getSelectedIndex()].getBracket()[i].length;j++){
			        	
			        		if(i==0&&j==0){
			        			panels[i].add(Box.createRigidArea(new Dimension(0,5)));
			        			if(tournaments[tournamentListSpectator.getSelectedIndex()].getBracket()[0].length!=3){
			            			spacing =32+46;
			            		}
			        		}
			        		else if(j==0){
			        			panels[i].add(Box.createRigidArea(new Dimension(0,spacing)));
			        			spacing=spacing+46;
			        		}
			        		else{
			        			panels[i].add(Box.createRigidArea(new Dimension(0,28)));

			        		}
			        		JLabel teamA;
			        		JLabel teamB;
			        		JLabel matchNum = new JLabel("Match #"+numMatches);
			        		Font font = matchNum.getFont();
			        		Map attributes = font.getAttributes();
			        		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			        		matchNum.setFont(font.deriveFont(attributes));
			        		panels[i].add(matchNum);

			        		try{
			        			teamA = new JLabel(tournaments[tournamentListSpectator.getSelectedIndex()].getBracket()[i][j].getTeam1().getName());
			        		}
			        		catch(Exception e3){
			        			teamA = new JLabel("No team");
			        		}
			        		panels[i].add(teamA);
			        		
			        		numMatches++;
			        		try{
			            		teamB = new JLabel(tournaments[tournamentListSpectator.getSelectedIndex()].getBracket()[i][j].getTeam2().getName());

			        		}
			        		catch(Exception e3){
			            		teamB = new JLabel("No team");
			        		}
			        		panels[i].add(teamB);
			        	}
			        }
			        
		    	CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card13");
			}
			}
			catch(Exception e2){
				//This will run if tournament i set to divisions
				divNumBox.removeAll();
				
				columnpanel.removeAll();
				if(currentUser==0){
					for(int i=1;i<=tournaments[tournamentList.getSelectedIndex()].getDivisions().length;i++){
						divNumBox.addItem(i);
					}
						for(int i =1;i<=tournaments[tournamentList.getSelectedIndex()].getDivisions()[0].getMatchTot();i++){
				        	comboBox1.addItem(i);
				        }
						match[][] m = tournaments[tournamentList.getSelectedIndex()].getDivisions()[0].getBracket();
					  	int x = m[0].length;
				        int y = tournaments[tournamentList.getSelectedIndex()].getDivisions()[0].getLayers();
				        columnpanel1.setLayout(new GridLayout(1, y, 0, 1));
				        columnpanel1.setBackground(Color.gray);
				        int numMatches =1;
				        JPanel[] panels = new JPanel[y];
				        for(int i=0;i<y;i++){
				        	panels[i] =new JPanel();
				        	columnpanel1.add(panels[i]);
				        	panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.PAGE_AXIS));
				        }
				        int spacing =32;
				        for(int i =0;i<y;i++){
				        	for(int j=0;j<tournaments[tournamentList.getSelectedIndex()].getDivisions()[0].getBracket()[i].length;j++){
				        	
				        		if(i==0&&j==0){
				        			panels[i].add(Box.createRigidArea(new Dimension(0,5)));
				        			if(tournaments[tournamentList.getSelectedIndex()].getDivisions()[0].getBracket()[0].length!=3){
				            			spacing =32+46;
				            		}
				        		}
				        		else if(j==0){
				        			panels[i].add(Box.createRigidArea(new Dimension(0,spacing)));
				        			spacing=spacing+46;
				        		}
				        		else{
				        			panels[i].add(Box.createRigidArea(new Dimension(0,28)));

				        		}
				        		JLabel teamA;
				        		JLabel teamB;
				        		JLabel matchNum = new JLabel("Match #"+numMatches);
				        		Font font = matchNum.getFont();
				        		Map attributes = font.getAttributes();
				        		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
				        		matchNum.setFont(font.deriveFont(attributes));
				        		panels[i].add(matchNum);

				        		try{
				        			teamA = new JLabel(tournaments[tournamentList.getSelectedIndex()].getDivisions()[0].getBracket()[i][j].getTeam1().getName());
				        		}
				        		catch(Exception e3){
				        			teamA = new JLabel("No team");
				        		}
				        		panels[i].add(teamA);
				        		
				        		numMatches++;
				        		try{
				            		teamB = new JLabel(tournaments[tournamentList.getSelectedIndex()].getDivisions()[0].getBracket()[i][j].getTeam2().getName());

				        		}
				        		catch(Exception e3){
				            		teamB = new JLabel("No team");
				        		}
				        		panels[i].add(teamB);
				        	}
				    
					
			        }
			    	CardLayout cardLayout = (CardLayout) cards.getLayout();
					cardLayout.show(cards, "card14");
					}
			}
			if(currentUser==1){
				for(int i=1;i<=tournaments[tournamentListCoach.getSelectedIndex()].getDivisions().length;i++){
					divNumBox.addItem(i);
				}
				for(int i =1;i<=tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[0].getMatchTot();i++){
		        	comboBox1.addItem(i);
		        }
				match[][] m = tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[0].getBracket();
			  	int x = m[0].length;
		        int y = tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[0].getLayers();
		        columnpanel1.setLayout(new GridLayout(1, y, 0, 1));
		        columnpanel1.setBackground(Color.gray);
		        int numMatches =1;
		        JPanel[] panels = new JPanel[y];
		        for(int i=0;i<y;i++){
		        	panels[i] =new JPanel();
		        	columnpanel1.add(panels[i]);
		        	panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.PAGE_AXIS));
		        }
		        int spacing =32;
		        for(int i =0;i<y;i++){
		        	for(int j=0;j<tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[0].getBracket()[i].length;j++){
		        	
		        		if(i==0&&j==0){
		        			panels[i].add(Box.createRigidArea(new Dimension(0,5)));
		        			if(tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[0].getBracket()[0].length!=3){
		            			spacing =32+46;
		            		}
		        		}
		        		else if(j==0){
		        			panels[i].add(Box.createRigidArea(new Dimension(0,spacing)));
		        			spacing=spacing+46;
		        		}
		        		else{
		        			panels[i].add(Box.createRigidArea(new Dimension(0,28)));

		        		}
		        		JLabel teamA;
		        		JLabel teamB;
		        		JLabel matchNum = new JLabel("Match #"+numMatches);
		        		Font font = matchNum.getFont();
		        		Map attributes = font.getAttributes();
		        		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		        		matchNum.setFont(font.deriveFont(attributes));
		        		panels[i].add(matchNum);

		        		try{
		        			teamA = new JLabel(tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[0].getBracket()[i][j].getTeam1().getName());
		        		}
		        		catch(Exception e3){
		        			teamA = new JLabel("No team");
		        		}
		        		panels[i].add(teamA);
		        		
		        		numMatches++;
		        		try{
		            		teamB = new JLabel(tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[0].getBracket()[i][j].getTeam2().getName());

		        		}
		        		catch(Exception e3){
		            		teamB = new JLabel("No team");
		        		}
		        		panels[i].add(teamB);
		        	}
			}
	    	CardLayout cardLayout = (CardLayout) cards.getLayout();
			cardLayout.show(cards, "card14");
			}
			if(currentUser==2){
				for(int i =1;i<=tournaments[tournamentListCoach.getSelectedIndex()].getActualBracket().getMatchTot();i++){
		        	comboBox1.addItem(i);
		        }
				match[][] m = tournaments[tournamentListCoach.getSelectedIndex()].getBracket();
			  	int x = m[0].length;
		        int y = tournaments[tournamentListCoach.getSelectedIndex()].getActualBracket().getLayers();
		        columnpanel1.setLayout(new GridLayout(1, y, 0, 1));
		        columnpanel1.setBackground(Color.gray);
		        int numMatches =1;
		        JPanel[] panels = new JPanel[y];
		        for(int i=0;i<y;i++){
		        	panels[i] =new JPanel();
		        	columnpanel1.add(panels[i]);
		        	panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.PAGE_AXIS));
		        }
		        int spacing =32;
		        for(int i =0;i<y;i++){
		        	for(int j=0;j<tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[i].length;j++){
		        	
		        		if(i==0&&j==0){
		        			panels[i].add(Box.createRigidArea(new Dimension(0,5)));
		        			if(tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[0].length!=3){
		            			spacing =32+46;
		            		}
		        		}
		        		else if(j==0){
		        			panels[i].add(Box.createRigidArea(new Dimension(0,spacing)));
		        			spacing=spacing+46;
		        		}
		        		else{
		        			panels[i].add(Box.createRigidArea(new Dimension(0,28)));

		        		}
		        		JLabel teamA;
		        		JLabel teamB;
		        		JLabel matchNum = new JLabel("Match #"+numMatches);
		        		Font font = matchNum.getFont();
		        		Map attributes = font.getAttributes();
		        		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		        		matchNum.setFont(font.deriveFont(attributes));
		        		panels[i].add(matchNum);

		        		try{
		        			teamA = new JLabel(tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[i][j].getTeam1().getName());
		        		}
		        		catch(Exception e3){
		        			teamA = new JLabel("No team");
		        		}
		        		panels[i].add(teamA);
		        		
		        		numMatches++;
		        		try{
		            		teamB = new JLabel(tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[i][j].getTeam2().getName());

		        		}
		        		catch(Exception e3){
		            		teamB = new JLabel("No team");
		        		}
		        		panels[i].add(teamB);
		        	}
			}
	    	CardLayout cardLayout = (CardLayout) cards.getLayout();
			cardLayout.show(cards, "card14");
			}
			if(currentUser==3){
					for(int i =1;i<=tournaments[tournamentListSpectator.getSelectedIndex()].getActualBracket().getMatchTot();i++){
			        	comboBox1.addItem(i);
			        }
					match[][] m = tournaments[tournamentListSpectator.getSelectedIndex()].getBracket();
				  	int x = m[0].length;
			        int y = tournaments[tournamentListSpectator.getSelectedIndex()].getActualBracket().getLayers();
			        columnpanel1.setLayout(new GridLayout(1, y, 0, 1));
			        columnpanel1.setBackground(Color.gray);
			        int numMatches =1;
			        JPanel[] panels = new JPanel[y];
			        for(int i=0;i<y;i++){
			        	panels[i] =new JPanel();
			        	columnpanel1.add(panels[i]);
			        	panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.PAGE_AXIS));
			        }
			        int spacing =32;
			        for(int i =0;i<y;i++){
			        	for(int j=0;j<tournaments[tournamentListSpectator.getSelectedIndex()].getBracket()[i].length;j++){
			        	
			        		if(i==0&&j==0){
			        			panels[i].add(Box.createRigidArea(new Dimension(0,5)));
			        			if(tournaments[tournamentListSpectator.getSelectedIndex()].getBracket()[0].length!=3){
			            			spacing =32+46;
			            		}
			        		}
			        		else if(j==0){
			        			panels[i].add(Box.createRigidArea(new Dimension(0,spacing)));
			        			spacing=spacing+46;
			        		}
			        		else{
			        			panels[i].add(Box.createRigidArea(new Dimension(0,28)));

			        		}
			        		JLabel teamA;
			        		JLabel teamB;
			        		JLabel matchNum = new JLabel("Match #"+numMatches);
			        		Font font = matchNum.getFont();
			        		Map attributes = font.getAttributes();
			        		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			        		matchNum.setFont(font.deriveFont(attributes));
			        		panels[i].add(matchNum);

			        		try{
			        			teamA = new JLabel(tournaments[tournamentListSpectator.getSelectedIndex()].getBracket()[i][j].getTeam1().getName());
			        		}
			        		catch(Exception e3){
			        			teamA = new JLabel("No team");
			        		}
			        		panels[i].add(teamA);
			        		
			        		numMatches++;
			        		try{
			            		teamB = new JLabel(tournaments[tournamentListSpectator.getSelectedIndex()].getBracket()[i][j].getTeam2().getName());

			        		}
			        		catch(Exception e3){
			            		teamB = new JLabel("No team");
			        		}
			        		panels[i].add(teamB);
			        	}
			        }
			        
		    	CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card14");
			}
			}
		
		catch(Exception e3){
			JOptionPane.showMessageDialog(null, "Cannot display tournament, please select a tournament from the list","No Tournament Selected",JOptionPane.INFORMATION_MESSAGE);
			
		}}
	
	//REFRESH SCROLLPANE FOR DIVISIONS
	//===========================================
	private void refreshDiv(ActionEvent e){
	
		columnpanel1.removeAll();
		int divN = divNumBox.getSelectedIndex();
		if(currentUser==0){
			for(int i=1;i<=tournaments[tournamentList.getSelectedIndex()].getDivisions().length;i++){
				divNumBox.addItem(i);
			}
				for(int i =1;i<=tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getMatchTot();i++){
		        	comboBox1.addItem(i);
		        }
				match[][] m = tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket();
			  	int x = m[0].length;
		        int y = tournaments[tournamentList.getSelectedIndex()].getDivisions()[0].getLayers();
		        columnpanel1.setLayout(new GridLayout(1, y, 0, 1));
		        columnpanel1.setBackground(Color.gray);
		        int numMatches =1;
		        JPanel[] panels = new JPanel[y];
		        for(int i=0;i<y;i++){
		        	panels[i] =new JPanel();
		        	columnpanel1.add(panels[i]);
		        	panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.PAGE_AXIS));
		        }
		        int spacing =32;
		        for(int i =0;i<y;i++){
		        	for(int j=0;j<tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[i].length;j++){
		        	
		        		if(i==0&&j==0){
		        			panels[i].add(Box.createRigidArea(new Dimension(0,5)));
		        			if(tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[0].length!=3){
		            			spacing =32+46;
		            		}
		        		}
		        		else if(j==0){
		        			panels[i].add(Box.createRigidArea(new Dimension(0,spacing)));
		        			spacing=spacing+46;
		        		}
		        		else{
		        			panels[i].add(Box.createRigidArea(new Dimension(0,28)));

		        		}
		        		JLabel teamA;
		        		JLabel teamB;
		        		JLabel matchNum = new JLabel("Match #"+numMatches);
		        		Font font = matchNum.getFont();
		        		Map attributes = font.getAttributes();
		        		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		        		matchNum.setFont(font.deriveFont(attributes));
		        		panels[i].add(matchNum);

		        		try{
		        			teamA = new JLabel(tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j].getTeam1().getName());
		        		}
		        		catch(Exception e3){
		        			teamA = new JLabel("No team");
		        		}
		        		panels[i].add(teamA);
		        		
		        		numMatches++;
		        		try{
		            		teamB = new JLabel(tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j].getTeam2().getName());

		        		}
		        		catch(Exception e3){
		            		teamB = new JLabel("No team");
		        		}
		        		panels[i].add(teamB);
		        	}
		        	divNumBox.removeAll();//clear list before it gets called again and accidentally adds too many items :(			
	        }
	    	CardLayout cardLayout = (CardLayout) cards.getLayout();
			cardLayout.show(cards, "card14");
			}
	if(currentUser==1){
		for(int i=1;i<=tournaments[tournamentListCoach.getSelectedIndex()].getDivisions().length;i++){
			divNumBox.addItem(i);
		}
		for(int i =1;i<=tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[divN].getMatchTot();i++){
        	comboBox1.addItem(i);
        }
		match[][] m = tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[divN].getBracket();
	  	int x = m[0].length;
        int y = tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[divN].getLayers();
        columnpanel1.setLayout(new GridLayout(1, y, 0, 1));
        columnpanel1.setBackground(Color.gray);
        int numMatches =1;
        JPanel[] panels = new JPanel[y];
        for(int i=0;i<y;i++){
        	panels[i] =new JPanel();
        	columnpanel1.add(panels[i]);
        	panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.PAGE_AXIS));
        }
        int spacing =32;
        for(int i =0;i<y;i++){
        	for(int j=0;j<tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[divN].getBracket()[i].length;j++){
        	
        		if(i==0&&j==0){
        			panels[i].add(Box.createRigidArea(new Dimension(0,5)));
        			if(tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[divN].getBracket()[0].length!=3){
            			spacing =32+46;
            		}
        		}
        		else if(j==0){
        			panels[i].add(Box.createRigidArea(new Dimension(0,spacing)));
        			spacing=spacing+46;
        		}
        		else{
        			panels[i].add(Box.createRigidArea(new Dimension(0,28)));

        		}
        		JLabel teamA;
        		JLabel teamB;
        		JLabel matchNum = new JLabel("Match #"+numMatches);
        		Font font = matchNum.getFont();
        		Map attributes = font.getAttributes();
        		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        		matchNum.setFont(font.deriveFont(attributes));
        		panels[i].add(matchNum);

        		try{
        			teamA = new JLabel(tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j].getTeam1().getName());
        		}
        		catch(Exception e3){
        			teamA = new JLabel("No team");
        		}
        		panels[i].add(teamA);
        		
        		numMatches++;
        		try{
            		teamB = new JLabel(tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j].getTeam2().getName());

        		}
        		catch(Exception e3){
            		teamB = new JLabel("No team");
        		}
        		panels[i].add(teamB);
        	}
        	divNumBox.removeAll();
	}
	CardLayout cardLayout = (CardLayout) cards.getLayout();
	cardLayout.show(cards, "card14");
	}
	if(currentUser==2){
		for(int i =1;i<=tournaments[tournamentListCoach.getSelectedIndex()].getActualBracket().getMatchTot();i++){
        	comboBox1.addItem(i);
        }
		match[][] m = tournaments[tournamentListCoach.getSelectedIndex()].getBracket();
	  	int x = m[0].length;
        int y = tournaments[tournamentListCoach.getSelectedIndex()].getActualBracket().getLayers();
        columnpanel1.setLayout(new GridLayout(1, y, 0, 1));
        columnpanel1.setBackground(Color.gray);
        int numMatches =1;
        JPanel[] panels = new JPanel[y];
        for(int i=0;i<y;i++){
        	panels[i] =new JPanel();
        	columnpanel1.add(panels[i]);
        	panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.PAGE_AXIS));
        }
        int spacing =32;
        for(int i =0;i<y;i++){
        	for(int j=0;j<tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[i].length;j++){
        	
        		if(i==0&&j==0){
        			panels[i].add(Box.createRigidArea(new Dimension(0,5)));
        			if(tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[0].length!=3){
            			spacing =32+46;
            		}
        		}
        		else if(j==0){
        			panels[i].add(Box.createRigidArea(new Dimension(0,spacing)));
        			spacing=spacing+46;
        		}
        		else{
        			panels[i].add(Box.createRigidArea(new Dimension(0,28)));

        		}
        		JLabel teamA;
        		JLabel teamB;
        		JLabel matchNum = new JLabel("Match #"+numMatches);
        		Font font = matchNum.getFont();
        		Map attributes = font.getAttributes();
        		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        		matchNum.setFont(font.deriveFont(attributes));
        		panels[i].add(matchNum);

        		try{
        			teamA = new JLabel(tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[i][j].getTeam1().getName());
        		}
        		catch(Exception e3){
        			teamA = new JLabel("No team");
        		}
        		panels[i].add(teamA);
        		
        		numMatches++;
        		try{
            		teamB = new JLabel(tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[i][j].getTeam2().getName());

        		}
        		catch(Exception e3){
            		teamB = new JLabel("No team");
        		}
        		panels[i].add(teamB);
        	}
	}
	CardLayout cardLayout = (CardLayout) cards.getLayout();
	cardLayout.show(cards, "card14");
	}
	if(currentUser==3){
			for(int i =1;i<=tournaments[tournamentListSpectator.getSelectedIndex()].getActualBracket().getMatchTot();i++){
	        	comboBox1.addItem(i);
	        }
			match[][] m = tournaments[tournamentListSpectator.getSelectedIndex()].getBracket();
		  	int x = m[0].length;
	        int y = tournaments[tournamentListSpectator.getSelectedIndex()].getActualBracket().getLayers();
	        columnpanel1.setLayout(new GridLayout(1, y, 0, 1));
	        columnpanel1.setBackground(Color.gray);
	        int numMatches =1;
	        JPanel[] panels = new JPanel[y];
	        for(int i=0;i<y;i++){
	        	panels[i] =new JPanel();
	        	columnpanel1.add(panels[i]);
	        	panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.PAGE_AXIS));
	        }
	        int spacing =32;
	        for(int i =0;i<y;i++){
	        	for(int j=0;j<tournaments[tournamentListSpectator.getSelectedIndex()].getBracket()[i].length;j++){
	        	
	        		if(i==0&&j==0){
	        			panels[i].add(Box.createRigidArea(new Dimension(0,5)));
	        			if(tournaments[tournamentListSpectator.getSelectedIndex()].getBracket()[divN].length!=3){
	            			spacing =32+46;
	            		}
	        		}
	        		else if(j==0){
	        			panels[i].add(Box.createRigidArea(new Dimension(0,spacing)));
	        			spacing=spacing+46;
	        		}
	        		else{
	        			panels[i].add(Box.createRigidArea(new Dimension(0,28)));

	        		}
	        		JLabel teamA;
	        		JLabel teamB;
	        		JLabel matchNum = new JLabel("Match #"+numMatches);
	        		Font font = matchNum.getFont();
	        		Map attributes = font.getAttributes();
	        		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	        		matchNum.setFont(font.deriveFont(attributes));
	        		panels[i].add(matchNum);

	        		try{
	        			teamA = new JLabel(tournaments[tournamentListSpectator.getSelectedIndex()].getBracket()[i][j].getTeam1().getName());
	        		}
	        		catch(Exception e3){
	        			teamA = new JLabel("No team");
	        		}
	        		panels[i].add(teamA);
	        		
	        		numMatches++;
	        		try{
	            		teamB = new JLabel(tournaments[tournamentListSpectator.getSelectedIndex()].getBracket()[i][j].getTeam2().getName());

	        		}
	        		catch(Exception e3){
	            		teamB = new JLabel("No team");
	        		}
	        		panels[i].add(teamB);
	        	}
	        }
	        
    	CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, "card14");
	}
	}
	//         SHOW EDIT TOURNAMENT PANEL
	//===============================================
	
	private void editTournamentActionPerformed(ActionEvent e) {
		try{
			editInt = 1;
			nameBox2.setText(tournaments[tournamentList.getSelectedIndex()].getName());			
			startDateChooser2.setDate(tournaments[tournamentList.getSelectedIndex()].getStartDate().getTime());
			endDateChooser2.setDate(tournaments[tournamentList.getSelectedIndex()].getEndDate().getTime());
			locationBox2.setText(tournaments[tournamentList.getSelectedIndex()].getlocation().getName());
			minTeamsBox2.setSelectedIndex(tournaments[tournamentList.getSelectedIndex()].getMinTeams()-1);
			maxTeamsBox2.setSelectedIndex(tournaments[tournamentList.getSelectedIndex()].getMaxTeams()-1);
	
		
			if(tournaments[tournamentList.getSelectedIndex()].getStyle()==false){
				btnSingle2.setSelected(true);
			}
			else if (tournaments[tournamentList.getSelectedIndex()].getStyle()==true){
				btnDivisions2.setSelected(true);
			}
			
			listModel.clear();
		
			for(int i=0;i<tournaments[tournamentList.getSelectedIndex()].teams.length;i++){
				if(tournaments[tournamentList.getSelectedIndex()].teams[i]!=null){
					listModel.addElement(tournaments[tournamentList.getSelectedIndex()].teams[i].getName());
				}
			}
			CardLayout cardLayout = (CardLayout) cards.getLayout();
			cardLayout.show(cards, "card10");
		}
		catch(Exception e3){			
			JOptionPane.showMessageDialog(null,"Please select a tournament from the list","Tournament Not Found",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	//			CALL AND SHOW EDIT TEAMS CARD
	//===============================================
	
	private void editTeamsActionPerformed(ActionEvent e){
		
		try{
			listModel.clear();

			for(int i=0;i<tournaments[tournamentListCoach.getSelectedIndex()].teams.length;i++){
				if(tournaments[tournamentListCoach.getSelectedIndex()].teams[i]!=null){
					listModel.addElement(tournaments[tournamentListCoach.getSelectedIndex()].teams[i].getName());
				}
			}
			if(currentUser==0){
				lblloggedInAs_4.setText("(Logged in as Organizer)");
			}
			if(currentUser==1){
				lblloggedInAs_4.setText("(Logged in as Coach)");
			}
			else{
				lblloggedInAs_4.setText("");
			}
			CardLayout cardLayout = (CardLayout) cards.getLayout();
			cardLayout.show(cards, "card12");
			
		}
		catch(Exception e3){
			JOptionPane.showMessageDialog(null,"Please select a tournament from the list","Tournament Not Selected",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	//			CALL AND SHOW EDIT TEAM PANEL
	//===========================================
	private void btnShowEditTeamPanelActionPerformed(ActionEvent e){
		try{
			selTeam=teamListCoach.getSelectedIndex();
			nameBox4.setText(tournaments[tournamentListCoach.getSelectedIndex()].teams[teamListCoach.getSelectedIndex()].getName());
			numPlayersBox_1.setSelectedIndex(tournaments[tournamentListCoach.getSelectedIndex()].teams[teamListCoach.getSelectedIndex()].getMaxPlayers()-5);
			coachBox_1.setText(tournaments[tournamentListCoach.getSelectedIndex()].teams[teamListCoach.getSelectedIndex()].getCoach());
			listModel2.clear();
			for(int i=0;i<tournaments[tournamentListCoach.getSelectedIndex()].teams[selTeam].players.length;i++){
				if(tournaments[tournamentListCoach.getSelectedIndex()].teams[selTeam].players[i]!=null){
					listModel2.addElement(tournaments[tournamentListCoach.getSelectedIndex()].teams[selTeam].players[i].getName()+", "+tournaments[tournamentListCoach.getSelectedIndex()].teams[selTeam].players[i].getFirst()+", "+tournaments[tournamentListCoach.getSelectedIndex()].teams[selTeam].players[i].getPlayerNumber());
				}
			}
			
			playerListEdit = new JList(listModel2);
			scrollPane3.setViewportView(playerListEdit);
			CardLayout cardLayout = (CardLayout) cards.getLayout();
			cardLayout.show(cards, "card11");
		}
		catch(Exception e2){
			JOptionPane.showMessageDialog(null,"Please select a tournament from the list","Tournament Not Selected",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	//         DELETE TEAM FROM TOURNAMENT
	//========================================
	
	private void btnDeleteTeamActionPerformed(ActionEvent e) {
		try{
			if(currentUser==0){
				tournaments[tournamentList.getSelectedIndex()].removeTeam(teamList.getSelectedIndex());
				listModel.remove(teamList.getSelectedIndex());
				listModel.clear();

			}
			if(currentUser==1){
				tournaments[tournamentListCoach.getSelectedIndex()].removeTeam(teamListCoach.getSelectedIndex());
				listModel.remove(teamListCoach.getSelectedIndex());
				listModel.clear();
			}
			if(currentUser==0){
				for (int i=0;i<tournaments[tournamentList.getSelectedIndex()].teams.length;i++){
					if(tournaments[tournamentList.getSelectedIndex()].teams[i]!=null){
						listModel.addElement(tournaments[tournamentList.getSelectedIndex()].teams[i].getName());
					}
				}
			}
			if(currentUser==1){
				for (int i=0;i<tournaments[tournamentListCoach.getSelectedIndex()].teams.length;i++){
					if(tournaments[tournamentListCoach.getSelectedIndex()].teams[i]!=null){
						listModel.addElement(tournaments[tournamentListCoach.getSelectedIndex()].teams[i].getName());
					}
				}
			}
		}
		
		catch(Exception e3){
			JOptionPane.showMessageDialog(null,"Please select a team from the list","Tournament Not Selected",JOptionPane.INFORMATION_MESSAGE);

		}
	}
	//      SHOW REGISTER NEW TEAM PAGE
	//===========================================
	
	private void btnRegisterNewTeamActionPerformed(ActionEvent e){
		if(currentUser==0){
			int i = tournamentList.getSelectedIndex();
			//Check if Tournament is selected
			if(i!=-1){
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card7");
			}
			else{
				JOptionPane.showMessageDialog(null,"Please select a tournament from the list","Tournament Not Found",JOptionPane.INFORMATION_MESSAGE);

			}
		}
		
		else if(currentUser==1){
			int i = tournamentListCoach.getSelectedIndex();
			//Check if Tournament is selected
			if(i!=-1){
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card7");
			}
			else{
				JOptionPane.showMessageDialog(null,"Please select a tournament from the list","Tournament Not Found",JOptionPane.INFORMATION_MESSAGE);
			}			
		}		
	
	}
	//      LINK TO EDIT MATCH PAGE
	//===========================================

	
	private void editmatchActionPerformed(ActionEvent e){
		
		int matchID = comboBox.getSelectedIndex();	
		match m = null;

		try{
			if(tournaments[tournamentList.getSelectedIndex()].getStyle()==false){
				for(int i =0;i<tournaments[tournamentList.getSelectedIndex()].getActualBracket().getLayers();i++){
					for(int j =0;j<tournaments[tournamentList.getSelectedIndex()].getBracket()[i].length;j++){
						try{
							if(matchID==tournaments[tournamentList.getSelectedIndex()].getBracket()[i][j].getId()){
								m= tournaments[tournamentList.getSelectedIndex()].getBracket()[i][j];
							}
						}
						catch(Exception e3){}	
					}
				}
			}
			else{
				int divN = divNumBox.getSelectedIndex();
				for(int i =0;i<tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getLayers();i++){
					for(int j =0;j<tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[i].length;j++){
						try{
							if(matchID==tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j].getId()){
								m= tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j];
							}
						}
						catch(Exception e3){}	
					}
				}
			}
		}
		catch(Exception e4){
			if(tournaments[tournamentListCoach.getSelectedIndex()].getStyle()==false){
				for(int i =0;i<tournaments[tournamentListCoach.getSelectedIndex()].getActualBracket().getLayers();i++){
					for(int j =0;j<tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[i].length;j++){
						try{
							if(matchID==tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[i][j].getId()){
								m= tournaments[tournamentListCoach.getSelectedIndex()].getBracket()[i][j];
							}
						}
						catch(Exception e3){}
					}
				}
			}
			else{
				int divN = divNumBox.getSelectedIndex();
				for(int i =0;i<tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[divN].getLayers();i++){
					for(int j =0;j<tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[divN].getBracket()[i].length;j++){
						try{
							if(matchID==tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j].getId()){
								m= tournaments[tournamentListCoach.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j];
							}
						}
						catch(Exception e3){}	
					}
				}
			}
		}
	
		try{
			if(currentUser==0){
				teamABox.setText(m.getTeam1().getName());
				teamBBox.setText(m.getTeam2().getName());
				try{
					winnerBox.setText(m.getWinner().getName());
					
				}
				catch(Exception e3){
					winnerBox.setText("");
				}
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card9");
			}
			
			if(currentUser==1){
				JOptionPane.showMessageDialog(null, "Sorry, coaches cannot edit matches","Insufficient Privileges",JOptionPane.INFORMATION_MESSAGE);
			}
			
			if(currentUser==2){
				teamABox.setText(m.getTeam1().getName());
				teamBBox.setText(m.getTeam2().getName());
				try{
					winnerBox.setText(m.getWinner().getName());
					
				}
				catch(Exception e3){
					winnerBox.setText("");
				}
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card9");
			}
			
			if(currentUser==3){
				JOptionPane.showMessageDialog(null, "Sorry, spectators cannot edit matches","Insufficient Privileges",JOptionPane.INFORMATION_MESSAGE);

			}
			
		}
		catch(Exception e2){
			JOptionPane.showMessageDialog(null, "Sorry, the match you have selected cannot be edited yet","Invalid Match",JOptionPane.ERROR_MESSAGE);
		}
		
		
		
	
	}
	
	//      DELETE TOURNAMENT
	//==========================================
	
	private void deleteTournamentActionPerformed(ActionEvent e){
		try{
			for (int i = tournamentList.getSelectedIndex();i<tournaments.length;i++){
				if(tournaments[i]!=null){
					tournaments[i] = tournaments[i+1];
				}
			}
			listModel3.remove(tournamentList.getSelectedIndex());
			listModel3.clear();
			for (int i=0;i<tournaments.length;i++){
				if(tournaments[i]!=null){
					listModel3.addElement(tournaments[i].getName());
				}
			}
		}
		catch(Exception e2){
			JOptionPane.showMessageDialog(null, "Please select a tournament from the list","No Tournament Selected",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	//     SAVE CHANGES TO TOURNAMENT
	//=====================================
	
	private void btnSaveActionPerformed(ActionEvent e){
		try{
			boolean verified=true;

			GregorianCalendar start = new GregorianCalendar();
			start.setTime(startDateChooser2.getDate());
			GregorianCalendar end = new GregorianCalendar();
			end.setTime(endDateChooser2.getDate()); 
			
			if(start.after(end)){
				verified=false;
				JOptionPane.showMessageDialog(null, "The start date of the tournament cannot be after the end date","Incorrect Information",JOptionPane.ERROR_MESSAGE);

			}
			
		
			int i =tournamentList.getSelectedIndex();
			if(btnSingle2.isSelected()==true){
				tournaments[tournamentList.getSelectedIndex()].setStyle(0);
			}
			if(btnDivisions2.isSelected()==true){
				tournaments[tournamentList.getSelectedIndex()].setStyle(1);
			}
			listModel3.remove(tournamentList.getSelectedIndex());
			listModel3.addElement(tournaments[i].getName());
			
			if(verified==true){
				tournaments[i].setName(nameBox2.getText());
				tournaments[i].setStartDate(start);
				tournaments[i].setEndDate(end);
				tournaments[i].setLocation(locationBox2.getText());
				tournaments[i].setMinTeams(minTeamsBox2.getSelectedIndex()+1);
				tournaments[i].setMaxTeams(maxTeamsBox2.getSelectedIndex()+1);

				
				goHome();

			}
		}
		catch(Exception e2){
			JOptionPane.showMessageDialog(null, "Sorry, you can't leave any fields blank", "Uh oh..", JOptionPane.ERROR_MESSAGE);
		}
	}
	//				SAVE MATCH
	//===========================================
	
	private void saveMatchActionPerformed(ActionEvent e){
		try{
			try{
				tournaments[tournamentList.getSelectedIndex()].getActualBracket().progMatch();

			}
			catch(Exception e2){
				tournaments[tournamentListCoach.getSelectedIndex()].getActualBracket().progMatch();

			}
			int matchID = comboBox.getSelectedIndex();
			try{
				if(tournaments[tournamentList.getSelectedIndex()].getStyle()==false){
					for(int i =0;i<tournaments[tournamentList.getSelectedIndex()].getActualBracket().getLayers();i++){
						for(int j =0;j<tournaments[tournamentList.getSelectedIndex()].getBracket()[i].length;j++){
							try{
								if(matchID==tournaments[tournamentList.getSelectedIndex()].getBracket()[i][j].getId()){
									if(set1ABox.getSelectedIndex()>0&&set1BBox.getSelectedIndex()>0){
										tournaments[tournamentList.getSelectedIndex()].getBracket()[i][j].setScore(set1ABox.getSelectedIndex(),set1BBox.getSelectedIndex(),1);
										set1ABox.setSelectedIndex(0);
										set1BBox.setSelectedIndex(0);

									}
									if(set2ABox.getSelectedIndex()>0&&set2BBox.getSelectedIndex()>0){
										tournaments[tournamentList.getSelectedIndex()].getBracket()[i][j].setScore(set2ABox.getSelectedIndex(),set2BBox.getSelectedIndex(),1);
										set2ABox.setSelectedIndex(0);
										set2BBox.setSelectedIndex(0);

									}
									if(set3ABox.getSelectedIndex()>0&&set3BBox.getSelectedIndex()>0){
										tournaments[tournamentList.getSelectedIndex()].getBracket()[i][j].setScore(set3ABox.getSelectedIndex(),set3BBox.getSelectedIndex(),1);
										set3ABox.setSelectedIndex(0);
										set3BBox.setSelectedIndex(0);

									}
									if(set4ABox.getSelectedIndex()>0&&set4BBox.getSelectedIndex()>0){
										tournaments[tournamentList.getSelectedIndex()].getBracket()[i][j].setScore(set4ABox.getSelectedIndex(),set4BBox.getSelectedIndex(),1);
										set4ABox.setSelectedIndex(0);
										set4BBox.setSelectedIndex(0);
									}
									if(set5ABox.getSelectedIndex()>0&&set5BBox.getSelectedIndex()>0){
										tournaments[tournamentList.getSelectedIndex()].getBracket()[i][j].setScore(set5ABox.getSelectedIndex(),set5BBox.getSelectedIndex(),1);	
										set5ABox.setSelectedIndex(0);
										set5BBox.setSelectedIndex(0);
									}
								}
								
								CardLayout cardLayout = (CardLayout) cards.getLayout();
								cardLayout.show(cards, "card13");
							}
							catch(Exception e3){}	
						}
					}
				}
				else{
					int divN = divNumBox.getSelectedIndex();
					for(int i =0;i<tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getLayers();i++){
						for(int j =0;j<tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[i].length;j++){
							try{
								if(matchID==tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j].getId()){
									if(set1ABox.getSelectedIndex()>0&&set1BBox.getSelectedIndex()>0){
										tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j].setScore(set1ABox.getSelectedIndex(),set1BBox.getSelectedIndex(),1);
										set1ABox.setSelectedIndex(0);
										set1BBox.setSelectedIndex(0);
									}
									if(set2ABox.getSelectedIndex()>0&&set2BBox.getSelectedIndex()>0){
										tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j].setScore(set2ABox.getSelectedIndex(),set2BBox.getSelectedIndex(),1);
										set2ABox.setSelectedIndex(0);
										set2BBox.setSelectedIndex(0);
									}
									if(set3ABox.getSelectedIndex()>0&&set3BBox.getSelectedIndex()>0){
										tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j].setScore(set3ABox.getSelectedIndex(),set3BBox.getSelectedIndex(),1);
										set3ABox.setSelectedIndex(0);
										set3BBox.setSelectedIndex(0);

									}
									if(set4ABox.getSelectedIndex()>0&&set4BBox.getSelectedIndex()>0){
										tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j].setScore(set4ABox.getSelectedIndex(),set4BBox.getSelectedIndex(),1);
										set4ABox.setSelectedIndex(0);
										set4BBox.setSelectedIndex(0);
									}
									if(set5ABox.getSelectedIndex()>0&&set5BBox.getSelectedIndex()>0){
										tournaments[tournamentList.getSelectedIndex()].getDivisions()[divN].getBracket()[i][j].setScore(set5ABox.getSelectedIndex(),set5BBox.getSelectedIndex(),1);	
										set5ABox.setSelectedIndex(0);
										set5BBox.setSelectedIndex(0);
									}
								}
								CardLayout cardLayout = (CardLayout) cards.getLayout();
								cardLayout.show(cards, "card14");
							}
							catch(Exception e3){}	
						}
					}
				}
	
		}
			catch(Exception e2){
				
			}
		}
		catch(Exception e3){
			
		}		
		 
		
	}
	//              CREATE TOURNAMENT FROM FIELDS
	//=======================================================
	
	private void btnCreateTournamentActionPerformed(ActionEvent e){
		try{
			boolean verified=true;
			boolean found =false;
			int spot=0;
			int i=0;
			while(found==false){
				if(tournaments[i]==null){
					spot=i;
					found = true;
				}
				i++;
			}
			GregorianCalendar start = new GregorianCalendar();
			start.setTime(startDateChooser.getDate());
			GregorianCalendar end = new GregorianCalendar();
			end.setTime(endDateChooser.getDate());
			if(start.after(end)){
				verified=false;
				JOptionPane.showMessageDialog(null, "The start date of the tournament cannot be after the end date","Incorrect Information",JOptionPane.ERROR_MESSAGE);
			}	
		
			GregorianCalendar deadline = new GregorianCalendar();
			deadline.setTime(regEndChooser.getDate());
			
			if(deadline.after(start)){
				verified=false;
				JOptionPane.showMessageDialog(null, "The registration deadline of the tournament cannot be after the start date","Incorrect Information",JOptionPane.ERROR_MESSAGE);
			}
			if(minTeamsBox.getSelectedIndex()+1>(maxTeamsBox.getSelectedIndex()+1)){
				verified=false;
				JOptionPane.showMessageDialog(null, "The minimum number of teams cannot be greater than the maximum","Incorrect Information",JOptionPane.ERROR_MESSAGE);

			}
			//Revert back to correct screen
			if(verified==true){
				tournaments[spot] = new tournament(nameBox.getText(),start,end);
				tournaments[spot].setLocation(locationBox.getText());
				tournaments[spot].setMinTeams(minTeamsBox.getSelectedIndex()+1);
				tournaments[spot].setMaxTeams(maxTeamsBox.getSelectedIndex()+1);
				listModel3.addElement(tournaments[spot].getName());
				tournaments[spot].setDeadline(deadline);

				if(btnSingle.isSelected()){
					tournaments[spot].setStyle(0);
					btnSingle.setSelected(false);
				}
				else if(btnDivisions.isSelected()){
					tournaments[spot].setStyle(1);
					btnDivisions.setSelected(false);
				}
				startDateChooser.setDate(null);
				endDateChooser.setDate(null);
				regEndChooser.setDate(null);
				regStartChooser.setDate(null);
				nameBox.setText("");
				locationBox.setText("");
				minTeamsBox.setSelectedIndex(0);
				maxTeamsBox.setSelectedIndex(0);
				goHome();

			}
		}
		catch(Exception e1){
			JOptionPane.showMessageDialog(null, "Sorry, Please Enter all the required information", "Uh oh..", JOptionPane.ERROR_MESSAGE);
		}
	}
	//				GO BACK TO VIEW TEAMS PANEL
	//==================================================
	private void backToViewTeamsPanelActionPerformed(ActionEvent e){
		playerList = new JList(listModel2);
		playerListEdit.removeAll();
		playerListEdit = new JList(listModel2);
		teamList.removeAll();
		teamListCoach.removeAll();
		teamList = new JList(listModel);
		//teamListCoach = new JList(listModel);
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, "card12");
	}
	
	//                 DELETE PLAYER FROM TEAM
	//================================================
	
	private void deletePlayerActionPerformed(ActionEvent e){
		if(currentUser==0){
			try{
				player p = tournaments[tournamentList.getSelectedIndex()].teams[teamList.getSelectedIndex()].players[playerList.getSelectedIndex()];
				for (int i = playerList.getSelectedIndex();i<tournaments[tournamentList.getSelectedIndex()].teams[teamList.getSelectedIndex()].players.length;i++){
					if(tournaments[tournamentList.getSelectedIndex()].teams[teamList.getSelectedIndex()].players[i]!=null){
						tournaments[tournamentList.getSelectedIndex()].teams[teamList.getSelectedIndex()].players[i] = tournaments[tournamentList.getSelectedIndex()].teams[teamList.getSelectedIndex()].players[i+1];
					}
				}
				listModel2.remove(playerList.getSelectedIndex());
			}
			catch(Exception e2){
				player p = players[playerList.getSelectedIndex()];
				for (int i = playerList.getSelectedIndex();i<players.length;i++){
					if(players[i]!=null){
						players[i] = tournaments[tournamentList.getSelectedIndex()].teams[teamList.getSelectedIndex()].players[i+1];
					}
				}
				listModel2.remove(playerList.getSelectedIndex());
			}
			
		}
		if(currentUser==1){
						
			player p = tournaments[tournamentListCoach.getSelectedIndex()].teams[teamListCoach.getSelectedIndex()].players[playerListEdit.getSelectedIndex()];
		
			for (int i = playerListEdit.getSelectedIndex();i<tournaments[tournamentListCoach.getSelectedIndex()].teams[teamListCoach.getSelectedIndex()].players.length;i++){
				if(tournaments[tournamentListCoach.getSelectedIndex()].teams[teamListCoach.getSelectedIndex()].players[i]!=null){
					tournaments[tournamentListCoach.getSelectedIndex()].teams[teamListCoach.getSelectedIndex()].players[i] = tournaments[tournamentListCoach.getSelectedIndex()].teams[teamListCoach.getSelectedIndex()].players[i+1];
				}
			}
			listModel2.remove(playerListEdit.getSelectedIndex());
		}
	
	}
	
	//			SAVE TOURNAMENTS ARRAY
	//===========================================
	
	private void save(ActionEvent e) throws FileNotFoundException, IOException{
		 ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tournaments.ser"));
	        out.writeObject(tournaments);
	        out.flush();
	        out.close();
	}
	//         REGISTER TEAM FOR TOURNAMENT
	//=============================================
	
	private void registerTeamActionPerformed(ActionEvent e){
		team Team= new team(nameBox3.getText());
		Team.setCoach(coachBox.getText());
		Team.setPlayers(numPlayersBox.getSelectedIndex()+5);
		int count =0;
		for(int i = 0;i<players.length;i++){
			if(players[i]!=null){
				count++;
			}
		}
	
		if(count<=Team.getMaxPlayers()&&Team.getMaxPlayers()>=6){
			Team.setPlayersArray(players);
			for(int i=0;i<players.length;i++){
				players[i] = null;
			}
			listModel.addElement(Team.getName());
			boolean found =false;
			int spot=0;
			int i=0;
			while(found==false){
				try{
					if(tournaments[tournamentList.getSelectedIndex()].teams[i]==null){
						spot=i;
						found = true;
						tournaments[tournamentList.getSelectedIndex()].teams[spot]= Team;
					}
					i++;
					

				}
				catch(Exception e2){
					if(tournaments[tournamentListCoach.getSelectedIndex()].teams[i]==null){
						spot=i;
						found = true;
						tournaments[tournamentListCoach.getSelectedIndex()].teams[spot]= Team;
					}
					i++;
				}
			}
			try{
				JOptionPane.showMessageDialog(null, "Thank you Registering the "+nameBox3.getText()+" in the "+tournaments[tournamentList.getSelectedIndex()].getName());

			}
			catch(Exception e3){
				JOptionPane.showMessageDialog(null, "Thank you Registering the "+nameBox3.getText()+" in the "+tournaments[tournamentListCoach.getSelectedIndex()].getName());

			}
			
			if(editInt==0){
				goHome();

			}
			else{
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card10");
			}
			nameBox3.setText("");
			coachBox.setText("");
			numPlayersBox.setSelectedIndex(0);
			listModel2.clear();
			
		}
		else if(Team.getMaxPlayers()<6){
			JOptionPane.showMessageDialog(null,"Your team doesn't have enough players","Too many players",JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(null,"Your team has too many players","Too many players",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	//			ADD PLAYER TO TEAM
	//=================================
	
	private void addPlayerActionPerformed(ActionEvent E){
		listModel.clear();
		addPlayerDialog dialog = new addPlayerDialog();
		player Player= dialog.showDialoag();
		boolean found = false;
		int spot =0;
		int i = 0;
		if(currentUser==0){
				while(found==false){
					try{
						if(tournaments[tournamentList.getSelectedIndex()].teams[teamList.getSelectedIndex()].players[i]==null){
							spot=i;
							found=true;
						}
					}
					catch(Exception e2){
						if(players[i]==null){
							spot=i;
							found=true;
						}
					}
					if(found!=true){
						i++;
					}
					
				}
				try{
					tournaments[tournamentList.getSelectedIndex()].teams[teamList.getSelectedIndex()].players[spot] = Player;
				}
				catch(Exception e2){
					players[i] = Player;
				}
				
				listModel2.addElement(Player.getName()+", "+Player.getFirst()+"   "+Player.getPlayerNumber());
				playerList = new JList(listModel2);
				scrollPane2.setViewportView(playerList);
			
		}
			
		if(currentUser==1){
				while(found==false){
					try{
						if(tournaments[tournamentListCoach.getSelectedIndex()].teams[teamListCoach.getSelectedIndex()].players[i]==null){
							spot=i;
							found=true;
						}
					}
					catch(Exception e2){
						if(players[i]==null){
							spot=i;
							found=true;
						}
					}
					if(found!=true){
						i++;
					}
					
				}
				try{
					tournaments[tournamentListCoach.getSelectedIndex()].teams[teamListCoach.getSelectedIndex()].players[spot] = Player;
				}
				catch(Exception e2){
					players[i] = Player;
				}
				
				listModel2.addElement(Player.getName()+", "+Player.getFirst()+"   "+Player.getPlayerNumber());
				playerListEdit = new JList(listModel2);
				scrollPane3.setViewportView(playerListEdit);
			
		}		
	}
	//			SAVE CHANGES TO SELECTED TEAM
	//==========================================================
	
	private void saveTeamActionPerformed(ActionEvent e){
		try{
			if(currentUser==0){
				if(tournaments[tournamentList.getSelectedIndex()].teams[teamList.getSelectedIndex()].getPlayerCount()<tournaments[tournamentList.getSelectedIndex()].teams[teamList.getSelectedIndex()].getMaxPlayers()){
				}
				tournaments[tournamentList.getSelectedIndex()].teams[teamList.getSelectedIndex()].setName(nameBox4.getText());
				tournaments[tournamentList.getSelectedIndex()].teams[teamList.getSelectedIndex()].setPlayers(numPlayersBox_1.getSelectedIndex()+5);
				tournaments[tournamentList.getSelectedIndex()].teams[teamList.getSelectedIndex()].setCoach(coachBox_1.getText());
				editInt=0;
				listModel2.clear();
				goHome();
			}
			if(currentUser==1){
			
				tournaments[tournamentListCoach.getSelectedIndex()].teams[selTeam].setName(nameBox4.getText());
				tournaments[tournamentListCoach.getSelectedIndex()].teams[selTeam].setPlayers(numPlayersBox_1.getSelectedIndex()+5);
				tournaments[tournamentListCoach.getSelectedIndex()].teams[selTeam].setCoach(coachBox_1.getText());
				editInt=0;
				listModel2.clear();
				for(int i=0;i<tournaments[tournamentListCoach.getSelectedIndex()].teams.length;i++){
					if(tournaments[tournamentListCoach.getSelectedIndex()].teams[i]!=null){
						listModel.addElement(tournaments[tournamentListCoach.getSelectedIndex()].teams[i].getName());
					}
				}
				goHome();
			}
			
		}
		catch(Exception e2){
		
		}
	}
	//		BACK TO BRACKET VIEW
	//==========================================
	private void backToBracket(ActionEvent e){
		set1ABox.setSelectedIndex(0);
		set1BBox.setSelectedIndex(0);
		set2ABox.setSelectedIndex(0);
		set2BBox.setSelectedIndex(0);
		set3ABox.setSelectedIndex(0);
		set3BBox.setSelectedIndex(0);
		set4ABox.setSelectedIndex(0);
		set4BBox.setSelectedIndex(0);
		set5ABox.setSelectedIndex(0);
		set5BBox.setSelectedIndex(0);
		if(currentUser==0){
			if(tournaments[tournamentList.getSelectedIndex()].getStyle()==false){
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card13");
			}
			else{
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card14");
			}
		}
		if(currentUser==1){
			if(tournaments[tournamentListCoach.getSelectedIndex()].getStyle()==false){
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card13");
			}
			else{
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card14");
			}
		}
		if(currentUser==2){
			if(tournaments[tournamentListReferee.getSelectedIndex()].getStyle()==false){
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card13");
			}
			else{
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card14");
			}
		}
		if(currentUser==3){
			if(tournaments[tournamentListSpectator.getSelectedIndex()].getStyle()==false){
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card13");
			}
			else{
				CardLayout cardLayout = (CardLayout) cards.getLayout();
				cardLayout.show(cards, "card14");
			}
		}
		
	}
	//		QUIT PROGRAM AND SAVE
	//===================================
	
	private void quit(ActionEvent e){
		try {
			save(e);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.exit(0);
	}
	
	//		SHOW ABOUT US DIALOG
	//==============================================
	private void aboutUS(ActionEvent e){
		AboutDialog about = new AboutDialog();
		about.showDialog();
	}
	
	//    ADD ALL ACTIONLISTENERS 
	//=============================================
	
	
	public void addListeners(){	
		btnBack.addActionListener(e -> homeActionPerformed(e));
		btnBack2.addActionListener(e -> homeActionPerformed(e));
		btnBack3.addActionListener(e -> homeActionPerformed(e));
		btnSave.addActionListener(e -> btnSaveActionPerformed(e));
		btnGo.addActionListener(e -> btnGoActionPerformed(e));
		btnAddTeam.addActionListener(e -> btnRegisterNewTeamActionPerformed(e));
		btnDeleteTeam.addActionListener(e -> btnDeleteTeamActionPerformed(e));
		btnView_3.addActionListener(e->btnViewActionPerformed(e));
		btnCreate.addActionListener(e-> btnCreateTournamentActionPerformed(e));
		btnDeletePlayer.addActionListener(e-> deletePlayerActionPerformed(e));
		btnRegister.addActionListener(e-> registerTeamActionPerformed(e));
		btnAddPlayer.addActionListener(e->addPlayerActionPerformed(e));		
		btnAddPlayer_1.addActionListener(e->addPlayerActionPerformed(e));		
		logoutItem.addActionListener(e->logoutActionPerformed(e));
		btnEditTeams.addActionListener(e->editTeamsActionPerformed(e));
		btnCreateNew.addActionListener(e->createTournamentItemActionPerformed(e));
		btnRegisterNewTeam.addActionListener(e->btnRegisterNewTeamActionPerformed(e));
		btnRegisterNewTeam_1.addActionListener(e->btnRegisterNewTeamActionPerformed(e));
		btnEditTournament.addActionListener(e->editTournamentActionPerformed(e));
		btnDeleteTournament.addActionListener(e->deleteTournamentActionPerformed(e));
		btnGenerate_1.addActionListener(e->generateBracketActionPerformed(e));
		btnView.addActionListener(e->btnViewActionPerformed(e));
		btnView_1.addActionListener(e->btnViewActionPerformed(e));
		btnView_2.addActionListener(e->btnViewActionPerformed(e));
		btnView_3.addActionListener(e->btnViewActionPerformed(e));
		btnSaveMatch.addActionListener(e->saveMatchActionPerformed(e));
		btnBack5.addActionListener(e->backToViewTeamsPanelActionPerformed(e));
		btnBack4.addActionListener(e -> homeActionPerformed(e));
		btnDeleteTeam1.addActionListener(e -> btnDeleteTeamActionPerformed(e));
		btnEditTeam1_1.addActionListener(e->btnShowEditTeamPanelActionPerformed(e));
		btnDeletePlayer_1.addActionListener(e-> deletePlayerActionPerformed(e));
		btnSaveTeam.addActionListener(e->saveTeamActionPerformed(e));
		btnBack6.addActionListener(e->homeActionPerformed(e));
		btnEditMatch.addActionListener(e->editmatchActionPerformed(e));
		btnBack7.addActionListener(e->homeActionPerformed(e));
		btnEditMatch1.addActionListener(e->editmatchActionPerformed(e));
		btnGoDiv.addActionListener(e->refreshDiv(e));
		btnBack8.addActionListener(e->backToBracket(e));
		
		
		//SAVE TOURNAMENTS TO FILE
		saveItem.addActionListener(e->{
			try {
				save(e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		quitItem.addActionListener(e->quit(e));
		aboutItem.addActionListener(e->aboutUS(e));//SHOW ABOUT DIALOG
	}
	
	//I			INTIALIZE PRETTY MUCH EVERYTHING
	//================================================
	
	
	private void initComponents() {
		
		//INITIALIZE COMPONENTS
		//============================================
		
		DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
		menuBar1 = new JMenuBar();
		fileMenu = new JMenu();
		saveItem = new JMenuItem();
		logoutItem = new JMenuItem();
		quitItem = new JMenuItem();
		aboutMenu = new JMenu();
		aboutItem = new JMenuItem();
		cards = new JPanel();
		loginScreen = new JPanel();
		lblLogin = new JLabel();
		userChooseBox = new JComboBox();
		lblLogo = compFactory.createLabel("");
		btnGo = new JButton();
		manageScreen = new JPanel();
		coachScreen = new JPanel();
		spectatorScreen = new JPanel();
		refScreen = new JPanel();
		createTournamentPanel = new JPanel();
		editTeamPanel = new JPanel();
		titleCreate = compFactory.createTitle("Create New Tournament");
		lblName = new JLabel();
		nameBox = new JTextField();
		lblStartDate = new JLabel();
		lblEndDate = new JLabel();
		startDateChooser = new JDateChooser();
		endDateChooser = new JDateChooser();
		lblLocation = new JLabel();
		locationBox = new JTextField();
		lblMinTeams = new JLabel();
		lblMaxTeams = new JLabel();
		minTeamsBox = new JComboBox();
		maxTeamsBox = new JComboBox();
		btnSingle = new JRadioButton();
		btnDivisions = new JRadioButton();
		btnCreate = new JButton();
		btnBack = new JButton();
		registerTeamPanel = new JPanel();
		titleEdit2 = compFactory.createTitle("Register New Team");
		lblTeamName = new JLabel();
		nameBox3 = new JTextField();
	
		lblNumPlayers = new JLabel();
		lblCoachName = new JLabel();
		coachBox = new JTextField();
		scrollPane2 = new JScrollPane();
		scrollPane3 = new JScrollPane();

		fldPlayers = new JTextArea();
		lblPlayers = new JLabel();
		btnAddPlayer = new JButton();
		btnDeletePlayer = new JButton();
		btnRegister = new JButton();
		btnBack3 = new JButton();
		bracketViewPanel = new JPanel();
		editMatchPanel = new JPanel();
		titleEditMatch = compFactory.createTitle("Edit Match");
		lblTeamA = new JLabel();
		titleEdit3 = compFactory.createTitle("Edit cbx");
		teamABox = new JTextField();
		lblTeamB = new JLabel();
		teamBBox = new JTextField();
		lblScore = new JLabel();
		lblTeamA2 = new JLabel();
		lblTeamB2 = new JLabel();
		winnerBox = new JTextField();
		lblWinner = new JLabel();
		lblSet = new JLabel();
		label9 = new JLabel();
		minTeamsBox2 = new JComboBox();

		set1ABox = new JComboBox();
		
		//FOR LOOPS FOR POPULATING BOXES
		for(int i=0;i<31;i++){
			set1ABox.addItem(i);
		}
		set1BBox = new JComboBox();
		for(int i=0;i<31;i++){
			set1BBox.addItem(i);
		}
		label13 = new JLabel();
		set2ABox = new JComboBox();
		for(int i=0;i<31;i++){
			set2ABox.addItem(i);
		}
		set2BBox = new JComboBox();
		for(int i=0;i<31;i++){
			set2BBox.addItem(i);
		}
		label1 = new JLabel();
		set3ABox = new JComboBox();
		for(int i=0;i<31;i++){
			set3ABox.addItem(i);
		}
		set3BBox = new JComboBox();
		for(int i=0;i<31;i++){
			set3BBox.addItem(i);
		}
		label2 = new JLabel();
		set4ABox = new JComboBox();
		for(int i=0;i<31;i++){
			set4ABox.addItem(i);
		}
		set4BBox = new JComboBox();
		for(int i=0;i<31;i++){
			set4BBox.addItem(i);
		}
		label3 = new JLabel();
		set5ABox = new JComboBox();
		for(int i=0;i<31;i++){
			set5ABox.addItem(i);
		}
		set5BBox = new JComboBox();
		for(int i=0;i<31;i++){
			set5BBox.addItem(i);
		}
		numPlayersBox = new JComboBox();
		for (int i=6;i<=30;i++){
			numPlayersBox.addItem(i);
		}
		for(int i=1;i<=20;i++){
			minTeamsBox2.addItem(i);;
		}
		maxTeamsBox2 = new JComboBox();
		for(int i=1;i<=20;i++){
			maxTeamsBox2.addItem(i);;
		}
		numPlayersBox_1 = new JComboBox();
		for (int i=6;i<=30;i++){
			numPlayersBox_1.addItem(i);
		}
		btnUpdate = new JButton();
		btnSaveMatch = new JButton();
		btnBack8 = new JButton();
		editTournamentPanel = new JPanel();
		titleEdit = compFactory.createTitle("Edit Tournament");
		lblName2 = new JLabel();
		nameBox2 = new JTextField();
		lblStartDate2 = new JLabel();
		lblEndDate2 = new JLabel();
		startDateChooser2 = new JDateChooser();
		endDateChooser2 = new JDateChooser();
		lblLocation2 = new JLabel();
		locationBox2 = new JTextField();
		lblMinTeams2 = new JLabel();
		lblMaxTeams2 = new JLabel();
	
		btnSingle2 = new JRadioButton();
		btnDivisions2 = new JRadioButton();
		btnSave = new JButton();
		btnBack2 = new JButton();
		btnAddTeam = new JButton();
		btnDeleteTeam = new JButton();
		scrollPane1 = new JScrollPane();
		teamList = new JList();
		regStartChooser= new JDateChooser();
		regEndChooser= new JDateChooser();
		lblRegStart = new JLabel();
		lblRegEnd = new JLabel();;
		JList playerList = new JList();
		btnGenerate = new JButton();
		lblTeamName_1=new JLabel();
		lblNumPlayers_1=new JLabel();
		lblCoachName_1=new JLabel();
		lblPlayers_1=new JLabel();
		btnAddPlayer_1=new JButton();
		btnDeletePlayer_1= new JButton();
		btnSaveTeam = new JButton();
		btnBack5 = new JButton();
		nameBox4 = new JTextField();

		coachBox_1 = new JTextField();
		lblRegisteredTeams= new JLabel();;
		scrollPanel4 = new JScrollPane();;
		teamListCoach= new JList();
		viewTeamsPanel =new JPanel();
		btnEditTeam1=new JButton();
		viewTeamsPanel=new JPanel();
		players = new player[20];
		editInt =0;
		showSingle= new JPanel();
		showDivisions = new JPanel();
		JList playerListEdit = new JList();

		
		
		
		//			SET FRAME UP
		//============================f================
		setTitle("Volleyball Tournament Manager");
		setIconImage(new ImageIcon(getClass().getResource("/volleyball/resources/volleyball.png")).getImage());
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new CardLayout());

		//======== menuBar1 ========
		{

			//======== fileMenu ========
			{
				fileMenu.setText("File");

				//---- saveItem ----
				saveItem.setText("Save");
				fileMenu.add(saveItem);

				//---- logoutItem ----
				logoutItem.setText("Logout");
				fileMenu.add(logoutItem);

				//---- quitItem ----
				quitItem.setText("Quit");
				fileMenu.add(quitItem);
			}
			menuBar1.add(fileMenu);

			//======== aboutMenu ========
			{
				aboutMenu.setText("About");

				//---- aboutItem ----
				aboutItem.setText("About Volleyball Tournament Manager");
				aboutMenu.add(aboutItem);
			}
			menuBar1.add(aboutMenu);
		}
		setJMenuBar(menuBar1);

		//======== cards ========
		{
			cards.setPreferredSize(new Dimension(565, 565));

			cards.setLayout(new CardLayout());

			//======== loginScreen ========
			{
				loginScreen.setPreferredSize(new Dimension(565, 565));

				//---- lblLogin ----
				lblLogin.setText("Please Choose User:");
				//-----userChooseBox
				userChooseBox.addItem("Organizer");
				userChooseBox.addItem("Coach");
				userChooseBox.addItem("Referee");
				userChooseBox.addItem("Spectator");
			
				//---- lblLogo ----
				lblLogo.setIcon(new ImageIcon(getClass().getResource("/volleyball/resources/logo.png")));

				//---- btnGo ----
				btnGo.setText("Go!");

				GroupLayout loginScreenLayout = new GroupLayout(loginScreen);
				loginScreen.setLayout(loginScreenLayout);
				loginScreenLayout.setHorizontalGroup(
					loginScreenLayout.createParallelGroup()
						.addGroup(loginScreenLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(loginScreenLayout.createSequentialGroup()
							.addGap(208, 208, 208)
							.addGroup(loginScreenLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addGroup(loginScreenLayout.createSequentialGroup()
									.addComponent(userChooseBox)
									.addGap(18, 18, 18)
									.addComponent(btnGo)
									.addGap(6, 6, 6))
								.addComponent(lblLogin)))
				);
				loginScreenLayout.setVerticalGroup(
					loginScreenLayout.createParallelGroup()
						.addGroup(loginScreenLayout.createSequentialGroup()
							.addGap(84, 84, 84)
							.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(lblLogin)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(loginScreenLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(userChooseBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnGo))
							.addContainerGap(143, Short.MAX_VALUE))
				);
			}
			cards.add(loginScreen, "card1");
			
			//==========manageScreen===========
			
			JLabel lblManageTournaments = DefaultComponentFactory.getInstance().createTitle("Manage Tournaments");
			lblManageTournaments.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			scrollPane = new JScrollPane();
			for(int i=0;i<tournaments.length;i++){
				if(tournaments[i] != null){
					listModel3.addElement(tournaments[i].getName());
				}
			}
			
			tournamentList = new JList(listModel3);
			scrollPane.setViewportView(tournamentList);
			
			btnCreateNew = new JButton("Create New Tournament");
			
			btnRegisterNewTeam = new JButton("Register New Team");
			
			btnEditTournament = new JButton("Edit Tournament");
			
			btnGenerate_1 = new JButton("Generate Bracket");
			
			btnView = new JButton("View Tournament");
			
			btnDeleteTournament = new JButton("Delete Tournament");
			
			JLabel lblloggedInAs = new JLabel("(Logged in as Organizer)");
			lblloggedInAs.setFont(new Font("Dialog", Font.BOLD, 10));
			
			//GROUP LAYOUTS ARE MESSY :(
			GroupLayout gl_manageScreen = new GroupLayout(manageScreen);
			gl_manageScreen.setHorizontalGroup(
				gl_manageScreen.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_manageScreen.createSequentialGroup()
						.addGroup(gl_manageScreen.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_manageScreen.createSequentialGroup()
								.addGap(46)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_manageScreen.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_manageScreen.createSequentialGroup()
										.addGap(18)
										.addGroup(gl_manageScreen.createParallelGroup(Alignment.LEADING)
											.addComponent(btnEditTournament, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
											.addComponent(btnRegisterNewTeam, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
											.addComponent(btnCreateNew, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
											.addComponent(btnDeleteTournament, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)))
									.addGroup(gl_manageScreen.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_manageScreen.createParallelGroup(Alignment.LEADING)
											.addComponent(btnView, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
											.addComponent(btnGenerate_1, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)))))
							.addGroup(gl_manageScreen.createSequentialGroup()
								.addGap(177)
								.addComponent(lblManageTournaments))
							.addGroup(gl_manageScreen.createSequentialGroup()
								.addGap(209)
								.addComponent(lblloggedInAs)))
						.addContainerGap())
			);
			gl_manageScreen.setVerticalGroup(
				gl_manageScreen.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_manageScreen.createSequentialGroup()
						.addComponent(lblManageTournaments)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblloggedInAs)
						.addGap(18)
						.addGroup(gl_manageScreen.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_manageScreen.createSequentialGroup()
								.addComponent(btnCreateNew)
								.addGap(18)
								.addComponent(btnRegisterNewTeam)
								.addGap(18)
								.addComponent(btnEditTournament)
								.addGap(18)
								.addComponent(btnDeleteTournament)
								.addGap(18)
								.addComponent(btnGenerate_1)
								.addGap(18)
								.addComponent(btnView))
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(46, Short.MAX_VALUE))
			);
			
			
			manageScreen.setLayout(gl_manageScreen);
			cards.add(manageScreen, "card2");

			//======== coachScreen ========


			JLabel lblAvailableTournaments = DefaultComponentFactory.getInstance().createTitle("Available Tournaments");
			lblAvailableTournaments.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			scrollPane_1 = new JScrollPane();
			
			tournamentListCoach = new JList(listModel3);
			
			scrollPane_1.setViewportView(tournamentListCoach);
			
			btnView_1 = new JButton("View Tournament");

			btnRegisterNewTeam_1 = new JButton("Register New Team");
			btnEditTeams = new JButton("Edit Teams");
			
			JLabel lblloggedInAs_1 = new JLabel("(Logged in as Coach)");
			lblloggedInAs_1.setFont(new Font("Dialog", Font.BOLD, 10));
			
			//GROUP LAYOUTS ARE MESSY :(

			GroupLayout coachScreenLayout = new GroupLayout(coachScreen);
			coachScreenLayout.setHorizontalGroup(
				coachScreenLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(coachScreenLayout.createSequentialGroup()
						.addGroup(coachScreenLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(coachScreenLayout.createSequentialGroup()
								.addGap(177)
								.addComponent(lblAvailableTournaments))
							.addGroup(coachScreenLayout.createSequentialGroup()
								.addGap(46)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addGroup(coachScreenLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(btnView_1, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnEditTeams, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnRegisterNewTeam_1, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE))))
						.addContainerGap())
					.addGroup(Alignment.TRAILING, coachScreenLayout.createSequentialGroup()
						.addContainerGap(216, Short.MAX_VALUE)
						.addComponent(lblloggedInAs_1, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
						.addGap(212))
			);
			coachScreenLayout.setVerticalGroup(
				coachScreenLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(coachScreenLayout.createSequentialGroup()
						.addComponent(lblAvailableTournaments)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblloggedInAs_1, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
						.addGap(20)
						.addGroup(coachScreenLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(coachScreenLayout.createSequentialGroup()
								.addComponent(btnRegisterNewTeam_1)
								.addGap(18)
								.addComponent(btnEditTeams)
								.addGap(18)
								.addComponent(btnView_1))
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(44, Short.MAX_VALUE))
			);
			
			
			coachScreen.setLayout(coachScreenLayout);
			cards.add(coachScreen, "card3");

			//======== refScreen ========


			lblAvailableTournaments_1 = DefaultComponentFactory.getInstance().createTitle("Available Tournaments");
			lblAvailableTournaments_1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			scrollPane_2 = new JScrollPane();
			
			tournamentListReferee = new JList(listModel3);
			scrollPane_2.setViewportView(tournamentListReferee);
			btnView_2 = new JButton("View Tournament");
			
			JLabel lblloggedInAs_2 = new JLabel("(Logged in as Referee)");
			lblloggedInAs_2.setFont(new Font("Dialog", Font.BOLD, 10));
			
			
			//GROUP LAYOUTS ARE MESSY :(

			GroupLayout refScreenLayout = new GroupLayout(refScreen);
			refScreenLayout.setHorizontalGroup(
				refScreenLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(refScreenLayout.createSequentialGroup()
						.addGroup(refScreenLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(refScreenLayout.createSequentialGroup()
								.addGap(177)
								.addComponent(lblAvailableTournaments_1))
							.addGroup(refScreenLayout.createSequentialGroup()
								.addGap(46)
								.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(btnView_2, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap())
					.addGroup(Alignment.TRAILING, refScreenLayout.createSequentialGroup()
						.addContainerGap(218, Short.MAX_VALUE)
						.addComponent(lblloggedInAs_2, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
						.addGap(210))
			);
			refScreenLayout.setVerticalGroup(
				refScreenLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(refScreenLayout.createSequentialGroup()
						.addComponent(lblAvailableTournaments_1)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblloggedInAs_2, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
						.addGap(20)
						.addGroup(refScreenLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnView_2))
						.addContainerGap(44, Short.MAX_VALUE))
			);
			
			
			refScreen.setLayout(refScreenLayout);
			cards.add(refScreen, "card4");

			//======== spectatorScreen ========
			lblAvailableTournaments_2 = DefaultComponentFactory.getInstance().createTitle("Available Tournaments");
			lblAvailableTournaments_2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			scrollPane_3 = new JScrollPane();
			
			tournamentListSpectator = new JList(listModel3);
			scrollPane_3.setViewportView(tournamentListSpectator);
			btnView_3 = new JButton("View Tournament");
			
			JLabel lblloggedInAs_3 = new JLabel("(Logged in as Spectator)");
			lblloggedInAs_3.setFont(new Font("Dialog", Font.BOLD, 10));
			
			
			//GROUP LAYOUTS ARE MESSY :(

			GroupLayout spectatorScreenLayout = new GroupLayout(spectatorScreen);
			spectatorScreenLayout.setHorizontalGroup(
				spectatorScreenLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(spectatorScreenLayout.createSequentialGroup()
						.addGroup(spectatorScreenLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(spectatorScreenLayout.createSequentialGroup()
								.addGap(177)
								.addComponent(lblAvailableTournaments_2))
							.addGroup(spectatorScreenLayout.createSequentialGroup()
								.addGap(46)
								.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(btnView_3, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap())
					.addGroup(Alignment.TRAILING, spectatorScreenLayout.createSequentialGroup()
						.addContainerGap(215, Short.MAX_VALUE)
						.addComponent(lblloggedInAs_3)
						.addGap(211))
			);
			spectatorScreenLayout.setVerticalGroup(
				spectatorScreenLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(spectatorScreenLayout.createSequentialGroup()
						.addComponent(lblAvailableTournaments_2)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblloggedInAs_3, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
						.addGap(20)
						.addGroup(spectatorScreenLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnView_3))
						.addContainerGap(44, Short.MAX_VALUE))
			);
			
			
			spectatorScreen.setLayout(spectatorScreenLayout);
			cards.add(spectatorScreen, "card5");
			//======== createTournamentPanel ========
			{

				//---- titleCreate ----
				titleCreate.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
				titleCreate.setText("Create New Tournament");

				//---- lblName ----
				lblName.setText("Tournament Name:");
				//--------minTeamsBox
				for(int i=1;i<=20;i++){
					minTeamsBox.addItem(i);
				}
				//--------maxTeamsBox
				for(int i=1;i<=20;i++){
					maxTeamsBox.addItem(i);
				}
				//---- lblStartDate ----
				lblStartDate.setText("Start Date:");

				//---- lblEndDate ----
				lblEndDate.setText("End Date:");

				//---- lblLocation ----
				lblLocation.setText("Location:");

				//---- lblMinTeams ----
				lblMinTeams.setText("Minimum Number of Teams:");

				//---- lblMaxTeams ----
				lblMaxTeams.setText("Maximum Number of Teams:");

				//---- btnSingle ----
				btnSingle.setText("Single Elimination");

				//---- btnDivisions ----
				btnDivisions.setText("Divisions");

				//---- btnCreate ----
				btnCreate.setText("Create Tournament");

				//---- btnBack ----
				btnBack.setText("Back");
				btnBack.addActionListener(e -> homeActionPerformed(e));

				//---- lblRegStart ----
				lblRegStart.setText("Registration Start Date:");

				//---- lblRegEnd ----
				lblRegEnd.setText("Registration End Date:");
				
				JLabel lblOptional = new JLabel("(Optional)");
				
				lblNewLabel = new JLabel("(Optional)");
				
				//GROUP LAYOUTS ARE MESSY :(

				GroupLayout createTournamentPanelLayout = new GroupLayout(createTournamentPanel);
				createTournamentPanelLayout.setHorizontalGroup(
					createTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(createTournamentPanelLayout.createSequentialGroup()
							.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(createTournamentPanelLayout.createSequentialGroup()
									.addGap(31)
									.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(createTournamentPanelLayout.createSequentialGroup()
											.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(createTournamentPanelLayout.createSequentialGroup()
													.addGap(31)
													.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.TRAILING)
														.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
															.addGroup(createTournamentPanelLayout.createSequentialGroup()
																.addGap(12)
																.addComponent(lblEndDate))
															.addComponent(lblStartDate))
														.addComponent(lblLocation)
														.addComponent(lblMinTeams)
														.addComponent(lblMaxTeams)))
												.addComponent(lblName, Alignment.TRAILING))
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(endDateChooser, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
												.addComponent(nameBox, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
												.addComponent(startDateChooser, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
												.addComponent(locationBox, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
												.addComponent(minTeamsBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(maxTeamsBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addGroup(createTournamentPanelLayout.createSequentialGroup()
											.addComponent(btnSingle)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnDivisions)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblNewLabel)
											.addGap(21)))
									.addGap(0, 36, Short.MAX_VALUE))
								.addGroup(createTournamentPanelLayout.createSequentialGroup()
									.addGap(0, 54, Short.MAX_VALUE)
									.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(createTournamentPanelLayout.createSequentialGroup()
											.addComponent(lblRegStart)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(regStartChooser, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
										.addComponent(btnBack)
										.addGroup(createTournamentPanelLayout.createSequentialGroup()
											.addComponent(lblRegEnd)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(regEndChooser, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btnCreate)))
							.addContainerGap())
						.addGroup(Alignment.TRAILING, createTournamentPanelLayout.createSequentialGroup()
							.addContainerGap(177, Short.MAX_VALUE)
							.addComponent(titleCreate)
							.addGap(153))
				);
				createTournamentPanelLayout.setVerticalGroup(
					createTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(createTournamentPanelLayout.createSequentialGroup()
							.addComponent(titleCreate)
							.addGap(26)
							.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(createTournamentPanelLayout.createSequentialGroup()
									.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(nameBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblName))
									.addGap(10)
									.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblStartDate)
										.addComponent(startDateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblEndDate)
										.addComponent(endDateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblLocation)
										.addComponent(locationBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblMinTeams)
										.addComponent(minTeamsBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblMaxTeams)
										.addComponent(maxTeamsBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnSingle)
										.addComponent(btnDivisions)
										.addComponent(lblNewLabel))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(regStartChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblRegStart))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(regEndChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblRegEnd))
							.addPreferredGap(ComponentPlacement.RELATED, 193, Short.MAX_VALUE)
							.addGroup(createTournamentPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnCreate)
								.addComponent(btnBack))
							.addContainerGap())
				);
				createTournamentPanel.setLayout(createTournamentPanelLayout);
			}
			cards.add(createTournamentPanel, "card6");

			//======== registerTeamPanel ========
			{

				//---- titleEdit2 ----
				titleEdit2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
				titleEdit2.setText("Register New Team");

				//---- lblTeamName ----
				lblTeamName.setText("Team Name:");

				//---- lblNumPlayers ----
				lblNumPlayers.setText("Number of Players?");

				//---- lblCoachName ----
				lblCoachName.setText("Coach:");

				//---- lblPlayers ----
				lblPlayers.setText("Players:");

				//---- btnAddPlayer ----
				btnAddPlayer.setText("Add Player");

				//---- btnDeletePlayer ----
				btnDeletePlayer.setText("Delete Player");

				//---- btnRegister ----
				btnRegister.setText("Register");

				//---- btnBack3 ----
				btnBack3.setText("Back");

				//======== scrollPane2 ========
				{
					playerList = new JList(listModel2);
					scrollPane2.setViewportView(playerList);
				}
				//GROUP LAYOUTS ARE MESSY :(

				GroupLayout registerTeamPanelLayout = new GroupLayout(registerTeamPanel);
				registerTeamPanelLayout.setHorizontalGroup(
					registerTeamPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(registerTeamPanelLayout.createSequentialGroup()
							.addGroup(registerTeamPanelLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(registerTeamPanelLayout.createSequentialGroup()
									.addGap(174)
									.addComponent(titleEdit2))
								.addGroup(registerTeamPanelLayout.createSequentialGroup()
									.addGap(118)
									.addGroup(registerTeamPanelLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblTeamName)
										.addComponent(lblNumPlayers)
										.addComponent(lblCoachName))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(registerTeamPanelLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(nameBox3, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
										.addComponent(numPlayersBox, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
										.addComponent(coachBox, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)))
								.addGroup(registerTeamPanelLayout.createSequentialGroup()
									.addGap(97)
									.addGroup(registerTeamPanelLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(registerTeamPanelLayout.createSequentialGroup()
											.addComponent(lblPlayers)
											.addGap(156))
										.addGroup(registerTeamPanelLayout.createSequentialGroup()
											.addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)))
									.addGroup(registerTeamPanelLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(registerTeamPanelLayout.createSequentialGroup()
											.addGap(0, 97, Short.MAX_VALUE)
											.addComponent(btnBack3)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnRegister))
										.addGroup(registerTeamPanelLayout.createSequentialGroup()
											.addGroup(registerTeamPanelLayout.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(btnAddPlayer, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnDeletePlayer, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
											.addGap(13, 135, Short.MAX_VALUE)))))
							.addContainerGap())
				);
				registerTeamPanelLayout.setVerticalGroup(
					registerTeamPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(registerTeamPanelLayout.createSequentialGroup()
							.addComponent(titleEdit2)
							.addGap(24)
							.addGroup(registerTeamPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTeamName)
								.addComponent(nameBox3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(registerTeamPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(numPlayersBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNumPlayers))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(registerTeamPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCoachName)
								.addComponent(coachBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(30)
							.addComponent(lblPlayers)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(registerTeamPanelLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE)
								.addGroup(registerTeamPanelLayout.createSequentialGroup()
									.addComponent(btnAddPlayer)
									.addGap(18)
									.addComponent(btnDeletePlayer)))
							.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
							.addGroup(registerTeamPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnRegister)
								.addComponent(btnBack3))
							.addContainerGap())
				);
				registerTeamPanel.setLayout(registerTeamPanelLayout);
			}
			cards.add(registerTeamPanel, "card7");
			//======== bracketViewPanel ========
			{
				//GROUP LAYOUTS ARE MESSY :(

				GroupLayout bracketViewPanelLayout = new GroupLayout(bracketViewPanel);
				bracketViewPanel.setLayout(bracketViewPanelLayout);
				bracketViewPanelLayout.setHorizontalGroup(
					bracketViewPanelLayout.createParallelGroup()
						.addGap(0, 553, Short.MAX_VALUE)
				);
				bracketViewPanelLayout.setVerticalGroup(
					bracketViewPanelLayout.createParallelGroup()
						.addGap(0, 533, Short.MAX_VALUE)
				);
			}
			cards.add(bracketViewPanel, "card8");

			//======== editMatchPanel ========
			{

				//---- titleEditMatch ----
				titleEditMatch.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
				titleEditMatch.setText("Edit Match");

				//---- lblTeamA ----
				lblTeamA.setText("Team A:");

				//---- teamABox ----
				teamABox.setEditable(false);

				//---- lblTeamB ----
				lblTeamB.setText("Team B:");

				//---- teamBBox ----
				teamBBox.setEditable(false);

				//---- lblScore ----
				lblScore.setText("Update Score:");

				//---- lblTeamA2 ----
				lblTeamA2.setText("Team A");

				//---- lblTeamB2 ----
				lblTeamB2.setText("Team B");

				//---- winnerBox ----
				winnerBox.setEditable(false);

				//---- lblWinner ----
				lblWinner.setText("Winner:");

				//---- lblSet ----
				lblSet.setText("Set");

				//---- label9 ----
				label9.setText("1");

				//---- label13 ----
				label13.setText("2");

				//---- label1 ----
				label1.setText("3");

				//---- label2 ----
				label2.setText("4");

				//---- label3 ----
				label3.setText("5");

				//---- btnUpdate ----
				btnUpdate.setText("Update");

				//---- button6 ----
				btnSaveMatch.setText("Save");

				//---- button10 ----
				btnBack8.setText("Back");
				
				//GROUP LAYOUTS ARE MESSY :(

				GroupLayout editMatchPanelLayout = new GroupLayout(editMatchPanel);
				editMatchPanelLayout.setHorizontalGroup(
					editMatchPanelLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(editMatchPanelLayout.createSequentialGroup()
							.addContainerGap(93, Short.MAX_VALUE)
							.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(editMatchPanelLayout.createSequentialGroup()
									.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(editMatchPanelLayout.createSequentialGroup()
											.addComponent(lblScore)
											.addGap(18)
											.addComponent(lblSet))
										.addComponent(label9)
										.addComponent(label13)
										.addComponent(label1)
										.addComponent(label2)
										.addComponent(label3)
										.addComponent(lblWinner))
									.addPreferredGap(ComponentPlacement.UNRELATED))
								.addGroup(editMatchPanelLayout.createSequentialGroup()
									.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblTeamB)
										.addComponent(lblTeamA))
									.addGap(35)))
							.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(teamABox, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
								.addComponent(teamBBox, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
								.addComponent(winnerBox, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
								.addGroup(editMatchPanelLayout.createSequentialGroup()
									.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(set5ABox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(set4ABox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(set3ABox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(set2ABox, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(set1ABox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblTeamA2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGap(18)
									.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(editMatchPanelLayout.createSequentialGroup()
											.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.TRAILING, false)
												.addGroup(editMatchPanelLayout.createSequentialGroup()
													.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(set2BBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
														.addComponent(set1BBox, 0, 47, Short.MAX_VALUE))
													.addPreferredGap(ComponentPlacement.RELATED))
												.addComponent(set3BBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(set4BBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(set5BBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
											.addGap(54)
											.addComponent(btnUpdate))
										.addComponent(lblTeamB2))))
							.addContainerGap(55, Short.MAX_VALUE))
						.addGroup(editMatchPanelLayout.createSequentialGroup()
							.addGap(0, 412, Short.MAX_VALUE)
							.addComponent(btnBack8)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSaveMatch)
							.addContainerGap())
						.addGroup(editMatchPanelLayout.createSequentialGroup()
							.addGap(222)
							.addComponent(titleEditMatch)
							.addContainerGap(235, Short.MAX_VALUE))
				);
				editMatchPanelLayout.setVerticalGroup(
					editMatchPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(editMatchPanelLayout.createSequentialGroup()
							.addComponent(titleEditMatch)
							.addGap(12)
							.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(teamABox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTeamA))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(teamBBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTeamB))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(winnerBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblWinner))
							.addGap(23)
							.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSet)
								.addComponent(lblTeamA2)
								.addComponent(lblScore)
								.addComponent(lblTeamB2))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label9)
								.addComponent(set1ABox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(set1BBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label13)
								.addComponent(set2ABox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(set2BBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(editMatchPanelLayout.createSequentialGroup()
									.addGap(18)
									.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(label1)
										.addComponent(set3ABox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(set3BBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(editMatchPanelLayout.createSequentialGroup()
									.addGap(2)
									.addComponent(btnUpdate)))
							.addGap(18)
							.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(label2)
								.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(set4ABox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(set4BBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(label3)
								.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(set5ABox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(set5BBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
							.addGroup(editMatchPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSaveMatch)
								.addComponent(btnBack8))
							.addContainerGap())
				);
				editMatchPanel.setLayout(editMatchPanelLayout);
			}
			cards.add(editMatchPanel, "card9");

			//======== editTournamentPanel ========
			{

				//---- titleEdit ----
				titleEdit.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
				titleEdit.setText("Edit Tournament");

				//---- lblName2 ----
				lblName2.setText("Tournament Name:");

				//---- lblStartDate2 ----
				lblStartDate2.setText("Start Date:");

				//---- lblEndDate2 ----
				lblEndDate2.setText("End Date:");

				//---- lblLocation2 ----
				lblLocation2.setText("Location:");

				//---- lblMinTeams2 ----
				lblMinTeams2.setText("Minimum Number of Teams:");

				//---- lblMaxTeams2 ----
				lblMaxTeams2.setText("Maximum Number of Teams:");

				//---- btnSingle2 ----
				btnSingle2.setText("Single Elimination");

				//---- btnDivisions2 ----
				btnDivisions2.setText("Divisions");

				//---- btnSave ----
				btnSave.setText("Save Changes");

				//---- btnBack2 ----
				btnBack2.setText("Back");

				//---- btnAddTeam ----
				btnAddTeam.setText("Add Team");
				

				//---- btnDeleteTeam ----
				btnDeleteTeam.setText("Delete Team");
				
				
			


				//======== scrollPane1 ========
				{

				
					teamList = new JList(listModel);
					scrollPane1.setViewportView(teamList);
				}
				
				//GROUP LAYOUTS ARE MESSY :(

				GroupLayout editTournamentPanelLayout = new GroupLayout(editTournamentPanel);
				editTournamentPanelLayout.setHorizontalGroup(
					editTournamentPanelLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(editTournamentPanelLayout.createSequentialGroup()
							.addGap(31)
							.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(editTournamentPanelLayout.createSequentialGroup()
									.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblName2)
										.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(editTournamentPanelLayout.createSequentialGroup()
												.addGap(12)
												.addComponent(lblEndDate2))
											.addComponent(lblStartDate2))
										.addComponent(lblLocation2)
										.addComponent(lblMinTeams2)
										.addComponent(lblMaxTeams2))
									.addGap(23)
									.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(endDateChooser2, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
										.addComponent(nameBox2, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
										.addComponent(startDateChooser2, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)
										.addComponent(locationBox2, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
										.addComponent(maxTeamsBox2, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(minTeamsBox2, 0, 52, Short.MAX_VALUE)))
								.addGroup(editTournamentPanelLayout.createSequentialGroup()
									.addComponent(btnSingle2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnDivisions2)
									.addGap(81)))
							.addContainerGap(74, Short.MAX_VALUE))
						.addGroup(editTournamentPanelLayout.createSequentialGroup()
							.addGap(0, 213, Short.MAX_VALUE)
							.addComponent(titleEdit)
							.addGap(192))
						.addGroup(editTournamentPanelLayout.createSequentialGroup()
							.addGap(111)
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
							.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(editTournamentPanelLayout.createSequentialGroup()
									.addGap(37)
									.addComponent(btnBack2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnSave, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
								.addGroup(editTournamentPanelLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnAddTeam, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
								.addGroup(editTournamentPanelLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnDeleteTeam, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)))
							.addContainerGap())
				);
				editTournamentPanelLayout.setVerticalGroup(
					editTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(editTournamentPanelLayout.createSequentialGroup()
							.addComponent(titleEdit)
							.addGap(26)
							.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblName2)
								.addComponent(nameBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblStartDate2)
								.addComponent(startDateChooser2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblEndDate2)
								.addComponent(endDateChooser2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblLocation2)
								.addComponent(locationBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblMinTeams2)
								.addComponent(minTeamsBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblMaxTeams2)
								.addComponent(maxTeamsBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSingle2)
								.addComponent(btnDivisions2))
							.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
							.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
								.addGroup(editTournamentPanelLayout.createSequentialGroup()
									.addComponent(btnAddTeam)
									.addGap(9)
									.addComponent(btnDeleteTeam)))
							.addGap(11)
							.addGroup(editTournamentPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnBack2)
								.addComponent(btnSave))
							.addGap(60))
				);
				editTournamentPanel.setLayout(editTournamentPanelLayout);
			}
			cards.add(editTournamentPanel, "card10");
			
			//		EDIT TEAMS FROM TOURNAMENT (COACH)
			//====================================
			{
				//---- titleEdit3 ----
				titleEdit3.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
				titleEdit3.setText("Edit Team");

				//---- lblTeamName_1 ----
				lblTeamName_1.setText("Team Name:");

				//---- lblNumPlayers_1 ----
				lblNumPlayers_1.setText("Number of Players?");

				//---- lblCoachName_1 ----
				lblCoachName_1.setText("Coach:");

				//---- lblPlayers_1 ----
				lblPlayers_1.setText("Players:");

				//---- btnAddPlayer_1 ----
				btnAddPlayer_1.setText("Add Player");

				//---- btnDeletePlayer_1 ----
				btnDeletePlayer_1.setText("Delete Player");

				//---- btnRegister_1 ----
				btnSaveTeam.setText("Save");

				//---- btnBack5 ----
				btnBack5.setText("Back");

				//======== scrollPane3 ========
				//
					playerListEdit = new JList(listModel2);
					scrollPane3.setViewportView(playerListEdit);
				}

			//GROUP LAYOUTS ARE MESSY :(

				GroupLayout editTeamPanelLayout = new GroupLayout(editTeamPanel);
				editTeamPanelLayout.setHorizontalGroup(
					editTeamPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(editTeamPanelLayout.createSequentialGroup()
							.addGroup(editTeamPanelLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(editTeamPanelLayout.createSequentialGroup()
									.addGap(118)
									.addGroup(editTeamPanelLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblTeamName_1)
										.addComponent(lblNumPlayers_1)
										.addComponent(lblCoachName_1))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(editTeamPanelLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(nameBox4, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
										.addComponent(coachBox_1, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
										.addComponent(numPlayersBox_1, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
								.addGroup(editTeamPanelLayout.createSequentialGroup()
									.addGap(97)
									.addGroup(editTeamPanelLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(editTeamPanelLayout.createSequentialGroup()
											.addComponent(lblPlayers_1)
											.addGap(156))
										.addGroup(editTeamPanelLayout.createSequentialGroup()
											.addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)))
									.addGroup(editTeamPanelLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(editTeamPanelLayout.createSequentialGroup()
											.addGap(0, 101, Short.MAX_VALUE)
											.addComponent(btnBack5)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnSaveTeam))
										.addGroup(editTeamPanelLayout.createSequentialGroup()
											.addGroup(editTeamPanelLayout.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(btnAddPlayer_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnDeletePlayer_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
											.addGap(13, 112, Short.MAX_VALUE))))
								.addGroup(editTeamPanelLayout.createSequentialGroup()
									.addGap(220)
									.addComponent(titleEdit3)))
							.addContainerGap())
				);
				editTeamPanelLayout.setVerticalGroup(
					editTeamPanelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(editTeamPanelLayout.createSequentialGroup()
							.addComponent(titleEdit3)
							.addGap(24)
							.addGroup(editTeamPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTeamName_1)
								.addComponent(nameBox4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(editTeamPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(numPlayersBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNumPlayers_1))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(editTeamPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCoachName_1)
								.addComponent(coachBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(30)
							.addComponent(lblPlayers_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(editTeamPanelLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane3, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE)
								.addGroup(editTeamPanelLayout.createSequentialGroup()
									.addComponent(btnAddPlayer_1)
									.addGap(18)
									.addComponent(btnDeletePlayer_1)))
							.addPreferredGap(ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
							.addGroup(editTeamPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSaveTeam)
								.addComponent(btnBack5))
							.addContainerGap())
				);
				editTeamPanel.setLayout(editTeamPanelLayout);
			}
			cards.add(editTeamPanel, "card11");
			
			//		VIEW TEAMS PANEL REGISTERED FOR TOURNAMENT
			//===============================================
			
			lblRegisteredTeams_1 = DefaultComponentFactory.getInstance().createTitle("Registered Teams");
			lblRegisteredTeams_1.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			scrollPanel4_1 = new JScrollPane();
			
			teamListCoach = new JList(listModel);
			scrollPanel4_1.setViewportView(teamListCoach);
			btnEditTeam1_1 = new JButton("Edit Team");
			
			lblloggedInAs_4 = new JLabel("(Logged in as Coach)");
			lblloggedInAs_4.setFont(new Font("Dialog", Font.BOLD, 10));
			
			btnDeleteTeam1 = new JButton("Delete Team");
			
			btnBack4 = new JButton("Back");
			
			
			//GROUP LAYOUTS ARE MESSY :(

			GroupLayout viewTeamsPanelLayout = new GroupLayout(viewTeamsPanel);
			viewTeamsPanelLayout.setHorizontalGroup(
				viewTeamsPanelLayout.createParallelGroup(Alignment.TRAILING)
					.addGroup(viewTeamsPanelLayout.createSequentialGroup()
						.addContainerGap(46, Short.MAX_VALUE)
						.addGroup(viewTeamsPanelLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(viewTeamsPanelLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(viewTeamsPanelLayout.createSequentialGroup()
									.addComponent(scrollPanel4_1, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addGroup(viewTeamsPanelLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnDeleteTeam1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(btnEditTeam1_1, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
									.addContainerGap())
								.addGroup(viewTeamsPanelLayout.createSequentialGroup()
									.addComponent(lblRegisteredTeams_1)
									.addGap(191))
								.addGroup(viewTeamsPanelLayout.createSequentialGroup()
									.addComponent(lblloggedInAs_4)
									.addGap(217)))
							.addGroup(Alignment.TRAILING, viewTeamsPanelLayout.createSequentialGroup()
								.addComponent(btnBack4)
								.addContainerGap())))
			);
			viewTeamsPanelLayout.setVerticalGroup(
				viewTeamsPanelLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(viewTeamsPanelLayout.createSequentialGroup()
						.addComponent(lblRegisteredTeams_1)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblloggedInAs_4, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
						.addGap(20)
						.addGroup(viewTeamsPanelLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(scrollPanel4_1, GroupLayout.PREFERRED_SIZE, 454, GroupLayout.PREFERRED_SIZE)
							.addGroup(viewTeamsPanelLayout.createSequentialGroup()
								.addComponent(btnEditTeam1_1)
								.addGap(18)
								.addComponent(btnDeleteTeam1)))
						.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
						.addComponent(btnBack4)
						.addContainerGap())
			);
			
			
			viewTeamsPanel.setLayout(viewTeamsPanelLayout);
			cards.add(viewTeamsPanel, "card12");
			
			
			//VIEW SINGLE ELIMINATION TOURNAMENT
			//===========================================

			
		
	        setBounds(100, 100, 565, 565);
	        showSingle.setLayout(null);
	        
	        //CONTROLS FOR MATCHES
	        //===========================================
	        comboBox = new JComboBox();
	      
	        comboBox.setBounds(72, 8, 42, 24);
	        showSingle.add(comboBox);
	        //==============================================
	        
	        
	        //		ELEMNTS
	        //============================================
	        btnEditMatch = new JButton("Edit Match");
	        btnEditMatch.setBounds(126, 8, 108, 25);
	        showSingle.add(btnEditMatch);
	        
	        JLabel lblMatchNum = new JLabel("Match #");
	        lblMatchNum.setBounds(10, 13, 70, 15);
	        showSingle.add(lblMatchNum);

	        scrollPane6 = new JScrollPane();
	        scrollPane6.setBounds(10, 40, 555, 523);
	        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	        showSingle.add(scrollPane6);

	        borderlayoutpanel = new JPanel();
	        scrollPane6.setViewportView(borderlayoutpanel);
	        borderlayoutpanel.setLayout(new BorderLayout(0, 0));

	        columnpanel = new JPanel();
	        borderlayoutpanel.add(columnpanel, BorderLayout.NORTH);
	        
	        //		GET NUMBERS FOR GRID SIZE
	        //=========================================
	        
	        
	        btnBack6 = new JButton("Back");
	        btnBack6.setBounds(246, 8, 75, 25);
	        showSingle.add(btnBack6);
	        
	        
	        //===============================================
	        
	       
			cards.add(showSingle, "card13");
			
			//		SHOW DIVISIONS PANEL
			//==================================
			

	        setBounds(100, 100, 565, 565);
	        showDivisions.setLayout(null);
	        
	        //CONTROLS FOR MATCHES
	        //===========================================
	        comboBox1 = new JComboBox();
	      
	        comboBox1.setBounds(72, 8, 42, 24);
	        showDivisions.add(comboBox1);
	        //==============================================
	        
	        
	        //		ELEMNTS
	        //============================================
	        btnEditMatch1 = new JButton("Edit Match");
	        btnEditMatch1.setBounds(126, 8, 108, 25);
	        showDivisions.add(btnEditMatch1);
	        
	        JLabel lblMatchNum1 = new JLabel("Match #");
	        lblMatchNum1.setBounds(10, 13, 70, 15);
	        showDivisions.add(lblMatchNum1);

	        scrollPane7 = new JScrollPane();
	        scrollPane7.setBounds(10, 40, 555, 523);
	        //scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	        showDivisions.add(scrollPane7);

	        borderlayoutpanel1 = new JPanel();
	        scrollPane7.setViewportView(borderlayoutpanel1);
	        borderlayoutpanel1.setLayout(new BorderLayout(0, 0));

	        columnpanel1 = new JPanel();
	        borderlayoutpanel1.add(columnpanel1, BorderLayout.NORTH);
	        
	        //		GET NUMBERS FOR GRID SIZE
	        //=========================================
	        
	        
	        btnBack7 = new JButton("Back");
	        btnBack7.setBounds(246, 8, 75, 25);
	        showDivisions.add(btnBack7);
	        
	        
	        //===============================================
	        
	       
			cards.add(showDivisions, "card14");
			
			divNumBox = new JComboBox();
			divNumBox.setBounds(445, 8, 42, 24);
			showDivisions.add(divNumBox);
			
			lblDivNum = new JLabel("Division #");
			lblDivNum.setBounds(371, 13, 70, 15);
			showDivisions.add(lblDivNum);
			
			btnGoDiv = new JButton("Go!");
			btnGoDiv.setFont(new Font("Dialog", Font.BOLD, 10));
			btnGoDiv.setBounds(499, 8, 54, 25);
			showDivisions.add(btnGoDiv);
			
			
		contentPane.add(cards, "card15");
		pack();
		setLocationRelativeTo(getOwner());

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(btnSingle2);
		buttonGroup1.add(btnDivisions2);
		//   - End of component initialization  
	}

	
	//   - Variables declaration 
	//====================================
	
	JButton btnBack6;
	JPanel columnpanel;
	JPanel borderlaoutpanel;
	JScrollPane scrollPane6;
	JPanel borderlayoutpanel;
	JComboBox comboBox;
	private JMenuBar menuBar1;
	private JMenu fileMenu;
	private JMenuItem saveItem;
	private JMenuItem logoutItem;
	private JMenuItem quitItem;
	private JMenu aboutMenu;
	private JMenuItem aboutItem;
	private JPanel cards;
	private JPanel loginScreen;
	private JLabel lblLogin;
	private JComboBox userChooseBox;
	private JLabel lblLogo;
	private JButton btnGo;
	private JPanel manageScreen;
	private JPanel coachScreen;
	private JPanel spectatorScreen;
	private JPanel refScreen;
	private JPanel createTournamentPanel;
	private JLabel titleCreate;
	private JLabel lblName;
	private JTextField nameBox;
	private JLabel lblStartDate;
	private JLabel lblEndDate;
	private JDateChooser startDateChooser;
	private JDateChooser endDateChooser;
	private JLabel lblLocation;
	private JTextField locationBox;
	private JLabel lblMinTeams;
	private JLabel lblMaxTeams;
	private JComboBox minTeamsBox;
	private JComboBox maxTeamsBox;
	private JRadioButton btnSingle;
	private JRadioButton btnDivisions;
	private JButton btnCreate;
	private JButton btnBack;
	private JPanel registerTeamPanel;
	private JPanel editTeamPanel;
	private JLabel titleEdit2;
	private JLabel lblTeamName;
	private JTextField nameBox3;
	private JComboBox numPlayersBox;
	private JLabel lblNumPlayers;
	private JLabel lblCoachName;
	private JTextField coachBox;
	private JScrollPane scrollPane2;
	private JTextArea fldPlayers;
	private JLabel lblPlayers;
	private JButton btnAddPlayer;
	private JButton btnDeletePlayer;
	private JButton btnRegister;
	private JButton btnBack3;
	private JPanel bracketViewPanel;
	private JPanel editMatchPanel;
	private JLabel titleEditMatch;
	private JLabel lblTeamA;
	private JTextField teamABox;
	private JLabel lblTeamB;
	private JTextField teamBBox;
	private JLabel lblScore;
	private JLabel lblTeamA2;
	private JLabel lblTeamB2;
	private JTextField winnerBox;
	private JLabel lblWinner;
	private JLabel lblSet;
	private JLabel label9;
	private JComboBox set1ABox;
	private JComboBox set1BBox;
	private JLabel label13;
	private JComboBox set2ABox;
	private JComboBox set2BBox;
	private JLabel label1;
	private JComboBox set3ABox;
	private JComboBox set3BBox;
	private JLabel label2;
	private JComboBox set4ABox;
	private JComboBox set4BBox;
	private JLabel label3;
	private JComboBox set5ABox;
	private JComboBox set5BBox;
	private JButton btnUpdate;
	private JButton btnSaveMatch;
	private JButton btnBack8;
	private JPanel editTournamentPanel;
	private JLabel titleEdit;
	private JLabel lblName2;
	private JTextField nameBox2;
	private JLabel lblStartDate2;
	private JLabel lblEndDate2;
	private JDateChooser startDateChooser2;
	private JDateChooser endDateChooser2;
	private JLabel lblLocation2;
	private JTextField locationBox2;
	private JLabel lblMinTeams2;
	private JLabel lblMaxTeams2;
	private JComboBox minTeamsBox2;
	private JComboBox maxTeamsBox2;
	private JRadioButton btnSingle2;
	private JRadioButton btnDivisions2;
	private JButton btnSave;
	private JButton btnBack2;
	private JButton btnAddTeam;
	private JButton btnDeleteTeam;
	private JScrollPane scrollPane1;
	private JList teamList;
	private tournament[] tournaments;
	private int selectedTournament=0;
	private DefaultListModel listModel = new DefaultListModel();
	private DefaultListModel listModel2 = new DefaultListModel();
	private DefaultListModel listModel3= new DefaultListModel();
	JScrollPane scrollPane;
	private JScrollPane scrollPane_3;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_1;
	private JDateChooser regStartChooser;
	private JDateChooser regEndChooser;
	private JLabel lblRegStart;
	private JLabel lblRegEnd;	
	JList playerList;
	JButton btnLogout;
	JList tournamentList;
	JButton btnCreateNew;
	private JButton btnRegisterNewTeam;
	private JButton btnRegisterNewTeam_1;
	JButton btnEditTournament;
	JButton btnView;
	private JButton btnView_3;
	private JButton btnView_2;
	private JButton btnView_1;

	private JButton btnDeleteTournament;
	private JLabel lblAvailableTournaments_1;
	private JLabel lblAvailableTournaments_2;
	private JButton btnGenerate;
	private JButton btnGenerate_1;
	private JButton btnEditTeams;
	private int currentUser;
	private JList tournamentListCoach;
	private JList tournamentListReferee;
	private JList tournamentListSpectator;
	JLabel lblTeamName_1;
	JLabel lblNumPlayers_1;
	JLabel lblCoachName_1;
	JLabel lblPlayers_1;
	JButton btnAddPlayer_1;
	JButton btnDeletePlayer_1;
	JButton btnSaveTeam;
	JButton btnBack5;
	private JLabel titleEdit3;
	private JTextField nameBox4;
	private JComboBox numPlayersBox_1;
	private JTextField coachBox_1;
	private JScrollPane scrollPane3;
	private JLabel lblRegisteredTeams;
	private JLabel lblRegisteredTeams_1;
	private JScrollPane scrollPanel4;
	private JScrollPane scrollPanel4_1;
	private JList teamListCoach;
	private JPanel viewTeamsPanel;
	private JButton btnEditTeam1;
	private JButton btnEditTeam1_1;
	private JLabel lblloggedInAs_4;
	private JButton btnDeleteTeam1;
	private JButton btnBack4;
	private JList playerListEdit;
	private player[] players;
	private JLabel lblNewLabel;
	private int editInt;
	private int selTeam;
	private JPanel showSingle;
	JButton btnEditMatch;
	private JPanel showDivisions;
	private JComboBox comboBox1;
	private JButton btnEditMatch1;
	private JScrollPane scrollPane7;
	private JPanel borderlayoutpanel1;
	private JPanel columnpanel1;
	private JButton btnBack7;
	private JLabel lblDivNum;
	private JComboBox divNumBox;
	private JButton btnGoDiv;

	
	
	//  MAIN METHOD TO RUN VOLLEYBALL TOURNAMENT PROGRAM
	//========================================================
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					volleyballTourn frame = new volleyballTourn();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
