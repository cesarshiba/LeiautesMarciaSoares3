package br.com.cesarshiba.leiautesmarciasoares;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainClass extends Application {

	//identifica primeira execução para abrir com splash
	public static boolean loadSplash = false;
	//identifica cliente selecionado
    public static int idCliente = 0;

	/*
	 * chave para gerar .jar em produção
	 * devido localização dos formulários e 
	 * arquivos auxiliares em pastas diferentes
	 */
	private static boolean PRODUCAO = false;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent parentRoot = FXMLLoader.load(getClass().getResource(caminho() +"/frmPrincipal.fxml"));
			primaryStage.setTitle("Dra. Márcia Soares");
			primaryStage.getIcons().add(new Image("logo marcia.png"));
			primaryStage.setResizable(true);
			primaryStage.setMaximized(true);
			primaryStage.setIconified(false);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.setScene(new Scene(parentRoot));
			primaryStage.show();
		} catch (Exception e) {
			System.out.println("Erro:"+ e);
			System.exit(0);
		}
	}
	
	public static String caminho() {
		if(PRODUCAO) {
			return "/resources";
		}
		return "";
	}
}
