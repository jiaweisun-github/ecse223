package ca.mcgill.ecse223.block.view;


import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.controller.TOGame;
import ca.mcgill.ecse223.block.controller.TOGridCell;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import javax.swing.JSeparator;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Font;
import java.awt.Graphics;

public class BlockEditWindow extends JFrame {

	private JPanel contentPane;
	private JButton createBlockButton;
	private JButton deleteBlockButton;
	private JComboBox toBlockComboBox;
	private JButton gameSettingsButton;
	private JComboBox toGridCellComboBox;
	private JComboBox levelComboBox;
	private JButton saveButton;
	private JComboBox yPositionComboBox;
	private JComboBox xPositionComboBox;
	private JButton removeBlockButton;
	private JButton moveBlockButton;
	private JButton positionBlockButton;
	private JButton updateBlockButton;
	private JSlider blockBlueSlider;
	private JSlider blockGreenSlider;
	private JPanel testBlock;
	private JSlider blockRedSlider;
	
	//playArea
	private JPanel playArea;
	private JPanel newPanel;
	private JPanel gridforblocks;
	// data elements
	private String error = "";
	//blocks
	private HashMap<Integer, TOBlock> blocks;
	//grid cells
	private HashMap<Integer, TOGridCell> gridCells;
	//games
	private HashMap<Integer, TOGame> games;
	private JSlider pointsSlider;
	private HashMap<Integer, Integer> levels;
	private HashMap<Integer, Integer> gridHorizontalPosition;
	private HashMap<Integer, Integer> gridVerticalPosition;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {				
				try {
					BlockEditWindow frame = new BlockEditWindow();
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
	public BlockEditWindow() {
		setTitle("BLOCK CREATOR 9000");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 710, 574);

		JMenuBar menuBar = new JMenuBar();
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		createBlockButton = new JButton("Create Block");
		createBlockButton.setBounds(432, 191, 123, 23);
		createBlockButton.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				createBlockButtonActionPerformed(evt);
				
//			JOptionPane.showMessageDialog(null, "helo");
//				//addBlockperformed();
			}
		});

		
		playArea = new playArea();
		playArea.setBounds(30, 30, 390, 390);
		playArea.setAlignmentX(0.0f);
		playArea.setAlignmentY(0.0f);
		playArea.setBackground(Color.WHITE);
		
		deleteBlockButton = new JButton("Delete Block");
		deleteBlockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteBlockButtonActionPerformed(e);
			}
		});
		deleteBlockButton.setBounds(563, 257, 123, 23);

		JLabel lblListOfBlocks = new JLabel("List of Game Blocks:\r\n");
		lblListOfBlocks.setBounds(432, 227, 139, 14);
		playArea.setLayout(null);
		contentPane.setLayout(null);
		contentPane.add(playArea);
		contentPane.add(createBlockButton);
		contentPane.add(deleteBlockButton);
		contentPane.add(lblListOfBlocks);
		toBlockComboBox = new JComboBox();
		toBlockComboBox.setBounds(563, 224, 123, 20);
		contentPane.add(toBlockComboBox);
		toBlockComboBox.setModel(new DefaultComboBoxModel(new String[] {"Blocks"}));
		toBlockComboBox.setToolTipText("Select a block");

		JLabel lblListOfBlocks_1 = new JLabel("List of Level Blocks:");
		lblListOfBlocks_1.setBounds(432, 367, 146, 14);
		contentPane.add(lblListOfBlocks_1);

		JLabel lblListOfLevels = new JLabel("List of Levels:");
		lblListOfLevels.setBounds(432, 47, 110, 14);
		contentPane.add(lblListOfLevels);

		levelComboBox = new JComboBox();
		levelComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshData();
			}
		});
		levelComboBox.setModel(new DefaultComboBoxModel(new String[] {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9", "Level 10", "Level 11", "Level 12", "Level 13", "Level 14", "Level 15", "Level 16", "Level 17", "Level 18", "Level 19", "Level 20", "Level 21", "Level 22", "Level 23", "Level 24", "Level 25", "Level 26", "Level 27", "Level 28", "Level 29", "Level 30", "Level 31", "Level 32", "Level 33", "Level 34", "Level 35", "Level 36", "Level 37", "Level 38", "Level 39", "Level 40", "Level 41", "Level 42", "Level 43", "Level 44", "Level 45", "Level 46", "Level 47", "Level 48", "Level 49", "Level 50", "Level 51", "Level 52", "Level 53", "Level 54", "Level 55", "Level 56", "Level 57", "Level 58", "Level 59", "Level 60", "Level 61", "Level 62", "Level 63", "Level 64", "Level 65", "Level 66", "Level 67", "Level 68", "Level 69", "Level 70", "Level 71", "Level 72", "Level 73", "Level 74", "Level 75"}));
		levelComboBox.setBounds(563, 44, 123, 20);
		contentPane.add(levelComboBox);

		toGridCellComboBox = new JComboBox();
		toGridCellComboBox.setModel(new DefaultComboBoxModel(new String[] {"CurrentLevelBlocks"}));
		toGridCellComboBox.setBounds(563, 364, 123, 20);
		contentPane.add(toGridCellComboBox);

		updateBlockButton = new JButton("Update Block");
		updateBlockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				updateBlockButtonActionPerformed(evt);
				
			}
		});
		updateBlockButton.setBounds(563, 191, 123, 23);
		contentPane.add(updateBlockButton);

		positionBlockButton = new JButton("Position Block");
		positionBlockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				positionBlockButtonActionPerformed(evt);
			}
		});

		positionBlockButton.setBounds(432, 257, 123, 23);
		contentPane.add(positionBlockButton);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(432, 68, 254, 2);
		contentPane.add(separator_1);

		gameSettingsButton = new JButton("Game Settings");
		gameSettingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		gameSettingsButton.setBounds(432, 8, 123, 23);
		contentPane.add(gameSettingsButton);

		moveBlockButton = new JButton("Move Block");
		moveBlockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				moveBlockButtonActionPerformed(evt);
			}
		});
		moveBlockButton.setBounds(432, 397, 123, 23);
		contentPane.add(moveBlockButton);

		removeBlockButton = new JButton("Remove Block");
		removeBlockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeBlockButtonActionPerformed(arg0);
			}
		});
		removeBlockButton.setBounds(563, 397, 123, 23);
		contentPane.add(removeBlockButton);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(430, 494, 254, 2);
		contentPane.add(separator_2);

		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				saveButtonActionPerformed(evt);
			}
		});
		saveButton.setBounds(563, 8, 123, 23);
		contentPane.add(saveButton);

		JPanel panel_121 = new JPanel();
		panel_121.setBorder(null);
		panel_121.setBackground(Color.WHITE);
		panel_121.setBounds(10, 30, 20, 390);
		contentPane.add(panel_121);
		panel_121.setLayout(null);

		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setBorder(null);
		formattedTextField_1.setBounds(0, 10, 20, 20);
		panel_121.add(formattedTextField_1);
		formattedTextField_1.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_1.setText("1");

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setText("8");
		formattedTextField.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField.setBorder(null);
		formattedTextField.setBounds(0, 164, 20, 20);
		panel_121.add(formattedTextField);

		JFormattedTextField formattedTextField_2 = new JFormattedTextField();
		formattedTextField_2.setText("7");
		formattedTextField_2.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_2.setBorder(null);
		formattedTextField_2.setBounds(0, 142, 20, 20);
		panel_121.add(formattedTextField_2);

		JFormattedTextField formattedTextField_3 = new JFormattedTextField();
		formattedTextField_3.setText("6");
		formattedTextField_3.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_3.setBorder(null);
		formattedTextField_3.setBounds(0, 120, 20, 20);
		panel_121.add(formattedTextField_3);

		JFormattedTextField formattedTextField_4 = new JFormattedTextField();
		formattedTextField_4.setText("5");
		formattedTextField_4.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_4.setBorder(null);
		formattedTextField_4.setBounds(0, 98, 20, 20);
		panel_121.add(formattedTextField_4);

		JFormattedTextField formattedTextField_5 = new JFormattedTextField();
		formattedTextField_5.setText("4");
		formattedTextField_5.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_5.setBorder(null);
		formattedTextField_5.setBounds(0, 76, 20, 20);
		panel_121.add(formattedTextField_5);

		JFormattedTextField formattedTextField_6 = new JFormattedTextField();
		formattedTextField_6.setText("3");
		formattedTextField_6.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_6.setBorder(null);
		formattedTextField_6.setBounds(0, 54, 20, 20);
		panel_121.add(formattedTextField_6);

		JFormattedTextField formattedTextField_7 = new JFormattedTextField();
		formattedTextField_7.setText("2");
		formattedTextField_7.setHorizontalAlignment(SwingConstants.CENTER);
		formattedTextField_7.setBorder(null);
		formattedTextField_7.setBounds(0, 32, 20, 20);
		panel_121.add(formattedTextField_7);

		xPositionComboBox = new JComboBox();
		xPositionComboBox.setModel(new DefaultComboBoxModel(new String[] {"HorizontalPosition"}));
		xPositionComboBox.setBounds(563, 293, 123, 22);
		contentPane.add(xPositionComboBox);

		yPositionComboBox = new JComboBox();
		yPositionComboBox.setModel(new DefaultComboBoxModel(new String[] {"VerticalPosition"}));
		yPositionComboBox.setBounds(563, 328, 123, 23);
		contentPane.add(yPositionComboBox);

		JLabel lblLevelXposition = new JLabel("Level xPosition:");
		lblLevelXposition.setBounds(432, 297, 123, 14);
		contentPane.add(lblLevelXposition);

		JLabel lblLevelYposition = new JLabel("Level yPosition:");
		lblLevelYposition.setBounds(432, 332, 110, 14);
		contentPane.add(lblLevelYposition);

		blockBlueSlider = new JSlider();

		blockBlueSlider.setValue(128);
		blockBlueSlider.setMaximum(255);
		blockBlueSlider.setBounds(467, 164, 175, 14);
		contentPane.add(blockBlueSlider);
		blockBlueSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//JSlider source = (JSlider) e.getSource();
				//testBlock.setBackground(new Color(blockRedSlider.getValue(), blockGreenSlider.getValue(), source.getValue()));
				testBlock.setBackground(new Color(blockRedSlider.getValue(), blockGreenSlider.getValue(), blockBlueSlider.getValue()));
			}
		});
		JLabel lblBlue = new JLabel("Blue:");
		lblBlue.setBounds(430, 164, 46, 14);
		contentPane.add(lblBlue);

		JLabel lblGreen = new JLabel("Green:");
		lblGreen.setBounds(432, 137, 46, 14);
		contentPane.add(lblGreen);

		blockGreenSlider = new JSlider();
		blockGreenSlider.setValue(30);
		blockGreenSlider.setMaximum(255);
		blockGreenSlider.setBounds(467, 137, 175, 14);
		contentPane.add(blockGreenSlider);
		blockGreenSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				testBlock.setBackground(new Color(blockRedSlider.getValue(), source.getValue(), blockBlueSlider.getValue()));
			}
		});
		blockRedSlider = new JSlider();

		blockRedSlider.setSnapToTicks(true);
		blockRedSlider.setValue(150);
		blockRedSlider.setMaximum(255);
		blockRedSlider.setBounds(467, 110, 175, 14);
		contentPane.add(blockRedSlider);
		blockRedSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				testBlock.setBackground(new Color(source.getValue(), blockGreenSlider.getValue(), blockBlueSlider.getValue()));
			}
		});

		JLabel lblRed = new JLabel("Red:");
		lblRed.setBounds(432, 110, 46, 14);
		contentPane.add(lblRed);

		testBlock = new JPanel();
		testBlock.setBackground(new Color(blockRedSlider.getValue(), blockGreenSlider.getValue(), blockBlueSlider.getValue()));
		testBlock.setPreferredSize(new Dimension(20, 20));
		testBlock.setBorder(new LineBorder(new Color(0, 0, 0)));
		testBlock.setBounds(654, 131, 20, 20);
		contentPane.add(testBlock);
		GridBagLayout gbl_testBlock = new GridBagLayout();
		gbl_testBlock.columnWidths = new int[]{0, 0};
		gbl_testBlock.rowHeights = new int[]{0, 0};
		gbl_testBlock.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_testBlock.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		testBlock.setLayout(gbl_testBlock);

		JLabel lblPoints = new JLabel("Points:");
		lblPoints.setBounds(430, 83, 46, 14);
		contentPane.add(lblPoints);

		pointsSlider = new JSlider();
		pointsSlider.setMinimum(1);
		pointsSlider.setMaximum(1000);

		contentPane.add(pointsSlider);

		JLabel pointsLabel = new JLabel(String.valueOf(pointsSlider.getValue()));
		pointsLabel.setBounds(656, 83, 30, 14);
		contentPane.add(pointsLabel);
		pointsSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				pointsLabel.setText(String.valueOf(pointsSlider.getValue()));

			}
		});
		pointsSlider.setBounds(467, 83, 175, 14);
		
		JPanel panel_122 = new JPanel();
		panel_122.setBackground(Color.WHITE);
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

		JButton btnPublishGame = new JButton("Publish Game");
		btnPublishGame.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnPublishGame.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				publishGameActionPerformed(evt);
			}
		});
		btnPublishGame.setBounds(488, 443, 146, 40);
		contentPane.add(btnPublishGame);
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshData();
				System.out.println("Refreshed!");
			}
		});
		refreshButton.setBounds(179, 451, 110, 23);
		contentPane.add(refreshButton);
	}
	
	public void publishGameActionPerformed(ActionEvent e) {
		error = "";
		try {
			Block223Controller.publishGame();
			Block223Controller.saveGame();
			Block223Controller.logout();
			RegisterLoginPage login = new RegisterLoginPage();
			login.setVisible(true);
			this.setVisible(false);
		}
		catch(InvalidInputException e10) {
			error = e10.getMessage();
			JOptionPane.showMessageDialog(null, error);
		}
		
	}

	public void deleteBlockButtonActionEvent(ActionEvent e) {
		// TODO Auto-generated method stub
		TOBlock selectedBlock = (TOBlock) toBlockComboBox.getSelectedItem();
		toBlockComboBox.removeItem(selectedBlock);
		try {
			Block223Controller.deleteBlock(selectedBlock.getId());
		} catch (InvalidInputException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			System.out.println(e1);
		}
		refreshData();
		
	}

	private void createBlockButtonActionPerformed(ActionEvent evt) {
		try {
			//Block223C
			Block223Controller.addBlock(blockRedSlider.getValue(), blockGreenSlider.getValue(), blockBlueSlider.getValue(), pointsSlider.getValue());

		} catch (InvalidInputException e) {
			String error = e.getMessage();
			JOptionPane.showMessageDialog(null, error);
		}
		refreshData();
		
		//NOW HAVE TO ADD THIS BLOCK TO HASHMAP
	}

	private void saveButtonActionPerformed(ActionEvent evt) {
		try {

			Block223Controller.saveGame();
			JOptionPane.showMessageDialog(null, "Game Saved");
		} catch (InvalidInputException e) {
			String error = e.getMessage();
			JOptionPane.showMessageDialog(null, error);
		}


		pointsSlider = new JSlider();
		pointsSlider.setMaximum(1000);

		contentPane.add(pointsSlider);

		JLabel pointsLabel = new JLabel(String.valueOf(pointsSlider.getValue()));
		pointsLabel.setBounds(654, 30, 30, 14);
		contentPane.add(pointsLabel);
		pointsSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				pointsLabel.setText(String.valueOf(pointsSlider.getValue()));

			}
		});
		pointsSlider.setBounds(467, 30, 175, 14);
	}


	private void mntmLogOutActionPerformed(ActionEvent evt) {
		Block223Controller.logout();
		RegisterLoginPage loginpage = new RegisterLoginPage();
		loginpage.setVisible(true);
		this.setVisible(false);
	}

	private void deleteBlockButtonActionPerformed(ActionEvent evt) {
		// clear error message and basic input validation
		error = "";

		int selectedBlock = toBlockComboBox.getSelectedIndex();

		if (selectedBlock < 0) {
			error = "Block needs to be selected for deletion!";}

		if (error.length() == 0) {
			// call the controller
			try{
				Block223Controller.deleteBlock(blocks.get(selectedBlock).getId()); 	//We need to get the blockId value that is associated with this block index in hashMap
			} catch (InvalidInputException e) {
				System.out.println(e);
			}
		}


		// update visuals
		refreshData();
	}
	private void updateBlockButtonActionPerformed(ActionEvent evt) {
		// clear error message and basic input validation
		error = "";
		int redColor = blockRedSlider.getValue();
		int greenColor = blockGreenSlider.getValue();
		int blueColor = blockBlueSlider.getValue();
		int blockPoint = pointsSlider.getValue();
		int selectedBlock = toBlockComboBox.getSelectedIndex();

		if (selectedBlock < 0) {
			error = "Block needs to be selected for deletion!";}

		if (error.length() == 0) {
			// call the controller
			try{
				Block223Controller.updateBlock(blocks.get(selectedBlock).getId(), redColor, greenColor, blueColor, blockPoint); 
				//We need to get the blockId value that is associated with this block index in hashMap
			} catch (InvalidInputException e) {
				System.out.println(e);
			}
		}


		// update visuals
		refreshData();
	}

	private void removeBlockButtonActionPerformed(ActionEvent evt) {

		String error = "";
		int level = levelComboBox.getSelectedIndex();
		int gridCell = toGridCellComboBox.getSelectedIndex();

		if(gridCell < 0) {
		error = "An existing block needs to be selected to be removed!";
		}
		
		if (level < 0) {
		error = error + "A level needs to be selected for block! ";
		}
		
		error = error.trim();
		
		if(error.length() > 0) {
			JOptionPane.showMessageDialog(null, error);
		}
//		
//		gridCells = new HashMap<Integer, TOGridCell>();
//		List<TOGridCell> toGridCells = null;
//		try {
//			toGridCells =  Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(levels.get(levelComboBox.getSelectedIndex() + 1));
//			System.out.println(toGridCells);
//
//		} catch(InvalidInputException e) {
//			System.out.println(e);
//		}


		if(error == "") {
			try {
//
//				TOGridCell gridCellRemove = gridCells.get(gridCell);
//				System.out.println("ok");
//				System.out.println(level);
//				System.out.println(gridCells.get(gridCell));
//				Block223Controller.removeBlock(level + 1, gridCellRemove.getGridHorizontalPosition(), gridCellRemove.getGridVerticalPosition());
//				Block223Controller.removeBlock(1, 1, 1);
//				System.out.println("removed?");
				Block223Controller.removeBlock(level + 1, gridCells.get(gridCell).getGridHorizontalPosition(), gridCells.get(gridCell).getGridVerticalPosition());
			} catch (InvalidInputException e) {
				error = e.getMessage();
				JOptionPane.showMessageDialog(null, error);
			}
			//getCurrentLevel existe pas dans TOGridCell (et aucun des transfer objects) mais ca existe dans le model (Game.java), jsp comment le get.
		}
		
		refreshData();


	}
	private void positionBlockButtonActionPerformed(ActionEvent evt) {
		String error = "";
		
		int selectedBlock = toBlockComboBox.getSelectedIndex();		
		if (selectedBlock < 0) {
		error = "Block needs to be selected in order to place it in the game!";
		}
		
		int level = levelComboBox.getSelectedIndex();
		if (level < 0) {
		error = error + "A level needs to be selected for block! ";
		}
		
		int newGridHorizontalPosition = xPositionComboBox.getSelectedIndex() + 1;
		//this gives me the index which means if I select y=1 , the actual value is 0 so we need to add + 1 
		int newGridVerticalPosition = yPositionComboBox.getSelectedIndex() + 1;


		//int selectedAssignment = selectedBlock.getId(); //replace assignmentlist par JPanel list?? CA VA ETRE LE PANEL QUI VA ETRE CHOISI

		if (newGridHorizontalPosition < 0) {
		error = error + "A horizontal grid position needs to be selected for block!";}
		if (newGridVerticalPosition < 0) {
		error = error + "A vertical grid position needs to be selected for block!";}

		error = error.trim();
		
		if(error.length() > 0) {
			JOptionPane.showMessageDialog(null, error);
		}

		if (error == "") {
			try {
				Block223Controller.positionBlock((blocks.get(selectedBlock)).getId(), levels.get(level), newGridHorizontalPosition, newGridVerticalPosition);
//				JOptionPane.showMessageDialog(null, "Successfully positionned block at x: " + newGridHorizontalPosition + " and y: " + newGridVerticalPosition);
			} catch (InvalidInputException e) {
				error = e.getMessage();
				JOptionPane.showMessageDialog(null, error);
			}
	}
		//update visuals
		refreshData();
	}
	

	private void moveBlockButtonActionPerformed(ActionEvent evt) {

		String error = "";

		int gridCell = toGridCellComboBox.getSelectedIndex();
		int level = levelComboBox.getSelectedIndex();
		int newGridHorizontalPosition = xPositionComboBox.getSelectedIndex() + 1;
		int newGridVerticalPosition = yPositionComboBox.getSelectedIndex() + 1;
		
		if (gridCell < 0) {
		error = "An existing block needs to be selected in order to move it in the game!";}
		
		if (level < 0) {
		error = error + "A level needs to be selected for block! ";}
		
		if (newGridVerticalPosition < 0){
		error = error + "A new vertical position must be selected.";
		}
		
		if (newGridHorizontalPosition < 0){
		error = error + "A new horizontal position must be selected.";
		}

		error = error.trim();
		
		if(error.length() > 0) {
			JOptionPane.showMessageDialog(null, error);
		}
		
		if (error == "") {
		try {
			Block223Controller.moveBlock(levels.get(level), gridCells.get(gridCell).getGridHorizontalPosition(), gridCells.get(gridCell).getGridVerticalPosition(),
					newGridHorizontalPosition,  newGridVerticalPosition);
		}
		catch (InvalidInputException e) {
			System.out.println(e);}
		}
		refreshData();
	}

	public List<TOGridCell> getBlocksAtCurrentLevel() {
		int level = 1;
		int currentLevel = levelComboBox.getSelectedIndex() + 1;

//				levels.get(levelComboBox.getSelectedIndex());
		List<TOGridCell> currentLevelBlocks = null;
			try {
				currentLevelBlocks = Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(currentLevel);
//				JOptionPane.showMessageDialog(null, "Successfully got blocks of level " + level);
			} catch (InvalidInputException e) {
				System.out.println(e.getMessage());
			}
		return currentLevelBlocks;
	}

	
	public class playArea extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		playArea() {
			setPreferredSize(new Dimension(390,390));
		}
		private int boxSize = 20;
		
		@Override 
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// always set color of the component first and then the object
			//This has to be here so each time it gets the new blocks 
			List<TOGridCell> blocks = getBlocksAtCurrentLevel();
			// Background
			g.setColor(new Color(51,204,255));
			g.fillRect(0, 0, 390, 390);

			// ball
			g.setColor(Color.red);
			g.fillOval(195, 195, 10, 10);

			// paddle
			g.setColor(Color.green);
			g.fillRect(195, 360, 20, 5);
			
			for (TOGridCell block : blocks) {
				int i = 1;
				int j = 1;
				int xPosition = 10;
				int yPosition = 10;
				int x = block.getGridHorizontalPosition();
				int y = block.getGridVerticalPosition();

				if (x == 1 || y == 1) {
					xPosition = 10;
					yPosition = 10;
				}

				while (i < x) {
					xPosition += 25;
					i++;
				}

				while (j < y) {
					yPosition += 22;
					j++;
				}

				// create new block
				g.setColor(new Color(block.getRed(), block.getGreen(), block.getBlue()));
				g.fillRect(xPosition, yPosition, boxSize, boxSize);
			}
			
		}
	}
	private void refreshData() {
		playArea.revalidate();
		playArea.repaint();	
		if (error == null || error.length() == 0) {			
			blocks = new HashMap<Integer, TOBlock>();
			toBlockComboBox.removeAllItems();
			Integer index = 0;
			List<TOBlock> toBlocks = null;
			try {
				toBlocks = Block223Controller.getBlocksOfCurrentDesignableGame();
			} catch (InvalidInputException e) {
				error = e.getMessage();
				System.out.println(error);
			}
			for (TOBlock block : toBlocks) {
				blocks.put(index, block);
				toBlockComboBox.addItem("Block ID: " + block.getId());
				index++;
			}
			toBlockComboBox.setSelectedIndex(-1);

			levels = new HashMap<Integer, Integer>();
			levelComboBox.removeAllItems();
			index = 0;
			int level = 0;
			try {
				level = Block223Controller.getCurrentDesignableGame().getNrLevels();
			} catch (InvalidInputException e1) {
				// TODO Auto-generated catch block
				System.out.println(e1);
			}
			for (int i = 1; i <= level; i++, index++) {
				levels.put(index, i);
				levelComboBox.addItem("Level : " + i );
			}

			gridCells = new HashMap<Integer, TOGridCell>();
			toGridCellComboBox.removeAllItems();
			index = 0;
			List<TOGridCell> toGridCells = null;
			try {
				toGridCells =  Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(levels.get(levelComboBox.getSelectedIndex()));

			} catch(InvalidInputException e) {
				System.out.println(e);
			}
			for (TOGridCell gridCell : toGridCells) {
			gridCells.put(index, gridCell);
			toGridCellComboBox.addItem("Grid ID: " + gridCell.getId() + " x: " + gridCell.getGridHorizontalPosition() + " y: " + gridCell.getGridVerticalPosition());
			index++;
			}
			toGridCellComboBox.setSelectedIndex(-1);
		
			gridHorizontalPosition = new HashMap<Integer, Integer>();
			xPositionComboBox.removeAllItems();
			index = 0;
			for (int i = 1; i <= 15 ; i++, index++) {
				gridHorizontalPosition.put(index, i);
				xPositionComboBox.addItem(i);
			}		
			xPositionComboBox.setSelectedIndex(-1);
			
			gridVerticalPosition = new HashMap<Integer, Integer>();
			yPositionComboBox.removeAllItems();
			index = 0;
			for (int i = 1; i <= 15 ; i++, index++) {
				gridHorizontalPosition.put(index,i);
				yPositionComboBox.addItem(i);
			}
			yPositionComboBox.setSelectedIndex(-1);
			
			}
		}
	}





