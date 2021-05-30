package ventanas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Button;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import Control_BD.Conectar;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.TextField;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.UIManager;

public class Login {

	private static JFrame frame;
	private JTextField txtUsuario;
	private JPasswordField pwdPass;
	private int xx,xy;
	private Conectar con;
	
	public static void main (String args[]) {
		Login log = new Login();
	}
	
	

	public Login() {
		initialize();
		con = new Conectar();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frame.getContentPane().setFocusTraversalPolicyProvider(true);
		frame.getContentPane().setBackground(new Color(169, 169, 169));
		frame.getContentPane().setForeground(Color.DARK_GRAY);
		frame.setBounds(100, 100, 1087, 652);
		frame.getContentPane().setLayout(null);
	
		JPanel panel = new JPanel();
		panel.setForeground(new Color(0, 0, 0));
		panel.setBackground(new Color(169, 169, 169));
		panel.setBounds(10, 11, 544, 662);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Athleticus \u00A9 powered by Alexander & David | 2021");
		lblNewLabel_3.setFont(new Font("Arial Black", Font.PLAIN, 15));
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setBounds(33, 528, 462, 27);
		panel.add(lblNewLabel_3);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(22, 565, 483, 2);
		panel.add(separator_2);
		
		JLabel Icon = new JLabel("");
		Icon.setIcon(new ImageIcon(Login.class.getResource("/imagenes/LogotipoAthleticus.png")));
		Icon.setBounds(10, 17, 500, 500);
		panel.add(Icon);
		
		Button button = new Button("Entrar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtUsuario.getText().isEmpty() || pwdPass.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campos Vacios por favor ingrese su usuario o contraseña",
					"Campos Vacios", JOptionPane.WARNING_MESSAGE);
				}else {
					try {
						con.conectar_bd();
						ResultSet rs = con.buscar_socio(txtUsuario.getText(), pwdPass.getPassword());
						
						
						if (rs.next()) {
							
							Usuario_Principal us = new Usuario_Principal(rs);
							
							frame.setVisible(false);
							frame.dispose();
							
						}else {
							JOptionPane.showMessageDialog(null, "Usuario No encontrado",
									"Error de cuenta", JOptionPane.WARNING_MESSAGE);
						}
						
					} catch (ClassNotFoundException e1) {
						System.out.println("error al conectar" + e1.getMessage());
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error datos incorrectos o no validos ",
								"Error datos incorrectos", JOptionPane.WARNING_MESSAGE);
						
					}
				
				}
			}
		});
		button.setFont(new Font("Arial Black", Font.BOLD, 17));
		button.setForeground(new Color(255, 255, 255));
		button.setBackground(new Color(255, 0, 0));
		button.setBounds(582, 289, 394, 54);
		frame.getContentPane().add(button);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtUsuario.setBounds(582, 118, 394, 48);
		frame.getContentPane().add(txtUsuario);
		txtUsuario.setColumns(15);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(584, 164, 394, 2);
		frame.getContentPane().add(separator);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(new Font("Arial Black", Font.BOLD, 17));
		lblUsuario.setBounds(582, 89, 80, 31);
		frame.getContentPane().add(lblUsuario);
				
		JLabel lblPass = new JLabel("Password");
		lblPass.setForeground(new Color(255, 255, 255));
		lblPass.setFont(new Font("Arial Black", Font.BOLD, 17));
		lblPass.setBounds(582, 186, 105, 31);
		frame.getContentPane().add(lblPass);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(584, 263, 394, 2);
		frame.getContentPane().add(separator_1);
		
		pwdPass = new JPasswordField();
		pwdPass.setBounds(582, 217, 394, 48);
		frame.getContentPane().add(pwdPass);
	
	
		JLabel lblNewLabel_2 = new JLabel("Inicia sesion");
		lblNewLabel_2.setBounds(791, 10, 150, 53);
		frame.getContentPane().add(lblNewLabel_2);
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Arial Black", Font.BOLD, 20));
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(SystemColor.text);
		separator_3.setBounds(991, 557, -416, -15);
		frame.getContentPane().add(separator_3);
		
		JLabel lblAdmin = new JLabel("Administradores");
		lblAdmin.setForeground(Color.WHITE);
		lblAdmin.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAdmin.setBackground(SystemColor.menu);
		lblAdmin.setBounds(709, 515, 141, 20);
		frame.getContentPane().add(lblAdmin);
		
		Button btnAdmin = new Button("Iniciar Sesion");
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtUsuario.getText().isEmpty() || pwdPass.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Campos Vacios",
					"Campos Vacios", JOptionPane.WARNING_MESSAGE);
				}else {
					try {
						con.conectar_bd();
						ResultSet rs = con.buscar_admin(txtUsuario.getText(), pwdPass.getPassword());
						
						
						if (rs.next()) {
							
							principal_admin us = new principal_admin(rs);
							
							frame.setVisible(false);
							frame.dispose();
							
						}else {
							JOptionPane.showMessageDialog(null, "Administrador No encontrado",
									"Error de cuenta", JOptionPane.WARNING_MESSAGE);
						}
						
					} catch (ClassNotFoundException e1) {
						System.out.println("error al conectar" + e1.getMessage());
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error datos incorrectos o no validos ",
								"Error datos incorrectos", JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}
		});
		btnAdmin.setForeground(Color.WHITE);
		btnAdmin.setFont(new Font("Arial Black", Font.BOLD, 17));
		btnAdmin.setBackground(Color.BLACK);
		btnAdmin.setBounds(582, 541, 394, 54);
		frame.getContentPane().add(btnAdmin);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(584, 427, 394, -4);
		frame.getContentPane().add(separator_4);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(584, 540, 394, 2);
		frame.getContentPane().add(separator_5);
		
		JButton btnExit = new JButton("X");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnExit.setForeground(Color.RED);
		btnExit.setBounds(1004, 26, 59, 23);
		frame.getContentPane().add(btnExit);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
