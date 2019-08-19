package ca.mcgill.ecse223.block.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;

import java.awt.Panel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;


public class RegisterLoginPage extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4755970038257074101L;
	private JPanel contentPane;
	private JTextField textFieldUsername;
	private JPasswordField passwordFieldPlayer;
	private JPasswordField passwordFieldAdmin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterLoginPage frame = new RegisterLoginPage();
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
	public RegisterLoginPage() {
		setTitle("Sign Up / Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 487, 364);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Panel panel = new Panel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel JLabelUsername = new JLabel("Username");
		JLabelUsername.setBounds(121, 10, 62, 16);
		panel.add(JLabelUsername);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(188, 5, 130, 26);
		panel.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		JLabel JLabelPlayerPassword = new JLabel("Player Password");
		JLabelPlayerPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		JLabelPlayerPassword.setBounds(76, 44, 107, 16);
		panel.add(JLabelPlayerPassword);
		
		passwordFieldPlayer = new JPasswordField();
		passwordFieldPlayer.setBounds(188, 39, 130, 26);
		panel.add(passwordFieldPlayer);
		
		JButton btnRegister = new JButton("Register");
		
	
		btnRegister.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				RegisterButtonActionPerformed(evt);
			}
		});
		btnRegister.setBounds(145, 123, 156, 29);
		panel.add(btnRegister);
		
		passwordFieldAdmin = new JPasswordField();
		passwordFieldAdmin.setBounds(188, 73, 130, 26);
		panel.add(passwordFieldAdmin);
		
		JLabel lblAdminPassword = new JLabel("Admin Password");
		lblAdminPassword.setBounds(76, 78, 107, 16);
		panel.add(lblAdminPassword);
		
		JButton btnLogInAdmin = new JButton("Log in as Admin");
		btnLogInAdmin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				LogInAdminActionPerformed(evt);
			}
		});
		btnLogInAdmin.setBounds(145, 164, 156, 29);
		panel.add(btnLogInAdmin);
		
		JButton btnLogInPlayer = new JButton("Log In as Player");
		btnLogInPlayer.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				LogInPlayerActionPerformed(evt);
			}
		});
		btnLogInPlayer.setBounds(145, 205, 156, 29);
		panel.add(btnLogInPlayer); 
	}
	
	private void LogInPlayerActionPerformed(ActionEvent evt) {
		String username = this.textFieldUsername.getText().trim();
		String password = String.valueOf(this.passwordFieldPlayer.getPassword());
		try {
			Block223Controller.login(username, password);
			PlayerPage playerpage = new PlayerPage();
			playerpage.setVisible(true);
			this.setVisible(false);
		} catch (InvalidInputException e) {
			String error = e.getMessage();
			JOptionPane.showMessageDialog(null, error);
		}
	}

	private void LogInAdminActionPerformed(ActionEvent evt) {
		String username = this.textFieldUsername.getText().trim();
		String password = String.valueOf(this.passwordFieldAdmin.getPassword());
		try {
			Block223Controller.login(username, password);
			CreateGamePage adminpage = new CreateGamePage();
			adminpage.setVisible(true);
			this.setVisible(false);
			System.out.println(Block223Application.getCurrentUserRole());
		} catch (InvalidInputException e) {
			String error = e.getMessage();
			JOptionPane.showMessageDialog(null, error);
		}
	}
	
	

	private void RegisterButtonActionPerformed(java.awt.event.ActionEvent evt) {
		String username = this.textFieldUsername.getText().trim();
		String playerPassword = String.valueOf(this.passwordFieldPlayer.getPassword());
		String adminPassword = String.valueOf(this.passwordFieldAdmin.getPassword());
		try {
			Block223Controller.register(username, playerPassword, adminPassword);
			JOptionPane.showMessageDialog(null, "Registration successful");
		} catch (InvalidInputException e) {
			String error = e.getMessage();
			JOptionPane.showMessageDialog(null, error);
		}
	}
	
	
}
