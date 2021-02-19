package br.com.cesarshiba.leiautesmarciasoares;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FrmLogo implements Initializable{

	@FXML
	public ImageView imgLogo;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Image imagem = new Image(MainClass.caminho() +"/logo marcia.png");
		imgLogo.setImage(imagem);
	}

}
