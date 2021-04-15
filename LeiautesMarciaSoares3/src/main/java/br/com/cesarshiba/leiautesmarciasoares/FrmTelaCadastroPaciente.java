package br.com.cesarshiba.leiautesmarciasoares;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

public class FrmTelaCadastroPaciente implements Initializable {

    @FXML
    public BorderPane root;

    @FXML
    public Button btnSalvar;

    @FXML
    public Button btnVoltar;

    @FXML
    public TextField txtNOMECLIENTE;

    @FXML
    public TextField txtEMAILCLIENTE;

    @FXML
    public TextField txtENDERECOCLIENTE;

    @FXML
    public TextField txtTELEFONECLIENTE;

    @FXML
    public TextField txtPROFISSAOCLIENTE;

    @FXML
    public TextField txtESTADOCIVILCLIENTE;

    @FXML
    public TextField txtESCOLARIDADECLIENTE;

    @FXML
    public TextField txtINDICACAOCLIENTE;

    @FXML
    public DatePicker txtDATANASCIMENTOCLIENTE;

    @FXML
    public Label lblIDADE;

    @FXML
    public RadioButton optFeminino;

    @FXML
    public RadioButton optMasculino;

    @FXML
    public TextArea txtOBJETIVOCLIENTE;

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnVoltar.setOnAction(value -> {
			Stage stage1 = (Stage) btnVoltar.getScene().getWindow();
			stage1.close();
		});
		btnVoltar.setOnMouseEntered(value -> {
			btnVoltar.setStyle("-fx-background-color:#000000; -fx-border-color:  #ffffff; -fx-border-radius: 20; -fx-background-radius: 20; -fx-text-fill: #ffffff");
		});
		btnVoltar.setOnMouseExited(value -> {
			btnVoltar.setStyle("-fx-background-color: #f7ebc3; -fx-border-color: #000000; -fx-border-radius: 20; -fx-background-radius: 20");
		});
		btnSalvar.setOnMouseEntered(value -> {
			btnSalvar.setStyle("-fx-background-color:#000000; -fx-border-color:  #ffffff; -fx-border-radius: 20; -fx-background-radius: 20; -fx-text-fill: #ffffff");
		});
		btnSalvar.setOnMouseExited(value -> {
			btnSalvar.setStyle("-fx-background-color: #f7ebc3; -fx-border-color: #000000; -fx-border-radius: 20; -fx-background-radius: 20");
		});
		if (MainClass.idCliente != 0) {
			clsAcessaDB db = new clsAcessaDB();
			try {
				Cliente cliente = db.leCliente(MainClass.idCliente);
				carregaTela(cliente);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void carregaTela(Cliente cliente) {
		txtNOMECLIENTE.setText(cliente.getTXTNOMECLIENTE());
		txtEMAILCLIENTE.setText(cliente.getTXTEMAILCLIENTE());
		if (cliente.getTXTSEXOCLIENTE().equals("Feminino")){
			optFeminino.setSelected(true);
		} else {
			optMasculino.setSelected(true);
		}
		LocalDate data = LocalDate.parse(cliente.getTXTDATANASCIMENTOCLIENTE());
		LocalDate hoje = LocalDate.now();
		Period periodo = Period.between(data, hoje);
		txtDATANASCIMENTOCLIENTE.setValue(data);
		lblIDADE.setText(String.valueOf(periodo.getYears()));
		txtENDERECOCLIENTE.setText(cliente.getTXTENDERECOCLIENTE());
		txtTELEFONECLIENTE.setText(cliente.getTXTTELEFONECLIENTE());
		txtPROFISSAOCLIENTE.setText(cliente.getTXTPROFISSAOCLIENTE());
		txtESTADOCIVILCLIENTE.setText(cliente.getTXTESTADOCIVILCLIENTE());
		txtESCOLARIDADECLIENTE.setText(cliente.getTXTESCOLARIDADECLIENTE());
		txtINDICACAOCLIENTE.setText(cliente.getTXTINDICACAOCLIENTE());
		txtOBJETIVOCLIENTE.setText(cliente.getTXTOBJETIVOCLIENTE());
	}
}