package Control_BD;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

public class Conectar {

	
	private String url ;
	
	private String user;
	private String pass;
	
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private CallableStatement cst;
	private ResultSet rs;
	
//	public static void main(String args[]) {
//		Conectar con = new Conectar();
//		try {
//			con.conectar_bd();
//			ResultSet r = con.buscar_admin("administrador", "qwerty2021");
//			r.next();
//			System.out.println(r.getString(3));
//			
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}

	public Conectar() {
		conn = null;
		stmt = null;
		pstmt = null;
		cst = null;
		rs = null;
		user = "root";
		pass = "";
		url = "jdbc:mysql://localhost:3306/padel";
		LocalDate fecha = null;
	 
	}
	/** Conectar y desconectar la base de datos **/
		
	public void conectar_bd() throws SQLException, ClassNotFoundException {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(url, user,pass);

	}
	public void desconectar_bd() throws SQLException {
		
		conn.close();
	}
	
	/** fin de conectar y desconectar **/
		

	/** Resulsets con las consultas **/

	public ResultSet buscar_admin(String user, char[] pasw) throws SQLException {
		String pa = "";
		for (char c : pasw) {
			pa += c;
		}

		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		String query = "select * from socio where socio.usuario = '" + user + "' and socio.pass = '" + pa + "' and socio.administrador = true";
		rs = stmt.executeQuery(query);
		
		return  rs;
	}
	
	public ResultSet buscarParejas() throws SQLException {
		
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		rs = stmt.executeQuery("select * from pareja");
		return rs;
	}
	
	public ResultSet buscarLigas() throws SQLException {
		
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		rs = stmt.executeQuery("select * from liga where fecha_inicio >= now()");
		return rs;
	}
	
	public ResultSet allLigas() throws SQLException {
		
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		rs = stmt.executeQuery("select * from liga");
		return rs;
	}
	
	public ResultSet buscarParticipantes(int id) throws SQLException {
		
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		rs = stmt.executeQuery("select * from participantes_liga where id_liga = '"+id+"'");
		return rs;
	}
	
	
	public ResultSet buscar_socio(String user,char[] pasw) throws SQLException {
		String pa = "";
		for (char c : pasw) {
			pa += c;
		}
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		String query = "select * from socio where socio.usuario = '" + user + "' and socio.pass = '" + pa + "' and socio.administrador = false";
		rs = stmt.executeQuery(query);
		
		return rs;
	}
	
	public ResultSet reservas(String fecha_ele) throws SQLException {
		
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		String query = "select * from reserva where reserva.fecha = '" + fecha_ele + "'";
		rs = stmt.executeQuery(query);
		
		return rs;
	}
	
	public ResultSet misReservas(int id) throws SQLException {
		
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		rs = stmt.executeQuery("select id_reserva,id_pista,fecha,franja from reserva where id_socio = " + id + " and fecha >= now()");
		
		return rs;
	}
	
	public ResultSet buscarAdmin(String dni) {
		
		
		
		return rs;
	}
	
	public ResultSet partidosJugador(String nombre, int cantidad) throws SQLException {
		
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                ResultSet.CONCUR_UPDATABLE);
		
		rs = stmt.executeQuery("select puntos_oro_A,puntos_oro_B,smash_ganadores_A,smash_ganadores_B,errores_no_forzados_A,errores_no_forzados_B,"
				+ "socio.nombre,pareja.id_pareja, partido.id_pareja_a, detalles_partido.puntuacion "
				+ "from detalles_partido "
				+ "inner join partido on partido.id_partido = detalles_partido.id_partido "
				+ "inner join socio "
				+ "inner join pareja "
				+ "where id_socio = (select id_socio from socio where nombre like "+"'"+nombre+"'"+ ")"
				+ "and (pareja.id_pareja = partido.id_pareja_a or pareja.id_pareja = partido.id_pareja_B)"
				+ "and (id_socio = pareja.id_socio_a or id_socio = pareja.id_socio_b) limit "+ cantidad);
			
		
		return rs;
	}
	
	
	/** Fin de las consultas **/
	
	/** 
	 * Update de la cuenta
	 * @throws SQLException 
	 * 
	 * **/
	
	public void updateSocio(String usu,String nom,String pa,String dni, int id) throws SQLException {
		
		pstmt = conn.prepareStatement("update socio set usuario = ?, nombre = ?,pass = ?, dni = ? where id_socio = ?");
		
		pstmt.setString(1, usu);
		pstmt.setString(2, nom);
		pstmt.setString(3, pa);
		pstmt.setString(4, dni);
		pstmt.setInt(5, id);
		
		if (pstmt.executeUpdate() > 0) {
			JOptionPane.showMessageDialog(null, "Se ha modificado correctamente",
					"Modicacion de campos", JOptionPane.INFORMATION_MESSAGE);
			
		}
		
	}
	
	/**
	 * 	fin de los update
	 *  **/
	
	/** Metodos para realizar todo tipo de alta **/
	public void reservas_pista(int pista,int socio,String fecha,String hora_inicio,String hora_fin) throws SQLException {
		
		
		cst= conn.prepareCall("{call reservar_pista(?,?,?,?,?)}");
		
		cst.setInt(1, pista);
		cst.setInt(2, socio);
		cst.setString(3, fecha);
		cst.setString(4, hora_inicio);
		cst.setString(5, hora_fin);
		
		if(cst.executeUpdate() == 1){
			JOptionPane.showMessageDialog(null, "Reserva Añadida Correctamente",
					"Reserva", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	public void alta_usuario(String usuario,String nombre,String fecha_nacimiento,String dni) throws SQLException{
		
		cst= conn.prepareCall("{call alta_usuario(?,?,?,?,?)}");
		
		cst.setString(1, usuario.replace(" ", "_") + "2021");
		cst.setString(2, nombre);
		cst.setString(3, usuario+2021);
		cst.setString(4, fecha_nacimiento);
		cst.setString(5, dni);
	
		if (cst.execute()) {
			JOptionPane.showMessageDialog(null, "Se ha Añadido correctamente",
					"Añadir campos", JOptionPane.INFORMATION_MESSAGE);
			
		}
	}
	
	public void alta_admin(String usuario,String nombre,String pass,String date,String dni) throws SQLException {
		

		cst= conn.prepareCall("{call alta_usuario(?,?,?,?,?,?)}");
		
		cst.setString(1, usuario);
		cst.setString(2, nombre);
		cst.setString(3, pass);
		cst.setString(4, date);
		cst.setString(5, dni);
		cst.setBoolean(6, true);

		if (cst.execute()) {
			JOptionPane.showMessageDialog(null, "Se ha Añadido correctamente",
					"Añadir campos", JOptionPane.INFORMATION_MESSAGE);
			
		}
	}
	
	public void alta_liga(String nombre, String creacion, String fecha_inicio, int categoria) throws SQLException {
		
		cst = conn.prepareCall("{call alta_liga(?,?,?,?)}");
		
		cst.setString(1, nombre);
		cst.setString(2, creacion);
		cst.setString(3, fecha_inicio);
		cst.setInt(4, categoria);
		
		if (cst.execute()) {
			JOptionPane.showMessageDialog(null, "Se ha Añadido correctamente",
					"Añadir campos", JOptionPane.INFORMATION_MESSAGE);
			
		}
		
	}
	
	public void detalles_partido(int partido, int puntos_a, int puntos_b, int smash_a, int smash_b, int errores_a, int errores_b, String puntuacion,int ganador) throws SQLException {
		stmt = conn.createStatement();
		stmt.execute(""
				+ "insert into detalles_partido (id_partido,puntos_oro_a,puntos_oro_b,smash_ganadores_a,smash_ganadores_b,errores_no_forzados_a,errores_no_forzados_b,puntuacion,ganador)"
				+ "values(partido,puntos_a,puntos_b,smash_a,smash_b,errores_a,erores_b,puntuacion,ganador)");
		
		JOptionPane.showMessageDialog(null, "Se ha Añadido correctamente",
		"Añadir campos", JOptionPane.INFORMATION_MESSAGE);
			
		
	}
	
	public void alta_partido(int liga, int pareja_a, int pareja_b,int fase) throws SQLException {
		stmt = conn.createStatement();
		stmt.execute("insert into partido (id_liga,id_pareja_A,id_pareja_B,fase_liga) values("+liga+","+ pareja_a+","+pareja_b+","+fase+")");
		JOptionPane.showMessageDialog(null, "Se ha Añadido correctamente",
				"Añadir campos", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void alta_pareja(int pareja_a, int pareja_b) throws SQLException {
		stmt = conn.createStatement();
		stmt.execute("insert into pareja(id_pareja_a,id_pareja_b) values(pareja_a, pareja_b)");
		JOptionPane.showMessageDialog(null, "Se ha Añadido correctamente",
				"Añadir campos", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void inscribir_participantes(int liga, int pareja) throws SQLException {
		stmt = conn.createStatement();
		stmt.execute("insert into participantes_liga (id_liga,participante) values("+liga+","+ pareja+")");
		JOptionPane.showMessageDialog(null, "Se ha Añadido correctamente",
				"Añadir campos", JOptionPane.INFORMATION_MESSAGE);
	}

	public void partidos(int liga, int pareja_a, int pareja_b, int fase) throws SQLException {
		stmt = conn.createStatement();
		stmt.execute("insert into partido (id_liga,id_pareja_a,id_pareja_b,fase_liga)values(liga,pareja_a,pareja_b,)");
		
		JOptionPane.showMessageDialog(null, "Se ha Añadido correctamente",
				"Añadir campos", JOptionPane.INFORMATION_MESSAGE);
	}
	

	public void cancelarReserva(int id_reser) throws SQLException {
		stmt = conn.createStatement();
		stmt.execute("delete from reserva where id_reserva= '"+id_reser+"'");
		JOptionPane.showMessageDialog(null, "Se ha Eliminado correctamente",
				"Añadir campos", JOptionPane.INFORMATION_MESSAGE);

	}
	/** FIN DE LOS METODOS ALTA **/
	
}
