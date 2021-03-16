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

public class FrmTelaMedidas implements Initializable {

    @FXML
    public BorderPane root;

    @FXML
    public Button btnSalvar;

    @FXML
    public Button btnVoltar;

    @FXML
    public TextField txtAltura;

    @FXML
    public RadioButton optBaixaAtividadeFisica;

    @FXML
    public RadioButton optMediaAtividadeFisica;

    @FXML
    public RadioButton optAltaAtividadeFisica;

    @FXML
    public TextField txtVET;

    @FXML
    public TextField txtImpedancia;

    @FXML
    public TextField txtPesoAtual;

    @FXML
    public TextField txtPesoDesejado;

    @FXML
    public TextField txtPesoVariacaoMinima;

    @FXML
    public TextField txtPesoVariacaoMaxima;

    @FXML
    public TextField txtTMBTotal;

    @FXML
    public TextField txtTMBTotalPeso;

    @FXML
    public TextField txtIMCCalculado;

    @FXML
    public TextField txtIMCMinimo;

    @FXML
    public TextField txtIMCMaximo;

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
			btnVoltar.setStyle("-fx-background-color:  #b3d37a; -fx-border-color: #414247; -fx-border-radius: 20");
		});
		btnSalvar.setOnMouseEntered(value -> {
			btnSalvar.setStyle("-fx-background-color:#000000; -fx-border-color:  #ffffff; -fx-border-radius: 20; -fx-background-radius: 20; -fx-text-fill: #ffffff");
		});
		btnSalvar.setOnMouseExited(value -> {
			btnSalvar.setStyle("-fx-background-color:  #b3d37a; -fx-border-color: #414247; -fx-border-radius: 20");
		});
	}
}