package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Control_BD.Conectar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Usuario_Principal extends JFrame {

	private Conectar con;
	private ResultSet user;
	private String datos[][];
	
	private JScrollPane scrollPane;
	private JPanel contentPane;
	private JPanel panel;
	private JButton btnBuscadorJugador;
	private JButton btnHorario;
	private JButton btnReservar;
	private JButton btnModificar;
	private JButton btnBuscador;
	private JTextField txtUsuario;
	private JTextField txtNombreJ;
	private JTextField txtCantidad;
	private JTextField txtNombre;
	private JTextField txtPass;
	private JTextField txtDni;
	private JButton btnAceptar;
	private JTable table;

	private ResultSet rs;
	
	/**
	 * Create the frame.
	 */
	public Usuario_Principal(ResultSet usuario) {
		user = usuario;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(240, 240, 580, 363);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnBuscadorJugador = new JButton("Buscar Jugador");
		btnBuscadorJugador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				partidosJugados();
			}
		});
		btnBuscadorJugador.setBounds(280, 295, 140, 23);
		contentPane.add(btnBuscadorJugador);
		
		btnReservar = new JButton("Reservar Pista");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservarPista ps = new reservarPista(usuario);
			}
		});
		btnReservar.setBounds(140, 295, 140, 23);
		contentPane.add(btnReservar);
		
		/** Panel con la actualizacion de datos **/
		panel = new JPanel();
		panel.setBounds(10, 11, 544, 273);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblUser = new JLabel("Usuario");
		lblUser.setBounds(75, 47, 173, 20);
		panel.add(lblUser);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(75, 72, 173, 20);
		panel.add(lblNombre);
		
		JLabel lblPass = new JLabel("Password");
		lblPass.setBounds(75, 97, 173, 20);
		panel.add(lblPass);
		
		JLabel lblDni = new JLabel("Dni");
		lblDni.setBounds(75, 122, 173, 20);
		panel.add(lblDni);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(258, 47, 173, 20);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(258, 72, 173, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtPass = new JTextField();
		txtPass.setBounds(258, 97, 173, 20);
		panel.add(txtPass);
		txtPass.setColumns(10);
		
		txtDni = new JTextField();
		txtDni.setBounds(258, 122, 173, 20);
		panel.add(txtDni);
		txtDni.setColumns(10);
		
		btnAceptar = new JButton("Actualizar Campos de la cuenta");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					con = new Conectar();
					con.conectar_bd();
					con.updateSocio(txtUsuario.getText(), txtNombre.getText(), txtPass.getText(), txtDni.getText(),usuario.getInt("id_socio"));
					con.desconectar_bd();
				} catch (ClassNotFoundException e1) {
					System.out.println("error de clase");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Modificacion No se puede realizar por: "+ e1.getMessage(),
							"Reserva", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnAceptar.setBounds(75, 179, 356, 23);
		panel.add(btnAceptar);
		
	
		
		btnModificar = new JButton("Modificar Cuenta");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modificarUsuario(usuario);
			}
		});
		btnModificar.setBounds(420, 295, 140, 23);
		contentPane.add(btnModificar);
		btnHorario = new JButton("Horario partido");
		btnHorario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				misReservas(usuario);
			}
		});
		btnHorario.setBounds(0, 295, 140, 23);
		contentPane.add(btnHorario);
		
		/** Fin de la actualizacion de datos **/ 
		setVisible(true);
	}
	
	public void misReservas(ResultSet usuario) {
		panel.setVisible(false);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 544, 275);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNum = new JLabel("Numero de reserva");
		lblNum.setBounds(154, 25, 122, 22);
		
		JLabel lblHoraInicio = new JLabel("Hora Inicio");
		lblHoraInicio.setBounds(154, 71, 122, 22);
		
		JLabel lblPista = new JLabel("Numero de pista");
		lblPista.setBounds(154, 115, 122, 22);
		
		JComboBox<?> comboBox = new JComboBox();
		comboBox.setBounds(334, 25, 50, 22);

		JComboBox<?> comboBox_1 = new JComboBox();
		comboBox_1.setBounds(334, 70, 50, 22);
		comboBox_1.setEnabled(false);
		
		JComboBox<?> comboBox_2 = new JComboBox();
		comboBox_2.setBounds(334, 115, 50, 22);
		comboBox_2.setEnabled(false);

		int contador = 0;
		con = new Conectar();
		try {
			
			con.conectar_bd();
			rs = con.misReservas(usuario.getInt("id_socio"));
			while(rs.next()) {
				contador++;
				
			}
			String reservas2[] = new String[contador];
			String reservas3[] = new String[contador];
			String reservas[] = new String[contador];
			rs.beforeFirst();
			int pos = 0;
			while(rs.next()) {
			
				reservas3[pos] = rs.getString("id_pista");
				reservas2[pos] = rs.getString("franja");
				reservas[pos] = rs.getString("id_reserva");
				
				pos++;
			}
			
			comboBox.setModel(new DefaultComboBoxModel(reservas));
			comboBox_1.setModel(new DefaultComboBoxModel(reservas2));
			comboBox_2.setModel(new DefaultComboBoxModel(reservas3));
			
			
		} catch (SQLException e1) {
			System.out.println("error de sql" + e1.getMessage());
			JOptionPane.showMessageDialog(null, "Modificacion No se puede realizar por: "+ e1.getMessage(),
					"Reserva", JOptionPane.WARNING_MESSAGE);
		} catch (ClassNotFoundException e1) {
			System.out.println("error de conexion" + e1.getMessage());
		}
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox_1.setSelectedIndex(comboBox.getSelectedIndex());
				comboBox_2.setSelectedIndex(comboBox.getSelectedIndex());
			}
		});
//		comboBox_1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				comboBox_2.setSelectedIndex(comboBox_1.getSelectedIndex());
//				comboBox.setSelectedIndex(comboBox_1.getSelectedIndex());
//			}
//		});
//		comboBox_2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				comboBox.setSelectedIndex(comboBox_2.getSelectedIndex());
//				comboBox_1.setSelectedIndex(comboBox_2.getSelectedIndex());
//			}
//		});
		
		
		JButton btnCancelarReserva = new JButton("Cancelar Reserva");
		btnCancelarReserva.setBounds(151, 211, 213, 23);
		btnCancelarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(""+comboBox_2.getSelectedItem());
				try {
					con.cancelarReserva(Integer.parseInt( ""+comboBox.getSelectedItem()));
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Error de formato "+ e1.getMessage(),
							"Reserva", JOptionPane.WARNING_MESSAGE);
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Modificacion No se puede realizar por: "+ e1.getMessage(),
							"Reserva", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		JLabel lblNum_1 = new JLabel("Numero de reserva");
		lblNum_1.setBounds(154, 116, 122, 22);
		
		panel.add(btnCancelarReserva);
		panel.add(lblNum);
		panel.add(lblHoraInicio);
		panel.add(lblPista);
		panel.add(comboBox);
		panel.add(comboBox_1);
		panel.add(comboBox_2);
		panel.setVisible(true);
	}
	
	public void partidosJugados() {
		
		panel.setVisible(false);
		
		
		panel = new JPanel();
		panel.setBounds(10, 11, 520, 272);
		contentPane.add(panel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 134, 544, 135);
		panel.add(scrollPane);

		JLabel lblNombre = new JLabel("Nombre Jugador");
		lblNombre.setBounds(121, 31, 46, 14);
		panel.add(lblNombre);

		JLabel lblCantidad = new JLabel("Cantidad De partidos a mirar");
		lblCantidad.setBounds(121, 68, 46, 14);
		panel.add(lblCantidad);

		txtNombre = new JTextField();
		txtNombre.setBounds(212, 28, 86, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);

		txtCantidad = new JTextField();
		txtCantidad.setBounds(212, 65, 86, 20);
		panel.add(txtCantidad);
		txtCantidad.setColumns(10);
		
		table = new JTable();
		table.setEnabled(false);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setCellSelectionEnabled(true);
		
		btnBuscador = new JButton("Buscar Jugador");
		btnBuscador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				
					con = new Conectar();
					con.conectar_bd();
					
					ResultSet rs = con.partidosJugador(txtNombre.getText(), Integer.parseInt(txtCantidad.getText()));
					
					int num_col = 0;
					while (rs.next()) {
						num_col ++;
					}
					
					rs.beforeFirst();
					datos = new String[num_col][5];
					
					while (rs.next()) {
						int contador = 0;
						
						datos[contador][0] = rs.getString("socio.nombre");
						if(rs.getInt("id_pareja") == rs.getInt("id_pareja_a")) {
							datos[contador][1] = rs.getString("puntos_oro_a");
							datos[contador][2] = rs.getString("errores_no_forzados_a");
							datos[contador][3] = rs.getString("smash_ganadores_A");
							datos[contador][4] = rs.getString("puntuacion");
						}else {
							datos[contador][1] = rs.getString("puntos_oro_b");
							datos[contador][2] = rs.getString("errores_no_forzados_b");
							datos[contador][3] = rs.getString("smash_ganadores_b");
							datos[contador][4] = rs.getString("puntuacion");
						}
						
						contador ++;
					}
					table = new JTable();
					table.setModel(new DefaultTableModel(
						datos,
						new String[] {
							"Nombre Buscado", "Puntos de oro del partido", "Smash Ganadores del partido", "Errors No forzados del partido", "Puntuacion"
						}
					));
					scrollPane.setViewportView(table);
					
					con.desconectar_bd();
				} catch (ClassNotFoundException e1) {
				
					System.out.println(e1.getMessage() );
				} catch (SQLException e2) {
					JOptionPane.showMessageDialog(null, "Modificacion No se puede realizar por: "+ e2.getMessage(),
							"Reserva", JOptionPane.WARNING_MESSAGE);
					System.out.println(e2.getMessage());
				}
			
			}});
		
		panel.add(table);
		btnBuscador.setBounds(75,179,356,23);
		panel.add(btnBuscador);
		
		panel.setVisible(true);	
	}

	public void pintarReservas() {
	panel.setVisible(false);
	panel = new JPanel();
	panel.setBounds(10, 11, 520, 272);
	contentPane.add(panel);
	
	try {
		
		con = new Conectar();
		con.conectar_bd();
		
		ResultSet rs = con.misReservas(user.getInt("id_socio"));
		
		int num_col = 0;
		while (rs.next()) {
			num_col ++;
		}
		
		rs.beforeFirst();
		datos = new String[num_col][3];
		while (rs.next()) {
			for (int i = 0; i < 3 ;i++) {
				datos[rs.getRow()-1][i] = rs.getString(i+1);
			}
			
		}
		table = new JTable();
		table.setModel(new DefaultTableModel(
			datos,
			new String[] {
				"Numero de pista", "Fecha De la reserva", "Hora de la reserva"
			}
		));
		panel.add(table);
		
		con.desconectar_bd();
	} catch (ClassNotFoundException e1) {
	
		System.out.println(e1.getMessage() );
	} catch (SQLException e2) {
		JOptionPane.showMessageDialog(null, "Modificacion No se puede realizar por: "+ e2.getMessage(),
				"Reserva", JOptionPane.WARNING_MESSAGE);
		System.out.println(e2.getMessage());
	}
	panel.setVisible(true);
}
	
	public void modificarUsuario(ResultSet usuario) {
		panel.setVisible(false);
		panel = new JPanel();
		panel.setBounds(10, 11, 520, 272);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(75, 72, 173, 20);
		
		
		JLabel lblPass = new JLabel("Password");
		lblPass.setBounds(75, 97, 173, 20);
		
		JLabel lblDni = new JLabel("Dni");
		lblDni.setBounds(75, 122, 173, 20);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(258, 47, 173, 20);
		txtUsuario.setColumns(10);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(258, 72, 173, 20);
		txtNombre.setColumns(10);
		
		txtPass = new JTextField();
		txtPass.setBounds(258, 97, 173, 20);
		txtPass.setColumns(10);
		
		txtDni = new JTextField();
		txtDni.setBounds(258, 122, 173, 20);
		txtDni.setColumns(10);
		
		JLabel lblUser = new JLabel("Usuario");
		lblUser.setBounds(75, 47, 173, 20);
		try {

			txtUsuario.setText(usuario.getString("usuario"));
			txtPass.setText(usuario.getString("pass"));
			txtNombre.setText(usuario.getString("Nombre"));
			txtDni.setText(usuario.getString("Dni"));
			
		} catch (SQLException e2) {
			System.out.println("error al ejecutar en la BD");
			JOptionPane.showMessageDialog(null, "Modificacion No se puede realizar por: "+ e2.getMessage(),
					"Reserva", JOptionPane.WARNING_MESSAGE);
		} 
		
		panel.add(lblNombre);
		panel.add(lblPass);
		panel.add(lblDni);
		panel.add(txtUsuario);
		panel.add(txtNombre);
		panel.add(txtPass);
		panel.add(txtDni);
		panel.add(lblUser);
		
		btnAceptar = new JButton("Actualizar Campos de la cuenta");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					con.conectar_bd();
					con.updateSocio(txtUsuario.getText(), txtNombre.getText(), txtPass.getText(), txtDni.getText(),usuario.getInt("id_socio"));
					con.desconectar_bd();
				} catch (ClassNotFoundException e1) {
					System.out.println("error de clase");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Modificacion No se puede realizar por: "+ e1.getMessage(),
							"Reserva", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnAceptar.setBounds(75, 179, 356, 23);
		panel.add(btnAceptar);
		panel.setVisible(true);
	}
}
