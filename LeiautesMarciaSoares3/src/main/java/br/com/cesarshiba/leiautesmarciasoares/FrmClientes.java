package br.com.cesarshiba.leiautesmarciasoares;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FrmClientes implements Initializable {

    @FXML
    public TableView<ClienteNomeSexoEmail> tblClientes;
    private ObservableList<ClienteNomeSexoEmail> obsClientes;

    @FXML
    public TableColumn<ClienteNomeSexoEmail, String> colNomeCliente;

    @FXML
    public TableColumn<ClienteNomeSexoEmail, String> colSexoCliente;

    @FXML
    public TableColumn<ClienteNomeSexoEmail, String> colEmailCliente;

    @FXML
    public Button btnOk;
    
	public void Ok(ActionEvent e) throws Exception {
		MainClass.idCliente=1;
		
		Stage stage = (Stage) btnOk.getScene().getWindow();
		stage.close();
	}

	public String caminho = "";
	//public String caminho = "C:\\Users\\cesar\\Documents\\Projetos\\JAVA\\LeiautesMarciaSoares\\src\\main\\java\\br\\com\\cesarshiba\\leiautesmarciasoares\\";
	
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		carregaClientes();
	}
    
    private void carregaClientes() {
    	obsClientes = FXCollections.observableArrayList(leClientes());
    	colNomeCliente.setCellValueFactory(new PropertyValueFactory<ClienteNomeSexoEmail,String>("nomeCliente"));
    	colSexoCliente.setCellValueFactory(new PropertyValueFactory<ClienteNomeSexoEmail,String>("sexoCliente"));
    	colEmailCliente.setCellValueFactory(new PropertyValueFactory<ClienteNomeSexoEmail,String>("emailCliente"));
    	tblClientes.setItems(obsClientes);
    	//tblClientes.getColumns().addAll(colNomeCliente,colSexoCliente,colEmailCliente);
    }
    
    private List<ClienteNomeSexoEmail> leClientes() {
    	Scanner scanner;
		List<ClienteNomeSexoEmail> lista = new ArrayList<>();
    	try {
			scanner = new Scanner(new FileInputStream(caminho + "clientes.txt"));
	    	while(scanner.hasNextLine()) {
	    		String[] textoSeparado;
	    		String token = scanner.nextLine();
	    		textoSeparado = token.split(";");
	        	ClienteNomeSexoEmail cliente = new ClienteNomeSexoEmail(textoSeparado[0], textoSeparado[1], textoSeparado[2]);
	    		lista.add(cliente);
	    	}
	    	return lista;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
    }
}
