package br.com.cesarshiba.leiautesmarciasoares;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class clsAcessaDB {

	private String url = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=C:\\Users\\cesar\\Documents\\Projetos\\Marcia Soares\\Access\\versao teste\\msbk2018.accdb;DriverID=22;READONLY=true}";

	public List<ClienteNomeSexoEmail> listaClientes() throws SQLException {
		String comando = "SELECT txtNOMECLIENTE, txtSEXOCLIENTE, txtEMAILCLIENTE FROM TAB_CLIENTE";
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection conn = DriverManager.getConnection(url , "" , "");
			try(PreparedStatement stmt = conn.prepareStatement(comando)){
				List<ClienteNomeSexoEmail> lista = new ArrayList<>();
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						ClienteNomeSexoEmail clientes = new ClienteNomeSexoEmail(rs.getString("txtNOMECLIENTE"),rs.getString("txtSEXOCLIENTE"),rs.getString("txtEMAILCLIENTE"));
						lista.add(clientes);
					}
					return lista;
				} catch(SQLException e) {
					System.out.println(e.getMessage());
					return lista;
				}
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
	}
	
	public String primeiroCliente() throws SQLException {
		String comando = "SELECT txtNOMECLIENTE FROM TAB_CLIENTE where ID = 3";
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			Connection conn = DriverManager.getConnection(url , "" , "");
			try(PreparedStatement stmt = conn.prepareStatement(comando)){
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						return rs.getString("txtNOMECLIENTE");
					}
					return null;
				} catch(SQLException e) {
					System.out.println(e.getMessage());
					return null;
				}
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return null;
		}
	}
}
