package ca.mcgill.ecse223.block.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOGame;

import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import javax.swing.JMenuItem;

public class UpdateGame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2627457159078328353L;

	private JPanel contentPane;

	/**
	 *
	 */
	//Initialize Main Panel
	private JPanel contentPanel;
	//Initialize Create Game Variables
	private JTextField GameNameTextField;
	//Initialize Play Area Variables
	private JTextField WidthTextField;
	private JTextField HeightTextField;
	//Initialize Level Variables
	private JTextField BlocksLevelTextField;
	private JTextField NumberLevelsTextField;
	//Initialize Ball Variables
	private JTextField MinYSpeedTextField;
	private JTextField MinXSpeedTextField;
	private JTextField SpeedIncreaseFactorTextField;
	//Initialize Paddle Variables
	private JTextField MinPaddleLengthTextField;
	private JTextField MaxPaddleLengthTextField;

	//data elements
	private String error = null;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateGame frame = new UpdateGame();
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
	public UpdateGame() {
		setTitle("Block223 Builder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 560);

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
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);


		//Separator 1

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 134, 464, 14);
		contentPanel.add(separator_1);

		//Play area parameters

		JLabel lblPlayArea = new JLabel("Play Area");
		lblPlayArea.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPlayArea.setBounds(10, 11, 68, 14);
		contentPanel.add(lblPlayArea);

		JLabel lblWidth = new JLabel("Width:");
		lblWidth.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblWidth.setBounds(10, 44, 46, 14);
		contentPanel.add(lblWidth);

		WidthTextField = new JTextField();
		WidthTextField.setBounds(66, 42, 112, 20);
		contentPanel.add(WidthTextField);
		WidthTextField.setColumns(10);

		JLabel lblHeight = new JLabel("Height:");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHeight.setBounds(10, 69, 46, 14);
		contentPanel.add(lblHeight);

		HeightTextField = new JTextField();
		HeightTextField.setColumns(10);
		HeightTextField.setBounds(66, 67, 112, 20);
		contentPanel.add(HeightTextField);

		//Level parameters

		JLabel lblLevel = new JLabel("Level\r\n");
		lblLevel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLevel.setBounds(219, 11, 68, 14);
		contentPanel.add(lblLevel);

		JLabel lblNumberOfLevels = new JLabel("# of Levels:");
		lblNumberOfLevels.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNumberOfLevels.setBounds(219, 42, 92, 14);
		contentPanel.add(lblNumberOfLevels);

		NumberLevelsTextField = new JTextField();
		NumberLevelsTextField.setColumns(10);
		NumberLevelsTextField.setBounds(321, 40, 112, 20);
		contentPanel.add(NumberLevelsTextField);

		JLabel lblBlocksPerLevel = new JLabel("Blocks per level:\r\n");
		lblBlocksPerLevel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBlocksPerLevel.setBounds(219, 67, 92, 14);
		contentPanel.add(lblBlocksPerLevel);

		BlocksLevelTextField = new JTextField();
		BlocksLevelTextField.setColumns(10);
		BlocksLevelTextField.setBounds(321, 67, 112, 20);
		contentPanel.add(BlocksLevelTextField);

		//Separator 2

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 137, 464, 14);
		contentPanel.add(separator_2);

		//Ball parameters

		JLabel lblBallParameters = new JLabel("Ball Parameters\r\n");
		lblBallParameters.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBallParameters.setBounds(10, 162, 112, 14);
		contentPanel.add(lblBallParameters);

		JLabel lblMinyspeed = new JLabel("MinYSpeed:\r\n");
		lblMinyspeed.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMinyspeed.setBounds(10, 201, 78, 14);
		contentPanel.add(lblMinyspeed);

		MinYSpeedTextField = new JTextField();
		MinYSpeedTextField.setColumns(10);
		MinYSpeedTextField.setBounds(110, 199, 68, 20);
		contentPanel.add(MinYSpeedTextField);

		JLabel lblMinxspeed = new JLabel("MinXSpeed:\r\n");
		lblMinxspeed.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMinxspeed.setBounds(10, 230, 78, 14);
		contentPanel.add(lblMinxspeed);

		MinXSpeedTextField = new JTextField();
		MinXSpeedTextField.setColumns(10);
		MinXSpeedTextField.setBounds(110, 228, 68, 20);
		contentPanel.add(MinXSpeedTextField);

		JLabel lblSpeedIncreaseFactor = new JLabel("Increase Factor:\r\n");
		lblSpeedIncreaseFactor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblSpeedIncreaseFactor.setBounds(10, 255, 97, 14);
		contentPanel.add(lblSpeedIncreaseFactor);


		SpeedIncreaseFactorTextField = new JTextField();
		SpeedIncreaseFactorTextField.setColumns(10);
		SpeedIncreaseFactorTextField.setBounds(110, 253, 68, 20);
		contentPanel.add(SpeedIncreaseFactorTextField);

		//Paddle parameters

		JLabel lblPaddleParameters = new JLabel("Paddle Parameters\r\n");
		lblPaddleParameters.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPaddleParameters.setBounds(219, 162, 134, 14);
		contentPanel.add(lblPaddleParameters);

		JLabel lblMinlength = new JLabel("MinLength: \r\n");
		lblMinlength.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMinlength.setBounds(219, 201, 78, 14);
		contentPanel.add(lblMinlength);

		MinPaddleLengthTextField = new JTextField();
		MinPaddleLengthTextField.setColumns(10);
		MinPaddleLengthTextField.setBounds(321, 199, 112, 20);
		contentPanel.add(MinPaddleLengthTextField);

		JLabel lblMaxlength = new JLabel("MaxLength:");
		lblMaxlength.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblMaxlength.setBounds(219, 230, 78, 14);
		contentPanel.add(lblMaxlength);

		MaxPaddleLengthTextField = new JTextField();
		MaxPaddleLengthTextField.setColumns(10);
		MaxPaddleLengthTextField.setBounds(321, 228, 112, 20);
		contentPanel.add(MaxPaddleLengthTextField);

		//Separator 3

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 298, 464, 14);
		contentPanel.add(separator);

		//Apply Game Settings

		JButton btnApplyGameSettings = new JButton("Apply Game Update");
		btnApplyGameSettings.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnApplyGameSettings.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				applyUpdateActionPerformed(evt);
			}
		});
		btnApplyGameSettings.setBounds(160, 318, 170, 23);
		contentPanel.add(btnApplyGameSettings);

		//Separator 4

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 356, 464, 14);
		contentPanel.add(separator_3);

		//Save game

		JLabel lblSaveGame = new JLabel("Save game?\r\n");
		lblSaveGame.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSaveGame.setBounds(215, 366, 92, 28);
		contentPanel.add(lblSaveGame);

		JComboBox savedGamesList = new JComboBox();
		List<TOGame> games = null;
		savedGamesList.addItem("Select: ");
		try {
			games = Block223Controller.getDesignableGames();
		}
		catch (InvalidInputException e) {
			savedGamesList.addItem("No games available for saving");
		}
		if (games.isEmpty()) {
			savedGamesList.addItem("No games available for saving");
		}
		for (int i=0; i<games.size(); i++) {
			savedGamesList.addItem(games.get(i).getName());
		}

		savedGamesList.setBounds(91, 406, 134, 23);
		contentPanel.add(savedGamesList);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String game = (String) savedGamesList.getSelectedItem();
				saveGameSettingsActionPerformed(evt, game);
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSave.setBounds(298, 407, 87, 23);
		contentPanel.add(btnSave);
	}
	
	
	private void saveGameSettingsActionPerformed(ActionEvent evt, String game) {
		try {
			Block223Controller.selectGame(game);
			Block223Controller.saveGame();
		}
		catch (InvalidInputException e) {
			error= e.getMessage();
			JOptionPane.showMessageDialog(null, error);
		}
	}
	
	private void mntmLogOutActionPerformed(ActionEvent evt) {
		Block223Controller.logout();
		RegisterLoginPage loginpage = new RegisterLoginPage();
		loginpage.setVisible(true);
		this.setVisible(false);
	}
	
	private void applyUpdateActionPerformed(java.awt.event.ActionEvent evt) {
		error = "";
		int nrLevels = 0;
		try {
			nrLevels = Integer.parseInt(NumberLevelsTextField.getText());
		}
		catch (NumberFormatException e) {
			error = "The number of levels needs to be a numerical value!";
			JOptionPane.showMessageDialog(null, error);
		}

		int nrBlocksPerLevel = 0;
		try {
			nrBlocksPerLevel = Integer.parseInt(BlocksLevelTextField.getText());
		}
		catch (NumberFormatException e) {
			error = "The number of blocks per level needs to be a numerical value!";
			JOptionPane.showMessageDialog(null, error);
		}

		int minBallSpeedX = 0;
		try {
			minBallSpeedX = Integer.parseInt(MinXSpeedTextField.getText());
		}
		catch (NumberFormatException e) {
			error = "The minimum speed needs to be a numerical value!";
			JOptionPane.showMessageDialog(null, error);
		}

		int minBallSpeedY = 0;
		try {
			minBallSpeedY = Integer.parseInt(MinYSpeedTextField.getText());
		}
		catch (NumberFormatException e) {
			error = "The minimum speed needs to be a numerical value!";
			JOptionPane.showMessageDialog(null, error);
		}

		double ballSpeedIncreaseFactor = 0;
		try {
			ballSpeedIncreaseFactor = Double.parseDouble(SpeedIncreaseFactorTextField.getText());
		}
		catch (NumberFormatException e) {
			error = "The increase factor needs to be a numerical value!";
			JOptionPane.showMessageDialog(null, error);
		}

		int maxPaddleLength = 0;
		try {
			maxPaddleLength = Integer.parseInt(MaxPaddleLengthTextField.getText());
		}
		catch (NumberFormatException e) {
			error = "The max length needs to be a numerical value!";
			JOptionPane.showMessageDialog(null, error);
		}

		int minPaddleLength = 0;
		try {
			minPaddleLength = Integer.parseInt(MinPaddleLengthTextField.getText());
		}
		catch (NumberFormatException e) {
			error = "The min length needs to be a numerical value!";
			JOptionPane.showMessageDialog(null, error);
		}

		if (error.length() == 0) {
			try {
				Block223Controller.setGameDetails(nrLevels, nrBlocksPerLevel, minBallSpeedX, minBallSpeedY, ballSpeedIncreaseFactor, maxPaddleLength, minPaddleLength);
				BlockEditWindow blockpage = new BlockEditWindow();
				blockpage.setVisible(true);
				this.setVisible(false);
			}
			catch (InvalidInputException e) {
				error = e.getMessage();
				JOptionPane.showMessageDialog(null, error);
			}
		}
	}
}
