package ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Iterator;

import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import com.toedter.calendar.JDayChooser;

import Control_BD.Conectar;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JCalendar;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class reservarPista extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	private JDateChooser dateChooser;
	private JScrollPane scrollPane;
	private JComboBox comBoxPista;
	private JComboBox cbIni;
	private JComboBox cbFin;
	
	private String felegida;
	private String[][] datos;
	private Conectar con;
	private ResultSet rs;
	private String f;
	
/** modificar los datos que contiene la tabla y cargar la tabla con los datos nuevos **/
	public void iniciar_datos() {
		datos = new String[13][6];
		int horas = 10;
		for (int i = 0; i < datos.length; i++) {
			datos[i][0] = horas + ":00";
			horas ++;
		}
	
		try {
			
			con.conectar_bd();
			java.util.Date fecha = dateChooser.getDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			f = sdf.format(fecha);
			rs = con.reservas(f);
			while (rs.next()) {
				for (int i = rs.getInt("franja"); i <= rs.getInt("Hora_fin"); i++) {
					datos[rs.getInt("franja")-10][rs.getInt("id_pista")] = "Reservado";
				}
				
			}
			
		} catch (ClassNotFoundException e1) {
			System.out.println("error en la reserva");
		} catch (SQLException e1) {
			System.out.println("error en la reserva");
		}
		
	}
	
	public void cargarTabla() {
		table.setModel(new DefaultTableModel(
				datos,
				new String[] {
					"Horarios", "Pista 1", "Pista 2", "Pista 3", "Pista 4", "Pista 5"
				}
				
			));
	
		scrollPane.setViewportView(table);
	}

	/** Fin de la modificacion **/
	
	public reservarPista(ResultSet user) {
		try {
			user.beforeFirst();
			user.next();
		} catch (SQLException e2) {
			System.out.println(e2.getMessage());
		}
		
		con = new Conectar();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 807, 497);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 176, 791, 282);
		scrollPane.setEnabled(false);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setEnabled(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		
		
		JLabel lblFecha = new JLabel("Fecha");
		lblFecha.setBounds(31, 24, 89, 23);
		contentPane.add(lblFecha);
		
		JLabel Hora = new JLabel("Hora Inicial");
		Hora.setBounds(31, 67, 89, 23);
		contentPane.add(Hora);
		
		cbIni = new JComboBox();
		cbIni.setMaximumRowCount(13);
		cbIni.setModel(new DefaultComboBoxModel(new String[] {"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"}));
		cbIni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbFin.setSelectedIndex(cbIni.getSelectedIndex());
			}
		});
		cbIni.setBounds(146, 68, 46, 20);
		contentPane.add(cbIni);
		
		JLabel lblHoraFinal = new JLabel("Hora Final");
		lblHoraFinal.setBounds(31, 102, 89, 23);
		contentPane.add(lblHoraFinal);
		
		cbFin = new JComboBox();
		cbFin.setEnabled(false);
		cbFin.setMaximumRowCount(13);
		cbFin.setModel(new DefaultComboBoxModel(new String[] {"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"}));
		cbFin.setBounds(146, 102, 46, 20);
		contentPane.add(cbFin);
		
	
		
		
		JButton btnReservar = new JButton("Reservar");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					
					int id = user.getInt("id_socio");
					
					con.conectar_bd();
					con.reservas_pista(Integer.parseInt(String.valueOf(comBoxPista.getSelectedItem())) ,id, f, ""+cbIni.getSelectedItem(), ""+cbFin.getSelectedItem());
					
					iniciar_datos();
					cargarTabla();
					
				} catch (ClassNotFoundException e1) {
					System.out.println(e1.getMessage() + "Class");
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Reserva No se puede realizar"+ e1.getMessage(),
							"Reserva", JOptionPane.WARNING_MESSAGE);
				
				}
				
			}
		});
		btnReservar.setBounds(31, 141, 89, 23);
		contentPane.add(btnReservar);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(573, 24, 46, 14);
	
		contentPane.add(lblNombre);
		
		JLabel lblDni = new JLabel("Dni");
		lblDni.setBounds(573, 61, 46, 14);		
		contentPane.add(lblDni);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(623, 21, 158, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(623, 58, 158, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		try {
			textField.setText(user.getString("nombre"));
			textField_1.setText(user.getString("dni"));
		} catch (SQLException e1) {
			System.out.println("El usuario no se ha cargado");
		}
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Volver al menu  anterior
			}
		});
		btnCancelar.setBounds(146, 141, 89, 23);
		contentPane.add(btnCancelar);
		
		dateChooser = new JDateChooser("yyyy/MM/dd","####/##/##",'_');
		dateChooser.setDate(Date.valueOf(LocalDate.now()));
		
		
		iniciar_datos();
		cargarTabla();
		
		dateChooser.setBounds(146, 24, 89, 20);
		contentPane.add(dateChooser);
		
		JButton btnBuscar = new JButton("Buscar Fecha");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciar_datos();
				cargarTabla();
			}
		});
		btnBuscar.setBounds(260, 24, 158, 23);
		contentPane.add(btnBuscar);
		
		JLabel lblPista = new JLabel("Numero Pista");
		lblPista.setBounds(260, 67, 89, 23);
		contentPane.add(lblPista);
		
		comBoxPista = new JComboBox();
		comBoxPista.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5"}));
		comBoxPista.setBounds(380, 68, 38, 20);
		contentPane.add(comBoxPista);
		
		
		setVisible(true);
	
	}
}
