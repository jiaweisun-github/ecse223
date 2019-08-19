package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.TOCurrentBlock;
import ca.mcgill.ecse223.block.controller.TOCurrentlyPlayedGame;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.controller.TOUserMode;



import ca.mcgill.ecse223.block.controller.InvalidInputException;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.border.BevelBorder;

public class Block223PlayModeTest extends JFrame implements Block223PlayModeInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TOCurrentlyPlayedGame playableGame;

	private JPanel contentPane;
	private JLabel currentGameName;
	// data elements
	private static String error = "";
	//PlayArea
	private JPanel playArea;
	//blocks
//	List<TOGridCell> blocks;
	ArrayList<JPanel> panelList;
	private JLabel lblPrevious;
	private JLabel lblNext;
	JTextArea gameArea;
	Block223PlayModeListener bp;
	private JButton button;
	private TOHallOfFame HOF;
	private JLabel displayHOF;
	private JLabel playerScore;
	private JLabel numberOfLives;
	private JLabel currentLevel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Block223PlayMode frame = new Block223PlayMode();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Block223PlayModeTest() {
		setTitle("BLOCK CREATOR 9000");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 640);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(192, 192, 192));
		setJMenuBar(menuBar);

		JMenu mnUser = new JMenu("User");
		menuBar.add(mnUser);

		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				mntmLogOutActionPerformed(evt);
			}
		});
		
		mnUser.add(mntmLogout);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(176, 224, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		playArea = new playArea();
		playArea.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255)));
		playArea.setForeground(Color.WHITE);
		playArea.setBounds(30, 30, 390, 390);
		playArea.setAlignmentX(0.0f);
		playArea.setAlignmentY(0.0f);
		playArea.setBackground(new Color(176, 196, 222));

		playArea.setLayout(null);
		contentPane.setLayout(null);
		contentPane.add(playArea);

		JPanel panel_121 = new JPanel();
		panel_121.setBorder(null);
		panel_121.setBackground(new Color(230, 230, 250));
		panel_121.setBounds(10, 30, 20, 390);
		contentPane.add(panel_121);
		panel_121.setLayout(null);

		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setBackground(new Color(230, 230, 250));
		formattedTextField_1.setBorder(null);
		formattedTextField_1.setBounds(0, 10, 20, 20);
		panel_121.add(formattedTextField_1);
		formattedTextField_1.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_1.setText("1");

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBackground(new Color(230, 230, 250));
		formattedTextField.setText("9");
		formattedTextField.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField.setBorder(null);
		formattedTextField.setBounds(0, 164, 20, 20);
		panel_121.add(formattedTextField);

		JFormattedTextField formattedTextField_2 = new JFormattedTextField();
		formattedTextField_2.setBackground(new Color(230, 230, 250));
		formattedTextField_2.setText("7");
		formattedTextField_2.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_2.setBorder(null);
		formattedTextField_2.setBounds(0, 142, 20, 20);
		panel_121.add(formattedTextField_2);

		JFormattedTextField formattedTextField_3 = new JFormattedTextField();
		formattedTextField_3.setBackground(new Color(230, 230, 250));
		formattedTextField_3.setText("6");
		formattedTextField_3.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_3.setBorder(null);
		formattedTextField_3.setBounds(0, 120, 20, 20);
		panel_121.add(formattedTextField_3);

		JFormattedTextField formattedTextField_4 = new JFormattedTextField();
		formattedTextField_4.setBackground(new Color(230, 230, 250));
		formattedTextField_4.setText("5");
		formattedTextField_4.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_4.setBorder(null);
		formattedTextField_4.setBounds(0, 98, 20, 20);
		panel_121.add(formattedTextField_4);

		JFormattedTextField formattedTextField_5 = new JFormattedTextField();
		formattedTextField_5.setBackground(new Color(230, 230, 250));
		formattedTextField_5.setText("4");
		formattedTextField_5.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_5.setBorder(null);
		formattedTextField_5.setBounds(0, 76, 20, 20);
		panel_121.add(formattedTextField_5);

		JFormattedTextField formattedTextField_6 = new JFormattedTextField();
		formattedTextField_6.setBackground(new Color(230, 230, 250));
		formattedTextField_6.setText("3");
		formattedTextField_6.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_6.setBorder(null);
		formattedTextField_6.setBounds(0, 54, 20, 20);
		panel_121.add(formattedTextField_6);

		JFormattedTextField formattedTextField_7 = new JFormattedTextField();
		formattedTextField_7.setBackground(new Color(230, 230, 250));
		formattedTextField_7.setText("2");
		formattedTextField_7.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_7.setBorder(null);
		formattedTextField_7.setBounds(0, 32, 20, 20);
		panel_121.add(formattedTextField_7);
		
		JFormattedTextField formattedTextField_8 = new JFormattedTextField();
		formattedTextField_8.setText("10");
		formattedTextField_8.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_8.setBorder(null);
		formattedTextField_8.setBackground(new Color(230, 230, 250));
		formattedTextField_8.setBounds(0, 186, 20, 20);
		panel_121.add(formattedTextField_8);

		JFormattedTextField formattedTextField_9 = new JFormattedTextField();
		formattedTextField_9.setText("11");
		formattedTextField_9.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_9.setBorder(null);
		formattedTextField_9.setBackground(new Color(230, 230, 250));
		formattedTextField_9.setBounds(0, 208, 20, 20);
		panel_121.add(formattedTextField_9);

		JFormattedTextField formattedTextField_10 = new JFormattedTextField();
		formattedTextField_10.setText("12");
		formattedTextField_10.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_10.setBorder(null);
		formattedTextField_10.setBackground(new Color(230, 230, 250));
		formattedTextField_10.setBounds(0, 230, 20, 20);
		panel_121.add(formattedTextField_10);

		JFormattedTextField formattedTextField_11 = new JFormattedTextField();
		formattedTextField_11.setText("13");
		formattedTextField_11.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_11.setBorder(null);
		formattedTextField_11.setBackground(new Color(230, 230, 250));
		formattedTextField_11.setBounds(0, 252, 20, 20);
		panel_121.add(formattedTextField_11);

		JFormattedTextField formattedTextField_12 = new JFormattedTextField();
		formattedTextField_12.setText("14");
		formattedTextField_12.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_12.setBorder(null);
		formattedTextField_12.setBackground(new Color(230, 230, 250));
		formattedTextField_12.setBounds(0, 274, 20, 20);
		panel_121.add(formattedTextField_12);

		JFormattedTextField formattedTextField_13 = new JFormattedTextField();
		formattedTextField_13.setText("15");
		formattedTextField_13.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_13.setBorder(null);
		formattedTextField_13.setBackground(new Color(230, 230, 250));
		formattedTextField_13.setBounds(0, 296, 20, 20);
		panel_121.add(formattedTextField_13);

		JPanel panel_122 = new JPanel();
		panel_122.setBackground(new Color(230, 230, 250));
		panel_122.setBorder(null);
		panel_122.setBounds(30, 10, 390, 20);
		contentPane.add(panel_122);
		panel_122.setLayout(null);
		
		JLabel label = new JLabel("1");
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(10, 0, 20, 20);
		panel_122.add(label);
		
		JLabel label_1 = new JLabel("2");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(35, 0, 20, 20);
		panel_122.add(label_1);
		
		JLabel label_2 = new JLabel("3");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(60, 0, 20, 20);
		panel_122.add(label_2);
		
		JLabel label_3 = new JLabel("4");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(85, 0, 20, 20);
		panel_122.add(label_3);
		
		JLabel label_4 = new JLabel("5");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(110, 0, 20, 20);
		panel_122.add(label_4);
		
		JLabel label_5 = new JLabel("6");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(135, 0, 20, 20);
		panel_122.add(label_5);
		
		JLabel label_6 = new JLabel("7");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(160, 0, 20, 20);
		panel_122.add(label_6);
		
		JLabel label_7 = new JLabel("8");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(185, 0, 20, 20);
		panel_122.add(label_7);
		
		JLabel label_8 = new JLabel("9");
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setBounds(210, 0, 20, 20);
		panel_122.add(label_8);
		
		JLabel label_9 = new JLabel("10");
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setBounds(235, 0, 20, 20);
		panel_122.add(label_9);
		
		JLabel label_10 = new JLabel("11");
		label_10.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setBounds(260, 0, 20, 20);
		panel_122.add(label_10);
		
		JLabel label_11 = new JLabel("12");
		label_11.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_11.setHorizontalAlignment(SwingConstants.CENTER);
		label_11.setBounds(285, 0, 20, 20);
		panel_122.add(label_11);
		
		JLabel label_12 = new JLabel("13");
		label_12.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_12.setHorizontalAlignment(SwingConstants.CENTER);
		label_12.setBounds(310, 0, 20, 20);
		panel_122.add(label_12);
		
		JLabel label_13 = new JLabel("14");
		label_13.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setBounds(335, 0, 20, 20);
		panel_122.add(label_13);
		
		JLabel label_14 = new JLabel("15");
		label_14.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_14.setHorizontalAlignment(SwingConstants.CENTER);
		label_14.setBounds(360, 0, 20, 20);
		panel_122.add(label_14);

		BasicArrowButton nextHOF = new BasicArrowButton(0);
		nextHOF.setBackground(new Color(192, 192, 192));
		nextHOF.setBounds(615, 385, 40, 35);
		contentPane.add(nextHOF);
		
		BasicArrowButton previousHOF = new BasicArrowButton(0);
		previousHOF.setBackground(new Color(192, 192, 192));
		previousHOF.setBounds(477, 385, 40, 35);
		contentPane.add(previousHOF);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(459, 73, 207, 2);
		contentPane.add(separator);
		
		currentLevel = new JLabel("Level: " + 1);
		currentLevel.setBounds(468, 14, 83, 16);
		contentPane.add(currentLevel);
		
		numberOfLives = new JLabel("Lives: " + 3);
		numberOfLives.setBounds(563, 14, 83, 16);
		contentPane.add(numberOfLives);
		
		playerScore = new JLabel("Score: " + 0);
		playerScore.setBounds(468, 45, 83, 16);
		contentPane.add(playerScore);
		
		JLabel lblHallOfFame = new JLabel("Hall Of Fame: ");
		lblHallOfFame.setBounds(469, 88, 186, 16);
		contentPane.add(lblHallOfFame);
		
		currentGameName = new JLabel("Current game:");
		currentGameName.setBounds(469, 118, 186, 16);
		contentPane.add(currentGameName);

		lblPrevious = new JLabel("Previous");
		lblPrevious.setBounds(472, 433, 61, 16);
		contentPane.add(lblPrevious);
		
		lblNext = new JLabel("Next");
		lblNext.setBounds(622, 433, 31, 16);
		contentPane.add(lblNext);


		displayHOF = new JLabel("");
		displayHOF.setHorizontalAlignment(SwingConstants.CENTER);
		displayHOF.setVerticalAlignment(SwingConstants.TOP);
		displayHOF.setBounds(469, 147, 196, 225);
		contentPane.add(displayHOF);

		JButton button = new JButton("Start Game");
		button.setBounds(175, 453, 337, 35);
		contentPane.add(button);

		gameArea = new JTextArea();
		gameArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(gameArea);
		scrollPane.setPreferredSize(new Dimension(375, 125));

		getContentPane().add(scrollPane, BorderLayout.CENTER);

		button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				button.setVisible(false);
				// initiating a thread to start listening to keyboard inputs
				bp = new Block223PlayModeListener();
				Runnable r1 = new Runnable() {
					@Override
					public void run() {
						// in the actual game, add keyListener to the game window
						gameArea.addKeyListener(bp);
					}
				};
				Thread t1 = new Thread(r1);
				t1.start();
				// to be on the safe side use join to start executing thread t1 before executing
				// the next thread
				try {
					t1.join();
				} catch (InterruptedException e1) {
				}

				// initiating a thread to start the game loop
				Runnable r2 = new Runnable() {
					@Override
					public void run() {
						try {
							Block223Controller.startGame(Block223PlayModeTest.this);
							button.setVisible(true);
						} catch (InvalidInputException e) {
						}
					}
				};
				Thread t2 = new Thread(r2);
				t2.start();
			}
		});
		refresh();
	}

	private void mntmLogOutActionPerformed(ActionEvent evt) {
		Block223Controller.logout();
		RegisterLoginPage loginpage = new RegisterLoginPage();
		loginpage.setVisible(true);
		this.setVisible(false);
	}

	public TOCurrentlyPlayedGame getCurrentPlayableGame() {
		
		try {
		playableGame = Block223Controller.getCurrentPlayableGame();
	} catch (InvalidInputException e ) {
		error = e.getMessage();
		JOptionPane.showMessageDialog(null, error);
	}
		return playableGame;
	}
	
	public class playArea extends JPanel {
		playArea() {
			setPreferredSize(new Dimension(420,420));
		}
		private int boxSize = 20;
		private double ballposX = 195;
		private double ballposY = 195;

		@Override
		public void paintComponent(Graphics g) {
			TOCurrentlyPlayedGame playableGame = getCurrentPlayableGame();
			int currentPaddleLength = (int) playableGame.getCurrentPaddleLength();
			List<TOCurrentBlock> blocks = playableGame.getBlocks();
			super.paintComponent(g);

			//Background
			g.setColor(Color.pink);
			g.fillRect(0,0,390,390);


			//ball
			g.setColor(Color.red);
			g.fillOval((int) playableGame.getCurrentBallX(), (int) playableGame.getCurrentBallY(), 10, 10);

			//paddle

			g.setColor(Color.green);
			g.fillRect((int) playableGame.getCurrentPaddleX(),360, currentPaddleLength, 5);


			for (TOCurrentBlock block : blocks) {
				// create new block
				g.setColor(new Color(block.getRed(), block.getGreen(), block.getBlue()));
				g.fillRect(block.getX(), block.getY(), boxSize, boxSize);
			}
		}
	}

	public String takeInputs() {
		if (bp == null) {
			return "";
		}
		return bp.takeInputs();
	}

	@Override
	public void refresh() {
		TOCurrentlyPlayedGame playableGame = getCurrentPlayableGame();
		numberOfLives.setText("Lives: " + playableGame.getLives());
		playerScore.setText("Score: " + playableGame.getScore());
		currentLevel.setText("Level: " + playableGame.getCurrentLevel());
		playArea.revalidate();
		playArea.repaint();
	}

/*	@Override
	public void endGame(int nrOfLives, TOHallOfFameEntry hof) {
		// TODO Auto-generated method stub

	} */
}