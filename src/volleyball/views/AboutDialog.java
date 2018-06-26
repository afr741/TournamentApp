package volleyball.views;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import java.awt.TextArea;
import java.awt.Toolkit;

public class AboutDialog extends JDialog {

	/**
	 * @author Ryan Ley
	 * 
	 * CS3715
	 * 
	 * Final Project
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setTitle("About");
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutDialog.class.getResource("/volleyball/resources/volleyball.png")));
		setBounds(100, 100, 565, 500);
		getContentPane().setLayout(new BorderLayout());
		{
			TextArea textArea = new TextArea("Course Name: COMP 3716\n"
					+ "Date Submitted: 2 Dec, 2016\n"
					+ "Project Name: Volleyball Tournament\n\n"
					+ "Student Involved:\n\n"
					+ "-Donald Joseph Ryan\n"
					+ "-Ryan Ley\n"
					+ "-Stephen TL Pelley\n"
					+ "-Anush Rakhmatov\n"
					+ "-Caelan Scott Hunt\n"
					+ "-Nick Hamilton\n"
					+ "\n"
					+ "   This project was made with the express purpose of the creation of a\n"
					+ "program for making volleyball tournaments and running said tournaments\n"
					+ "until they reach completion.\n\n"
					+ "   It does this by first allowing the creation of a tournament if one\n"
					+ "is not already made in the program. With the creation of the tournament\n"
					+ "you will have to change the variables to what you want the tournament to\n"
					+ "have ie maximum/minimum number of teams for the tournament, division or\n"
					+ "single elimination style, dates for the start and end of the tournament\n"
					+ "and etc. This way the tournament will meet whatever needs that the\n"
					+ "tournament organizer needs of it.\n\n"
					+ "   Now that the tournament has been made it is up to the coachs of the\n"
					+ "different teams to sign up for whatever tournaments have been created.\n"
					+ "They will do this by getting onto the system and adding in all of the\n"
					+ "required information needed by the system, and then they will slot\n"
					+ "their team into what tournaments they want from the list of tournaments\n"
					+ "in the program.\n\n"
					+ "   Next the referees will be included by the organizer and the fans will\n"
					+ "be made aware by the organizer that the tournament is going ahead as long\n"
					+ "as enough teams signed up for them.\n"
					+ "   With all of the other elements dealt with the program will now set up\n"
					+ "the brackets for the tournament in accordance with how it was set up by the\n"
					+ "organizer. This will end up going one of two ways based on the style that\n"
					+ "was chosen.\n\n"
					+ "   If the single elimination was chosen the program will set up the brackets\n"
					+ "so that all of the teams are in one large group broken down and placed at\n"
					+ "random into the initial matches. Then it will continue on with the winners\n"
					+ "of these games into games between two of the winners facing off per match.\n"
					+ "This will go on until there are two left and then the winner from that will\n"
					+ "be the overall winner of the tournament.\n\n"
					+ "   The other style is divisions which means that the teams are split up at\n"
					+ "the start of the tournament into a number of groups defined by the organizer.\n"
					+ "These groups are done in a way that there is an equal number of people in\n"
					+ "eachof them. Then each division will go through like the single elimination\n"
					+ "except at the end of each division where one is left the program will gather\n"
					+ "these teams together and will make another single elimination made from\n"
					+ "them. The last team at the end of this one is the overall winner of the\n"
					+ "tournament.");
			
			textArea.setEditable(false);
			getContentPane().add(textArea, BorderLayout.CENTER);
		}
	}
	public void showDialog(){
		setVisible(true);
	}
}
