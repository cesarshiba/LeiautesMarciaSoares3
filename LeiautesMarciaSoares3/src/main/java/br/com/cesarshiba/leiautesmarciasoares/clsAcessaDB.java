package br.com.cesarshiba.leiautesmarciasoares;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class clsAcessaDB {

	private String url = "jdbc:mysql://localhost:3306/editortexto01?useTimezone=true&serverTimezone=UTC";
	private String usuario="root";
	private String senha="190798";

	public List<ClienteNomeSexoEmail> listaClientes() throws SQLException {
		String comando = "SELECT id, txtNOMECLIENTE, txtSEXOCLIENTE, txtEMAILCLIENTE, txtOBJETIVOCLIENTE FROM marciasoaresv0.TAB_CLIENTE";
		try(Connection conn = DriverManager.getConnection(url,usuario,senha)){
			try(PreparedStatement stmt = conn.prepareStatement(comando)){
				List<ClienteNomeSexoEmail> lista = new ArrayList<>();
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						ClienteNomeSexoEmail clientes = new ClienteNomeSexoEmail(rs.getInt("id"),rs.getString("txtNOMECLIENTE"),rs.getString("txtSEXOCLIENTE"),
								rs.getString("txtEMAILCLIENTE"),rs.getString("txtOBJETIVOCLIENTE"));
						lista.add(clientes);
					}
					return lista;
				} catch(SQLException e) {
					System.out.println(e.getMessage());
					return lista;
				}
			}
		}
	}

	public Cliente leCliente(int id) throws SQLException {
		String comando = "SELECT id, txtNOMECLIENTE, txtSEXOCLIENTE, dataNASCIMENTOCLIENTE, txtEMAILCLIENTE, txtPROFISSAOCLIENTE, txtTELEFONECLIENTE, " + 
				"txtENDERECOCLIENTE, txtESTADOCIVILCLIENTE, txtESCOLARIDADECLIENTE, txtINDICACAOCLIENTE, txtOBJETIVOCLIENTE FROM marciasoaresv0.TAB_CLIENTE " +
				"where id = " + id;
		try(Connection conn = DriverManager.getConnection(url,usuario,senha)){
			try(PreparedStatement stmt = conn.prepareStatement(comando)){
				try(ResultSet rs = stmt.executeQuery()){
					while(rs.next()) {
						return new Cliente(rs.getInt("id"),rs.getString("txtNOMECLIENTE"),rs.getString("txtSEXOCLIENTE"),rs.getString("dataNASCIMENTOCLIENTE"),
								rs.getString("txtEMAILCLIENTE"),rs.getString("txtPROFISSAOCLIENTE"),rs.getString("txtTELEFONECLIENTE"),rs.getString("txtENDERECOCLIENTE"),
								rs.getString("txtESTADOCIVILCLIENTE"),rs.getString("txtESCOLARIDADECLIENTE"),rs.getString("txtINDICACAOCLIENTE"),rs.getString("txtOBJETIVOCLIENTE"));
					}
				} catch(SQLException e) {
					System.out.println(e.getMessage());
					return null;
				}
			}
		}
		return null;
	}
}
