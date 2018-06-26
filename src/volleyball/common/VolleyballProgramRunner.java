package volleyball.common;

/*
 * Written by @RyanLey
 * 
 * Nov 30 2016
 * 
 * 3715 -Final Project
 */
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.UIManager;

import volleyball.views.volleyballTourn;

public class VolleyballProgramRunner {
	static tournament[] tournaments;
	
	public static void main(String[] args) {
		try{
			// Deserialize the int[]
	        ObjectInputStream in = new ObjectInputStream(new FileInputStream("tournaments.ser"));
	        tournaments = (tournament[]) in.readObject();
	        in.close();
		}
		catch(Exception e3){
			tournaments = createMock.getMock();
		}		
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					volleyballTourn frame = new volleyballTourn(tournaments);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
