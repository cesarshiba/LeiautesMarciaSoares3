package br.com.cesarshiba.leiautesmarciasoares;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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
    public TextField txtNomePaciente;

    @FXML
    public TextField txtEmailPaciente;

    @FXML
    public RadioButton optFeminino;

    @FXML
    public RadioButton optMasculino;

    @FXML
    public TextField txtEnderecoPaciente;

    @FXML
    public TextField txtTelefonePaciente;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnVoltar.setOnAction(value -> {
			Stage stage1 = (Stage) btnVoltar.getScene().getWindow();
			stage1.close();
		});
		btnVoltar.setOnMouseEntered(value -> {
			btnVoltar.setStyle("-fx-background-color:#000000; -fx-border-color:  #ffffff; -fx-border-radius: 20; -fx-background-radius: 20");
		});
		btnVoltar.setOnMouseExited(value -> {
			btnVoltar.setStyle("-fx-background-color: #5864ff; -fx-border-color: #ffffff; -fx-border-radius: 20");
		});
		btnSalvar.setOnMouseEntered(value -> {
			btnSalvar.setStyle("-fx-background-color:#000000; -fx-border-color:  #ffffff; -fx-border-radius: 20; -fx-background-radius: 20");
		});
		btnSalvar.setOnMouseExited(value -> {
			btnSalvar.setStyle("-fx-background-color: #5864ff; -fx-border-color: #ffffff; -fx-border-radius: 20");
		});
	}
}