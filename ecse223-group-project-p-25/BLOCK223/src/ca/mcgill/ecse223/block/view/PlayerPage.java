package ca.mcgill.ecse223.block.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.controller.TOPlayableGame;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JComboBox;

public class PlayerPage extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 6791018688197203010L;
	private JPanel contentPane;
	private JComboBox PublishedGameList;

	private String error = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateGamePage frame = new CreateGamePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PlayerPage() {
		setTitle("Block223");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 189);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//Menu Bar Items

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenu mnUser = new JMenu("User");
		menuBar.add(mnUser);

		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mntmLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mntmLogOutActionPerformed(evt);
			}
		});
		mnUser.add(mntmLogOut);

		//Header 1

		JLabel lblCreateNewGame = new JLabel("Welcome to Block223!");
		lblCreateNewGame.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCreateNewGame.setBounds(10, 11, 157, 14);
		contentPane.add(lblCreateNewGame);

		JLabel lblSearchForA = new JLabel("Please search for an available game: ");
		lblSearchForA.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSearchForA.setBounds(10, 64, 203, 14);
		contentPane.add(lblSearchForA);
		
		PublishedGameList = new JComboBox();
		List<TOPlayableGame> games = null;
		PublishedGameList.addItem("Select: ");
		try {
			games = Block223Controller.getPlayableGames();
		}
		catch (InvalidInputException e) {
			String error = e.getMessage();
			JOptionPane.showMessageDialog(null, error);
		}
		if (games.isEmpty()) {
			PublishedGameList.addItem("No games available");
		}
		for (int i=0; i<games.size(); i++) {
			PublishedGameList.addItem(games.get(i).getName());
		}
		PublishedGameList.setBounds(227, 62, 150, 20);
		contentPane.add(PublishedGameList);
		
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startGameActionPerformed(evt);
			}
		});
		btnStartGame.setBounds(410, 60, 114, 23);
		contentPane.add(btnStartGame);
	}
	
	private void startGameActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";	
		String name = (String) PublishedGameList.getSelectedItem();
		int id = 1;
		Player player = (Player) Block223Application.getCurrentUserRole();
		for (PlayedGame game: player.getPlayedGames()) {
			if (game.getGame().getName() == name) {
				id = game.getId();
				break;
			}
		}
		try {
			Block223Controller.selectPlayableGame(name, id);
			Block223PlayMode playingUI = new Block223PlayMode();
			playingUI.setVisible(true);
			this.setVisible(false);
		}
		catch (InvalidInputException e) {
			error = e.getMessage();
			JOptionPane.showMessageDialog(null, error);
		}
	}


	private void mntmLogOutActionPerformed(ActionEvent evt) {
		Block223Controller.logout();
		RegisterLoginPage loginpage = new RegisterLoginPage();
		loginpage.setVisible(true);
		this.setVisible(false);
	}
}
