package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;

import Control_BD.Conectar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.UIManager;

public class principal_admin extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtFecha;
	private JTextField txtUser;
	private JTextField txtContra;
	private JTextField txtDni;

	private JPanel panel;
	private JTextField textField;
	private JTextField textField_3;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_4;
	private JTextField textField_5;
	
	
	private Conectar con;
	/**
	 * Create the frame.
	 */
	public principal_admin(ResultSet usuario) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAltaSocio = new JButton("Alta Socio");
		btnAltaSocio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaSocio();
			}
		});
		btnAltaSocio.setBounds(16, 373, 156, 23);
		contentPane.add(btnAltaSocio);
		
		JButton btnAltaPareja = new JButton("Alta Pareja");
		btnAltaPareja.setBounds(188, 373, 156, 23);
		contentPane.add(btnAltaPareja);
		
		JButton btnAltaLiga = new JButton("Alta Liga");
		btnAltaLiga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaLiga();
			}
		});
		btnAltaLiga.setBounds(188, 407, 156, 23);
		contentPane.add(btnAltaLiga);
		
		JButton btnReservarPista = new JButton("ReservarPista");
		btnReservarPista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reservarPista ps = new reservarPista(usuario);
			}
		});
		btnReservarPista.setBounds(360, 373, 156, 23);
		contentPane.add(btnReservarPista);
		
		JButton btnInscribirLiga = new JButton("Inscribir a la liga");
		btnInscribirLiga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaParticipantes();
			}
		});
		
		btnInscribirLiga.setBounds(360, 407, 156, 23);
		contentPane.add(btnInscribirLiga);
		
		JButton btnAltaAdmin = new JButton("Alta Admin");
		btnAltaAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaAdmin();
			}
		});
		btnAltaAdmin.setBounds(16, 407, 156, 23);
		contentPane.add(btnAltaAdmin);
		
		JButton btnRegistrarResultado = new JButton("Resultado Partido");
		btnRegistrarResultado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRegistrarResultado.setBounds(532, 373, 156, 23);
		contentPane.add(btnRegistrarResultado);
		
		JButton btnModificarPartido = new JButton("Crear Partido");
		btnModificarPartido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				altaPartido();
			}
		});
		btnModificarPartido.setBounds(532, 407, 156, 23);
		contentPane.add(btnModificarPartido);
		
		panel = new JPanel();
		panel.setBounds(10, 11, 684, 350);
		contentPane.add(panel);
		panel.setLayout(null);
		
		setVisible(true);
		
		
	}
	
	public void altaPartido() {
		
		panel.setVisible(false);

		con = new Conectar();
		
		
		panel = new JPanel();
		panel.setBounds(10, 11, 684, 350);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblLiga = new JLabel("ID liga");
		lblLiga.setBounds(233, 148, 127, 20);
		panel.add(lblLiga);
		
		JLabel lblParA = new JLabel("ID Pareja");
		lblParA.setBounds(233, 184, 127, 20);
		panel.add(lblParA);
		
		JLabel lblParB = new JLabel("ID Pareja");
		lblParB.setBounds(233, 219, 127, 20);
		panel.add(lblParB);
		
		JLabel lblFase = new JLabel("Fase De Liga");
		lblFase.setBounds(233, 256, 127, 20);
		panel.add(lblFase);
		JComboBox cbParA = new JComboBox();
		cbParA.setBounds(370, 183, 81, 22);

		JComboBox cbParB = new JComboBox();
		cbParB.setBounds(370, 218, 81, 22);
		
		JComboBox cbFase = new JComboBox();
		cbFase.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		cbFase.setBounds(370, 255, 81, 22);
		
		
		JComboBox cbLiga = new JComboBox();
		cbLiga.setBounds(370, 147, 81, 22);
		cbLiga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet rs;
					int contador = 0;
					int pos = 0;
					int contador2 = 0;
					con.conectar_bd();
					rs = con.buscarParticipantes(Integer.parseInt(""+cbLiga.getSelectedItem()));
					while(rs.next()) {
						
						contador2++;
					}
					String participantes[] = new String[contador2];
					
					rs.beforeFirst();
					int pos2 = 0;
					while(rs.next()) {
					
						participantes[pos2] = rs.getString("participante");
						pos2++;
						
					}
					cbParA.setModel(new DefaultComboBoxModel(participantes));
					cbParB.setModel(new DefaultComboBoxModel(participantes));
					
					con.desconectar_bd();
					
					
				}catch(ClassNotFoundException e1) {
					System.out.println("error de conxion");
				} catch (SQLException e1) {
					System.out.println("error de la ejecucion en base datos" + e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error datos incorrectos o no validos ",
							"Error datos incorrectos", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		
		
		try {
			ResultSet rs;
			int contador = 0;
			int pos = 0;
			contador = 0;
			con.conectar_bd();
			rs = con.allLigas();
			while(rs.next()) {
				
				contador++;
				
			}
			String ligas[] = new String[contador];
			
			rs.beforeFirst();
			pos = 0;
			while(rs.next()) {
			
				ligas[pos] = rs.getString("id_liga");
				pos++;
				
			}
			cbLiga.setModel(new DefaultComboBoxModel(ligas));
			
			con.desconectar_bd();
			
			
		}catch(ClassNotFoundException e) {
			System.out.println("error de conxion");
		} catch (SQLException e1) {
			System.out.println("error de la ejecucion en base datos" + e1.getMessage());
			JOptionPane.showMessageDialog(null, "Error datos incorrectos o no validos ",
					"Error datos incorrectos", JOptionPane.WARNING_MESSAGE);
		}
		
		
		JButton btnCrearPa = new JButton("Crear Partido");
		btnCrearPa.setBounds(233, 297, 218, 23);
		btnCrearPa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					con.conectar_bd();
					con.alta_partido(Integer.parseInt(""+cbLiga.getSelectedItem()),Integer.parseInt(""+cbParA.getSelectedItem()), Integer.parseInt(""+cbParB.getSelectedItem()),
							Integer.parseInt(""+cbFase.getSelectedItem()));
					con.desconectar_bd();
					
				} catch (ClassNotFoundException e1) {
					System.out.println("error al conectarse a la base de datos "+e1.getMessage());
				} catch (SQLException e1) {
					System.out.println("error al ejecutar la base de datos "+e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error datos incorrectos o no validos ",
							"Error datos incorrectos", JOptionPane.WARNING_MESSAGE);
				}
				
			
			}
		});
		
		panel.add(btnCrearPa);
		
		JTextPane txtpnFormularioParaDar = new JTextPane();
		txtpnFormularioParaDar.setEditable(false);
		txtpnFormularioParaDar.setText("Formulario para dar de alta un partido\r\nRecuerde que :\r\n\t- Una liga puede tener maximo de fases:\r\n\tFase 1: 4 partidos\r\n\tFase 2: 2 partidos\r\n\tFase 3: 3 partidos");
		txtpnFormularioParaDar.setBackground(UIManager.getColor("Button.background"));
		txtpnFormularioParaDar.setBounds(192, 34, 300, 120);
		
		panel.add(txtpnFormularioParaDar);
		panel.add(cbLiga);
		panel.add(cbParA);
		panel.add(cbParB);
		panel.add(cbFase);
		panel.setVisible(true);
		
	}
	// no utilizado ni probado
	public void eliminarAdmin() {
		panel.setVisible(false);
		panel = new JPanel();
		panel.setBounds(10, 11, 684, 350);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_8 = new JLabel("Nombre Del admin");
		lblNewLabel_8.setBounds(196, 158, 136, 20);
		panel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Dni");
		lblNewLabel_9.setBounds(196, 117, 136, 20);
		panel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Usuario");
		lblNewLabel_10.setBounds(196, 194, 136, 20);
		panel.add(lblNewLabel_10);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(352, 158, 136, 20);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(352, 117, 136, 20);
		panel.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setEditable(false);
		textField_5.setColumns(10);
		textField_5.setBounds(352, 194, 136, 20);
		panel.add(textField_5);
		
		JButton btnNewButton_1 = new JButton("Buscar Administrador");
		btnNewButton_1.setBounds(196, 253, 136, 20);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Confirmar");
		btnNewButton_1_1.setBounds(352, 253, 136, 20);
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnNewButton_1_1);
		
		JLabel lblNewLabel_11 = new JLabel("Dar de baja A un administrador");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_11.setBounds(228, 30, 227, 20);
		panel.add(lblNewLabel_11);
		
		JTextPane txtpnRelleneUnoDe = new JTextPane();
		txtpnRelleneUnoDe.setBackground(Color.WHITE);
		txtpnRelleneUnoDe.setEditable(false);
		txtpnRelleneUnoDe.setText("Rellene uno de los campos y busque si se encuentra\r\nlos otros campos se rellenaran por completo");
		txtpnRelleneUnoDe.setBounds(196, 61, 292, 41);
		panel.add(txtpnRelleneUnoDe);
		
		panel.setVisible(true);
	}
	// finalizado isn comprobar
	public void altaAdmin() {
		panel.setVisible(false);
		panel = new JPanel();
		panel.setBounds(10, 11, 684, 351);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Rellenar todos los campos para dar de alta al socio");
		lblNewLabel.setBounds(210, 31, 251, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(210, 74, 102, 14);
		panel.add(lblNombre);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento");
		lblFechaNacimiento.setBounds(210, 118, 102, 14);
		panel.add(lblFechaNacimiento);
		
		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(210, 162, 102, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(210, 206, 102, 14);
		panel.add(lblNewLabel_2);
		
		JLabel lblDni = new JLabel("Dni");
		lblDni.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDni.setBounds(210, 250, 102, 14);
		panel.add(lblDni);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(322, 71, 139, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(322, 115, 139, 20);
		panel.add(dateChooser);
		
		txtUser = new JTextField();
		txtUser.setColumns(10);
		txtUser.setBounds(322, 159, 139, 20);
		panel.add(txtUser);
		
		txtContra = new JTextField();
		txtContra.setColumns(10);
		txtContra.setBounds(322, 203, 139, 20);
		panel.add(txtContra);
		
		txtDni = new JTextField();
		txtDni.setColumns(10);
		txtDni.setBounds(322, 247, 139, 20);
		panel.add(txtDni);
		
		JButton btnCrearUser = new JButton("Dar de Alta a un admin");
		btnCrearUser.setBounds(210, 294, 251, 23);
		btnCrearUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String f;
				java.util.Date fecha = dateChooser.getDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				f = sdf.format(fecha);
				try {
					con.conectar_bd();
					con.alta_admin(txtUser.getText(), txtNombre.getText(),
							txtContra.getText(), f, txtDni.getText());
					con.desconectar_bd();
				} catch (SQLException e1) {
					
					System.out.println("Admin Ya creado o Usuario repetido");
					JOptionPane.showMessageDialog(null, "Error datos incorrectos o no validos ",
							"Error datos incorrectos", JOptionPane.WARNING_MESSAGE);
				} catch (ClassNotFoundException e1) {
					System.out.println("Error al conectar al servidor");
				}
				
			}
		});
		panel.add(btnCrearUser);
		
		
		
		panel.setVisible(true);
	}
	// Sin hacer
	public void modificarLiga() {
		panel.setVisible(false);
		panel = new JPanel();
		panel.setBounds(10, 11, 684, 350);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		
		panel.setVisible(true);
	}
	//finalizado
	public void altaLiga() {
		panel.setVisible(false);
		panel = new JPanel();
		panel.setBounds(10, 11, 684, 350);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Formulario para dar de alta una liga");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(251, 32, 185, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblLiga = new JLabel("Nombre Liga");
		lblLiga.setBounds(225, 75, 103, 20);
		panel.add(lblLiga);
		
		JLabel lblNewLabel_4 = new JLabel("Fecha de cracion");
		lblNewLabel_4.setBounds(225, 119, 103, 20);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Fecha de inicio");
		lblNewLabel_5.setBounds(225, 160, 103, 20);
		panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Categoria");
		lblNewLabel_6.setBounds(225, 202, 103, 20);
		panel.add(lblNewLabel_6);
		
		textField = new JTextField();
		textField.setBounds(338, 75, 137, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(338, 202, 137, 20);
		panel.add(textField_3);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(338, 119, 137, 20);
		panel.add(dateChooser);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(338, 160, 137, 20);
		panel.add(dateChooser_1);
		
		JButton btnCrearLiga = new JButton("Confirmar La liga");
		btnCrearLiga.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String fcrea;
				java.util.Date fecha = dateChooser.getDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				fcrea = sdf.format(fecha);
				
				String f;
				java.util.Date fecha2 = dateChooser.getDate();
				f = sdf.format(fecha);
				con = new Conectar();
				try {
					con.conectar_bd();
					con.alta_liga(textField.getText(), fcrea, f, Integer.parseInt(textField_3.getText()));
					con.desconectar_bd();
				} catch (NumberFormatException e1) {
					System.out.println("error de numberFormat");
				} catch (SQLException e1) {
					// falta el mensaje de error
					System.out.println("Error sql " + e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error datos incorrectos o no validos ",
							"Error datos incorrectos", JOptionPane.WARNING_MESSAGE);
				} catch (ClassNotFoundException e1) {
					System.out.println(e1.getMessage());
				}
				
			}
		});
		btnCrearLiga.setBounds(225, 285, 250, 23);
		panel.add(btnCrearLiga);
		
		panel.setVisible(true);
	}
	// finalizado
	public void altaParticipantes() {
		
		panel.setVisible(false);
		panel = new JPanel();
		panel.setBounds(10, 11, 684, 350);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_7 = new JLabel("Participante N\u00BA 1");
		lblNewLabel_7.setBounds(55, 171, 100, 20);
		panel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_7_1 = new JLabel("Participante N\u00BA 2");
		lblNewLabel_7_1.setBounds(266, 171, 100, 20);
		panel.add(lblNewLabel_7_1);
		
		JLabel lblNewLabel_7_2 = new JLabel("Participante N\u00BA 3");
		lblNewLabel_7_2.setBounds(477, 171, 100, 20);
		panel.add(lblNewLabel_7_2);
		
		JLabel lblNewLabel_7_3 = new JLabel("Participante N\u00BA 3");
		lblNewLabel_7_3.setBounds(55, 203, 100, 20);
		panel.add(lblNewLabel_7_3);
		
		JLabel lblNewLabel_7_4 = new JLabel("Participante N\u00BA 4");
		lblNewLabel_7_4.setBounds(266, 203, 100, 20);
		panel.add(lblNewLabel_7_4);
		
		JLabel lblNewLabel_7_5 = new JLabel("Participante N\u00BA 5");
		lblNewLabel_7_5.setBounds(477, 203, 100, 20);
		panel.add(lblNewLabel_7_5);
		
		JLabel lblNewLabel_7_6 = new JLabel("Participante N\u00BA 6");
		lblNewLabel_7_6.setBounds(55, 236, 100, 20);
		panel.add(lblNewLabel_7_6);
		
		JLabel lblNewLabel_7_6_1 = new JLabel("Participante N\u00BA 7");
		lblNewLabel_7_6_1.setBounds(266, 236, 100, 20);
		panel.add(lblNewLabel_7_6_1);
		
		
		JLabel lblNewLabel_12 = new JLabel("ID. Liga");
		lblNewLabel_12.setBounds(477, 102, 100, 20);
		panel.add(lblNewLabel_12);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(576, 101, 45, 22);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(154, 170, 45, 22);
	
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(154, 201, 45, 22);

		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setBounds(154, 235, 45, 22);

		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setBounds(365, 170, 45, 22);

		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setBounds(365, 201, 45, 22);
		
		JComboBox comboBox_7 = new JComboBox();
		comboBox_7.setBounds(365, 235, 45, 22);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(576, 170, 45, 22);

		
		JComboBox comboBox_9 = new JComboBox();
		comboBox_9.setBounds(576, 201, 45, 22);


		ResultSet rs;
		int contador = 0;
		con = new Conectar();
		try {
			
			con.conectar_bd();
			rs = con.buscarParejas();
			while(rs.next()) {
				
				contador++;
				
			}
			String reservas[] = new String[contador];
			
			rs.beforeFirst();
			int pos = 0;
			while(rs.next()) {
			
				reservas[pos] = rs.getString("id_pareja");
				pos++;
			}
		
			
			comboBox_1.setModel(new DefaultComboBoxModel(reservas));
			comboBox_2.setModel(new DefaultComboBoxModel(reservas));
			comboBox_3.setModel(new DefaultComboBoxModel(reservas));
			comboBox_4.setModel(new DefaultComboBoxModel(reservas));
			comboBox_5.setModel(new DefaultComboBoxModel(reservas));
			comboBox_6.setModel(new DefaultComboBoxModel(reservas));
			comboBox_7.setModel(new DefaultComboBoxModel(reservas));
			comboBox_9.setModel(new DefaultComboBoxModel(reservas));
			
			con.desconectar_bd();
			
			con.conectar_bd();
			rs = con.buscarLigas();
			contador = 0;
			while(rs.next()) {
				
				contador++;
				
			}
			String reservas2[] = new String[contador];
			
			rs.beforeFirst();
			int pos2 = 0;
			while(rs.next()) {
			
				reservas2[pos2] = rs.getString("id_liga");
				pos2++;
			}
			
			comboBox.setModel(new DefaultComboBoxModel(reservas2));
			
			con.desconectar_bd();
			
		} catch (SQLException e1) {
			System.out.println("error de sql" + e1.getMessage());
			JOptionPane.showMessageDialog(null, "Error datos incorrectos o no validos " ,
					"Error datos incorrectos", JOptionPane.WARNING_MESSAGE);
		} catch (ClassNotFoundException e1) {
			System.out.println("error de conexion" + e1.getMessage());
		}
		
		
		JTextPane txtpnSeleccioneElIdentificador = new JTextPane();
		txtpnSeleccioneElIdentificador.setBackground(UIManager.getColor("Button.background"));
		txtpnSeleccioneElIdentificador.setText("Seleccione el identificador de las parejas \r\nrecuerde :\r\n\t- No se permiten parejas repetidas\r\n\t- No se permiten participantes reptidos");
		txtpnSeleccioneElIdentificador.setBounds(55, 86, 300, 73);
		panel.add(txtpnSeleccioneElIdentificador);
		
		JButton btnConfir = new JButton("Confirmar eleccion de los participantes");
		btnConfir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int liga = Integer.parseInt(""+comboBox.getSelectedItem());
				try {
					con.conectar_bd();
					con.inscribir_participantes(liga,Integer.parseInt(""+comboBox_1.getSelectedItem()));
					con.inscribir_participantes(liga,Integer.parseInt(""+comboBox_2.getSelectedItem()));
					con.inscribir_participantes(liga,Integer.parseInt(""+comboBox_3.getSelectedItem()));
					con.inscribir_participantes(liga,Integer.parseInt(""+comboBox_4.getSelectedItem()));
					con.inscribir_participantes(liga,Integer.parseInt(""+comboBox_5.getSelectedItem()));
					con.inscribir_participantes(liga,Integer.parseInt(""+comboBox_6.getSelectedItem()));
					con.inscribir_participantes(liga,Integer.parseInt(""+comboBox_7.getSelectedItem()));
					con.inscribir_participantes(liga,Integer.parseInt(""+comboBox_9.getSelectedItem()));
					con.desconectar_bd();
				} catch (NumberFormatException e1) {
					System.out.println(e1.getMessage());
				
				} catch (SQLException e1) {
					// falta poner el mensaje
					System.out.println("Error de SQL " + e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error datos incorrectos o no validos ",
							"Error datos incorrectos", JOptionPane.WARNING_MESSAGE);
				} catch (ClassNotFoundException e1) {
					System.out.println("Error al conectar la base de datos");
				}
			}
		});
		btnConfir.setBounds(204, 294, 273, 23);
		panel.add(btnConfir);
		
		panel.add(comboBox);
		panel.add(comboBox_1);
		panel.add(comboBox_2);
		panel.add(comboBox_4);
		panel.add(comboBox_5);
		panel.add(comboBox_6);
		panel.add(comboBox_7);
		panel.add(comboBox_3);
		panel.add(comboBox_9);
		
		
		panel.setVisible(true);
	}
	// finalizado
	public void altaSocio() {
		panel.setVisible(false);
		panel = new JPanel();
		panel.setBounds(10, 11, 684, 351);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Rellenar todos los campos para dar de alta al socio");
		lblNewLabel.setBounds(210, 31, 251, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(210, 74, 102, 14);
		panel.add(lblNombre);
		
		JLabel lblFecha = new JLabel("Fecha Nacimiento");
		lblFecha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFecha.setBounds(210, 118, 102, 14);
		panel.add(lblFecha);
		
//		JLabel lblNewLabel_1 = new JLabel("Usuario");
//		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
//		lblNewLabel_1.setBounds(210, 162, 102, 14);
//		panel.add(lblNewLabel_1);
		
//		JLabel lblNewLabel_2 = new JLabel("Contrase\u00F1a");
//		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
//		lblNewLabel_2.setBounds(210, 206, 102, 14);
//		panel.add(lblNewLabel_2);
//		
		JLabel lblDni = new JLabel("Dni");
		lblDni.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDni.setBounds(210, 162, 102, 14);
		panel.add(lblDni);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(322, 71, 139, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(322, 115, 139, 20);
		panel.add(dateChooser);
		
//		txtUser = new JTextField();
//		txtUser.setColumns(10);
//		txtUser.setBounds(322, 159, 139, 20);
//		panel.add(txtUser);
////		
//		txtContra = new JTextField();
//		txtContra.setColumns(10);
//		txtContra.setBounds(322, 203, 139, 20);
//		panel.add(txtContra);
		
		txtDni = new JTextField();
		txtDni.setColumns(10);
		txtDni.setBounds(322, 159, 139, 20);
		panel.add(txtDni);
		
		JButton btnCrearUser = new JButton("Dar de Alta Al usuario");
		btnCrearUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String f;
				java.util.Date fecha = dateChooser.getDate();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				f = sdf.format(fecha);
				con = new Conectar();
				try {
					con.conectar_bd();
					con.alta_usuario(txtNombre.getText(), txtNombre.getText(),
							 f, txtDni.getText());
					con.desconectar_bd();
				} catch (SQLException e1) {
					
					System.out.println("Admin Ya creado o Usuario repetido");
					JOptionPane.showMessageDialog(null, "Error datos incorrectos o no validos ",
							"Error datos incorrectos", JOptionPane.WARNING_MESSAGE);
				} catch (ClassNotFoundException e1) {
					System.out.println("error de conexion");
				}
				
			}
		});
		btnCrearUser.setBounds(210, 294, 251, 23);
		
		
		panel.add(btnCrearUser);
		
		
		
		panel.setVisible(true);
	}
}
