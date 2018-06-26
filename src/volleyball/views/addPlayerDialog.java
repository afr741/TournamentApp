package volleyball.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import volleyball.common.player;
import volleyball.common.team;
import volleyball.common.tournament;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class addPlayerDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	player[] players ;
	private JTextField lastNameBox;
	private JTextField firstBox;
	private JTextField numberBox;
	private JTextField ageBox;
	private JButton cancelButton;
	private JButton button;
	player p;
	private JPanel buttonPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			addPlayerDialog dialog = new addPlayerDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Create the dialog.
	 */

	public addPlayerDialog() {
		//setModal(true);
		setTitle("Add New Player");
		setIconImage(Toolkit.getDefaultToolkit().getImage(addPlayerDialog.class.getResource("/volleyball/resources/volleyball.png")));
		initComponents();
		createEvents();
	}
	public void initComponents(){
		setBounds(100, 100, 325, 275);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JLabel lblAddPlayer = DefaultComponentFactory.getInstance().createTitle("Add New Player");
		lblAddPlayer.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		
		JLabel lblLastName = new JLabel("Last Name:");
		
		JLabel lblFirstInitial = new JLabel("First Initial:");
		
		JLabel lblNumber = new JLabel("Number:");
		
		JLabel lblAge = new JLabel("Age:");
		
		lastNameBox = new JTextField();
		lastNameBox.setColumns(10);
		
		firstBox = new JTextField();
		firstBox.setColumns(10);
		
		numberBox = new JTextField();
		numberBox.setColumns(10);
		
		ageBox = new JTextField();
		ageBox.setColumns(10);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_contentPanel.createSequentialGroup()
										.addGap(1)
										.addComponent(lblLastName))
									.addComponent(lblFirstInitial))
								.addComponent(lblNumber)
								.addComponent(lblAge))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(firstBox, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(ageBox, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(numberBox, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(lastNameBox, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)))
						.addComponent(lblAddPlayer))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(lblAddPlayer)
					.addGap(18)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lastNameBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLastName))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(firstBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFirstInitial))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(numberBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNumber))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(ageBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAge))
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		}
		{
			cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
		}
		
		button = new JButton("OK");
		
			button.setActionCommand("OK");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(165, Short.MAX_VALUE)
					.addComponent(cancelButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addGap(17))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(contentPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(cancelButton)
						.addComponent(button))
					.addGap(18)
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		getContentPane().setLayout(groupLayout);
	}
	public void createEvents(){
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			p = new player(lastNameBox.getText(),firstBox.getText(),Integer.parseInt(numberBox.getText()),Integer.parseInt(ageBox.getText()));
			setVisible(false);
		}
		});
	}
	public player showDialoag(){
		setModal(true);
		setVisible(true);
		return p;
	}
}
